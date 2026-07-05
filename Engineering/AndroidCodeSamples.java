// ============================================================
// Namastey Udaipur — Android MVP Core Code Samples
// Author: Nikhil Tiwari, Co-Founder & Product-Tech Lead
// Platform: Android API 16+ (Java)
// Year: 2015
// ============================================================
// PROPRIETARY — See LICENSE.md. Unauthorized use prohibited.
// ============================================================

package com.namasteyudaipur.app;

// ─────────────────────────────────────────────────────────────
// FILE 1: CulturalSession.java
// Manages user language preference + cultural profile
// Persisted via SharedPreferences across app sessions
// ─────────────────────────────────────────────────────────────

import android.content.Context;
import android.content.SharedPreferences;

public class CulturalSession {

    private static final String PREFS_NAME    = "namastey_prefs";
    private static final String KEY_LANGUAGE  = "user_language";
    private static final String KEY_STATE     = "user_home_state";
    private static final String KEY_ONBOARDED = "onboarding_complete";

    // Language codes matching Android string resource directories
    public static final String LANG_TAMIL   = "ta";
    public static final String LANG_BENGALI = "bn";
    public static final String LANG_HINDI   = "hi";
    public static final String LANG_ENGLISH = "en";

    private final SharedPreferences prefs;

    public CulturalSession(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /** Save the user's chosen language at onboarding */
    public void setLanguage(String languageCode) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply();
    }

    /** Returns language code, defaults to English if not set */
    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, LANG_ENGLISH);
    }

    /** Optional: user's home state for finer cultural filtering */
    public void setHomeState(String state) {
        prefs.edit().putString(KEY_STATE, state).apply();
    }

    public String getHomeState() {
        return prefs.getString(KEY_STATE, null); // null = not set, filter by language only
    }

    public boolean isOnboardingComplete() {
        return prefs.getBoolean(KEY_ONBOARDED, false);
    }

    public void markOnboardingComplete() {
        prefs.edit().putBoolean(KEY_ONBOARDED, true).apply();
    }

    /** Check if user has selected a non-default language (signal for CCTC metric) */
    public boolean hasNonDefaultLanguage() {
        String lang = getLanguage();
        return !LANG_ENGLISH.equals(lang) && !LANG_HINDI.equals(lang);
    }
}


// ─────────────────────────────────────────────────────────────
// FILE 2: LanguageSelectionActivity.java
// Screen 1 — mandatory first screen
// The product thesis starts here
// ─────────────────────────────────────────────────────────────

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class LanguageSelectionActivity extends Activity {

    private CulturalSession culturalSession;
    private String selectedLanguage = CulturalSession.LANG_ENGLISH; // safe default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        culturalSession = new CulturalSession(this);

        // If user already onboarded, skip straight to home
        if (culturalSession.isOnboardingComplete()) {
            launchHome();
            return;
        }

        setupLanguageButtons();
        setupContinueButton();
    }

    private void setupLanguageButtons() {
        // Each button tag holds the language code it represents
        int[] buttonIds = {
            R.id.btn_tamil,
            R.id.btn_bengali,
            R.id.btn_hindi,
            R.id.btn_english
        };
        String[] langCodes = {
            CulturalSession.LANG_TAMIL,
            CulturalSession.LANG_BENGALI,
            CulturalSession.LANG_HINDI,
            CulturalSession.LANG_ENGLISH
        };

        for (int i = 0; i < buttonIds.length; i++) {
            final String lang = langCodes[i];
            Button btn = (Button) findViewById(buttonIds[i]);
            btn.setTag(lang);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLanguageSelected(lang);
                }
            });
        }
    }

    private void onLanguageSelected(String languageCode) {
        selectedLanguage = languageCode;
        updateButtonStates();

        // Analytics: track which language was chosen at onboarding
        AnalyticsHelper.track(this, "language_selected", new AnalyticsHelper.Params()
            .put("language", languageCode)
            .put("source", "onboarding"));
    }

    private void updateButtonStates() {
        // Visually mark selected button; deselect others
        int[] buttonIds = {
            R.id.btn_tamil, R.id.btn_bengali,
            R.id.btn_hindi, R.id.btn_english
        };
        for (int id : buttonIds) {
            Button btn = (Button) findViewById(id);
            boolean isSelected = selectedLanguage.equals(btn.getTag());
            btn.setSelected(isSelected);
            // State list drawable handles visual change via XML
        }
    }

    private void setupContinueButton() {
        Button continueBtn = (Button) findViewById(R.id.btn_continue);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueTapped();
            }
        });
    }

    private void onContinueTapped() {
        // Persist the language choice
        culturalSession.setLanguage(selectedLanguage);
        culturalSession.markOnboardingComplete();

        // Apply locale so the app immediately renders in chosen language
        applyLocale(selectedLanguage);

        // Navigate to home
        launchHome();
    }

    /**
     * Applies Android locale so all string resources switch to the chosen language.
     * Requires string resource folders: values-ta/, values-bn/, values-hi/, values/
     */
    private void applyLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
            getResources().getDisplayMetrics());
    }

    private void launchHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // Remove from back stack — user should not back-navigate to language select
    }
}


// ─────────────────────────────────────────────────────────────
// FILE 3: Restaurant.java
// Data model for a culturally-filterable restaurant
// ─────────────────────────────────────────────────────────────

import java.util.List;

public class Restaurant {

    private String  id;
    private String  nameEn;
    private String  nameTa;       // Tamil name
    private String  nameBn;       // Bengali name
    private String  nameHi;       // Hindi name
    private String  cuisine;
    private double  latitude;
    private double  longitude;
    private String  phone;
    private boolean isOpenNow;
    private double  overallRating;

    // Community-specific data
    private int    tamilReviewCount;
    private double tamilRating;
    private int    bengaliReviewCount;
    private double bengaliRating;
    private int    hindiReviewCount;
    private double hindiRating;

    // Cultural amenity flags — the heart of the feature
    private boolean hasFilterCoffee;
    private boolean hasSouthIndianBreakfast;
    private boolean isVegetarianOnly;
    private boolean hasHalalMenu;
    private boolean hasNorthIndianFood;
    private boolean hasChettinadFood;

    private List<String> culturalTags; // e.g. ["filter_coffee", "south_indian_breakfast"]

    // Distance calculated client-side from user location
    private float distanceKm;

    /** Returns the restaurant name in the user's chosen language */
    public String getLocalizedName(String languageCode) {
        switch (languageCode) {
            case "ta": return nameTa != null ? nameTa : nameEn;
            case "bn": return nameBn != null ? nameBn : nameEn;
            case "hi": return nameHi != null ? nameHi : nameEn;
            default:   return nameEn;
        }
    }

    /** Returns review count for a specific language community */
    public int getCommunityReviewCount(String languageCode) {
        switch (languageCode) {
            case "ta": return tamilReviewCount;
            case "bn": return bengaliReviewCount;
            case "hi": return hindiReviewCount;
            default:   return 0;
        }
    }

    /** Returns community-specific rating, falls back to overall */
    public double getCommunityRating(String languageCode) {
        switch (languageCode) {
            case "ta": return tamilReviewCount > 0 ? tamilRating : overallRating;
            case "bn": return bengaliReviewCount > 0 ? bengaliRating : overallRating;
            case "hi": return hindiReviewCount > 0 ? hindiRating : overallRating;
            default:   return overallRating;
        }
    }

    /** True if this restaurant has any reviews from the user's language community */
    public boolean hasCommunityReviews(String languageCode) {
        return getCommunityReviewCount(languageCode) > 0;
    }

    // Standard getters / setters omitted for brevity
    public String getId()           { return id; }
    public String getNameEn()       { return nameEn; }
    public double getLatitude()     { return latitude; }
    public double getLongitude()    { return longitude; }
    public float getDistanceKm()    { return distanceKm; }
    public void setDistanceKm(float d) { this.distanceKm = d; }
    public boolean hasFilterCoffee()    { return hasFilterCoffee; }
    public List<String> getCulturalTags() { return culturalTags; }
    public double getOverallRating()    { return overallRating; }
}


// ─────────────────────────────────────────────────────────────
// FILE 4: RestaurantAdapter.java
// RecyclerView adapter — renders restaurants with cultural context
// Shows Tamil reviews prominently; dims zero-review restaurants
// ─────────────────────────────────────────────────────────────

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter
        extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final Context        context;
    private final List<Restaurant> restaurants;
    private final String         userLanguage;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(Context context, List<Restaurant> restaurants,
                             String userLanguage, OnItemClickListener listener) {
        this.context      = context;
        this.restaurants  = restaurants;
        this.userLanguage = userLanguage;
        this.listener     = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
            .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant r = restaurants.get(position);

        // Localised name in user's language
        holder.nameText.setText(r.getLocalizedName(userLanguage));

        // Community-specific rating and review count
        double communityRating = r.getCommunityRating(userLanguage);
        int reviewCount        = r.getCommunityReviewCount(userLanguage);

        holder.ratingText.setText(String.format("%.1f", communityRating));

        // Community badge — the core differentiator
        if (r.hasCommunityReviews(userLanguage)) {
            holder.communityBadge.setVisibility(View.VISIBLE);
            holder.communityBadge.setText(
                reviewCount + " " +
                getCommunityLabel(userLanguage) + " " +
                context.getString(R.string.travelers_reviewed_this)
            );
            // Full opacity — this restaurant has community validation
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.communityBadge.setVisibility(View.VISIBLE);
            holder.communityBadge.setText(
                context.getString(R.string.no_community_reviews_yet)
            );
            // Dimmed — no community signal yet
            holder.itemView.setAlpha(0.55f);
        }

        // Distance
        holder.distanceText.setText(
            String.format("%.1f km", r.getDistanceKm())
        );

        // Cultural tags (filter coffee, veg only, etc.)
        bindCulturalTags(holder, r);

        holder.itemView.setOnClickListener(v -> {
            AnalyticsHelper.track(context, "poi_viewed", new AnalyticsHelper.Params()
                .put("poi_id", r.getId())
                .put("poi_type", "restaurant")
                .put("has_community_reviews", r.hasCommunityReviews(userLanguage))
                .put("user_language", userLanguage));
            listener.onRestaurantClick(r);
        });
    }

    private String getCommunityLabel(String languageCode) {
        switch (languageCode) {
            case "ta": return context.getString(R.string.community_tamil);
            case "bn": return context.getString(R.string.community_bengali);
            case "hi": return context.getString(R.string.community_hindi);
            default:   return context.getString(R.string.community_traveler);
        }
    }

    private void bindCulturalTags(ViewHolder holder, Restaurant r) {
        // Tag container cleared and re-populated each bind
        holder.tagsContainer.removeAllViews();
        if (r.hasFilterCoffee()) {
            addTag(holder, context.getString(R.string.tag_filter_coffee));
        }
        if (r.hasSouthIndianBreakfast()) {
            addTag(holder, context.getString(R.string.tag_south_indian_breakfast));
        }
        if (r.isVegetarianOnly()) {
            addTag(holder, context.getString(R.string.tag_veg_only));
        }
    }

    private void addTag(ViewHolder holder, String label) {
        TextView tag = (TextView) LayoutInflater.from(context)
            .inflate(R.layout.chip_cultural_tag, holder.tagsContainer, false);
        tag.setText(label);
        holder.tagsContainer.addView(tag);
    }

    @Override
    public int getItemCount() { return restaurants.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, ratingText, distanceText, communityBadge;
        android.widget.LinearLayout tagsContainer;

        ViewHolder(View itemView) {
            super(itemView);
            nameText       = itemView.findViewById(R.id.text_restaurant_name);
            ratingText     = itemView.findViewById(R.id.text_rating);
            distanceText   = itemView.findViewById(R.id.text_distance);
            communityBadge = itemView.findViewById(R.id.text_community_badge);
            tagsContainer  = itemView.findViewById(R.id.container_tags);
        }
    }
}


// ─────────────────────────────────────────────────────────────
// FILE 5: GeofenceAudioService.java  [v2.0 — Audio Guide Feature]
// Background service that monitors user proximity to heritage sites
// Fires notification when user enters a 50m geofence around a POI
// Triggers the audio guide player in the user's language
// ─────────────────────────────────────────────────────────────

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceAudioService extends IntentService {

    private static final String TAG             = "GeofenceAudioService";
    private static final int    NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID      = "audio_guides";

    public GeofenceAudioService() {
        super("GeofenceAudioService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) return;

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            // Log error — don't crash; audio guide is an enhancement, not core
            return;
        }

        int transition = geofencingEvent.getGeofenceTransition();

        // Only act on ENTER — not EXIT or DWELL
        if (transition != Geofence.GEOFENCE_TRANSITION_ENTER) return;

        List<Geofence> triggeredGeofences = geofencingEvent.getTriggeringGeofences();
        if (triggeredGeofences == null || triggeredGeofences.isEmpty()) return;

        // Take the first triggered geofence
        String poiId = triggeredGeofences.get(0).getRequestId();

        // Retrieve user's language
        CulturalSession session = new CulturalSession(this);
        String language = session.getLanguage();

        // Check if audio file exists for this POI + language (offline check)
        AudioContentManager audioManager = new AudioContentManager(this);
        if (!audioManager.hasAudioForPoi(poiId, language)) {
            // No audio available — silently skip. Don't notify.
            return;
        }

        // Retrieve POI name in user's language
        String poiName = audioManager.getPoiName(poiId, language);

        // Fire the proximity notification
        sendProximityNotification(poiId, poiName, language);

        // Analytics: geofence triggered
        AnalyticsHelper.track(this, "audio_guide_geofence_triggered",
            new AnalyticsHelper.Params()
                .put("poi_id", poiId)
                .put("language", language)
                .put("audio_available", true));
    }

    private void sendProximityNotification(String poiId, String poiName, String language) {
        // Intent to open AudioGuideActivity when notification tapped
        Intent openPlayerIntent = new Intent(this, AudioGuideActivity.class);
        openPlayerIntent.putExtra(AudioGuideActivity.EXTRA_POI_ID, poiId);
        openPlayerIntent.putExtra(AudioGuideActivity.EXTRA_LANGUAGE, language);
        openPlayerIntent.putExtra(AudioGuideActivity.EXTRA_AUTOPLAY, true);
        openPlayerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, NOTIFICATION_ID, openPlayerIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        // Build notification — title in user's language
        String title    = getString(R.string.notif_audio_guide_title);   // "नमस्ते · You're near"
        String bodyText = poiName + " — "
                        + getString(R.string.notif_audio_guide_body);   // "Tamil audio guide ready"

        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_lotus)
                .setContentTitle(title + " " + poiName)
                .setContentText(bodyText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                // Action button: Play immediately
                .addAction(R.drawable.ic_play,
                    getString(R.string.notif_action_play),
                    pendingIntent)
                // Action button: Dismiss
                .addAction(R.drawable.ic_dismiss,
                    getString(R.string.notif_action_dismiss),
                    getDismissPendingIntent());

        NotificationManager nm =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private PendingIntent getDismissPendingIntent() {
        Intent dismissIntent = new Intent(this, NotificationDismissReceiver.class);
        return PendingIntent.getBroadcast(this, 0, dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
    }
}


// ─────────────────────────────────────────────────────────────
// FILE 6: AnalyticsHelper.java
// Thin wrapper around Firebase Analytics
// All product events flow through here for consistency
// ─────────────────────────────────────────────────────────────

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsHelper {

    /**
     * Track a product event with optional parameters.
     * All events go through this single method for consistency.
     * Event names match the schema defined in analytics/EventSchema.md
     */
    public static void track(Context context, String eventName, Params params) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = params != null ? params.toBundle() : new Bundle();
        analytics.logEvent(eventName, bundle);
    }

    public static void track(Context context, String eventName) {
        track(context, eventName, null);
    }

    /** Fluent builder for event parameters */
    public static class Params {
        private final Bundle bundle = new Bundle();

        public Params put(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Params put(String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        public Params put(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public Bundle toBundle() { return bundle; }
    }
}

// ─────────────────────────────────────────────────────────────
// END OF CODE SAMPLES
//
// Additional implementation files available on request:
//   HomeActivity.java          — cultural home screen
//   RestaurantDetailActivity   — POI detail with Tamil reviews
//   AudioGuideActivity.java    — audio player with scrubber
//   AudioContentManager.java   — offline audio file management
//   GeofenceManager.java       — register/deregister geofences
//   ApiService.java            — Retrofit API client
//   CulturalSortComparator.java — sorts restaurants: community-reviewed first
//
// Architecture: LAMP backend + Android Java client
// See engineering/Architecture.md for full system design
// ─────────────────────────────────────────────────────────────
