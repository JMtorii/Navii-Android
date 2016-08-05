package com.teamawesome.navii.util;

/**
 * Created by JMtorii on 15-09-19.
 */
public class Constants {



    private Constants() {
    }

    // Server
//    public static final String SERVER_URL = "http://192.168.1.172:8080";
    public static final String SERVER_URL = "https://naviappweb.com";
    public static final String SERVER_URL_JUN = "http://192.168.57.1:8080";
    public static final String SERVER_URL_STEVE = "http://192.168.2.114:8080";

    public static final String PICTURE_BUCKET = "elasticbeanstalk-us-east-1-107709745779/images";

    // View
    public static final int NO_ANIM = -1;

    // Tags
    public static final String INTRO_THANKS_FRAGMENT_TAG = "INTRO_THANKS_FRAGMENT_TAG";
    public static final String CHANGE_PASSWORD_FRAGMENT = "CHANGE_PASSWORD_FRAGMENT";
    public static final String PLANNING_CHOOSE_TAGS_FRAGMENT_TAG = "PLANNING_CHOOSE_TAGS_FRAGMENT_TAG";
    public static final String PREFERENCES_FRAGMENT_TAG = "PREFERENCES_FRAGMENT_TAG";
    public static final String EDIT_PROFILE_FRAGMENT_TAG = "EDIT_PROFILE_FRAGMENT_TAG";
    public static final String LOADING_WHEEL_TAG = "LOADING_WHEEL";

    // Preference Types
    public static final int PREFERENCE_TYPE_1 = 1;
    public static final int PREFERENCE_MIN_LIMIT = 1;
    public static final int PREFERENCE_MAX_LIMIT = 3;

    // Intents
    public static final String INTENT_TAGS = "TAGS";
    public static final String INTENT_ATTRACTION = "INTENT_ATTRACTION";
    public static final String INTENT_ITINERARY_TITLE = "INTENT_ITINERARY_TITLE";
    public static final String INTENT_ATTRACTION_LOCATION = "INTENT_ATTRACTION_LOCATION";
    public static final String INTENT_ATTRACTION_LIST = "INTENT_ATTRACTION_LIST";

    // Permissions
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 51808;
}