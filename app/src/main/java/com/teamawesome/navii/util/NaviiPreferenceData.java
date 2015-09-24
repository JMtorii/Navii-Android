package com.teamawesome.navii.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by JMtorii on 15-09-12.
 *
 * This class is used to simplify calls to SharedPreferences.
 */
public class NaviiPreferenceData {
    private static final String PREF_LOGGED_IN_USER_EMAIL = "logged_in_email";

    private static Context mContext;

    public static void init(Context context) {
        if (context == null) {
            Log.e("PREFERENCE_NO_CONTEXT", "No context");
        }

        mContext = context;
        // Add other init work
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static void setLoggedInUserEmail(final String email) {
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
}
