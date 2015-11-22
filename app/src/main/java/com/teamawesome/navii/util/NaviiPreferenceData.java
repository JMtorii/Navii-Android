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
    private static final String PREF_LOGGED_IN_USER_EMAIL = "logged_in_email";

    public static void setLoggedInUsername(String email) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_LOGGED_IN_USER_EMAIL, email);
        editor.apply();
    }

    public static String getLoggedInUserEmail() {
        return getSharedPreferences().getString(PREF_LOGGED_IN_USER_EMAIL, "");
    }

    public static void clearLoggedInEmailAddress() {
        Editor editor = getSharedPreferences().edit();
        editor.remove(PREF_LOGGED_IN_USER_EMAIL);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(NaviiApplication.getAppContext());
    }
}
