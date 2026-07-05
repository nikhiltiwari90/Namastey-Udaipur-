# System Architecture & Technical Decisions
## Namastey Udaipur — Engineering Documentation
*Author: Nikhil Tiwari, Co-Founder & Product-Tech Lead*
*Version: 1.0 (2015 MVP) + 2026 Evolution*

---

## 2015 MVP Architecture

### Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                     CLIENT LAYER                        │
│                                                         │
│  ┌──────────────────┐    ┌──────────────────────────┐  │
│  │   Android App    │    │      Web App             │  │
│  │   (Java, API 16) │    │  (PHP + Bootstrap + jQuery│  │
│  └────────┬─────────┘    └──────────┬───────────────┘  │
└───────────┼──────────────────────────┼─────────────────┘
            │                          │
            ▼                          ▼
┌─────────────────────────────────────────────────────────┐
│                    API / BACKEND LAYER                  │
│                                                         │
│         PHP REST API (custom, no framework)             │
│         JSON responses, language param in header        │
│                                                         │
└──────────────────────────┬──────────────────────────────┘
                           │
            ┌──────────────┼──────────────┐
            ▼              ▼              ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────────┐
│   MySQL DB   │  │  Google Maps │  │  Static Content  │
│  (POIs,      │  │  Android     │  │  (Images, cached │
│  Reviews,    │  │  API v2      │  │  translated text)│
│  Users,      │  └──────────────┘  └──────────────────┘
│  Bookings)   │
└──────────────┘
```

### Data Model (Simplified)

```sql
-- Core tables

POI (poi_id, name_hi, name_ta, name_bn, name_en,
     category, lat, lng, address, phone,
     cultural_tags, created_at)

REVIEW (review_id, poi_id, user_id, rating,
        text, language, user_state, created_at)

USER (user_id, preferred_language, home_state,
      device_id, created_at)

GUIDE (guide_id, name, languages_spoken[],
       aiesec_verified, specialty, daily_rate,
       contact_phone, rating, review_count)

BOOKING (booking_id, user_id, guide_id,
         tour_date, status, confirmed_at)

CULTURAL_TAG (tag_id, tag_key, display_hi,
              display_ta, display_bn, display_en)
-- Examples: "filter_coffee_available", "south_indian_breakfast",
--           "halal_menu", "no_beef", "vegetarian_only"
```

---

## Key Technical Decisions (2015)

### Decision 1: LAMP Stack over Node/Rails
**Choice:** PHP + MySQL on shared hosting
**Rationale:** Lowest deployment cost, fastest setup for solo technical founder, sufficient for pilot scale
**Tradeoff:** Limited scalability; would require migration at 10k+ concurrent users
**Regret Level:** Low — right for pilot scope

### Decision 2: Android-first, not Cross-Platform
**Choice:** Native Android (Java) over Cordova/PhoneGap hybrid
**Rationale:** 85%+ of Indian smartphone market on Android in 2015; native gave better performance on low-end devices; Indian tourists predominantly on Android
**Tradeoff:** Excluded iOS users; higher development cost for future iOS expansion
**Regret Level:** Low — correct market decision

### Decision 3: Manual Content Curation over Scraped Data
**Choice:** AIESEC volunteer manual curation over scraping MakeMyTrip/JustDial
**Rationale:** Scraped data has no cultural context; we needed human judgment on "is this actually good for Tamil travelers?" — a machine can't answer that
**Tradeoff:** Slow content scaling; AIESEC bandwidth constraints limited coverage
**Regret Level:** Low on approach, High on not building UGC submission earlier

### Decision 4: String Resource Localization over Runtime Translation
**Choice:** Pre-translated string resources in APK over calling a translation API at runtime
**Rationale:** Zero API cost; instant rendering; no latency on language switch; works offline
**Tradeoff:** App size grows with each language; manual translation update required per release
**Regret Level:** Low — right for MVP; would add community translation layer in v1.1

### Decision 5: No Offline-First Architecture (MVP Mistake)
**Choice:** Online-only with partial caching
**Rationale:** Underestimated connectivity gaps in Udaipur old city; assumed tourist areas had reliable 3G
**Tradeoff:** Users in old city experienced lag/failures at highest-intent moments (navigating heritage sites)
**Regret Level:** High — should have been offline-first from day one
**Fix:** v1.1 would implement full city content pack download on WiFi at hotel

---

## Technical Debt Inventory (At Pilot Close)

| Debt Item | Impact | Priority to Fix |
|---|---|---|
| No proper offline architecture | User experience breaks in old city | P0 |
| No event instrumentation | Cannot measure funnel, cannot A/B test | P0 |
| No UGC review submission in app | Content scaling blocked | P1 |
| Hardcoded API keys in APK | Security risk | P0 |
| No input sanitization in review form | SQL injection risk | P0 |
| Manual translation updates per release | Slow language expansion | P1 |
| Single database — no read replicas | Availability risk at scale | P2 |

---

## 2026 Architecture Vision: What This Becomes

### Modern Architecture with AI Layer

```
┌──────────────────────────────────────────────────────────────┐
│                      CLIENT LAYER                            │
│  React Native (iOS + Android)  │  Progressive Web App       │
└──────────────────┬───────────────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────────────┐
│               API GATEWAY (AWS API Gateway)                  │
└──────┬──────────────┬────────────────┬────────────────┬──────┘
       │              │                │                │
       ▼              ▼                ▼                ▼
┌──────────┐  ┌──────────────┐  ┌──────────┐  ┌──────────────┐
│ Identity │  │ Personali-   │  │ Content  │  │ Booking      │
│ Service  │  │ zation       │  │ Service  │  │ Service      │
│          │  │ Engine       │  │          │  │              │
└──────────┘  └──────┬───────┘  └────┬─────┘  └──────────────┘
                     │               │
                     ▼               ▼
              ┌─────────────┐  ┌──────────────────────────────┐
              │ ML Ranking  │  │ RAG Pipeline                 │
              │ Model       │  │                              │
              │             │  │ Vector DB (cultural context) │
              │ - Cultural  │  │ + LLM (Gemini/Claude)        │
              │   profile   │  │ + Multilingual embeddings    │
              │ - Collab    │  │                              │
              │   filtering │  └──────────────────────────────┘
              │ - Context   │
              └─────────────┘
```

### AI Features in 2026 Version

| Feature | Technology | Problem Solved |
|---|---|---|
| Cultural Profile Inference | Behavioral ML model | No more manual "where are you from" — inferred from usage patterns |
| Multilingual Semantic Search | Multilingual embeddings (LaBSE or similar) | "Find me something like Murugan Idli Kadai" works in Tamil |
| AI Trip Planner | RAG over curated content + LLM | "Plan my 3 days optimizing for my parents' comfort" |
| Real-time Cultural Translation | LLM with cultural context | Not just language translation — cultural explanation ("this dish is similar to...") |
| Review Summarization by Region | LLM summarization | "What do Tamil travelers say about this hotel" — synthesized from 50 reviews |
| Cold Start Solver | LLM content generation + community validation | New cities can be bootstrapped with AI-generated content, validated by community |

### AI Cost/Latency/Accuracy Tradeoffs (2026)

| Feature | Latency Target | Approach | Cost Control |
|---|---|---|---|
| Search | < 200ms | Embedding similarity (pre-computed) | Cache top queries per language |
| Recommendations | < 500ms | Pre-computed ranking, refreshed hourly | Batch inference, not real-time |
| Trip Planning | < 3 seconds (acceptable) | RAG + streaming LLM response | Token limits + cached itinerary templates |
| Review Summary | < 1 second | Cached summaries, refreshed weekly | Batch generation, not per-request |

---

## Scalability Path

| Users | Architecture Change Required |
|---|---|
| 0 – 1,000 | LAMP stack sufficient |
| 1,000 – 10,000 | Add read replica, CDN for static assets, Redis cache |
| 10,000 – 100,000 | Migrate to microservices, add recommendation engine, proper event pipeline |
| 100,000+ | ML personalization, multi-region deployment, real-time availability APIs |

---

*Architecture documentation written to demonstrate product-engineering collaboration thinking, not as production-ready spec.*
