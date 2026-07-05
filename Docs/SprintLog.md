# Sprint Planning Log
## Namastey Udaipur — MVP to v1.1
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Sprint Length:** 2 weeks
**Team:** 2 (Nikhil Tiwari — Product + Tech, Co-Founder — Content + Partnerships)

---

> **Note:** With a 2-person team, sprint planning was lightweight but disciplined. This log documents the actual prioritisation decisions made each sprint, including what was cut and why.

---

## Sprint 1 — March 2–15, 2015
**Theme:** Foundation — can we render Tamil script correctly and serve content?

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Language selection screen (Tamil, Bengali, Hindi, English) | L | Nikhil | ✅ Done | Noto fonts bundled in APK |
| MySQL schema — POI, Review, User, Guide tables | M | Nikhil | ✅ Done | |
| PHP REST API — POI list endpoint with language param | M | Nikhil | ✅ Done | |
| AIESEC content brief — what data to collect per POI | S | Co-founder | ✅ Done | Template shared with volunteers |
| Android string resources — Tamil translations for core UI | M | Nikhil | ✅ Done | |
| Google Maps Android API v2 integration | M | Nikhil | ✅ Done | API key in AndroidManifest *(security debt noted)* |

**Sprint velocity:** 18 points
**Carry-over:** 0

---

## Sprint 2 — March 16–29, 2015
**Theme:** Core discovery — restaurant list + cultural filtering

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Restaurant list screen with Tamil filter | L | Nikhil | ✅ Done | |
| Restaurant card component — community badge | M | Nikhil | ✅ Done | |
| Cultural amenity tag system | S | Nikhil | ✅ Done | filter_coffee, south_indian_breakfast etc. |
| Restaurant detail screen + Tamil review display | L | Nikhil | ✅ Done | |
| AIESEC seed content — 100 POIs entered | XL | Co-founder | ✅ Done | First batch of volunteer submissions |
| Bengali UI strings translation | M | Co-founder | ✅ Done | AIESEC Bengali volunteer translated |

**Sprint velocity:** 21 points
**Carry-over:** 0

---

## Sprint 3 — March 30 – April 12, 2015
**Theme:** Guide booking + map view

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Guide listing screen — Tamil-first sort | L | Nikhil | ✅ Done | |
| Guide profile screen + AIESEC badge | M | Nikhil | ✅ Done | |
| Guide booking — phone callback flow | M | Nikhil | ✅ Done | Simple form submission + email notification |
| Map view with cultural pins | L | Nikhil | ✅ Done | |
| Nearby list below map | M | Nikhil | ✅ Done | |
| Hotel listing screen with cultural tags | L | Nikhil | ✅ Done | |
| ~~In-app review submission~~ | XL | — | ❌ Cut | **Reason:** Guide booking was prioritised; bandwidth insufficient for both. *This was the wrong call in retrospect.* |

**Sprint velocity:** 19 points (excl. cut story)
**Carry-over:** In-app review submission → Backlog

---

## Sprint 4 — April 13–26, 2015
**Theme:** Polish + launch prep

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Home screen — cultural greeting + community picks | L | Nikhil | ✅ Done | |
| Event discovery screen | M | Nikhil | ✅ Done | |
| Settings screen — language switch | S | Nikhil | ✅ Done | |
| Partial offline caching — saved POIs | M | Nikhil | ✅ Done | SharedPreferences-based, not SQLite |
| AIESEC seed content — 100 more POIs (200 total) | XL | Co-founder | ✅ Done | |
| Play Store listing — Tamil description + screenshots | M | Nikhil | ✅ Done | |
| Beta testing with 5 AIESEC volunteers | M | Both | ✅ Done | 3 bug fixes actioned |

**Sprint velocity:** 22 points
**MVP SHIPPED: April 27, 2015** 🚀

---

## Sprint 5 — May 5–18, 2015 (Post-Launch)
**Theme:** Fix what broke — connectivity + booking flow

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Offline warning banner — show connectivity state honestly | S | Nikhil | ✅ Done | Shipped as hotfix May 3 after user complaints |
| Improve partial caching — cache restaurant list on load | M | Nikhil | ✅ Done | |
| Guide booking — switch to WhatsApp deeplink | M | Nikhil | ✅ Done | Immediate conversion improvement |
| Fix Unicode rendering bug on Android 4.1 devices | M | Nikhil | ✅ Done | Tamil script garbled on older devices |
| Fix SQL injection vulnerability in review form | M | Nikhil | ✅ Done | **P0 security fix** |
| Fix hardcoded API key — move to server-side | M | Nikhil | ✅ Done | **P0 security fix** |
| Cold email campaign — Tamil Nadu travel groups | L | Co-founder | ✅ Done | 12 installs from this cohort |

**Sprint velocity:** 19 points

---

## Sprint 6 — June 2–15, 2015
**Theme:** Firebase Analytics instrumentation

| Story | Size | Owner | Status | Notes |
|---|---|---|---|---|
| Firebase Analytics integration | M | Nikhil | ✅ Done | |
| Core event schema — 8 events instrumented | L | Nikhil | ✅ Done | app_launched, language_selected, poi_viewed, guide_booking_tapped, booking_confirmed, content_saved, search_performed, app_crashed |
| Crash reporting — Firebase Crashlytics | M | Nikhil | ✅ Done | Revealed old city crash pattern |
| ~~In-app review submission~~ | XL | — | ❌ Cut again | **Reason:** Festival season approaching — prioritised stability over new features. This was the second time this story was cut. Pattern noted. |
| AIESEC content drive — 40 more POI reviews | L | Co-founder | ⚠️ Partial | 22 added (volunteer availability dropped) |

**Sprint velocity:** 16 points (excl. cut story)

---

## Sprint 7–10 — July–October 2015
**Theme:** Community growth + seasonal push

*Compressed log for brevity:*

| Shipped | Cut / Delayed |
|---|---|
| Community review social sharing (manual) | In-app review submission *(cut 3rd time — engineering debt)* |
| QR code cards for tourist spots | Punjabi/Marathi language expansion |
| 20 more AIESEC POI reviews | Push notifications |
| WhatsApp share link (replacing QR) | Payment gateway |
| App crash fix — memory leak in map view | Cross-city Jaipur page |

**Pattern observed:** In-app review submission was cut 3 sprints in a row. Root cause: always something more urgent for a 2-person team. This created the content ceiling we hit in Q4. **Key learning documented in Postmortem.**

---

## Sprint 11 — November 2015 (Final Feature Sprint)
**Theme:** One last push before pilot close

| Story | Size | Owner | Status |
|---|---|---|---|
| In-app review submission — FINALLY shipped | XL | Nikhil | ✅ Done |
| Post-trip review prompt (push notification) | L | Nikhil | ⚠️ Partial — notification sent, no deep link |
| "Coming soon: Jaipur" waitlist page | M | Co-founder | ✅ Done |
| Final AIESEC content audit | M | Co-founder | ✅ Done |

**Pilot closed: January 17, 2016**

---

## Sprint Velocity Summary

| Sprint | Points | Key Outcome |
|---|---|---|
| Sprint 1 | 18 | Foundation shipped |
| Sprint 2 | 21 | Core discovery live |
| Sprint 3 | 19 | Guides + map live |
| Sprint 4 | 22 | MVP launched |
| Sprint 5 | 19 | Security + UX fixes |
| Sprint 6 | 16 | Instrumentation live |
| Sprint 7–10 | ~15 avg | Community growth |
| Sprint 11 | 14 | Review loop finally live |

---

## Backlog at Pilot Close (Unprioritised — For Reference)

- Full offline city pack download (SQLite)
- iOS app
- In-app payment gateway
- Punjabi / Marathi language packs
- Hotel booking integration
- Audio guide proximity feature *(see AudioGuide_PRD.md)*
- AI trip planner
- Guide rating system
- Post-trip "Tamil trip summary" share card
- Jaipur city expansion

---

*Sprint log authored by Nikhil Tiwari · Namastey Udaipur · Proprietary — see LICENSE.md*
