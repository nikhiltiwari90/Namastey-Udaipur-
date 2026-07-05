# Analytics: SQL Analysis & Event Schema
## Namastey Udaipur
*Author: Nikhil Tiwari | Product Analytics Lead*

---

## Event Schema (Firebase Analytics — v1.1 Design)

All events follow the naming convention: `noun_verb` or `screen_action`

```sql
-- EVENTS TABLE SCHEMA
CREATE TABLE events (
    event_id        VARCHAR(36) PRIMARY KEY,
    event_name      VARCHAR(100) NOT NULL,
    user_id         VARCHAR(36),
    session_id      VARCHAR(36),
    timestamp       TIMESTAMP NOT NULL,
    platform        ENUM('android', 'web'),
    app_version     VARCHAR(20),
    -- Cultural context
    user_language   VARCHAR(10),   -- 'ta', 'bn', 'hi', 'en', etc.
    user_state      VARCHAR(50),   -- 'Tamil Nadu', 'West Bengal', etc.
    -- Event parameters (JSON)
    params          JSON,
    -- Device context
    device_model    VARCHAR(100),
    os_version      VARCHAR(20),
    connectivity    ENUM('wifi', '4g', '3g', '2g', 'offline'),
    INDEX idx_user (user_id),
    INDEX idx_event_name (event_name),
    INDEX idx_timestamp (timestamp),
    INDEX idx_language (user_language)
);

-- KEY EVENTS
-- app_launched          { first_launch: bool }
-- language_selected     { language: str, source: 'onboarding'|'settings' }
-- state_selected        { state: str }
-- screen_viewed         { screen_name: str }
-- poi_viewed            { poi_id: str, poi_type: str, has_regional_reviews: bool }
-- regional_review_read  { poi_id: str, review_language: str }
-- guide_profile_viewed  { guide_id: str }
-- guide_booking_tapped  { guide_id: str }
-- booking_confirmed     { guide_id: str, booking_value: int }
-- review_submitted      { poi_id: str, rating: int, language: str }
-- search_performed      { query: str, query_language: str, result_count: int }
-- content_saved         { poi_id: str, poi_type: str }
-- offline_state_shown   { screen: str }
-- session_ended         { duration_seconds: int, screens_visited: int }
-- app_crashed           { screen: str, error: str }
```

---

## SQL Analysis Queries

### Query 1: Language Adoption Funnel

```sql
-- How many users complete language selection vs. skip?
-- And what language do they choose?

SELECT
    user_language,
    COUNT(DISTINCT user_id)                                    AS users_selected,
    ROUND(COUNT(DISTINCT user_id) * 100.0 / SUM(COUNT(DISTINCT user_id)) OVER(), 1) AS pct_of_total
FROM events
WHERE event_name = 'language_selected'
  AND DATE(timestamp) BETWEEN '2015-03-01' AND '2016-01-17'
GROUP BY user_language
ORDER BY users_selected DESC;

-- Expected output:
-- ta (Tamil)    → 38%
-- hi (Hindi)    → 31%
-- bn (Bengali)  → 22%
-- en (English)  → 9%
```

---

### Query 2: Onboarding Funnel — Drop-off Analysis

```sql
-- Where do users drop off in the onboarding flow?

WITH funnel_steps AS (
    SELECT
        user_id,
        MAX(CASE WHEN event_name = 'app_launched'       THEN 1 ELSE 0 END) AS launched,
        MAX(CASE WHEN event_name = 'language_selected'  THEN 1 ELSE 0 END) AS lang_selected,
        MAX(CASE WHEN event_name = 'screen_viewed'
                  AND JSON_EXTRACT(params, '$.screen_name') = 'home'
                                                         THEN 1 ELSE 0 END) AS reached_home,
        MAX(CASE WHEN event_name = 'poi_viewed'          THEN 1 ELSE 0 END) AS viewed_poi,
        MAX(CASE WHEN event_name IN ('guide_booking_tapped',
                                     'review_submitted',
                                     'content_saved')   THEN 1 ELSE 0 END) AS cultural_action
    FROM events
    WHERE DATE(timestamp) BETWEEN '2015-03-01' AND '2016-01-17'
    GROUP BY user_id
)
SELECT
    SUM(launched)        AS step1_launched,
    SUM(lang_selected)   AS step2_language,
    ROUND(SUM(lang_selected)    * 100.0 / NULLIF(SUM(launched), 0), 1)    AS lang_cvr,
    SUM(reached_home)    AS step3_home,
    ROUND(SUM(reached_home)     * 100.0 / NULLIF(SUM(lang_selected), 0), 1) AS home_cvr,
    SUM(viewed_poi)      AS step4_poi,
    ROUND(SUM(viewed_poi)       * 100.0 / NULLIF(SUM(reached_home), 0), 1) AS poi_cvr,
    SUM(cultural_action) AS step5_cctc_action,
    ROUND(SUM(cultural_action)  * 100.0 / NULLIF(SUM(viewed_poi), 0), 1)   AS cctc_cvr
FROM funnel_steps;
```

---

### Query 3: North Star Metric — CCTC Rate

```sql
-- Culturally Comfortable Trip Completions
-- Definition: non-default language + 3+ sessions + 1 cultural action

WITH user_sessions AS (
    SELECT
        user_id,
        COUNT(DISTINCT session_id)                                    AS total_sessions,
        MIN(timestamp)                                                AS first_seen,
        MAX(timestamp)                                                AS last_seen
    FROM events
    GROUP BY user_id
),
user_language AS (
    SELECT user_id, user_language
    FROM events
    WHERE event_name = 'language_selected'
    GROUP BY user_id, user_language
),
cultural_actions AS (
    SELECT DISTINCT user_id
    FROM events
    WHERE event_name IN ('guide_booking_tapped', 'review_submitted',
                         'content_saved', 'booking_confirmed')
)
SELECT
    COUNT(DISTINCT us.user_id)                      AS total_users,
    COUNT(DISTINCT CASE
        WHEN ul.user_language NOT IN ('en', 'hi')
         AND us.total_sessions >= 3
         AND ca.user_id IS NOT NULL
        THEN us.user_id END)                         AS cctc_users,
    ROUND(COUNT(DISTINCT CASE
        WHEN ul.user_language NOT IN ('en', 'hi')
         AND us.total_sessions >= 3
         AND ca.user_id IS NOT NULL
        THEN us.user_id END) * 100.0
        / NULLIF(COUNT(DISTINCT us.user_id), 0), 1) AS cctc_rate_pct
FROM user_sessions us
LEFT JOIN user_language ul     ON us.user_id = ul.user_id
LEFT JOIN cultural_actions ca  ON us.user_id = ca.user_id;
```

---

### Query 4: Retention Cohort — D1, D7, D30

```sql
-- Day-1, Day-7, Day-30 retention by language cohort

WITH first_launch AS (
    SELECT user_id, MIN(DATE(timestamp)) AS cohort_date
    FROM events
    WHERE event_name = 'app_launched'
    GROUP BY user_id
),
user_lang AS (
    SELECT user_id, user_language
    FROM events
    WHERE event_name = 'language_selected'
    GROUP BY user_id, user_language
),
activity AS (
    SELECT DISTINCT user_id, DATE(timestamp) AS activity_date
    FROM events
)
SELECT
    ul.user_language,
    COUNT(DISTINCT fl.user_id)                     AS cohort_size,
    -- D1 retention
    ROUND(COUNT(DISTINCT CASE
        WHEN a1.activity_date = DATE_ADD(fl.cohort_date, INTERVAL 1 DAY)
        THEN fl.user_id END) * 100.0
        / NULLIF(COUNT(DISTINCT fl.user_id), 0), 1) AS d1_retention_pct,
    -- D7 retention
    ROUND(COUNT(DISTINCT CASE
        WHEN a7.activity_date BETWEEN DATE_ADD(fl.cohort_date, INTERVAL 6 DAY)
                                  AND DATE_ADD(fl.cohort_date, INTERVAL 8 DAY)
        THEN fl.user_id END) * 100.0
        / NULLIF(COUNT(DISTINCT fl.user_id), 0), 1) AS d7_retention_pct,
    -- D30 retention
    ROUND(COUNT(DISTINCT CASE
        WHEN a30.activity_date BETWEEN DATE_ADD(fl.cohort_date, INTERVAL 28 DAY)
                                   AND DATE_ADD(fl.cohort_date, INTERVAL 32 DAY)
        THEN fl.user_id END) * 100.0
        / NULLIF(COUNT(DISTINCT fl.user_id), 0), 1) AS d30_retention_pct
FROM first_launch fl
LEFT JOIN user_lang ul  ON fl.user_id = ul.user_id
LEFT JOIN activity a1   ON fl.user_id = a1.user_id
LEFT JOIN activity a7   ON fl.user_id = a7.user_id
LEFT JOIN activity a30  ON fl.user_id = a30.user_id
GROUP BY ul.user_language
ORDER BY cohort_size DESC;
```

---

### Query 5: Content Gap Analysis — Which POIs Need More Regional Reviews?

```sql
-- Find POIs with fewer than 3 reviews from a specific language community
-- Priority list for AIESEC content team

SELECT
    p.poi_id,
    p.name_en,
    p.category,
    COUNT(CASE WHEN r.language = 'ta' THEN 1 END) AS tamil_reviews,
    COUNT(CASE WHEN r.language = 'bn' THEN 1 END) AS bengali_reviews,
    COUNT(CASE WHEN r.language = 'hi' THEN 1 END) AS hindi_reviews,
    COUNT(r.review_id)                             AS total_reviews,
    -- Priority score: weight by POI view frequency
    SUM(pv.view_count) * (3 - LEAST(COUNT(CASE WHEN r.language = 'ta' THEN 1 END), 3)) AS tamil_content_gap_score
FROM poi p
LEFT JOIN review r ON p.poi_id = r.poi_id
LEFT JOIN (
    SELECT JSON_EXTRACT(params, '$.poi_id') AS poi_id,
           COUNT(*) AS view_count
    FROM events
    WHERE event_name = 'poi_viewed'
    GROUP BY poi_id
) pv ON p.poi_id = pv.poi_id
GROUP BY p.poi_id, p.name_en, p.category
HAVING tamil_reviews < 3
ORDER BY tamil_content_gap_score DESC
LIMIT 20;
```

---

### Query 6: Guide Booking Funnel

```sql
-- Profile view → booking tapped → booking confirmed

WITH guide_funnel AS (
    SELECT
        JSON_EXTRACT(params, '$.guide_id') AS guide_id,
        SUM(CASE WHEN event_name = 'guide_profile_viewed'  THEN 1 ELSE 0 END) AS profile_views,
        COUNT(DISTINCT CASE WHEN event_name = 'guide_booking_tapped' THEN user_id END) AS booking_taps,
        COUNT(DISTINCT CASE WHEN event_name = 'booking_confirmed'    THEN user_id END) AS confirmed_bookings
    FROM events
    GROUP BY guide_id
)
SELECT
    guide_id,
    profile_views,
    booking_taps,
    confirmed_bookings,
    ROUND(booking_taps     * 100.0 / NULLIF(profile_views, 0), 1) AS view_to_tap_pct,
    ROUND(confirmed_bookings * 100.0 / NULLIF(booking_taps, 0), 1) AS tap_to_confirm_pct,
    ROUND(confirmed_bookings * 100.0 / NULLIF(profile_views, 0), 1) AS overall_cvr_pct
FROM guide_funnel
ORDER BY profile_views DESC;
```

---

### Query 7: Crash Analysis — Connectivity vs. Non-Connectivity Crashes

```sql
-- What % of crashes happen on weak connectivity?
-- Informs offline-first architecture priority

SELECT
    connectivity,
    COUNT(*) AS crash_count,
    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER(), 1) AS pct_of_crashes,
    COUNT(DISTINCT user_id) AS affected_users
FROM events
WHERE event_name = 'app_crashed'
GROUP BY connectivity
ORDER BY crash_count DESC;

-- Hypothesis: 2g + offline will account for 60%+ of crashes
-- This would be the evidence base for making offline architecture P0 in v1.1
```

---

## Dashboard Specifications

### Weekly Product Dashboard — Data Requirements

| Metric | Query | Refresh Frequency |
|---|---|---|
| CCTC Rate (week) | Query 3, filtered to last 7 days | Weekly |
| Language adoption breakdown | Query 1 | Weekly |
| Onboarding funnel | Query 2 | Weekly |
| Guide booking conversion | Query 6 | Weekly |
| Content gap top 10 | Query 5 | Weekly |
| D1/D7 cohort for new users | Query 4 | Weekly |
| Crash rate by connectivity | Query 7 | Daily |

---

*SQL written for MySQL 5.7. JSON_EXTRACT syntax may need adjustment for older MySQL versions.*
*All queries are illustrative of the analytics design; actual execution requires the events table to be populated by Firebase Analytics export.*
