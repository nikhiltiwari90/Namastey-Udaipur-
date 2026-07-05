# Product Requirements Document (PRD)
## Namastey Udaipur — v1.0 (MVP)
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Date:** March 2015
**Status:** Shipped — Pilot Complete
**Platform:** Android + Web
**Pilot City:** Udaipur, Rajasthan, India

---

## 1. Executive Summary

Namastey Udaipur is a culturally-personalized travel discovery and booking platform for domestic Indian and international tourists visiting Udaipur. Unlike generic travel apps that treat all users identically, Namastey Udaipur delivers a mother-tongue-first experience — surfacing food, accommodation, events, and local guides filtered and ranked by the traveler's own cultural and linguistic community.

The MVP targets domestic Indian travelers whose primary language is not Hindi or English — specifically Tamil, Bengali, Punjabi, and Marathi speakers — who currently experience Udaipur as a culturally generic destination despite their strong preference for culturally familiar experiences.

---

## 2. Problem Statement

### The Core Problem

India has 22 scheduled languages and hundreds of dialects. 60%+ of domestic tourists traveling interstate do not speak the local language of their destination and have limited English proficiency. Yet every major travel platform — MakeMyTrip, TripAdvisor, Google Maps, Justdial — presents content primarily in English or Hindi, with no cultural personalization.

The result: a Tamil family visiting Udaipur finds restaurant reviews written by Hindi speakers, hotel recommendations with no cultural context, and zero guidance on where to find filter coffee, Chettinad cuisine, or a Tamil-speaking guide.

### The Emotional Problem

This isn't just a language problem. It's a **comfort problem**.

When elderly travelers, first-time domestic tourists, or families with children travel outside their home state, they want:
- Food that doesn't make them anxious
- A place to stay that feels familiar
- Someone who understands them if they need help
- The confidence to explore without fear of being lost or misunderstood

Current solutions offer none of this. They assume cultural homogeneity that doesn't exist.

### Evidence of Problem

- Personal observation: 60 elderly Hindi-speaking Indians on a Europe tour, unable to engage with any local content, signage, or audio guides — leading to frustration, disengagement, and a diminished experience despite significant financial investment
- Local observation: Udaipur receives 3M+ tourists annually; vast majority from outside Rajasthan; no platform addresses their cultural context
- Proxy validation: Regional food communities, Facebook groups for Tamil/Bengali travelers consistently ask "where can I find [regional food] in [city]" — a manual workaround that proves demand

---

## 3. Target Users

### Primary Persona: The Domestic Cultural Traveler

**Name:** Karthik
**Age:** 38
**Location:** Chennai, Tamil Nadu
**Travel Context:** Traveling to Udaipur with wife and parents (ages 65, 67) for a 5-day heritage trip

**Pain Points:**
- Parents only speak Tamil and basic Hindi
- Worried about finding vegetarian South Indian food
- Doesn't trust Hindi-only reviews for hotel quality
- Wants to book a guide who can communicate with his parents
- Nervous about medication availability and medical help if parents fall ill

**Current Workaround:**
- Asks in Tamil travel Facebook groups 2 weeks before trip
- Calls hotels directly to ask about filter coffee
- Books through a local travel agent who "knows someone"

**What He Needs:**
- App in Tamil
- Restaurants reviewed by Tamil travelers
- Hotels that flag South Indian breakfast availability
- A guide who speaks Tamil or has worked with Tamil groups

---

### Secondary Persona: The International Regional Tourist

**Name:** Priya
**Age:** 29
**Location:** Singapore (Tamil diaspora)
**Travel Context:** First trip to Rajasthan, 7 days, solo female traveler

**Pain Points:**
- Understands Tamil and English, not Hindi
- Unfamiliar with Indian travel norms and safety context
- Wants authentic experiences but doesn't know who to trust
- Worried about cultural mismatch at hotels

---

### Tertiary Persona: The Elderly First-Timer

**Name:** Savitri & Ramesh
**Age:** 65, 68
**Location:** Patna, Bihar
**Travel Context:** First major trip outside Bihar, joining a group tour

**Pain Points:**
- Only speak Bhojpuri/Hindi
- Cannot navigate English apps
- Need everything explained simply
- High anxiety about unfamiliar food, hygiene standards

---

## 4. Jobs To Be Done

| Job | Functional | Emotional | Social |
|---|---|---|---|
| Find food I trust | Locate regional cuisine near me | Feel safe eating in unfamiliar city | Not embarrass family by choosing wrong place |
| Book right hotel | Filter hotels by cultural amenities | Feel at home, not like a stranger | Reassure elderly parents the stay will be comfortable |
| Navigate the city | Know what to see and in what order | Feel confident, not anxious | Impress family with good planning |
| Get help when lost | Find someone who speaks my language | Feel secure in unfamiliar place | Maintain dignity, not feel helpless |
| Book a guide | Find guide who understands my culture | Feel accompanied, not just transacted | Trust the experience is authentic |

---

## 5. Product Scope — MVP

### In Scope (v1.0)

| Feature | Priority | Rationale |
|---|---|---|
| Language selection onboarding | P0 | Core to entire product thesis |
| Culturally filtered restaurant discovery | P0 | Highest frequency use case |
| Regional review display | P0 | Trust signal; differentiator |
| Hotel discovery with cultural amenity tags | P1 | Booking conversion driver |
| Google Maps integration for navigation | P1 | Table stakes; don't rebuild |
| Local guide booking (AIESEC pilot) | P1 | Monetization + moat |
| Event and festival discovery | P2 | Engagement + retention driver |
| Offline content caching (partial) | P2 | Udaipur old city connectivity issues |
| User review submission | P2 | UGC loop — content scaling |

### Out of Scope (v1.0)

| Feature | Reason Excluded |
|---|---|
| Full offline mode | Engineering bandwidth; addressed in v1.1 |
| Payment gateway integration | Regulatory complexity; guide bookings via phone in v1 |
| Real-time availability for hotels | API cost; manual confirmation flow in v1 |
| AI-powered recommendations | Infrastructure cost; rule-based filtering sufficient for MVP |
| iOS app | Android-first; 85%+ of Indian smartphone market on Android in 2015 |
| Languages beyond 4 | Content curation bandwidth; expand with AIESEC scaling |

---

## 6. Feature Specifications

### 6.1 Language Selection & Cultural Onboarding

**User Flow:**
1. App launch → Language selector (Tamil / Bengali / Hindi / English / More coming soon)
2. User selects language → UI renders in selected language
3. Optional: "Where are you from?" (State selection) → Refines cultural filtering
4. Home screen loads with culturally-relevant featured content

**Acceptance Criteria:**
- App fully functional in all 4 MVP languages
- Language persists across sessions
- State selection optional, not mandatory (reduces friction)
- Language can be changed from settings at any time

---

### 6.2 Culturally Filtered Restaurant Discovery

**User Flow:**
1. User taps "Eat" → Restaurant list loads
2. Default filter: Restaurants reviewed by users from same region
3. Secondary filter: Cuisine type (South Indian, North Indian, Local Rajasthani, etc.)
4. Each restaurant card shows: Rating from regional travelers, number of reviews from region, cultural amenity tags (filter coffee available, vegetarian-only, halal, etc.)

**Content Source (MVP):** Manually curated via AIESEC volunteer network
**Content Source (v2):** User-generated reviews with community moderation

---

### 6.3 Local Guide Booking

**User Flow:**
1. User taps "Book a Guide"
2. Sees guides listed with: Language spoken, specialty (heritage, nature, food tours), availability, price per day
3. Taps guide → Profile with AIESEC-verified badge, photo, languages, reviews
4. Books via phone confirmation (v1) or in-app request form

**MVP Constraint:** No real-time payment; confirmation via callback within 2 hours
**Guide Source:** AIESEC India volunteer network + verified local guides

---

## 7. Non-Functional Requirements

| Requirement | Target | Notes |
|---|---|---|
| App load time | < 3 seconds on 3G | Udaipur tourist areas have variable connectivity |
| Offline functionality | Core content accessible without internet | City POIs, downloaded restaurant list |
| Android compatibility | API 16+ (Android 4.1 Jelly Bean) | Covers 95%+ of Indian Android market in 2015 |
| App size | < 15MB initial download | Low storage devices common in 2015 |
| Language rendering | Correct Unicode rendering for all scripts | Tamil, Bengali scripts require specific font handling |

---

## 8. Success Metrics

### North Star Metric
**"Culturally Comfortable Trip Completions"** — defined as users who: selected a non-Hindi/English language AND completed at least one booking (restaurant reservation, hotel inquiry, or guide booking) within their trip window.

### MVP Success Criteria (10-month pilot)
- 100+ app installs ✅ (Achieved)
- At least 3 languages actively used in session data (Target)
- Guide booking pilot initiated via AIESEC (In progress at close)
- User feedback: "felt more comfortable" qualitative signal (Achieved in cellular-covered zones)

---

## 9. Risks

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Content cold start — insufficient regional reviews | High | High | AIESEC seeding strategy |
| Cellular connectivity gaps in Udaipur old city | High | Medium | Partial offline caching |
| Manual content doesn't scale | High | High | Build UGC submission in v1.1 |
| Low organic discovery | Medium | High | Cold email to regional tourist groups |
| Guide quality inconsistency | Medium | High | AIESEC verification + rating system |
| Android fragmentation issues | Medium | Medium | Test on 5 device profiles |

---

## 10. Dependencies

| Dependency | Owner | Risk |
|---|---|---|
| AIESEC India content partnership | External | Partnership continuity risk |
| Google Maps API | External | API cost at scale |
| AIESEC volunteer guide network | External | Volunteer availability |
| Regional language font support (Android) | Platform | Android version fragmentation |

---

## 11. Open Questions at Launch

1. How do we handle users who select a language but want to switch mid-session?
2. What happens when a restaurant has zero reviews from user's region — show generic reviews or hide?
3. How do we verify guide quality before AIESEC formal rating system is live?
4. Should language be inferred from device locale or always explicit user choice?

---

*Document Version: 1.0 | Last Updated: March 2015 | Owner: Nikhil Tiwari*
