# Jobs To Be Done (JTBD) Framework
## Namastey Udaipur
*Author: Nikhil Tiwari | Product Discovery | March 2015*

---

## Core JTBD Statement

**When** a domestic Indian tourist arrives in Udaipur from a non-Hindi/non-English-speaking state,

**They want to** feel culturally at home — finding food they trust, accommodation that understands their needs, and people who speak their language —

**So they can** fully enjoy the experience they saved for and traveled to have, without the anxiety of cultural alienation in their own country.

---

## JTBD Hierarchy

### Job Level 1 — The Main Job
*"Help me have a comfortable, culturally familiar trip in an unfamiliar city"*

### Job Level 2 — Supporting Jobs

| Job | Trigger | Desired Outcome |
|---|---|---|
| Find food I trust | First meal in new city | Eat without anxiety, feel nourished |
| Book a hotel that gets me | Arrival planning | Wake up to filter coffee, feel at home |
| Navigate the city confidently | First morning in city | Don't feel lost, see the right things |
| Get help in my language | Any moment of confusion | Resolve the issue without humiliation |
| Share the experience with my community | Post-trip or during trip | Others from my region benefit from my knowledge |

### Job Level 3 — Micro Jobs (Feature-level)
- See reviews written by people like me (same region/language)
- Know whether this restaurant has food I grew up with
- Book a guide who can explain things the way my family will understand
- Download content before I leave hotel WiFi
- Read an event description in my language

---

## Why Existing Solutions Fail Each Job

| Job | Why TripAdvisor fails | Why Google Maps fails | Why Local Agent fails |
|---|---|---|---|
| Find food I trust | Reviews by culturally dissimilar travelers | No cultural filter on reviews | Expensive, not scalable, inconsistent |
| Book right hotel | No cultural amenity filtering | Room data, not cultural context | Knows a few hotels only |
| Navigate confidently | English content only | Maps in English/Hindi, no Tamil |  Personal navigation help, not scalable |
| Get help in my language | No help system | No help system | Not always available |
| Share with community | Generic community, not regional | No regional community | N/A |

---

## Emotional JTBD (The Jobs We Don't Advertise But Matter Most)

1. **"Make my parents' first big trip feel like I planned it perfectly"** — The adult child traveling with elderly parents carries enormous emotional weight. If the trip is uncomfortable, they feel they failed.

2. **"Don't make me feel like a second-class tourist in my own country"** — The implicit humiliation of a Tamil speaker in Rajasthan who cannot read signs, understand guides, or find familiar food is a real emotional pain that no existing product addresses.

3. **"Let me be the one who discovers the hidden gems for my community"** — Early adopters and travel enthusiasts want to be the first from their region to review a place, to be known in their community as someone with good travel taste.

---

# Decision Log
## Key Product Decisions — Namastey Udaipur

---

## Decision 001: Android-first, not cross-platform

**Date:** March 2015
**Decision Maker:** Nikhil Tiwari
**Decision:** Build native Android (Java) rather than Cordova/PhoneGap hybrid or web-only

**Context:** Limited engineering bandwidth (solo technical founder). Three options: native Android, cross-platform hybrid, or mobile web only.

**Options Considered:**
- Native Android: Best performance, higher build cost, Android-only
- Cordova hybrid: Code reuse, worse performance on low-end devices, known reliability issues in 2015
- Mobile web only: No install barrier, worse offline capability

**Decision Rationale:** 85%+ of Indian smartphone users on Android in 2015 (IDC data). Target demographic (domestic tourists from Tier 2/3 cities) overwhelmingly on Android. Native performance critical for maps and offline caching. Hybrid tooling in 2015 not mature enough for reliable offline support.

**Tradeoff Accepted:** iOS users excluded from MVP. Acceptable given market demographics.

**Outcome:** Correct decision. No iOS user complaints; Android target user base confirmed.

---

## Decision 002: Language selection as mandatory first screen

**Date:** March 2015
**Decision Maker:** Nikhil Tiwari
**Decision:** Language selection is the first screen after app launch — not optional, not in settings

**Context:** Design debate between two approaches:
- Option A: Auto-detect language from device locale, let user change in settings
- Option B: Explicit language selection screen on first launch

**Decision Rationale:** Auto-detect from device locale is unreliable in India — many users have English set as device language regardless of their actual preference. More importantly, the language selection IS the product moment — it signals to the user that this app was built for them specifically. It's the "aha moment" we want users to have immediately.

**Tradeoff Accepted:** Adds one step to onboarding. Worth it for product signal strength.

**Outcome:** Users who completed language selection showed higher engagement than those who skipped (anecdotal; no formal instrumentation).

---

## Decision 003: AIESEC partnership over scraping or paid data

**Date:** February 2015
**Decision Maker:** Nikhil Tiwari + Co-Founder
**Decision:** Content seeded exclusively through AIESEC volunteer network, not scraped from MakeMyTrip/JustDial

**Context:** Three content sourcing options:
- Scrape existing platforms: Fast, scalable, legally risky, culturally generic
- Paid data vendors: Expensive, no cultural intelligence
- AIESEC volunteer network: Slow, scalable only to volunteer bandwidth, but culturally authentic

**Decision Rationale:** Scraped data has no cultural context. A list of restaurants scraped from JustDial doesn't tell a Tamil traveler whether the sambar tastes like home. We needed human judgment — specifically, judgment from people who understand what "good South Indian food" means to someone from Chennai. AIESEC's network of volunteers from every Indian state gave us exactly that.

**Tradeoff Accepted:** Slow initial content; limited to AIESEC bandwidth; volunteer reliability variable.

**What I'd change:** Should have built UGC submission in v1 simultaneously. AIESEC for seed content, UGC for growth.

---

## Decision 004: No payment gateway in MVP

**Date:** March 2015
**Decision Maker:** Nikhil Tiwari
**Decision:** Guide bookings via phone callback, not in-app payment

**Context:** Options were: integrate a payment gateway (Instamojo or CCAvenue, available in India in 2015) or use manual phone confirmation.

**Decision Rationale:** Payment gateway integration in 2015 India required: entity registration, bank account, merchant agreements, compliance documentation. For a pilot-phase product with 8-15 bookings, the overhead was disproportionate. Phone callback was a viable MVP flow given the low volume.

**Tradeoff Accepted:** Higher booking friction; leaky conversion funnel; couldn't measure accurately.

**What I'd change:** Even a simple WhatsApp Business message flow with manual UPI payment would have been faster than voice callbacks and more measurable. This was available by 2016.

---

*Decision log maintained throughout pilot. 4 major decisions documented above; day-to-day decisions in project notes.*
