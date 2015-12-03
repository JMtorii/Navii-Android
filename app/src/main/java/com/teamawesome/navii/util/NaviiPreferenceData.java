package com.teamawesome.navii.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.teamawesome.navii.NaviiApplication;

/**
 * Created by JMtorii on 15-09-12.
 *
 * This class is used to simplify calls to SharedPreferences.
 */
public class NaviiPreferenceData {

    // TODO: move to Constants?
    private static final String PREF_IP_ADDRESS = "ip_address";
    private static final String PREF_LOGGED_IN_USER_ID = "logged_in_id";
    private static final String PREF_LOGGED_IN_USER_EMAIL = "logged_in_email";
    private static final String PREF_IS_FACEBOOK = "is_facebook";


    public static void setIPAddress(String address) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_IP_ADDRESS, address);
        editor.apply();
    }

    public static String getIPAddress() {
        return getSharedPreferences().getString(PREF_IP_ADDRESS, "");
    }

    public static void setUserId(int userId) {
        Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_LOGGED_IN_USER_ID, userId);
        editor.apply();
    }

    public static int getUserId() {
        return getSharedPreferences().getInt(PREF_LOGGED_IN_USER_ID, 0);
    }

    public static void setLoggedInUserEmail(String email) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_LOGGED_IN_USER_EMAIL, email);
        editor.apply();
    }

    public static String getLoggedInUserEmail() {
        return getSharedPreferences().getString(PREF_LOGGED_IN_USER_EMAIL, "");
    }

    public static void clearAllUserData() {
        Editor editor = getSharedPreferences().edit();
        editor.remove(PREF_LOGGED_IN_USER_ID);
        editor.remove(PREF_LOGGED_IN_USER_EMAIL);
        editor.remove(PREF_IS_FACEBOOK);
        editor.apply();
    }

    public static void setIsFacebook(boolean isFacebook) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_FACEBOOK, isFacebook);
        editor.apply();
    }

    public static boolean isFacebook() {
        return getSharedPreferences().getBoolean(PREF_IS_FACEBOOK, false);
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(NaviiApplication.getAppContext());
    }
}
