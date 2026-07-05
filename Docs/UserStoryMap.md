# User Story Map
## Namastey Udaipur — Full Feature Backlog
**Author:** Nikhil Tiwari, Co-Founder & Product Lead
**Framework:** Jeff Patton's User Story Mapping
**Last Updated:** January 2016

---

## How to Read This Map

The horizontal axis = the user's journey (left to right, chronologically)
The vertical axis = priority (top = MVP, bottom = future releases)

```
USER ACTIVITIES →  [Discover App] [Onboard]  [Discover City] [Book/Act]   [During Trip]  [Post Trip]
                        │              │              │             │              │             │
BACKBONE (Epic)    ─────┼──────────────┼──────────────┼─────────────┼──────────────┼─────────────┼─────
                        │              │              │             │              │             │
MVP (v1.0)         ─────┼──────────────┼──────────────┼─────────────┼──────────────┼─────────────┼─────
                        │              │              │             │              │             │
v1.1               ─────┼──────────────┼──────────────┼─────────────┼──────────────┼─────────────┼─────
                        │              │              │             │              │             │
v2.0 (Future)      ─────┼──────────────┼──────────────┼─────────────┼──────────────┼─────────────┼─────
```

---

## BACKBONE — User Activities (The Journey)

| Step 1 | Step 2 | Step 3 | Step 4 | Step 5 | Step 6 |
|---|---|---|---|---|---|
| **Discover App** | **Onboard** | **Discover City** | **Book & Act** | **During Trip** | **Post Trip** |
| Find Namastey Udaipur | Set my language & profile | Find places I trust | Make a booking or action | Navigate and experience | Share and reflect |

---

## EPICS — User Goals per Activity

| Discover App | Onboard | Discover City | Book & Act | During Trip | Post Trip |
|---|---|---|---|---|---|
| Learn what app does | Select my language | Find Tamil food | Book a guide | Navigate to POI | Leave a review |
| Install app | Set home state | Find right hotel | Bookmark for later | Play audio guide | Recommend to family |
| Trust that it's for me | See Tamil content immediately | Discover local events | Call a restaurant | Get proximity alert | Plan next city trip |

---

## FULL STORY MAP

### Activity 1: Discover App

**MVP (v1.0)**
- As a Tamil traveler, I want to see that the app UI is in Tamil from the first screenshot in the Play Store, so I know it's actually for me before installing
- As a potential user, I want to read a 1-line description that immediately tells me this is for Indian regional language speakers, not generic English users

**v1.1**
- As a user referred by a Facebook travel group, I want a deep link that opens the app directly in Tamil mode so I don't have to configure anything
- As an AIESEC member, I want a referral code that tags my contribution when someone installs through my link

**v2.0**
- As a traveler searching "Tamil travel app Udaipur", I want the app to appear in Google Play search results with Tamil script in the title so I immediately recognise it

---

### Activity 2: Onboard

**MVP (v1.0)**
- As a new user, I want to select my preferred language as the very first action in the app, so the entire experience is immediately in my language ✅
- As a Tamil user, I want to see the app title appear in Tamil script after I select my language, so I feel the app is truly mine ✅
- As a user with elderly parents, I want to optionally tell the app I'm traveling with elderly people, so recommendations are filtered for accessibility
- As a Bengali user, I want to see Bengali script render correctly without garbled characters, so I can actually read the UI ✅

**v1.1**
- As a returning user, I want my language preference remembered across app restarts without re-selecting every time ✅
- As a user changing my mind, I want to change my language from Settings at any time without losing my saved places
- As a user who prefers English UI but wants Tamil community reviews, I want to set "UI language" and "community filter" independently

**v2.0**
- As a new user, I want the app to detect my likely language from my device settings and pre-select it, so onboarding takes one tap not three
- As an elderly user, I want to set a larger text size preference during onboarding that persists across all screens

---

### Activity 3: Discover City

**MVP (v1.0)**
- As a Tamil traveler, I want restaurants sorted and filtered by Tamil community ratings by default, so I don't have to wade through irrelevant English reviews ✅
- As a food-conscious traveler, I want to see cultural amenity tags (filter coffee, South Indian breakfast, halal, veg-only) on every restaurant card, so I can decide at a glance ✅
- As a heritage tourist, I want to see a list of Udaipur's major sites explained in Tamil, so I know which ones to prioritise ✅
- As a hotel guest, I want to filter hotels by cultural amenity tags before booking, so I'm not surprised on arrival ✅
- As a curious traveler, I want to discover local events happening during my trip dates, explained in my language ✅

**v1.1**
- As a user with no Tamil reviews near me, I want to see a "be the first Tamil reviewer here" prompt, so I know the community is growing and I can contribute
- As a user saving content, I want to bookmark restaurants and hotels into a personal trip list, so I can access them quickly without searching again ✅ (partial)
- As a user in low connectivity, I want to see previously loaded content even when I lose signal, so I'm not stuck with a blank screen in the old city ✅ (partial)

**v2.0**
- As a Tamil traveler, I want to search for "filter coffee" in Tamil script and get relevant results, so I don't have to switch to English for search
- As a first-time Udaipur visitor, I want a curated "Tamil Traveler's Udaipur in 3 Days" itinerary pre-built for me, so I don't have to plan from scratch
- As a user near a heritage site, I want my phone to automatically notify me that a Tamil audio guide is available, so I don't miss the experience *(Audio Guide — see AudioGuide_PRD.md)*

---

### Activity 4: Book & Act

**MVP (v1.0)**
- As a traveler wanting a guide, I want to see guides who speak Tamil listed first, so I don't have to scan through irrelevant profiles ✅
- As a user selecting a guide, I want to see the AIESEC verification badge, so I trust the guide before committing ✅
- As a user booking a guide, I want to confirm via WhatsApp (not phone call), so I can book asynchronously without a voice call ✅ (v1.1 patch)
- As a user navigating to a restaurant, I want to tap a card and get walking directions in Google Maps, so I don't have to copy-paste an address ✅

**v1.1**
- As a user who bookmarked a restaurant, I want to see all my saved places in one screen, so I can plan my day from my shortlist
- As a user unsure about a restaurant, I want to call them directly from the app, so I can ask if they serve filter coffee before walking over

**v2.0**
- As a user booking a guide, I want to pay in-app via UPI, so I don't have to carry cash or arrange payment separately
- As a traveler booking a hotel, I want to complete the full booking within the app and have my cultural preferences (South Indian breakfast, no beef) noted automatically in the booking confirmation
- As a user who booked a guide, I want to receive a WhatsApp message the morning of my tour with the guide's location and a Tamil introduction, so I feel prepared

---

### Activity 5: During Trip

**MVP (v1.0)**
- As a traveler in the old city, I want cached restaurant data available even without a signal, so I can navigate to food even when connectivity drops ✅ (partial)
- As a traveler at a heritage site, I want to see the site's name in Tamil on the map pin, so I can recognise where I am without translating ✅

**v1.1**
- As a traveler who downloaded the city pack, I want full offline access to all my saved places, audio files, and guide contact, so I'm never stuck without information

**v2.0 — Audio Guide Feature (see AudioGuide_PRD.md)**
- As a traveler approaching City Palace, I want my phone to vibrate and show a notification: "Tamil audio guide ready for City Palace", so I know I can hear the story of this place *(Geofence trigger)*
- As a traveler listening to a Tamil audio guide, I want to control playback speed (0.75x for my elderly parents), so they can follow at a comfortable pace *(Speed control)*
- As a traveler who missed part of the audio, I want to scrub back 15 seconds with one tap, so I can replay a detail I didn't catch *(Skip back)*
- As a traveler moving from one zone to another within City Palace, I want the next audio chapter to auto-load, so the experience is continuous like a museum headset *(Auto-advance)*
- As a hearing-impaired traveler, I want to read a Tamil transcript of the audio guide while it plays, so I get the same cultural context others hear *(Transcript)*
- As a traveler walking toward Jagdish Temple, I want a notification telling me about the audio guide available there too, so I don't miss any stories along my route *(Multi-POI)*

---

### Activity 6: Post Trip

**MVP (v1.0)**
- *(No post-trip features shipped — largest gap in MVP)*

**v1.1**
- As a Tamil traveler returning home, I want to receive a push notification 48 hours after my last session asking me to review the places I visited, so I contribute to the Tamil community while memories are fresh
- As a traveler who had a great guide experience, I want to rate my guide in-app, so future Tamil travelers can trust the same guide
- As a traveler who found a great filter coffee spot, I want to leave a review in Tamil with one tap, so other Tamil travelers benefit from my discovery *(In-app review submission — highest priority v1.1)*

**v2.0**
- As a Tamil traveler home from Udaipur, I want to share a "My Tamil Udaipur Trip" summary card on WhatsApp that shows my top-rated places, so I help family and friends plan their own trips
- As a frequent traveler, I want to see my contribution count ("You've helped 43 Tamil travelers find great food"), so I feel valued in the community
- As a traveler who loved Udaipur, I want to register interest in a Jaipur version, so I'm notified when Namastey Udaipur expands there

---

## Story Sizing Reference

| Size | Story Points | Description | Examples |
|---|---|---|---|
| XS | 1 | UI copy change, config tweak | Change button label, add FAQ entry |
| S | 2 | Single-screen UI change | Add filter chip, update card layout |
| M | 3 | New UI component or simple API | Language badge component, WhatsApp deeplink |
| L | 5 | New screen or complex feature | Review submission screen, offline banner |
| XL | 8 | Multi-screen feature with backend | Guide booking flow, city pack download |
| XXL | 13 | Major system feature | Geofencing + audio guide pipeline, AI trip planner |

---

*User Story Map authored by Nikhil Tiwari · Namastey Udaipur · Proprietary — see LICENSE.md*
