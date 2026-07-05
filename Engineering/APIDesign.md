# API Design
## Namastey Udaipur — Backend REST API
**Author:** Nikhil Tiwari, Co-Founder & Product-Tech Lead
**Version:** v1.0 (MVP) · v2.0 (planned)
**Base URL:** `https://api.namasteyudaipur.com/v1`
**Format:** JSON · UTF-8 · All regional scripts encoded properly

---

## Authentication

MVP uses API key authentication via header:
```
X-API-Key: {device_api_key}
```
Device API key generated at first app launch, stored server-side tied to anonymous device ID.

---

## Core Endpoints

### GET /pois
Returns POIs filtered and sorted for the user's cultural context.

**Query parameters:**

| Param | Type | Required | Description |
|---|---|---|---|
| `lang` | string | Yes | Language code: `ta`, `bn`, `hi`, `en` |
| `type` | string | No | `restaurant`, `hotel`, `attraction`, `event` |
| `lat` | float | No | User latitude for distance calc |
| `lng` | float | No | User longitude for distance calc |
| `radius_km` | float | No | Search radius (default: 2.0) |
| `community_only` | bool | No | Only return POIs with community reviews in `lang` |
| `limit` | int | No | Max results (default: 20, max: 50) |

**Response:**
```json
{
  "status": "ok",
  "lang": "ta",
  "results": [
    {
      "id": "annapurna-south-indian-udaipur",
      "name": "அன்னபூர்ணா சாத்விக் ஹோட்டல்",
      "name_en": "Annapurna South Indian",
      "type": "restaurant",
      "cuisine": "South Indian",
      "lat": 24.5785,
      "lng": 73.6832,
      "distance_km": 0.3,
      "is_open_now": true,
      "overall_rating": 4.5,
      "community": {
        "lang": "ta",
        "review_count": 12,
        "rating": 4.7,
        "top_quote": "filter coffee மிகவும் நன்றாக இருந்தது"
      },
      "cultural_tags": [
        "filter_coffee",
        "south_indian_breakfast",
        "vegetarian_only"
      ],
      "cultural_tags_localised": [
        "ஃபில்டர் காஃபி",
        "தென்னிந்திய காலை உணவு",
        "சைவம் மட்டும்"
      ]
    }
  ],
  "meta": {
    "total": 47,
    "community_reviewed_count": 14,
    "returned": 20
  }
}
```

---

### GET /pois/{id}
Returns full detail for a single POI including community reviews.

**Query parameters:**

| Param | Type | Required | Description |
|---|---|---|---|
| `lang` | string | Yes | Language code |
| `reviews_lang` | string | No | Language of reviews to return (defaults to `lang`) |
| `review_limit` | int | No | Number of reviews (default: 10) |

**Response:**
```json
{
  "status": "ok",
  "poi": {
    "id": "city-palace-udaipur",
    "name": "நகர அரண்மனை",
    "name_en": "City Palace",
    "type": "attraction",
    "address": "City Palace Road, Old City, Udaipur",
    "address_localised": "சிட்டி பேலஸ் ரோடு, பழைய நகரம், உதய்பூர்",
    "lat": 24.5764,
    "lng": 73.6835,
    "opening_hours": "09:30 - 17:30",
    "entry_fee_inr": 30,
    "avg_visit_duration_mins": 120,
    "overall_rating": 4.8,
    "community": {
      "lang": "ta",
      "review_count": 8,
      "rating": 4.8,
      "highlights": [
        "Tamil audio guide available",
        "Wheelchair accessible ground floor",
        "Elderly-friendly route"
      ]
    },
    "reviews": [
      {
        "review_id": "rev-001",
        "author_name": "Priya S.",
        "author_city": "Chennai",
        "rating": 5,
        "text": "Tamil audio guide கிடைக்கிறது. Parents enjoy பண்ணினார்கள்.",
        "lang": "ta",
        "created_at": "2015-11-12T14:30:00Z"
      }
    ],
    "audio_guide_available": true,
    "audio_guide_languages": ["ta", "bn", "hi", "en"],
    "geofence": {
      "lat": 24.5764,
      "lng": 73.6835,
      "radius_m": 50
    }
  }
}
```

---

### GET /guides
Returns guides filtered by spoken language.

**Query parameters:**

| Param | Type | Required | Description |
|---|---|---|---|
| `speaks` | string | Yes | Language code user needs guide to speak |
| `date` | string | No | Availability date: `YYYY-MM-DD` |
| `specialty` | string | No | `heritage`, `food`, `nature`, `photography` |

**Response:**
```json
{
  "status": "ok",
  "guides": [
    {
      "id": "guide-rajan-kumar",
      "name": "Rajan Kumar",
      "languages": ["ta", "hi", "en"],
      "specialty": ["heritage", "lakes", "city_walk"],
      "specialty_localised": ["பாரம்பரியம்", "ஏரிகள்", "நகர நடை"],
      "aiesec_verified": true,
      "rating": 4.9,
      "review_count": 23,
      "daily_rate_inr": 800,
      "available_dates": ["2015-12-10", "2015-12-11", "2015-12-12"],
      "profile_photo_url": "https://cdn.namasteyudaipur.com/guides/rajan-kumar.jpg",
      "whatsapp_number": "+91XXXXXXXXXX"
    }
  ]
}
```

---

### POST /bookings
Creates a guide booking request.

**Request body:**
```json
{
  "guide_id": "guide-rajan-kumar",
  "user_device_id": "device-abc123",
  "tour_date": "2015-12-10",
  "tour_type": "full_day",
  "user_language": "ta",
  "user_name": "Karthik",
  "user_whatsapp": "+91XXXXXXXXXX",
  "group_size": 4,
  "notes": "Traveling with elderly parents. Need accessible route."
}
```

**Response:**
```json
{
  "status": "ok",
  "booking_id": "BK-20151210-001",
  "confirmation_message": "உங்கள் பதிவு பெறப்பட்டது. Rajan Kumar 45 நிமிடங்களில் WhatsApp மூலம் உறுதிப்படுத்துவார்.",
  "confirmation_message_en": "Your booking request is received. Rajan Kumar will confirm via WhatsApp within 45 minutes.",
  "guide_whatsapp": "+91XXXXXXXXXX",
  "booking_value_inr": 800
}
```

---

### POST /reviews
Submit a community review.

**Request body:**
```json
{
  "poi_id": "annapurna-south-indian-udaipur",
  "user_device_id": "device-abc123",
  "user_language": "ta",
  "user_home_state": "Tamil Nadu",
  "rating": 5,
  "text": "filter coffee மிகவும் நன்றாக இருந்தது. Parents மிகவும் enjoy பண்ணினார்கள்.",
  "cultural_tags_confirmed": ["filter_coffee", "vegetarian_only"]
}
```

**Response:**
```json
{
  "status": "ok",
  "review_id": "rev-ta-0047",
  "message": "நன்றி! உங்கள் மதிப்பாய்வு Tamil சமுதாயத்திற்கு உதவும்.",
  "message_en": "Thank you! Your review will help the Tamil community."
}
```

---

### GET /city-pack/manifest
Returns the latest city pack version and download URL.

**Query parameters:**

| Param | Type | Required | Description |
|---|---|---|---|
| `city` | string | Yes | `udaipur` |
| `lang` | string | Yes | Language code (determines which audio files to include) |
| `current_version` | int | No | Client's current pack version (for delta check) |

**Response:**
```json
{
  "status": "ok",
  "city": "udaipur",
  "lang": "ta",
  "latest_version": 3,
  "needs_update": true,
  "pack_size_mb": 82.4,
  "download_url": "https://cdn.namasteyudaipur.com/packs/udaipur-ta-v3.zip",
  "checksum_sha256": "a3f2b1...",
  "includes": {
    "pois_count": 247,
    "audio_pois_count": 12,
    "audio_chapters_count": 58,
    "map_tiles": true
  }
}
```

---

## Error Responses

All errors follow a consistent format:

```json
{
  "status": "error",
  "code": "INVALID_LANGUAGE",
  "message": "Language code 'xx' is not supported. Supported: ta, bn, hi, en",
  "message_localised": null
}
```

| HTTP Code | Error Code | Meaning |
|---|---|---|
| 400 | `MISSING_PARAM` | Required parameter absent |
| 400 | `INVALID_LANGUAGE` | Unsupported language code |
| 401 | `INVALID_API_KEY` | API key missing or invalid |
| 404 | `POI_NOT_FOUND` | POI ID does not exist |
| 429 | `RATE_LIMITED` | Too many requests (100/min per device) |
| 500 | `SERVER_ERROR` | Internal error |

---

## v2.0 Planned Endpoints

| Endpoint | Description |
|---|---|
| `GET /audio/geofences` | All geofence coordinates for city pack (replaces bundled JSON) |
| `GET /itinerary/generate` | AI-generated cultural itinerary (RAG-powered) |
| `GET /search?q=&lang=` | Multilingual semantic search across all POIs |
| `POST /events/rsvp` | Register interest in a local event |
| `GET /hotels` | Hotel listing with cultural amenity filtering |
| `POST /hotels/inquire` | Hotel inquiry with cultural preferences noted |

---

*API design authored by Nikhil Tiwari · Namastey Udaipur · Proprietary — see LICENSE.md*
