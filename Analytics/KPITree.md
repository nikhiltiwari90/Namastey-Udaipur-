# North Star Metric & KPI Framework
## Namastey Udaipur — Analytics Architecture
*Version 1.0 | Product Analytics Lead: Nikhil Tiwari*

---

## The North Star Metric

### "Culturally Comfortable Trip Completions" (CCTC)

**Definition:**
A user who:
1. Onboarded with a non-default language (Tamil / Bengali / Punjabi / etc.)
2. Opened the app at least 3 times during their trip window (arrival → departure)
3. Completed at least one cultural action: restaurant visit check-in OR hotel booking inquiry OR guide booking

**Why this metric:**

Most travel apps optimize for bookings. We optimize for *comfortable travel outcomes*. A user who books a hotel through us but spends their trip miserable because nothing felt culturally right is a failure. A user who found filter coffee on day one, booked a Tamil-speaking guide, and came back to review their experience — that's our success.

CCTC captures:
- Activation (language onboarding)
- Engagement (multiple sessions during trip)
- Outcome (cultural action completed)

**Target (MVP pilot):** 30% of installs become CCTCs within 30 days of download

---

## KPI Tree

```
NORTH STAR: Culturally Comfortable Trip Completions
│
├── ACQUISITION
│   ├── App Installs (Android + Web signups)
│   ├── Install Source Breakdown (cold email / AIESEC referral / organic)
│   ├── Language Distribution at Onboarding
│   │   ├── % Tamil onboarders
│   │   ├── % Bengali onboarders
│   │   ├── % Hindi onboarders
│   │   └── % English onboarders
│   └── Day-0 Activation Rate (completed language selection)
│
├── ACTIVATION
│   ├── Onboarding Completion Rate (language selected + first POI viewed)
│   ├── Time to First Cultural Action (first regional restaurant viewed)
│   ├── Profile Completion Rate (state/region selected)
│   └── First Session Depth (screens visited in session 1)
│
├── ENGAGEMENT
│   ├── Sessions per Trip Window (target: 3+)
│   ├── DAU/WAU during peak tourist season
│   ├── Feature Adoption
│   │   ├── Restaurant Discovery Usage Rate
│   │   ├── Guide Booking Click Rate
│   │   ├── Event Discovery Open Rate
│   │   └── Map Navigation Launch Rate
│   ├── Content Consumption
│   │   ├── Average POIs viewed per session
│   │   ├── Review read depth (scroll completion)
│   │   └── Save / Bookmark rate
│   └── Search Behavior
│       ├── Search queries in non-English language
│       └── Search-to-POI conversion rate
│
├── CONVERSION (Monetization)
│   ├── Guide Booking Request Rate (% of users who tap "Book Guide")
│   ├── Guide Booking Confirmation Rate (requests → confirmed bookings)
│   ├── Hotel Inquiry Rate
│   ├── Restaurant Reservation Rate (future)
│   └── Revenue per Booking (guide commission: 15-20%)
│
├── RETENTION
│   ├── Return Trip Rate (user installs again for next Udaipur visit)
│   ├── Referral Rate (shared app with travel companion)
│   ├── Review Submission Rate (% of users who leave a regional review)
│   └── Cross-City Demand Signal (users who ask "when is this for Jaipur?")
│
└── CONTENT HEALTH (Unique to our model)
    ├── Regional Review Coverage (% of listed POIs with 3+ regional reviews)
    ├── Content Freshness (% of reviews < 6 months old)
    ├── Language Coverage Completeness (% of POI names translated)
    └── AIESEC Content Validation Rate
```

---

## Funnel Analysis

### Acquisition → CCTC Funnel

| Stage | Event | Target Conversion | Actual (MVP) |
|---|---|---|---|
| Awareness | App store page view / cold email open | — | ~40% email open rate |
| Install | App downloaded | 100% baseline | 100+ installs |
| Onboarding Start | App launched | 85% | ~80% (est.) |
| Language Selected | Non-default language chosen | 60% | ~55% (est.) |
| First POI Viewed | Restaurant/hotel card opened | 70% | ~65% (est.) |
| Cultural Action | Regional restaurant saved/guide tapped | 40% | ~30% (est.) |
| **CCTC** | **3 sessions + cultural action in trip window** | **30%** | **~25% (est.)** |

*Note: Actual instrumentation was limited in MVP. Estimates based on qualitative user feedback and session logs. Proper event instrumentation is a P0 for v1.1.*

---

## Cohort Analysis Design

### D1 / D7 / D30 Retention Framework

**D1 Retention** (came back next day)
- Target: 35%
- Signal: User bookmarked a restaurant or saved a guide profile
- Hypothesis: Users who save content on day 1 return to use it

**D7 Retention** (came back within a week)
- Target: 20%
- Signal: User is in active trip window
- Hypothesis: 5-7 day trips mean D7 is peak utility window

**D30 Retention** (came back after 30 days)
- Target: 8%
- Signal: Planning next trip OR recommending to someone else
- Hypothesis: Post-trip reviewers and trip planners are our D30 users

---

## Guardrail Metrics

These metrics must NOT deteriorate even when optimizing for CCTC:

| Guardrail | Threshold | Why |
|---|---|---|
| Content Accuracy Complaints | < 2% of POI views | Wrong cultural info destroys trust permanently |
| App Crash Rate | < 1% of sessions | Crashes in low-connectivity areas kill the experience |
| Onboarding Drop Rate | < 20% abandon before language selection | Language selection IS the product |
| Guide Booking Cancellation Rate | < 15% | High cancellation signals guide quality issues |

---

## A/B Testing Roadmap

### Experiment 1: Onboarding Language Prompt
**Hypothesis:** Showing language selection as a full-screen first step (vs. buried in settings) will increase non-English language adoption by 25%
**Metric:** Language selection rate at onboarding
**Status:** Shipped as full-screen first step in MVP (no holdout — too small sample)

### Experiment 2: Review Display Format
**Hypothesis:** Showing "X Tamil travelers reviewed this" prominently on restaurant cards will increase tap rate vs. showing only aggregate star rating
**Metric:** Restaurant card tap rate, segmented by language
**Status:** Planned for v1.1

### Experiment 3: Guide Booking Entry Point
**Hypothesis:** Adding "Book a Tamil-speaking guide" CTA on home screen (vs. only in guide section) will increase guide booking funnel entry by 40%
**Metric:** Guide booking click rate
**Status:** Planned for v1.1

---

## Instrumentation Events (MVP Event Schema)

```
app_launched { language, device_model, os_version, connectivity_type }
language_selected { language_code, source: onboarding|settings }
region_selected { state_code, optional: true }
poi_viewed { poi_id, poi_type: restaurant|hotel|attraction, language, has_regional_reviews }
regional_review_read { poi_id, review_language, user_language }
guide_profile_viewed { guide_id, guide_languages }
guide_booking_initiated { guide_id, tour_type }
guide_booking_confirmed { guide_id, booking_value }
content_saved { poi_id, poi_type }
search_performed { query_language, result_count }
session_ended { session_duration, screens_visited, connectivity_issues: bool }
app_crashed { screen, error_type, connectivity_type }
```

---

## Dashboard Requirements

### Real-Time Operations Dashboard
- Active sessions (map view of Udaipur — where are users right now?)
- Current app health (crash rate, load time by city zone)
- Guide booking queue (pending confirmations)

### Weekly Product Dashboard
- CCTC rate by language cohort
- Funnel conversion week-over-week
- Content coverage gaps (POIs with < 3 regional reviews)
- Top search queries with no results

### Monthly Strategy Dashboard
- Language adoption trends
- Revenue from guide commissions
- Cross-city demand signals (users asking about other cities)
- AIESEC content contribution rate

---

*Analytics framework designed to be implemented in Firebase Analytics (Android) + custom backend event logging (Web)*
*Full SQL analysis examples: see /analytics/sql-analysis.md*
