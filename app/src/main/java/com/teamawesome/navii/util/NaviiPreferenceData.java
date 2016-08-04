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

    private static final String PREF_IP_ADDRESS = "ip_address";
    private static final String PREF_IS_LOGGED_IN = "is_logged_in";
    private static final String PREF_LOGGED_IN_USER_EMAIL = "logged_in_email";
    private static final String PREF_IS_FACEBOOK = "is_facebook";
    private static final String PREF_KEY_TOKEN = "token";

    public static void setIPAddress(String address) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_IP_ADDRESS, address);
        editor.apply();
    }

    public static String getIPAddress() {
        return getSharedPreferences().getString(PREF_IP_ADDRESS, "");
    }

    public static void createLoginSession(String email, String token) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_LOGGED_IN, true);
        //editor.putString(KEY_NAME, name);
        editor.putString(PREF_LOGGED_IN_USER_EMAIL, email);
        editor.putString(PREF_KEY_TOKEN, token);
        editor.apply();
    }

    public static void deleteLoginSession() {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_LOGGED_IN, false);
        //editor.putString(KEY_NAME, "");
        editor.putString(PREF_LOGGED_IN_USER_EMAIL, "");
        editor.putString(PREF_KEY_TOKEN, "");
        editor.apply();
    }

    public static void setLoggedInUserEmail(String email) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_LOGGED_IN_USER_EMAIL, email);
        editor.apply();
    }

    public static String getLoggedInUserEmail() {
        return getSharedPreferences().getString(PREF_LOGGED_IN_USER_EMAIL, "");
    }

    public static boolean isLoggedIn() {
        return getSharedPreferences().getBoolean(PREF_IS_LOGGED_IN, false);
    }

    public static String getToken() {
        return getSharedPreferences().getString(PREF_KEY_TOKEN, "");
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
        return PreferenceManager.getDefaultSharedPreferences(NaviiApplication.getInstance().getAppContext());
    }
}
