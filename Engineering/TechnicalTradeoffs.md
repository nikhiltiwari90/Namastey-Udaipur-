# Technical Tradeoffs & Engineering Decisions
## Namastey Udaipur
*Author: Nikhil Tiwari | Product-Tech Lead | 2015*

---

## Decision Framework

Every technical decision was evaluated against three constraints:
1. **Speed to ship** — 2 founders, limited runway, must validate fast
2. **Cost** — Zero external funding; hosting and API costs came from personal budget
3. **User experience** — Core user is a tourist in a new city, possibly on weak data

These constraints drove every tradeoff below.

---

## Tradeoff 1: Runtime Translation API vs. Static String Resources

**The Choice:** Translate UI at build time (static string files per language) vs. call a translation API at runtime (Google Translate API or similar)

**Runtime API approach:**
- Pros: Infinite language support, always up-to-date, no APK size growth
- Cons: API cost per request, latency on every screen render, offline unusable, quality inconsistent for regional Indian languages in 2015

**Static string resources approach:**
- Pros: Zero cost, zero latency, works offline, full control over translation quality, no API dependency
- Cons: App size grows ~2MB per language, requires code release to add/update translations, manual translation effort

**Decision:** Static string resources.

**Rationale:** The product's core value — working in your language in a low-connectivity tourist area — would be destroyed by network-dependent translation. In 2015, Google Translate quality for Tamil was also significantly worse than human-reviewed translations. The 2MB per language APK size increase was acceptable given the value delivered.

**What changed in 2026:** Modern LLMs produce near-human quality for Indian regional languages. A hybrid approach is now viable: static strings for core UI, LLM translation for UGC content.

---

## Tradeoff 2: Single MySQL Database vs. Read Replicas

**The Choice:** Single database instance vs. primary + read replica architecture

**Read replica approach:**
- Pros: Better read performance, higher availability, failover capability
- Cons: Higher hosting cost (2x), more complex deployment, operational overhead for solo technical founder

**Single instance approach:**
- Pros: Simple, cheap (~₹500/month on shared hosting), easy to manage
- Cons: Single point of failure, read/write contention at scale, no failover

**Decision:** Single MySQL instance on shared hosting.

**Rationale:** At 100 users, a single instance is entirely sufficient. The cost-benefit of read replicas doesn't materialize until 10,000+ concurrent users. Engineering complexity for a solo founder managing a pilot is a real cost.

**Technical debt incurred:** Database is a single point of failure. Acceptable for pilot; must be addressed before launch in new city.

---

## Tradeoff 3: Native Android vs. Progressive Web App

**The Choice:** Native Android app (Java) vs. PWA (mobile web, installable)

**PWA approach:**
- Pros: Cross-platform (Android + iOS), one codebase, no app store approval, easier update deployment
- Cons: PWA support on Android in 2015 was immature (Chrome 44), no push notifications, worse offline support, no home screen install in most Android browsers at the time

**Native Android approach:**
- Pros: Full Android SDK access, reliable offline support, push notifications, familiar development model, Google Maps Android API native integration
- Cons: Android-only, slightly longer build cycles, Play Store approval required

**Decision:** Native Android.

**Rationale:** PWA in 2015 was not production-ready for offline-first requirements. Google Maps integration was significantly better in native Android. Target users on Android 4.x devices where PWA support was negligible.

**What changed in 2026:** PWA is now a legitimate architecture choice. Service Workers provide robust offline support. A 2026 rebuild would use React Native for cross-platform + Progressive Web App for web, sharing a codebase.

---

## Tradeoff 4: Google Maps vs. OpenStreetMap

**The Choice:** Google Maps Android API vs. OpenStreetMap (OSMDroid or similar)

**OpenStreetMap approach:**
- Pros: Free, offline tiles downloadable, no API key requirements, no usage limits
- Cons: Map quality in India in 2015 was significantly inferior to Google Maps, especially for POI data and street-level accuracy in smaller Indian cities; no turn-by-turn navigation

**Google Maps approach:**
- Pros: Superior map quality, comprehensive POI database in India, well-documented Android API, familiar to users
- Cons: API cost at scale, usage limits, requires internet for tile loading (no offline tiles in v2 API without workarounds)

**Decision:** Google Maps Android API v2.

**Rationale:** Map quality was non-negotiable for a tourist navigation product. In 2015, OpenStreetMap's India data quality was insufficient for confident tourist navigation. The API cost at our pilot scale was negligible.

**Technical debt incurred:** No offline map tiles. Compounded the connectivity problem in Udaipur old city.

**What changed in 2026:** OpenStreetMap India data quality has improved significantly. For a cost-sensitive startup, OSM with Mapbox or self-hosted tile server is a viable alternative.

---

## Tradeoff 5: Monolith vs. Microservices

**The Choice:** Single PHP application vs. service-oriented or microservices architecture

**Microservices approach:**
- Pros: Independent scaling per service, technology flexibility, easier to add team members per service
- Cons: Massively over-engineered for a 2-person pilot, complex deployment, high operational overhead

**Monolith approach:**
- Pros: Simple deployment, easy to debug, fast to iterate, appropriate for team size
- Cons: Harder to scale individual components, technical debt compounds, tightly coupled code

**Decision:** PHP monolith.

**Rationale:** Microservices at pilot scale with a solo engineer is premature optimization. Martin Fowler's monolith-first principle applies directly here. Build the monolith, understand the domain boundaries, then extract services when scale demands it.

---

## Summary: Technical Debt Inventory at Pilot Close

| Item | Type | Priority | Fix |
|---|---|---|---|
| No offline architecture | Architecture | P0 | Full offline-first rebuild in v2 |
| API keys in APK | Security | P0 | Server-side key management |
| No input sanitization | Security | P0 | Parameterized queries throughout |
| No event instrumentation | Analytics | P0 | Firebase Analytics v1.1 |
| No UGC review submission | Feature gap | P1 | Build in v1.1 |
| Single DB instance | Reliability | P2 | Read replica before city 2 expansion |
| Manual translation updates | Operational | P2 | Community translation layer |
| No push notifications | Engagement | P2 | Firebase Cloud Messaging v1.1 |

---

*Technical decisions documented to demonstrate product-engineering judgment, not as production architecture guidance.*
