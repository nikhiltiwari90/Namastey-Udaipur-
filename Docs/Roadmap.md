# Product Roadmap
## Namastey Udaipur — 2015 Pilot → 2026 Vision
*Author: Nikhil Tiwari | Living Document*

---

## Roadmap Principles

1. **Depth before breadth** — one city done well beats 10 cities done thin
2. **Community before features** — every feature decision asks "does this grow the community or extract from it?"
3. **Instrumentation before optimization** — never optimize what you can't measure
4. **Offline before expansion** — every new city requires offline-first architecture

---

## Phase 1 — MVP (March 2015 → June 2015)

**Goal:** Prove the thesis. Does cultural personalization change how travelers experience Udaipur?

| Feature | Status | Notes |
|---|---|---|
| Language selection onboarding (4 languages) | ✅ Shipped | Tamil, Bengali, Hindi, English |
| Culturally filtered restaurant discovery | ✅ Shipped | Filtered by reviewer's language/region |
| Regional community reviews | ✅ Shipped | AIESEC-seeded content |
| Hotel discovery with cultural amenity tags | ✅ Shipped | Filter coffee, South Indian breakfast, etc. |
| Google Maps integration | ✅ Shipped | Maps + navigation |
| Guide booking (phone callback) | ✅ Shipped | AIESEC-verified guides |
| Event discovery | ✅ Shipped | Manually curated |
| AIESEC content seed (~200 POIs) | ✅ Shipped | Initial content corpus |

**Thesis validated:** Yes, with caveats (connectivity gaps, content thinness)

---

## Phase 2 — Stabilize + Instrument (July 2015 → October 2015)

**Goal:** Fix the known failures. Start measuring what we didn't measure at launch.

| Feature | Status | Notes |
|---|---|---|
| Firebase Analytics instrumentation | ✅ Shipped | Core events only |
| Offline warning banner | ✅ Shipped | Honest UX for connectivity loss |
| Partial content caching improvement | ✅ Shipped | Saved POIs available offline |
| WhatsApp booking confirmation | ✅ Shipped | Replaced phone callback |
| In-app review submission | 🔴 Missed | Largest execution gap |
| Push notifications (Firebase) | 🔴 Missed | Bandwidth constraint |

---

## Phase 3 — Growth Loop (October 2015 → January 2016)

**Goal:** Activate the UGC flywheel. Community reviews become the growth engine.

| Feature | Status | Notes |
|---|---|---|
| Community review social sharing | ⚠️ Partial | Manual sharing, not automated |
| Language expansion: Punjabi, Marathi | 🔴 Not shipped | Content curation bandwidth |
| Post-trip review prompt | 🔴 Not shipped | Push notification not implemented |
| Cross-city demand signal capture | ⚠️ Partial | Manual "coming soon" page only |

**Pilot closed:** January 2016

---

## Phase 4 — Planned v2.0 (Q1 2016 — Not Executed)

*What would have been built with continued funding/team*

| Feature | Rationale |
|---|---|
| Full offline city pack download | Fix the core UX failure in old city areas |
| In-app payment gateway (Instamojo) | Remove manual booking friction |
| Hotel booking integration | Second revenue stream |
| Jaipur city expansion | Prove multi-city model |
| Community translation layer | Scale language coverage without proportional cost |
| Hotel SaaS pilot | B2B revenue stream test |

---

## 2026 AI-Native Rebuild Roadmap

*If building today with modern AI stack*

### Sprint 1 — Foundation (Months 1-2)
- React Native app (iOS + Android) + PWA
- Offline-first architecture from day one (SQLite + city packs)
- Firebase Analytics with full event schema
- AI cultural profile inference (on-device ML)

### Sprint 2 — Content Engine (Months 2-3)
- LLM cold-start content generation pipeline
- Community validation workflow
- Multilingual semantic search (LaBSE embeddings)
- UGC review submission with AI translation

### Sprint 3 — Intelligence Layer (Months 3-5)
- Personalized recommendation ranking model
- RAG-powered trip planner (conversational)
- Review summarization by regional cohort
- Contextual guide booking CTAs

### Sprint 4 — Scale (Months 5-8)
- Jaipur + Varanasi expansion (LLM-bootstrapped content)
- In-app marketplace for guide bookings (payments, ratings, calendar)
- Hotel SaaS white-label product MVP
- 10 Indian languages supported

### Sprint 5 — Platform (Months 8-12)
- All 22 scheduled Indian languages
- Pan-India expansion plan
- API product for hotel cultural communication
- B2B revenue stream active

---

## Roadmap Decisions: What Was Cut and Why

| Feature | Cut From | Reason |
|---|---|---|
| iOS app | MVP, v1.1, v2 | Android covers 85%+ target demographic; limited engineering bandwidth |
| In-app payment | MVP | Regulatory overhead disproportionate to pilot scale |
| Social features (friend activity) | All phases | Complex trust and identity problem; not core JTBD |
| Hotel review section | MVP | Restaurant discovery was higher frequency; hotel was v2 |
| Real-time guide availability | MVP | Requires guide-side app; too complex for v1 |
| International cities | All phases | Pan-India is the full opportunity; no reason to go international before that |

---

*Roadmap is a living prioritization document, not a contract. Priorities shift with evidence.*
