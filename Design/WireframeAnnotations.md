# Wireframe Annotations
## Namastey Udaipur — Screen-by-Screen Design Rationale
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Tool:** Hand-sketched → Balsamiq Mockups → Figma (reference)

---

## How to Read This Document

Each section covers one screen. For each screen:
- **Purpose** — why this screen exists
- **Key decisions** — why it looks the way it does
- **What was considered and rejected** — the thinking behind the final choice
- **Engineer handoff notes** — critical implementation details

---

## SCR-01: Language Selection

```
┌─────────────────────────────────┐
│         [Logo: न]               │
│      नमस्ते Udaipur             │
│   Your city · Your language     │
│                                 │
│  ┌───────────┐  ┌───────────┐  │
│  │  தமிழ்   │  │  বাংলা    │  │
│  │  Tamil    │  │  Bengali  │  │
│  └───────────┘  └───────────┘  │
│  ┌───────────┐  ┌───────────┐  │
│  │  हिन्दी  │  │  English  │  │
│  └───────────┘  └───────────┘  │
│                                 │
│   + More languages coming soon  │
│                                 │
│  ┌─────────────────────────┐   │
│  │   Continue →  தொடர்க   │   │
│  └─────────────────────────┘   │
└─────────────────────────────────┘
```

**Purpose:** Establish the user's cultural context. This is the product thesis in one screen.

**Key decisions:**

*Native script shown first, English sub-label second* — The Tamil speaker should recognise their language immediately. "Tamil" in Roman script is secondary. We're signalling: we know your script.

*2×2 grid, not a dropdown* — A dropdown hides options behind interaction. The grid shows all 4 options simultaneously, communicating immediately that this is a multi-language product. Dropdowns also feel like a setting. The grid feels like a choice that matters.

*"Continue" button bilingual* — The CTA reads "Continue → தொடர்க". Once a language is selected, the CTA partially shifts to that language. This is the first moment the UI speaks to the user in their language — before they even tap Continue.

*No "Skip" option* — Language selection is mandatory. Considered adding skip, rejected because: (a) the skip user would get a generic English experience that's worse than any existing app, and (b) every user who completes language selection is more engaged. There is no value in supporting skipping.

**What was rejected:**

- *Auto-detect from device locale* — Unreliable in India. Many users have English as device language regardless of mother tongue. Also, the act of consciously selecting Tamil is an important moment of recognition ("this app sees me").
- *Settings-only language switch* — Burying language in settings means most users never find it. Product thesis requires it front and centre.

**Engineer handoff:**
- Selected button: background `#3A1A70`, border `#7040C0`, text white
- Unselected button: background `#12102A`, border `#2A2050`, native text `#E0D0FF`
- Continue button disabled (grey) until a language is selected
- On Continue tap: apply locale, persist to SharedPreferences, navigate to SCR-02

---

## SCR-02: Home — Cultural Mode

```
┌─────────────────────────────────┐
│ [Avatar:K]  நமஸ்தே Udaipur  [🔔]│  ← App bar
├─────────────────────────────────┤
│ Udaipur, Rajasthan · Day 1 of 5 │
│ வணக்கம், Karthik                │  ← Greeting in Tamil
│ Tamil community · 14 reviews    │
│ 29°C · Clear · Good day to go  │
├─────────────────────────────────┤
│ DISCOVER                        │
│ ┌──────┐ ┌──────┐ ┌──────┐    │
│ │சாப்பி│ │தங்க  │ │வழிகாட│    │  ← Tamil labels
│ │ Eat  │ │ Stay │ │ Guide│    │
│ └──────┘ └──────┘ └──────┘    │
├─────────────────────────────────┤
│ TAMIL TRAVELERS RECOMMEND       │
│ ┌─────────────────────────────┐│
│ │ Annapurna South Indian      ││
│ │ Filter coffee · ★4.7        ││
│ │ 12 Tamil travelers reviewed ││
│ └─────────────────────────────┘│
│ ┌─────────────────────────────┐│
│ │ City Palace Heritage Walk   ││
│ │ Tamil audio available       ││
│ └─────────────────────────────┘│
└─────────────────────────────────┘
```

**Purpose:** Make the user feel immediately at home. Within 3 seconds of landing on this screen, a Tamil traveler should think: "This is mine."

**Key decisions:**

*"TAMIL TRAVELERS RECOMMEND" not "FEATURED PLACES"* — Community attribution is the core signal. "Featured" is editorial and anonymous. "Tamil travelers recommend" is personal and communal. Experiment EXP-004 confirmed this section was tapped first, consistently.

*Feature grid labels in Tamil AND English* — Tamil primary, English sub-label. Same pattern as language buttons. Consistent grammar throughout the app.

*Weather shown* — Tourist utility. "29°C Clear" helps the user decide what to wear and what activities make sense. Small detail, high utility.

*No search bar on home* — Deliberate. Home is for discovery and community picks. Search is available in each category screen. Home should feel curated, not like a search engine.

**Engineer handoff:**
- Hero strip background: `#110F1E` with `#1E1A35` border
- Community pick cards: left accent bar `#7040C0`, 2dp width
- Section label: `#7060A8`, 7sp, 0.05em letter-spacing, all caps

---

## SCR-03: Restaurant Discovery

```
┌─────────────────────────────────┐
│ சாப்பிட — Restaurants           │  ← Screen title bilingual
├─────────────────────────────────┤
│ [🔍 Search in Tamil or English] │
├─────────────────────────────────┤
│ [Tamil picks✓][South Indian]    │
│ [Veg only][Near me][Local]      │  ← Filter chips
├─────────────────────────────────┤
│ ⚠ Weak signal · Cached results  │  ← Offline banner (contextual)
├─────────────────────────────────┤
│ ┌─────────────────────────┬───┐ │
│ │ Annapurna South Indian  │4.7│ │
│ │ South Indian · 0.3 km   └───┘ │
│ │ ● 12 Tamil travelers rated    │
│ │ [Filter coffee][Idli][Veg]    │
│ └───────────────────────────────┘ │
│ ┌─────────────────────────┬───┐ │
│ │ Nataraja Bhavan         │4.2│ │
│ │ Chettinad · 1.1 km      └───┘ │
│ │ ● 7 Tamil travelers rated     │
│ └───────────────────────────────┘ │
│ ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐ │
│   Jagdish Dhaba           3.9   │  ← Dimmed: no Tamil reviews
│   Rajasthani · 0.7 km           │
│   No Tamil reviews · be first!  │
│ └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘ │
└─────────────────────────────────┘
```

**Purpose:** Help the user find food they trust, quickly.

**Key decisions:**

*"Tamil picks" filter pre-selected* — The user selected Tamil at onboarding. We should not make them filter again. The Tamil community view is the default. They can remove it if they want the general view — but they shouldn't have to add it.

*Dimmed cards for zero-community-reviews* — The dimmed card at reduced opacity is not hidden — it's visible but clearly de-prioritised. The "be the first to review" text invites contribution. This balances honest UX with growth incentive.

*Offline banner above the list, not blocking it* — The banner appears below the filters and above the list. It does not take over the screen. Users see the content. They understand why it might be cached.

*Rating chip right-aligned on card* — Rating has high information density. Right-aligned keeps the name readable on the left. Standard pattern users expect.

**What was rejected:**

*Hiding zero-review restaurants* — Rejected. If we hide everything with no Tamil reviews, the list would be too thin in early months. Better to show with honest state than to mislead about coverage.

*Showing TripAdvisor aggregate rating* — Rejected. We don't show a generic rating alongside our community rating. That would undermine the community signal. One rating per card: community rating if available, overall rating as fallback.

**Engineer handoff:**
- "Tamil picks" chip: pre-selected on screen load if user language is Tamil
- Dimmed cards: `alpha = 0.55f` on the entire card view
- Offline banner: shows/hides based on `ConnectivityManager` broadcast receiver
- Distance calculated using Haversine formula client-side from last known location

---

## SCR-11: Audio Guide Player [v2.0]

```
┌─────────────────────────────────┐
│ ←                          ⋮   │
├─────────────────────────────────┤
│                                 │
│    [City Palace illustration]   │  ← 160dp hero
│                                 │
├─────────────────────────────────┤
│  City Palace                    │
│  நகர அரண்மனை                   │  ← POI name in Tamil
│  Chapter 2 of 6 · Zenana Mahal │
├─────────────────────────────────┤
│                                 │
│  ─────────────────────────────  │
│  [████████░░░░░░░░] 2:45 / 5:30│  ← Scrub bar
│  ─────────────────────────────  │
│                                 │
│    [◄◄15s]   [  ■  ]   [15s►► ]│  ← Skip · Stop · Skip
│               [  ▶  ]           │  ← Play
│                                 │
│    Speed: [0.75x][1x✓][1.25x]  │
│                                 │
│  ┌──────────────────────────┐  │
│  │ 📜 Read transcript (Tamil)│  │
│  └──────────────────────────┘  │
│                                 │
│  Next: Mardana Mahal  [Auto ›] │  ← Chapter queue
└─────────────────────────────────┘
```

**Purpose:** Deliver the museum headset experience. Hands-free cultural narration that walks with the user.

**Key decisions:**

*Speed control visible, not in settings* — 0.75x is for elderly users. This is a primary use case (Karthik's parents). Burying speed in settings means they'll never find it.

*Transcript as first-class feature* — Not hidden. "Read transcript" is always visible. Serves: hearing-impaired users, language learners who want to follow along, users in noisy areas.

*15-second skip, not 10 or 30* — 10s feels too short when you've missed context. 30s overshoots. 15s is standard in podcast apps; familiar to users.

*"Auto ›" for next chapter* — Default on. Mimics museum headset. User doesn't have to tap anything; the experience is continuous.

*Stop button shown (not pause)* — Stop releases audio focus. Pause keeps focus and keeps the player visible. Stop is for "I'm done with the guide." Pause is for "I'll come back." Both available; stop is the square icon, pause will appear when playing.

**Engineer handoff:**
- MediaPlayer state machine: see `engineering/AudioGuide_Architecture.md`
- Auto-advance: fires `mediaPlayer.setOnCompletionListener` → load next chapter
- Speed control: `mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(0.75f))`
- Transcript: JSON file co-located with audio file in city pack

---

## SCR-12: Proximity Notification [v2.0]

```
┌─────────────────────────────────────────┐
│  नमस्ते · You're near City Palace       │ ← Notification title
│  Tamil audio guide ready — 5 min        │ ← Body
│                                         │
│  [▶ Play Guide]         [Dismiss]       │ ← Action buttons
└─────────────────────────────────────────┘
```

**Purpose:** The "tap on the shoulder" moment. The user is walking, phone in pocket, and the city speaks to them.

**Key decisions:**

*Title starts with "नमस्ते"* — Brand recall. Every notification is from Namastey Udaipur, not a generic system alert.

*Duration in notification body* — "5 min narration" sets expectation. User knows whether they have time before moving on.

*Action buttons in notification* — User can start playback without opening the app. Reduces friction for the moment of engagement.

*Notification channel: "Audio Guides" separate from other notifications* — Users can mute audio guide notifications without muting other app notifications (guide booking confirmations, etc.).

**Engineer handoff:**
- Fire notification via `NotificationCompat.Builder` from `GeofenceAudioService`
- Notification channel: `audio_guides` — create on app startup (Android 8.0+)
- `setAutoCancel(true)` — dismiss when tapped
- Do NOT use `setOngoing(true)` — this notification should be dismissable

---

*Wireframe annotations authored by Nikhil Tiwari · Namastey Udaipur · Proprietary — see LICENSE.md*
