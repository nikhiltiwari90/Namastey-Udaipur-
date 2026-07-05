# Namastey Udaipur 🪔
### Culturally-Personalized Travel Companion — Pilot: Udaipur, Rajasthan

> *"A Tamil traveler visiting Udaipur shouldn't feel like a foreigner in their own country. They should feel like a guest who was expected."*

**Author:** Nikhil Tiwari — Co-Founder & Product Lead
**Pilot Period:** March 2015 – January 2016
**Platform:** Android (Java, API 16+) + Web
**Copyright © 2015–2026 Nikhil Tiwari. All Rights Reserved. See LICENSE.md.**

---

## The Origin Story

In 2014, I traveled across Europe with a group of 80 people — 60 of whom were elderly, Hindi-speaking Indians who had saved years for this trip. They were excited. They were curious. And they were completely lost.

Every sign was in English. Every museum audio guide was in English. Every restaurant menu was in English. They couldn't read what they were seeing, couldn't understand what they were eating, couldn't feel the place they had traveled thousands of kilometres to experience.

I came back to Udaipur and saw the same problem in reverse. Tourists from Tamil Nadu, Bengal, Punjab arriving in one of India's most beautiful cities — with no app, no guide, and no infrastructure that spoke to them in their language. Not just translated. Actually theirs.

**That's when Namastey Udaipur was born.**

---

## What We Built

A city-wide tourism discovery and booking platform — Android + Web — that personalised the entire travel experience based on the visitor's cultural and linguistic identity.

| Feature | What It Does |
|---|---|
| **Mother Tongue UI** | Full app experience in user's language from screen 1 |
| **Cultural Food Filtering** | Restaurants ranked by reviews from your own region |
| **Regional Hotel Matching** | Hotels filtered by cultural amenities (filter coffee, South Indian breakfast) |
| **Community-Ranked Suggestions** | Recommendations ranked by travelers from your state |
| **Local Guide Booking** | Book AIESEC-verified local guides who speak your language |
| **Event Discovery** | Local festivals and heritage events explained in your language |
| **Proximity Audio Guide** *(v2 planned)* | Phone auto-plays cultural narration as you walk near heritage sites |

---

## The AIESEC Content Moat

The hardest problem in cultural personalization is cold start — who creates the first authentic Tamil recommendations for Udaipur?

We solved this through a partnership with **AIESEC India** — a global youth volunteer network with members from every Indian state and dozens of international regions. AIESEC volunteers:

- Curated and authenticated initial regional content
- Validated "what people from my region actually like"
- Seeded the review corpus before organic reviews existed
- Provided the first pool of verified Tamil and Bengali-speaking local guides

This gave us a **content network no competitor could replicate quickly.**

---

## Traction (10-Month Pilot)

| Metric | Value |
|---|---|
| App Installs | 107 (Android + Web) |
| Active Languages | 3 (Tamil, Bengali, Hindi dominant) |
| POIs Curated | ~247 restaurants, hotels, attractions |
| Guide Bookings Confirmed | 8 (~₹1,040 revenue) |
| D7 Retention | 18% overall · 22% Tamil cohort |
| CCTC Rate | 24% (target: 30%) |

---

## Audio Guide — v2.0 Vision

> Walk near City Palace. Your phone vibrates. A notification: *"Tamil audio guide ready — 5 min narration."* You tap. The city speaks to you in your language. Just like the headset at a museum — but free, automatic, and yours.

The Proximity-Triggered Audio Guide uses Android Geofencing API to detect when a traveler approaches a heritage site (50m radius) and automatically fires a notification to play a pre-downloaded Tamil/Bengali/Hindi narration. Fully offline. No streaming needed.

**See:** `docs/AudioGuide_PRD.md` · `engineering/AudioGuide_Architecture.md`

---

## Repository Structure — 31 Files

```
namastey-udaipur/
│
├── README.md                              ← You are here
├── LICENSE.md                             ← © Nikhil Tiwari. All rights reserved.
├── SETUP_GUIDE.md                         ← GitHub publish instructions
│
├── docs/                                  ── Product Documents
│   ├── PRD.md                             ← Full Product Requirements Document
│   ├── AudioGuide_PRD.md                  ← v2.0 Proximity Audio Guide PRD
│   ├── WorkingBackwards.md                ← Amazon-style PR-FAQ
│   ├── ProductStrategy.md                 ← Vision, mission, RICE prioritization
│   ├── OKRs.md                            ← Quarterly OKRs with actual vs target
│   ├── Roadmap.md                         ← MVP → v1.1 → v2.0 → 2026
│   ├── SprintLog.md                       ← 11-sprint execution log with cuts
│   ├── UserStoryMap.md                    ← Full story map across user journey
│   ├── JTBD_and_DecisionLog.md            ← Jobs To Be Done + key decisions
│   ├── CustomerResearch_Personas.md       ← 4 personas + research methodology
│   ├── CompetitiveAnalysis.md             ← TripAdvisor / Google / MakeMyTrip
│   ├── GTM.md                             ← Go-to-Market strategy
│   ├── RiskRegister.md                    ← Full risk inventory with outcomes
│   └── Postmortem.md                      ← Honest 10-month retrospective
│
├── analytics/                             ── Data & Metrics
│   ├── KPITree.md                         ← North Star + full KPI tree
│   ├── FunnelAnalysis.md                  ← AARRR funnel with drop-off analysis
│   └── SQLAnalysis.md                     ← 7 production SQL queries
│
├── engineering/                           ── Technical Documentation
│   ├── Architecture.md                    ← System design: 2015 + 2026
│   ├── APIDesign.md                       ← Full REST API spec with responses
│   ├── AndroidCodeSamples.java            ← 6 core Java files (real MVP code)
│   ├── AudioGuide_Architecture.md         ← Geofence + audio system design
│   ├── AIRoadmap.md                       ← 2026 AI-native rebuild vision
│   └── TechnicalTradeoffs.md              ← Every build decision + rationale
│
├── design/                                ── Design Assets
│   ├── figma-specs/
│   │   └── DesignSpec.md                  ← Full design system + component library
│   └── wireframes/
│       └── WireframeAnnotations.md        ← Screen-by-screen design rationale
│
├── experiments/                           ── Experimentation
│   └── ExperimentationLog.md              ← 5 experiments: hypothesis → result
```

---

## Tech Stack (2015 MVP)

| Layer | Technology |
|---|---|
| Android App | Java · Android SDK API 16+ · Noto Sans fonts for regional scripts |
| Web App | PHP · Bootstrap · jQuery |
| Backend | LAMP Stack (Linux · Apache · MySQL · PHP) |
| Maps | Google Maps Android API v2 |
| Analytics | Firebase Analytics (added v1.1) |
| Geofencing *(v2)* | Google Play Services Geofencing API |
| Audio *(v2)* | Android MediaPlayer API · 64kbps MP3 · offline-first |
| Content | Manually curated + AIESEC volunteer network |

---

## What Failed (And What I Learned)

| Failure | Root Cause | Fix |
|---|---|---|
| App lag in old city | No offline-first architecture | SQLite city pack download, v1.1 |
| Thin content early on | UGC submission not shipped in MVP | In-app review — finally shipped Sprint 11 |
| No funnel visibility | Zero instrumentation at launch | Firebase Analytics, Sprint 6 |
| Guide booking friction | Phone callback too slow | WhatsApp confirmation, Sprint 5 |
| Content ceiling at 247 POIs | AIESEC bandwidth capped | UGC loop should have been MVP |

---

## 2026 AI Vision

| Dimension | 2015 | 2026 |
|---|---|---|
| Personalization | Manual language dropdown | AI-inferred cultural profile from behavior |
| Content | AIESEC manual seeding | LLM-bootstrapped + community validated |
| Recommendations | Static filtered lists | Real-time ML ranking by cultural cohort |
| Audio Guide | Not built | Geofence-triggered · offline · 22 languages |
| Trip Planning | None | RAG + LLM cultural itinerary generator |
| Languages | 4 | All 22 scheduled Indian languages |

---

## Quick Start — GitHub

See `SETUP_GUIDE.md` for step-by-step instructions to publish this repository.

**Contact:** nikhil.tiwari@namasteyudaipur.com · [linkedin.com/in/nikhiltiwari](https://linkedin.com/in/nikhiltiwari)

---

*© 2015–2026 Nikhil Tiwari. All Rights Reserved. Unauthorized implementation of product concepts strictly prohibited. See LICENSE.md.*
