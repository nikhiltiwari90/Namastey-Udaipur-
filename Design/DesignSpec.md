# Figma Design Specification
## Namastey Udaipur — Design System & Screen Annotations
**Product:** Namastey Udaipur — Culturally Personalized Travel Companion
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Design Tool:** Figma (2015 MVP reference) → Figma + FigJam (2026 vision)
**Platform:** Android (Primary) · Web (Secondary)
**Last Updated:** January 2016 (Pilot Close)

---

## Design Philosophy

> "The interface must feel like the user's native language — not just translated, but culturally intuitive."

Three design principles governed every screen decision:

**1. Language First, Feature Second**
The language selector is never buried. It is the hero of the onboarding. Every feature is subordinate to the cultural context the language creates.

**2. Community Signal Over Editorial Curation**
"12 Tamil travelers reviewed this" is more valuable than any star rating from an unknown source. Community attribution is shown prominently on every content card.

**3. Honest State Communication**
If the app is offline, say so. If there are no regional reviews yet, say so and invite the user to be first. Never hide the truth behind a spinner.

---

## Design System — Tokens

### Color Palette (2015 Holo Dark Theme)

| Token | Hex | Usage |
|---|---|---|
| `color-primary` | `#7040C0` | CTA buttons, active states, accent |
| `color-primary-light` | `#9060D8` | Secondary accents, community labels |
| `color-primary-dark` | `#3A1A70` | Pressed states, deep backgrounds |
| `color-surface` | `#110F1E` | Card backgrounds |
| `color-background` | `#09090F` | Screen backgrounds |
| `color-appbar` | `#0E0C1A` | Navigation bars |
| `color-text-primary` | `#E0D8FF` | Primary text |
| `color-text-secondary` | `#7060A8` | Muted text, labels |
| `color-text-hint` | `#4A3A80` | Placeholder text |
| `color-success` | `#50C060` | AIESEC badge, verified, online |
| `color-warning` | `#D08020` | Offline banner, weak signal |
| `color-rating` | `#60C070` | Rating badges |
| `color-border` | `#1E1A35` | Card borders, dividers |
| `color-accent-tamil` | `#9060D8` | Tamil community indicators |
| `color-accent-bengali` | `#378ADD` | Bengali community indicators |
| `color-accent-hindi` | `#BA7517` | Hindi community indicators |

### Typography

| Style | Size | Weight | Color Token | Usage |
|---|---|---|---|---|
| `title-large` | 16sp | 500 | `text-primary` | Screen titles, POI names |
| `title-medium` | 13sp | 500 | `text-primary` | Card titles, guide names |
| `title-small` | 11sp | 500 | `text-primary` | Section headers |
| `body-medium` | 10sp | 400 | `text-primary` | Review text, descriptions |
| `body-small` | 8sp | 400 | `text-secondary` | Cuisine tags, distances |
| `label-large` | 9sp | 500 | `text-secondary` | Filter chips, badges |
| `label-small` | 7sp | 400 | `text-hint` | Timestamps, sub-labels |
| `native-script` | 11sp | 500 | `text-primary` | Tamil/Bengali script rendering — requires Noto Sans Tamil / Noto Sans Bengali fonts |

### Spacing System (8dp grid)

| Token | Value | Usage |
|---|---|---|
| `space-xs` | 4dp | Icon-label gap, tag gap |
| `space-sm` | 8dp | Component internal padding |
| `space-md` | 12dp | Card padding |
| `space-lg` | 16dp | Section spacing |
| `space-xl` | 24dp | Screen margins |

### Corner Radius

| Token | Value | Usage |
|---|---|---|
| `radius-sm` | 4dp | Tags, badges, rating chips |
| `radius-md` | 8dp | Cards, buttons |
| `radius-lg` | 12dp | Hero cards, guide profile cards |
| `radius-pill` | 16dp | Filter chips, language buttons |
| `radius-avatar` | 50% | Guide avatars, user profile circles |

### Elevation (Android Material-era)

| Level | dp | Usage |
|---|---|---|
| 0 | 0dp | Background, flat surfaces |
| 1 | 2dp | Cards at rest |
| 2 | 4dp | Cards hovered/pressed |
| 3 | 8dp | App bar |
| 4 | 16dp | Bottom sheet |

---

## Component Library

### Component 01: Language Button

**States:** Default · Selected · Disabled
**Anatomy:**
- Container: 100% width, 48dp height, radius-pill, border 1dp
- Native script label: title-medium, centered
- English sub-label: label-small, 4dp below native

**Behaviour:**
- Tap → selected state (border: color-primary, bg: color-primary-dark)
- Only one selected at a time (single select)
- Haptic feedback on selection

---

### Component 02: Cultural Community Badge

**Anatomy:**
- Pulse dot: 5dp diameter, color-accent-[language]
- Label: "N [Language] travelers reviewed this"
- label-small, color-accent-[language]

**Usage:** Appears on every restaurant card, hotel card, and POI card where regional reviews exist.

---

### Component 03: Restaurant Card

**Anatomy:**
```
┌─────────────────────────────────┐
│ Restaurant Name        [Rating] │  ← title-medium + rating chip
│ Cuisine · Distance · Status     │  ← body-small, text-secondary
│ ● N Tamil travelers rated this  │  ← community badge
│ [Tag] [Tag] [Tag]               │  ← cultural amenity tags
└─────────────────────────────────┘
```
- Card: surface bg, radius-md, border
- Left accent bar (2dp): shown only when regional reviews > 0, color-primary
- Zero reviews state: reduced opacity (0.55), "be first to review" CTA

---

### Component 04: Guide Profile Card

**Anatomy:**
```
┌─────────────────────────────────┐
│ [Avatar]  Name                  │
│ [●]       Languages spoken      │  ← ● = verified dot
│           Specialty             │
│           ₹Rate/day   [Badge]   │  ← AIESEC badge right-aligned
└─────────────────────────────────┘
```
- Avatar: 32dp circle, border color-primary (1.5dp)
- Verified dot: 8dp circle, color-success, bottom-right of avatar
- Non-Tamil guides: shown at 0.6 opacity below Tamil guides

---

### Component 05: Offline State Banner

**Anatomy:**
- Warning dot: 5dp, color-warning
- Text: "Weak signal · Showing cached results"
- Full-width banner, bg: rgba(warning, 0.1), border: 0.5dp warning

**Behaviour:**
- Appears automatically when connectivity = 2G or offline
- Auto-dismisses when connectivity improves
- Never blocks content — sits above list, below search

---

### Component 06: Audio Guide Trigger Card *(Future Feature — v2)*

**Anatomy:**
```
┌─────────────────────────────────┐
│ [►] Audio Guide — Tamil         │  ← play icon + language
│     "You are 50m from           │  ← proximity trigger text
│      Jagdish Temple"            │
│ [████████░░░░░░] 2:30 / 5:45   │  ← progress bar + time
│ [◄◄] [■] [►]    Speed: 1x      │  ← playback controls
└─────────────────────────────────┘
```
- Triggered by geofence proximity (50m radius)
- Notification sent even when app is backgrounded
- See: Audio Guide PRD (`/docs/AudioGuide_PRD.md`)

---

## Screen Inventory

| Screen ID | Screen Name | Status | Key Components |
|---|---|---|---|
| SCR-01 | Splash / Language Selection | ✅ Shipped | Language buttons, CTA |
| SCR-02 | Home — Cultural Mode | ✅ Shipped | Hero strip, feature grid, community list |
| SCR-03 | Restaurant Discovery | ✅ Shipped | Search, filters, restaurant cards |
| SCR-04 | Restaurant Detail | ✅ Shipped | POI header, stats, community reviews |
| SCR-05 | Hotel Discovery | ✅ Shipped | Hotel cards with cultural amenity tags |
| SCR-06 | Guide Listing | ✅ Shipped | Guide cards, Tamil-first sort |
| SCR-07 | Guide Profile | ✅ Shipped | Full guide detail, booking CTA |
| SCR-08 | Map View | ✅ Shipped | Cultural pins, nearby list |
| SCR-09 | Event Discovery | ✅ Shipped | Event cards with language filter |
| SCR-10 | Settings | ✅ Shipped | Language switch, offline download |
| SCR-11 | Audio Guide Player | 🔮 v2 | Proximity trigger, Tamil audio player |
| SCR-12 | Notification — Proximity Alert | 🔮 v2 | Background notification, tap-to-play |
| SCR-13 | Review Submission | 🔄 v1.1 | Star rating, text in own language |
| SCR-14 | City Pack Download | 🔄 v1.1 | Progress, offline confirmation |

---

## User Flow Maps

### Flow 1: Language Selection → First Cultural Action
```
App Launch
    → SCR-01: Language Selection (Tamil selected)
    → SCR-02: Home (Tamil mode activated)
    → SCR-03: Restaurant Discovery (Tamil filter auto-applied)
    → SCR-04: Restaurant Detail (Tamil reviews shown)
    → Bookmark / Navigate → [Cultural Action ✓]
```

### Flow 2: Guide Booking
```
SCR-02: Home → "வழிகாட்டி" card
    → SCR-06: Guide Listing (Tamil guides first)
    → SCR-07: Guide Profile (Rajan Kumar)
    → Tap "Book" → WhatsApp deeplink opens
    → Booking confirmed via WhatsApp
    → [Booking ✓ — CCTC action recorded]
```

### Flow 3: Audio Guide (v2 — Future)
```
User walks near City Palace (within 50m geofence)
    → Background service detects geofence entry
    → Push notification: "You're near City Palace — Tamil audio guide ready"
    → User taps notification
    → SCR-11: Audio Guide Player opens
    → Tamil narration plays automatically
    → As user moves to next POI, next audio segment auto-queued
```

---

## Figma File Structure (Reference)

```
Namastey Udaipur — Design System
├── 📄 Page 1: Cover & Overview
├── 📄 Page 2: Design Tokens (Colors, Typography, Spacing)
├── 📄 Page 3: Component Library
│   ├── Frame: Language Buttons (all states)
│   ├── Frame: Cards (Restaurant, Hotel, Guide, Event)
│   ├── Frame: Community Badges (Tamil, Bengali, Hindi)
│   ├── Frame: Navigation (Bottom nav states)
│   ├── Frame: Offline/Error States
│   └── Frame: Audio Guide Player (v2)
├── 📄 Page 4: Android Screens — MVP
│   ├── Frame: SCR-01 Language Selection
│   ├── Frame: SCR-02 Home Tamil
│   ├── Frame: SCR-03 Restaurants
│   ├── Frame: SCR-04 Restaurant Detail
│   ├── Frame: SCR-06 Guide Listing
│   ├── Frame: SCR-08 Map View
│   └── Frame: SCR-10 Settings
├── 📄 Page 5: User Flows (FigJam-style)
│   ├── Flow: Onboarding → First Cultural Action
│   ├── Flow: Guide Booking
│   └── Flow: Audio Guide Proximity (v2)
├── 📄 Page 6: Android Screens — v2 Vision
│   ├── Frame: AI Cultural Profile
│   ├── Frame: Audio Guide Player
│   ├── Frame: Proximity Notification
│   └── Frame: Trip Planner Chat
└── 📄 Page 7: Handoff Annotations
    ├── Frame: Spacing annotations
    ├── Frame: Font specifications
    └── Frame: Interaction notes for engineers
```

---

## Handoff Notes for Engineering

### Font Loading (Critical)
The app requires Noto Sans Tamil and Noto Sans Bengali to correctly render regional language scripts. These must be bundled in the APK assets, not loaded from network, to ensure offline functionality.

```xml
<!-- res/font/ directory — add these font files -->
noto_sans_tamil_regular.ttf
noto_sans_tamil_medium.ttf
noto_sans_bengali_regular.ttf
noto_sans_bengali_medium.ttf
devanagari_regular.ttf
```

### Touch Targets
All interactive elements maintain minimum 48dp × 48dp touch target per Android accessibility guidelines. Language buttons expand to fill grid regardless of content length.

### Right-to-Left (RTL) Consideration
Urdu support (future) requires RTL layout mirroring. Design has been built with logical rather than physical directional properties (start/end not left/right) in anticipation.

### Audio Guide (v2) — Engineer Notes
See `engineering/AudioGuide_Architecture.md` for full technical spec. Key points:
- Geofence radius: 50m (configurable)
- Audio files: pre-cached in city pack download
- Playback SDK: Android MediaPlayer API
- Background service: Foreground service with notification

---

*Design specification authored by Nikhil Tiwari. All design assets, component definitions, and user flows are proprietary intellectual property. See LICENSE.md.*
