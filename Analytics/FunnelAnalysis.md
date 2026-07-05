# Funnel Analysis
## Namastey Udaipur — Acquisition → Activation → Retention → Revenue
*Author: Nikhil Tiwari | Product Analytics*

---

## Full AARRR Funnel

```
AWARENESS
    ↓
Cold email to regional tourist groups (~200 emails sent)
AIESEC India network broadcast
Hotel lobby QR cards (limited traction)

ACQUISITION                              Target    Actual (est.)
    ↓
Emails opened                            40%       ~40%  ✓
Clicked link / visited landing page      25%       ~22%
App installed (Android + Web)            100+      100+  ✓
Day-0 app launch                         85%       ~80%

ACTIVATION
    ↓
Language selection completed             70%       ~65%
Home screen reached with cultural mode   65%       ~60%
First POI (restaurant/hotel) viewed      60%       ~55%
Regional review read                     45%       ~40%
Content saved or guide tapped            35%       ~28%

RETENTION
    ↓
D1 retention (returned next day)         35%       ~28% (est.)
D7 retention (within trip window)        20%       ~18% (est.)
D30 retention (post-trip return)         8%        ~5%  (est.)

REVENUE
    ↓
Guide booking inquiry                    15%       ~12% of installs
Guide booking confirmed                  8%        ~7%  of installs
Est. revenue per booking (commission)    ₹120-160  ~₹130 avg
Total pilot revenue                      —         ~₹1,040 (8 bookings)

REFERRAL
    ↓
App shared with travel companion         10%       ~8% (est.)
Organic review submitted                 5%        ~3% (limited by no in-app submission)
Facebook group mention                   Unknown   Several confirmed anecdotally
```

---

## Funnel Drop-Off Analysis

### Biggest Drop: Activation → Guide Booking (Monetization)

**Drop from "regional review read" (40%) to "guide booking inquiry" (12%)**

Root causes:
1. Guide booking was hard to find — buried in navigation, not surfaced contextually
2. Phone callback confirmation added friction — users unsure if it would work
3. No social proof on guide profiles beyond AIESEC badge in early months
4. Some users found guides through direct AIESEC contact, bypassing the app

**Fix for v1.1:** Contextual guide CTA injected on heritage site POI pages ("Visiting City Palace? Book a Tamil-speaking guide who knows this site deeply") + WhatsApp confirmation flow.

---

### Second Biggest Drop: D7 → D30 Retention (18% → 5%)

**Expected, but lower than target**

Root causes:
1. App is inherently trip-scoped — user visits Udaipur for 4-5 days, then stops needing it
2. No post-trip engagement loop — no prompt to leave a review after returning home
3. No cross-city expansion — user wants Jaipur next but we don't have it

**Fix:** Push notification on trip day 4 — "Help the next Tamil traveler — leave your reviews before you leave Udaipur." Post-trip review prompt 48 hours after last session.

---

## Seasonal Funnel Analysis

Udaipur peak tourist season: October – March (winter)
Off-season: April – June (summer, very hot)

**Implication for metrics:**
- Monthly active users will show sharp seasonality — not a product failure
- Cohort analysis must be season-adjusted
- CCTC rate should be measured within 30-day windows of peak season, not annually

```
Estimated monthly installs by season:
Oct-Mar (peak):     15-20 installs/month
Apr-Jun (summer):   3-5 installs/month
Jul-Sep (monsoon):  5-8 installs/month
```

---

## Funnel Comparison: Languages

| Language | Onboarding Completion | D7 Retention | Guide Booking Rate |
|---|---|---|---|
| Tamil | ~70% | ~22% | ~9% |
| Bengali | ~65% | ~18% | ~6% |
| Hindi | ~60% | ~15% | ~4% |
| English | ~55% | ~12% | ~3% |

**Insight:** Tamil cohort shows highest engagement and monetization across every funnel stage. This validates Tamil travelers as the primary ICP. Hindi and English cohorts are lower-engagement — possibly already well-served by existing platforms; using us as supplement rather than primary tool.

---

## Key Funnel Optimization Priorities (v1.1)

| Priority | Intervention | Target Impact |
|---|---|---|
| P0 | Firebase instrumentation — measure everything | Eliminate guesswork; inform all v2 decisions |
| P0 | In-app review submission — activate UGC loop | Improve D7/D30 retention via contribution engagement |
| P1 | Contextual guide CTA on heritage site pages | +5pp guide booking inquiry rate |
| P1 | WhatsApp booking confirmation | +10pp guide booking conversion (tap → confirm) |
| P2 | Post-trip review prompt (48hr push notification) | +3pp D30 retention |
| P2 | "Coming soon: Jaipur" cross-city demand capture | Measure expansion demand; seed next city list |

---

*Funnel metrics are estimates based on partial session data and qualitative observation. Proper measurement requires Firebase Analytics implementation (v1.1 P0).*
