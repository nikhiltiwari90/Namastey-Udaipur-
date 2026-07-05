# Experimentation & Hypothesis Log
## Namastey Udaipur
*Author: Nikhil Tiwari | Product Analytics | 2015*

---

## Experimentation Philosophy

With 100+ installs in a pilot, formal A/B testing was not statistically viable. Instead, we used a structured hypothesis-driven approach: document the assumption, define the metric that would prove or disprove it, ship a version, observe, and record the learning.

This is the honest record of those experiments.

---

## Experiment Log

---

### EXP-001: Language Selection Placement

**Hypothesis:**
Placing language selection as the mandatory first screen (vs. inside Settings) will produce higher non-Hindi/English language adoption because it signals cultural relevance immediately.

**Method:** Shipped language-first onboarding as default. No holdout group (sample too small). Compared behavior of users who selected a non-default language vs. those who used English/Hindi.

**Metric:** Language selection completion rate at first launch; subsequent engagement by language cohort.

**Observation:**
- Users who selected Tamil or Bengali showed noticeably higher session depth and return rate (anecdotal — no formal instrumentation)
- 3 users changed language to English after initial selection — suggests some multilingual users prefer English UI even if they speak Tamil
- No users complained about the mandatory language selection step

**Learning:** Language-first placement was correct. The 3 who switched to English represents a multilingual segment we should design for — they want to SEE that Tamil is available, then may choose English for convenience.

**Decision:** Keep language-first; add "continue in English" secondary CTA for multilingual users.

**Confidence:** Medium (no control group; small sample)

---

### EXP-002: AIESEC Badge vs. No Badge on Guide Profiles

**Hypothesis:**
Displaying "AIESEC Verified" badge on guide profiles will increase guide booking inquiry rate by showing third-party credibility signal.

**Method:** All guides in MVP had AIESEC badge. Two guides added later (non-AIESEC) did not have badge. Compared inquiry rates informally.

**Metric:** Guide profile views → booking inquiry conversion.

**Observation:**
- AIESEC-badged guides received more inquiries relative to their profile view count
- One user explicitly mentioned "the verified badge made me feel safer booking"
- Non-badged guides received fewer inquiries despite similar ratings

**Learning:** Third-party trust signal matters for guide booking. For a market where guide quality is uncertain, AIESEC verification is a meaningful trust signal.

**Decision:** Keep AIESEC badge; expand to other verifiable trust signals (reviews from same language community, photo ID verification).

**Confidence:** Low-Medium (very small sample; confounded by guide personality differences)

---

### EXP-003: Offline Warning Banner

**Hypothesis:**
Showing a clear "weak signal — cached results" banner when connectivity drops will reduce user frustration compared to unexplained slow loads.

**Method:** Added orange warning banner in v1 (post-launch patch). Before patch, users experienced silent lag with no explanation.

**Metric:** User feedback sentiment on connectivity issues.

**Observation:**
- Pre-banner: 4 user complaints about "app broken" "app not working" in old city areas
- Post-banner: 1 complaint phrased as "why is only cached content showing?" — user understood the cause
- Net improvement in perceived reliability even though actual connectivity was unchanged

**Learning:** Honest communication about technical limitations improves trust more than hiding them. Users forgive constraints they understand.

**Decision:** Expand offline state communication; add "download city pack for offline use" CTA.

**Confidence:** Medium

---

### EXP-004: Featured Community Picks on Home Screen

**Hypothesis:**
Showing "Tamil travelers recommend today" section on home screen (vs. generic featured places) will increase tap rate to restaurant/hotel section.

**Method:** Added community-attributed section to home screen in v1.1 patch. Observed informally.

**Metric:** Home screen section tap rate (no formal instrumentation — observed via session notes).

**Observation:**
- Users consistently opened the community-attributed section first
- One user: "I liked that it said Tamil travelers — I knew it was for me"
- Generic "featured places" section below it got significantly fewer taps

**Learning:** Attribution to community is a stronger pull than editorial curation. "Tamil travelers recommend" > "Editor's picks".

**Decision:** Remove generic editorial section; double down on community-attributed surfaces everywhere.

**Confidence:** Low (informal observation only)

---

### EXP-005: WhatsApp vs. Phone Call for Guide Booking Confirmation

**Hypothesis:**
WhatsApp message confirmation (introduced in October 2015) will convert guide booking inquiries faster than voice callback, reducing drop-off.

**Method:** Switched from voice callback to WhatsApp message in October 2015. Compared booking completion rate before and after.

**Metric:** Booking inquiry → confirmed booking conversion rate.

**Observation:**
- Voice callback period: ~53% conversion (8 confirmed out of ~15 inquiries)
- WhatsApp period (2 months): ~70% conversion on smaller volume
- Response time: Voice callbacks averaged 3.2 hours; WhatsApp averaged 45 minutes

**Learning:** Asynchronous confirmation (WhatsApp) dramatically improved speed and conversion. Users comfortable confirming a booking without a phone call.

**Decision:** WhatsApp as default confirmation channel. Phone call as fallback.

**Confidence:** Medium (small absolute numbers; directionally strong)

---

## Hypotheses We Wanted to Test But Couldn't (Sample Size Limitations)

| Hypothesis | Why Blocked | Plan for v2 |
|---|---|---|
| Push notifications improve return rate | No push notification infrastructure in MVP | Firebase Cloud Messaging in v1.1 |
| Showing # of Tamil reviews on restaurant card increases tap rate | No A/B test infrastructure | Google Optimize in v2 |
| State selection (after language) improves recommendation quality | Feature not built in MVP | Build + test in v1.1 |
| Social sharing of reviews drives viral acquisition | No share feature in MVP | In-app share in v1.1 |

---

## Key Learning Across All Experiments

1. **Community attribution outperforms editorial curation** — "Tamil travelers say" beats "we recommend"
2. **Trust signals matter more than feature richness** — AIESEC badge drove more conversions than an additional guide feature would have
3. **Honest failure states build more trust than hidden failures** — show the offline banner, explain the lag
4. **Async > synchronous in booking flows** — WhatsApp beat phone calls on speed and conversion

---

*Experimentation limited by MVP instrumentation gaps. v1.1 priority: Firebase Analytics event schema to enable proper experiment measurement.*
