package com.teamawesome.navii.util;

/**
 * Created by JMtorii on 15-09-19.
 */
public class Constants {

    private Constants() {
    }

    public static final String SERVER_URL = "http://192.168.0.21:8080";
    //public static final String SERVER_URL = "http://10.0.3.2:8080";
    public static final String SERVER_URL_JUN = "http://142.151.249.132:8080";
    public static final String SERVER_URL_STEVE = "http://192.168.2.114:8080";
    public static final String SERVER_URL_PROD = "https://www.naviappweb.com:443";

    public static final String PICTURE_BUCKET = "elasticbeanstalk-us-east-1-107709745779/images";

    public static final int TRENDING_DESTINATION_DURATION_MS = 5000;

    // View
    public static final int NO_ANIM = -1;

    // Tags
    public static final String CHOOSE_LOCATION_FRAGMENT_TAG = "CHOOSE_LOCATION_FRAGMENT_TAG";
    public static final String INTRO_PAYMENT_FRAGMENT_TAG = "INTRO_PAYMENT_FRAGMENT_TAG";
    public static final String INTRO_THANKS_FRAGMENT_TAG = "INTRO_THANKS_FRAGMENT_TAG";

    public static final String CHANGE_PASSWORD_FRAGMENT = "CHANGE_PASSWORD_FRAGMENT";


    public static final String PLANNING_SAVED_TRIPS_FRAGMENT_TAG = "PLANNING_SAVED_TRIPS_FRAGMENT_TAG";
    public static final String PLANNING_PLANNED_TRIPS_FRAGMENT_TAG = "PLANNING_PLANNED_TRIPS_FRAGMENT_TAG";
    public static final String PLANNING_CHOOSE_TAGS_FRAGMENT_TAG = "PLANNING_CHOOSE_TAGS_FRAGMENT_TAG";

    public static final String NOTIFICATIONS_FRAGMENT_TAG = "NOTIFICATIONS_FRAGMENT_TAG";
    public static final String PREFERENCES_FRAGMENT_TAG = "PREFERENCES_FRAGMENT_TAG";
    public static final String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    public static final String EDIT_PROFILE_FRAGMENT_TAG = "EDIT_PROFILE_FRAGMENT_TAG";

    public static final String ITINERARY_RECOMMEND_FRAGMENT = "ITINERARY_RECOMMEND_FRAGMENT";
    public static final String PACKAGE_DESCRIPTION = "PACKAGE_DESCRIPTION";
    public static final String NO_WIFI_DIALOG = "NO_WIFI";
    public static final String LOADING_WHEEL_TAG = "LOADING_WHEEL";

    // Preference Types
    public static final int PREFERENCE_TYPE_1 = 1;
    public static final int PREFERENCE_TYPE_2 = 2;
    public static final int PREFERENCE_TYPE_3 = 3;

    public static final int PREFERENCE_MIN_LIMIT = 1;
    public static final int PREFERENCE_MAX_LIMIT = 3;

    // Permissions
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 51808;
}