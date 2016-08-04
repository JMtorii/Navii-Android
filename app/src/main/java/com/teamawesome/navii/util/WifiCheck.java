package com.teamawesome.navii.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ian on 3/29/2016.
 * Checks wifi connection mid use.
 */
public class WifiCheck {

    /**
     * Checks if device is connected to WIFI
     * @param activity the activity which the user is viewing
     * @return True iff the user is connected to wifi
     */
    public static boolean isConnected(Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return networkinfo != null;
    }
}
