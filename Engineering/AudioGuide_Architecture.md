# Audio Guide — Engineering Architecture
## Namastey Udaipur v2.0
**Author:** Nikhil Tiwari, Co-Founder & Product-Tech Lead
**Feature:** Proximity-Triggered Cultural Audio Guide
**Status:** Planned — v2.0

---

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        ANDROID DEVICE                           │
│                                                                 │
│  ┌──────────────────┐     ┌────────────────────────────────┐   │
│  │  Android OS      │     │  Namastey Udaipur App           │   │
│  │  Location Layer  │────►│                                 │   │
│  │  (Geofencing API)│     │  ┌──────────────────────────┐  │   │
│  └──────────────────┘     │  │ GeofenceAudioService      │  │   │
│                           │  │ (Foreground Service)      │  │   │
│  ┌──────────────────┐     │  └──────────┬───────────────┘  │   │
│  │  Android OS      │     │             │                   │   │
│  │  Notification    │◄────│  ┌──────────▼───────────────┐  │   │
│  │  System          │     │  │ AudioContentManager       │  │   │
│  └──────────────────┘     │  │ (reads from SQLite cache) │  │   │
│                           │  └──────────┬───────────────┘  │   │
│  ┌──────────────────┐     │             │                   │   │
│  │  Local Storage   │◄────│  ┌──────────▼───────────────┐  │   │
│  │  /audio/ta/*.mp3 │     │  │ AudioGuideActivity         │  │   │
│  │  /audio/bn/*.mp3 │────►│  │ (MediaPlayer + UI)        │  │   │
│  │  /audio/hi/*.mp3 │     │  └──────────────────────────┘  │   │
│  │  pois.db (SQLite)│     │                                 │   │
│  └──────────────────┘     └────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
            │ (WiFi only, on city pack download)
            ▼
┌─────────────────────────────────────────────────────────────────┐
│                      BACKEND SERVER                             │
│                                                                 │
│  City Pack Build Service                                        │
│  ├── Audio files: recorded → compressed → versioned            │
│  ├── POI database: structured content + translations            │
│  ├── Geofence coordinates: lat/lng + radius per POI             │
│  └── Packaged as: udaipur-city-pack-v{N}.zip                   │
└─────────────────────────────────────────────────────────────────┘
```

---

## Geofencing Design Decisions

### Why Android Geofencing API (not continuous GPS)

**Rejected alternative:** Continuous GPS polling in a background thread.

**Why rejected:**
- Battery drain: continuous GPS = ~15% additional drain per hour
- Android background restrictions (API 26+) would kill the service
- Unnecessary precision — we don't need metre-level accuracy, we need 50m radius

**Chosen approach:** Android Geofencing API (via Google Play Services)

**Why:**
- OS-managed — Android batches and optimises location checks
- Typical battery impact: < 1% additional drain
- Reliable ENTER/EXIT/DWELL transition events
- Survives app backgrounding

**Trade-off accepted:** Requires Google Play Services — acceptable since target market (Indian Android tourists) overwhelmingly use Play Services-equipped devices.

---

### Geofence Configuration

```java
// Registered for each heritage site POI in city pack
Geofence geofence = new Geofence.Builder()
    .setRequestId(poi.getId())                    // poi_id as geofence ID
    .setCircularRegion(
        poi.getLatitude(),
        poi.getLongitude(),
        50.0f                                     // 50m radius
    )
    .setExpirationDuration(Geofence.NEVER_EXPIRE) // Active for whole trip
    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
    .setLoiteringDelay(30_000)                    // 30s dwell before DWELL fires
    .build();
```

**50m radius rationale:**
- < 50m: Too tight — user walks past without triggering; GPS jitter causes false misses
- > 100m: Too loose — triggers while user is still on street, before they enter site
- 50m: Sweet spot for Indian heritage sites; most entry plazas are ~30-40m wide

**Dead zone buffer:** Adjacent POIs (e.g. Jagdish Temple and City Palace, 400m apart) have sufficient separation. No overlap issue in Udaipur's heritage geography.

---

## Audio Content Pipeline

```
CONTENT CREATION
│
├── 1. Historian writes script per POI per chapter (English)
├── 2. Cultural consultant reviews for accuracy
├── 3. AIESEC language volunteers translate (Tamil, Bengali, Hindi)
├── 4. Community review — bilingual members validate translation
├── 5. Professional voice artist records (one per language)
│      Recording spec: 44.1kHz WAV, quiet studio environment
├── 6. Audio engineer normalises levels, removes noise
├── 7. Compressed to 64kbps MP3 (quality/size balance)
│      Typical chapter: 3-5 min = ~1.5-2.5MB per language
└── 8. Versioned and packaged into city pack

CITY PACK STRUCTURE
udaipur-city-pack-v2.zip (~85MB)
├── manifest.json          (version, checksums, POI list)
├── pois.db                (SQLite — all POI data + translations)
├── geofences.json         (coordinates for all registered POIs)
├── map-tiles/             (Offline map tiles)
└── audio/
    ├── ta/
    │   ├── city-palace/
    │   │   ├── ch1-intro.mp3          (~1.8MB)
    │   │   ├── ch2-zenana-mahal.mp3   (~2.1MB)
    │   │   ├── ch3-mardana-mahal.mp3  (~1.9MB)
    │   │   └── transcript-ta.json
    │   ├── jagdish-temple/
    │   └── fateh-sagar-lake/
    ├── bn/ (same structure)
    └── hi/ (same structure)
```

---

## Audio Playback — MediaPlayer State Machine

```
[IDLE]
  │ prepare(audioFilePath)
  ▼
[PREPARED]
  │ start()
  ▼
[PLAYING] ──── pause() ────► [PAUSED]
  │               ◄─────────── start()
  │ completion
  ▼
[CHAPTER_COMPLETE]
  │ if next chapter exists && autoAdvance == true
  ▼
[LOADING_NEXT]
  │ prepare(nextChapterPath)
  ▼
[PLAYING] (next chapter)
  │
  │ if all chapters complete
  ▼
[TOUR_COMPLETE]
  → Show "Review this site" prompt
  → Unlock "next site" suggestion
```

**Audio Focus handling:**
```java
// AudioGuideActivity.java — request audio focus before playing
AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
int result = am.requestAudioFocus(
    afChangeListener,
    AudioManager.STREAM_MUSIC,
    AudioManager.AUDIOFOCUS_GAIN
);

if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
    mediaPlayer.start();
}

// Release focus and pause on incoming call
AudioManager.OnAudioFocusChangeListener afChangeListener = focusChange -> {
    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
        mediaPlayer.pause();   // Phone call incoming — pause
    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
        mediaPlayer.start();   // Call ended — resume
    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
        mediaPlayer.stop();    // Permanent loss — stop
    }
};
```

---

## Battery & Performance Constraints

| Concern | Target | Approach |
|---|---|---|
| Geofence battery drain | < 1% additional /hr | OS-managed geofencing, not continuous GPS |
| Audio playback drain | Acceptable (standard media) | No special optimisation needed |
| City pack download size | < 100MB | 64kbps MP3 compression; tiles only for city area |
| SQLite query time | < 50ms | Indexed on poi_id and language |
| Notification delivery | < 2s after geofence trigger | Foreground service — not subject to Doze |

---

## Android Permissions & User Consent

```xml
<!-- AndroidManifest.xml additions for Audio Guide -->

<!-- Location — required for geofencing -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- Background location — required for geofencing while app is backgrounded -->
<!-- Must show additional rationale dialog on Android 10+ -->
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />

<!-- Foreground service — keeps geofence active -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Restart geofence service after device reboot -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- Service declaration -->
<service
    android:name=".GeofenceAudioService"
    android:exported="false"
    android:foregroundServiceType="location" />
```

**Consent UX:**

Location permission requested at first audio guide interaction, not at app install. Dialog shows:

> "Namastey Udaipur needs location access to automatically play Tamil audio guides as you approach heritage sites — like a museum headset for the whole city."
> [Allow] [Not now — I'll play manually]

If denied: Audio guide accessible manually via POI detail screen. Geofence notifications simply don't fire. Core app is fully functional.

---

## v2 Technical Debt Accepted

| Debt | Reason Accepted |
|---|---|
| No audio streaming — only local playback | Connectivity unreliable in old city; offline-first is non-negotiable |
| No user-contributed audio | Content quality risk; moderation complexity out of scope for v2 |
| Single geofence radius (50m) for all POIs | Configurable radius is v2.1 — good enough for launch |
| No dynamic content updates mid-trip | City pack versioning handles this — updates require new download |

---

*Engineering document authored by Nikhil Tiwari · Namastey Udaipur · Proprietary*
