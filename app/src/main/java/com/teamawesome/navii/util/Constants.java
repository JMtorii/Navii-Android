package com.teamawesome.navii.util;

/**
 * Created by JMtorii on 15-09-19.
 */
public class Constants {
    private Constants() {}

    // TODO: move to config file based on environment. Have Jenkins inject?
    public static final String SERVER_URL = "http://192.168.57.1:8080";

    // View
    public static final int NO_ANIM = -1;

    // Tags
    public static final String INTRO_VIEW_PAGER_FRAGMENT_TAG = "INTRO_VIEW_PAGER_FRAGMENT_TAG";
    public static final String CHOOSE_LOCATION_FRAGMENT_TAG = "CHOOSE_LOCATION_FRAGMENT_TAG";
    public static final String INTRO_PAYMENT_FRAGMENT_TAG = "INTRO_PAYMENT_FRAGMENT_TAG";
    public static final String INTRO_THANKS_FRAGMENT_TAG = "INTRO_THANKS_FRAGMENT_TAG";

    public static final String PLANNING_SAVED_TRIPS_FRAGMENT_TAG = "PLANNING_SAVED_TRIPS_FRAGMENT_TAG";
    public static final String PLANNING_PLANNED_TRIPS_FRAGMENT_TAG = "PLANNING_PLANNED_TRIPS_FRAGMENT_TAG";

    public static final String NOTIFICATIONS_FRAGMENT_TAG = "NOTIFICATIONS_FRAGMENT_TAG";
    public static final String PREFERENCES_FRAGMENT_TAG = "PREFERENCES_FRAGMENT_TAG";
}
