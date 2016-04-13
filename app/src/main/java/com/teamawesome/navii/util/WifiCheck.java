package com.teamawesome.navii.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.teamawesome.navii.fragment.main.NaviiFragment;

/**
 * Created by Ian on 3/29/2016.
 * Checks wifi connection mid use.
 */
public class WifiCheck {

    /**
     *
     * @param f the fragment which the user is viewing
     * @return True iff the user is connected to wifi
     */
    public static boolean isConnected(NaviiFragment f){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) f.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return networkinfo != null;
    }
}
