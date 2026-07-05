# Risk Register
## Namastey Udaipur — Full Risk Inventory
*Author: Nikhil Tiwari | Updated Throughout Pilot | 2015*

---

## Risk Framework

Each risk scored on:
- **Likelihood:** 1 (very low) → 5 (near-certain)
- **Impact:** 1 (negligible) → 5 (existential)
- **Risk Score = Likelihood × Impact**

---

## Product Risks

| ID | Risk | L | I | Score | Status | Mitigation |
|---|---|---|---|---|---|---|
| PR-01 | Content cold start — insufficient regional reviews at launch | 4 | 5 | 20 | **Materialized** | AIESEC seeding reduced severity; UGC gap was the gap |
| PR-02 | Wrong primary persona — built for wrong user type | 2 | 5 | 10 | Not materialized | Validated via user interviews pre-build |
| PR-03 | Language selection creates friction that drops onboarding | 3 | 4 | 12 | Partially materialized | Some users skipped; core adopters completed |
| PR-04 | Low trust in app-recommended guides | 3 | 4 | 12 | Partially — addressed via AIESEC badge | Verified guide profiles with AIESEC badge |
| PR-05 | Users revert to Google/WhatsApp groups after trying app | 4 | 4 | 16 | Partially — connectivity drove this | Offline-first architecture would have retained users |

---

## Technical Risks

| ID | Risk | L | I | Score | Status | Mitigation |
|---|---|---|---|---|---|---|
| TR-01 | Cellular connectivity failures in Udaipur old city | 3 | 4 | 12 | **Materialized** | Partial caching insufficient; v1.1 needed full offline mode |
| TR-02 | Android fragmentation — UI breaks on low-end devices | 3 | 3 | 9 | Partially materialized | Tested on 5 device profiles; some edge cases |
| TR-03 | Google Maps API rate limits exceeded at scale | 1 | 3 | 3 | Not materialized | Scale too small for MVP |
| TR-04 | SQL injection via review form | 4 | 5 | 20 | Not exploited, but vulnerability confirmed | Fixed post-discovery |
| TR-05 | API keys hardcoded in APK (decompilable) | 4 | 4 | 16 | **Identified, not exploited** | Moved to server-side key management |
| TR-06 | Unicode rendering issues for Tamil/Bengali scripts | 3 | 4 | 12 | Partially materialized | Fixed on Android 4.4+; older devices had issues |

---

## Business / Market Risks

| ID | Risk | L | I | Score | Status | Mitigation |
|---|---|---|---|---|---|---|
| BR-01 | Google Maps adds regional language review filtering | 2 | 5 | 10 | Not materialized (as of 2015) | Build community moat before this happens |
| BR-02 | MakeMyTrip launches cultural personalization feature | 2 | 4 | 8 | Not materialized | Community loyalty is our moat |
| BR-03 | AIESEC India partnership dissolves | 2 | 4 | 8 | Not materialized | Build UGC to reduce dependence |
| BR-04 | Regional guide quality inconsistency damages trust | 3 | 5 | 15 | One poor experience reported | Rating system + faster response to complaints |
| BR-05 | Seasonal tourism makes annual metrics look worse | 4 | 2 | 8 | Expected | Metric windows adjusted for tourist season |

---

## Operational Risks

| ID | Risk | L | I | Score | Status | Mitigation |
|---|---|---|---|---|---|---|
| OR-01 | Solo technical founder creates single point of failure | 4 | 4 | 16 | **Real constraint throughout** | Document all systems; hire engineer in Year 2 |
| OR-02 | Guide booking callback SLA slips (>2 hours) | 3 | 3 | 9 | Materialized under volume | WhatsApp confirmation faster than voice callback |
| OR-03 | AIESEC volunteer content quality inconsistent | 3 | 4 | 12 | Partially materialized | Structured content template + review process |
| OR-04 | Manual content updates not keeping up with new openings | 4 | 3 | 12 | **Materialized** | UGC submission is the fix |

---

## Risks That Did NOT Materialize (Learning)

**"Users won't switch from existing apps"** — Wrong assumption in the other direction. Users who found us actively preferred us, but the acquisition problem was harder than the retention problem. We over-worried about switching costs, under-worried about discovery.

**"Multilingual content is legally risky"** — Not an issue at pilot scale.

**"Tourism board will object to an unauthorized city app"** — No friction encountered. Tourism authorities were indifferent.

---

## Top 3 Risks I Would Manage Differently

1. **TR-01 (Offline connectivity)** — Should have been a P0 constraint from day one. Should have built offline architecture before building features.

2. **OR-01 (Single technical founder)** — Should have raised ₹5-10L from friends/family seed round to hire a part-time engineer from month 4.

3. **PR-01 (Content cold start)** — AIESEC seeding was right, but should have activated UGC submission simultaneously. Never rely on a single content source.

---

*Risk register maintained as a living document. Reviewed monthly with co-founder.*
