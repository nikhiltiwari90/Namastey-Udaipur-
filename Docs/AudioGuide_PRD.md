# PRD — Audio Guide Feature
## Namastey Udaipur v2.0
**Feature Name:** Proximity-Triggered Cultural Audio Guide
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Status:** Planned — v2.0
**Platform:** Android (Primary) · iOS (v2.1)
**Date:** Q1 2016 (planned)

---

## One-Liner

> When a traveler walks within 50 metres of a heritage site, their phone automatically plays a cultural audio guide in their mother tongue — just like the headsets handed out at world-class museums, but free, always in your language, and triggered by where you actually are.

---

## The Problem

### What exists today in Udaipur heritage sites

City Palace, Jagdish Temple, Fateh Sagar Lake, Bagore Ki Haveli — Udaipur's most visited heritage sites — offer exactly two options for understanding what you're looking at:

1. **Hired human guide** — ₹500–₹1,500 per day, Hindi or English only for most, inconsistent quality
2. **Nothing** — the majority of tourists read a single English plaque and move on

The result: A Tamil grandmother stands in front of a 400-year-old palace that her family saved two years to visit, reads nothing she understands, and experiences the place as a beautiful backdrop rather than a living story.

### The museum headset analogy

Every world-class museum — the Louvre, the British Museum, the Smithsonian — solves this with audio guides. You pick up a headset at the entrance, choose your language, and the museum speaks to you as you walk through it. The experience transforms. You don't just see the object. You understand it.

Namastey Udaipur can do this for an entire city. Automatically. In 22 languages. Without a headset desk.

### Why existing apps fail this

- **Google Maps:** No cultural narrative, no regional language audio, no automatic triggering
- **Wikipedia / Wikivoyage:** Text-only, English-primary, no proximity trigger
- **City-specific audio guide apps (e.g. Rick Steves):** English-only, Western cities only, manual play required
- **Tourism board apps:** Exist for some cities, always in Hindi/English, poor UX, not maintained

**No product in India delivers automatic, proximity-triggered, mother-tongue audio narration for heritage sites.** This is a greenfield opportunity.

---

## User Story

**As** Karthik, a Tamil traveler visiting Udaipur with his elderly parents,

**When** I walk toward City Palace with my family,

**I want** my phone to automatically start playing a Tamil audio narration that explains what I'm about to see — the history, the architecture, what to look for inside —

**So that** my parents can experience the palace the way it deserves to be experienced, not as a collection of incomprehensible signs, but as a living story told in their language.

---

## Jobs To Be Done

| Job | Current Workaround | What Audio Guide Does |
|---|---|---|
| Understand what I'm seeing | Read English plaques, miss most context | Hear the full story in my language, hands-free |
| Plan what to look at inside | Wander and hope | Guided narrative tells me what's significant and why |
| Know when to move on | Guess, feel rushed or bored | Audio segment ends → natural cue to move forward |
| Share the experience with family | Translate badly in real time | Everyone hears it simultaneously on their own phones |
| Remember what I saw after the trip | Photos without context | Rich audio memory + saved transcript |

---

## Feature Specification

### Core Behaviour: Proximity Trigger

**Mechanism:** Android Geofencing API monitors device location against a registered set of heritage site coordinates. When the device enters a geofence (configurable radius, default 50m), a foreground notification is fired.

**Trigger flow:**
```
User walks within 50m of City Palace
    → Geofence entry event fired by Android OS
    → App background service receives broadcast
    → Notification posted: "You're near City Palace — Tamil audio guide ready"
    → User taps notification
    → Audio Guide Player screen opens
    → Tamil narration begins playing automatically
    → As user moves to next zone within site, next chapter auto-loads
```

**Notification anatomy:**
- **Title:** "नमस्ते · You're near [Site Name]"
- **Body:** "Tamil audio guide ready — tap to start · 5 min narration"
- **Action button:** "▶ Play Guide"
- **Second action:** "Dismiss"
- **Icon:** The Namastey Udaipur lotus icon
- **Channel:** "Audio Guides" (user can mute independently of other notifications)

**Permission required:** Location (Fine) — requested at onboarding with clear explanation. Without location permission, feature is unavailable; user shown explanation and setting shortcut.

---

### Audio Player Screen

**Layout:**
```
┌──────────────────────────────────────────┐
│  ← Back                    ⋮ Options    │  ← App bar
├──────────────────────────────────────────┤
│  [Site illustration / photo]             │  ← 160dp hero image
├──────────────────────────────────────────┤
│  City Palace — நகர அரண்மனை              │  ← POI name in Tamil
│  Chapter 2 of 6 · The Zenana Mahal      │  ← Chapter indicator
│                                          │
│  ─────────────────────────────────       │
│  [████████████░░░░░░░░░░░░] 2:45 / 5:30 │  ← Scrub bar + time
│  ─────────────────────────────────       │
│                                          │
│      [◄◄ 15s]  [  ■  ]  [15s ►► ]      │  ← Skip back · Stop · Skip fwd
│                 [ ▶ Play ]               │  ← Primary play CTA
│                                          │
│      Speed: [0.75x] [1x✓] [1.25x]       │  ← Speed control (elderly users)
│                                          │
│  ┌──────────────────────────────────┐   │
│  │ 📜 Read transcript in Tamil       │   │  ← Accessibility + follow-along
│  └──────────────────────────────────┘   │
│                                          │
│  Next: The Mardana Mahal  [Auto ›]      │  ← Auto-advance to next chapter
└──────────────────────────────────────────┘
```

**Design decisions:**
- Speed control (0.75x) is deliberately visible — a primary user is an elderly parent who may need slower pace
- Transcript is not hidden in settings — it's a first-class feature for hearing-impaired users and language learners
- Auto-advance is on by default — mimics museum headset experience where narration flows as you walk
- Large touch targets throughout — minimum 48dp, accommodate older users

---

### Audio Content Structure

Each POI has a structured audio content package:

```json
{
  "poi_id": "city-palace-udaipur",
  "poi_name": {
    "en": "City Palace",
    "ta": "நகர அரண்மனை",
    "bn": "সিটি প্যালেস",
    "hi": "सिटি पैलेस"
  },
  "geofence": {
    "lat": 24.5764,
    "lng": 73.6835,
    "radius_meters": 50
  },
  "chapters": [
    {
      "chapter_id": "cp-intro",
      "title_en": "The Palace at a Glance",
      "title_ta": "அரண்மனை ஒரு பார்வை",
      "duration_seconds": 120,
      "trigger": "geofence_entry",
      "audio_files": {
        "ta": "audio/ta/city-palace/cp-intro.mp3",
        "bn": "audio/bn/city-palace/cp-intro.mp3",
        "hi": "audio/hi/city-palace/cp-intro.mp3",
        "en": "audio/en/city-palace/cp-intro.mp3"
      },
      "transcript": {
        "ta": "உங்களுக்கு முன்னே நிற்பது...",
        "en": "Before you stands one of..."
      }
    }
  ]
}
```

**Content creation pipeline:**
1. Hindi/English script written by historian/cultural consultant
2. AIESEC volunteers review for accuracy per region
3. Professional voice artist records in each language
4. Audio files compressed to 64kbps MP3 (balance of quality and download size)
5. Included in city pack download — fully offline capable

---

### Offline Architecture (Critical)

Audio guide content is pre-downloaded as part of the City Pack. A tourist cannot rely on streaming audio while standing in the old city's connectivity dead zones.

**City Pack contents (Udaipur):**
```
udaipur-city-pack-v1.zip (~80MB total)
├── pois.db              (SQLite — all POI data, translated)
├── map-tiles/           (Offline map tiles for city area)
├── reviews-cache.db     (Top 20 reviews per POI per language)
├── geofences.json       (All registered geofence coordinates)
└── audio/
    ├── ta/              (Tamil audio files ~18MB)
    ├── bn/              (Bengali audio files ~18MB)
    ├── hi/              (Hindi audio files ~18MB)
    └── en/              (English audio files ~18MB)
```

**Download UX:**
- Prompted after language selection: "Download Udaipur city pack? 80MB. Works offline anywhere in the city."
- Download on WiFi only by default (data saver behaviour)
- Progress indicator with estimated time
- Partial resume on interrupted download

---

## Metrics & Success Definition

### North Star for Audio Guide Feature
**"Heritage Moments"** — number of audio guide sessions played to >50% completion

### Feature KPIs

| Metric | Definition | Target (Month 3) |
|---|---|---|
| Proximity trigger rate | % of users near a geofenced POI who receive notification | 80% (of users with location permission) |
| Notification tap rate | Notifications sent → Audio player opened | 45% |
| Session completion rate | Sessions played to >50% completion | 55% |
| Chapter advance rate | Users who progress past chapter 1 | 40% |
| Offline play rate | % of sessions played from cached audio | 70% |
| Transcript usage rate | % of sessions where transcript opened | 20% |
| Speed adjustment rate | % of sessions where user changes speed | 15% (proxy for elderly users) |
| Location permission grant rate | % of users who grant Fine Location | 65% |

### Guardrail Metrics (Must Not Regress)

| Guardrail | Threshold |
|---|---|
| Battery drain increase | < 5% additional drain with geofencing on |
| False positive trigger rate | < 3% of triggers when user is not actually near a POI |
| Notification complaint rate | < 2% of users who disable audio guide notifications |
| Audio quality complaint rate | < 5% of sessions |

---

## Technical Requirements

### Android Permissions Required
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

### Key Technical Constraints
- **Battery:** Geofencing via Android OS (not continuous GPS polling) — minimises battery impact
- **Background restrictions:** Android 8.0+ background execution limits require Foreground Service with persistent notification
- **Audio focus:** Must request and respect AudioFocus — pause when call received, duck when navigation speaks
- **Minimum Android version:** API 21 (Android 5.0) for reliable geofencing + MediaSession API

---

## Risks

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Users deny location permission | High | High | Clear value explanation at prompt; graceful fallback to manual play |
| Audio quality poor for regional languages | Medium | High | Professional voice artists; community review before release |
| Geofence false positives in dense heritage area | Medium | Medium | Minimum 50m radius; dead zone buffers between adjacent POIs |
| Battery drain complaints | Medium | Medium | OS-managed geofencing; battery usage disclosure |
| Heritage site authorities object | Low | High | Position as enhancement, not replacement for official guides |
| Audio content becomes outdated | Medium | Medium | Version the content; quarterly review cycle |

---

## Out of Scope (v2.0)

| Feature | Rationale |
|---|---|
| AR overlay on camera | Engineering complexity; separate product track |
| User-recorded audio contributions | Content quality risk; v3 consideration |
| Real-time guide narration (live call) | Cost and reliability; human guide booking serves this |
| Turn-by-turn walking navigation with audio | Separate navigation feature; integrate in v2.1 |
| iOS support | Android-first; iOS in v2.1 |

---

*PRD authored by Nikhil Tiwari · Namastey Udaipur · Proprietary — see LICENSE.md*
