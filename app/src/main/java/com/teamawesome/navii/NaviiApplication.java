package com.teamawesome.navii;

import android.app.Application;
import android.content.Context;

/**
 * Created by JMtorii on 2015-11-21.
 */
public class NaviiApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        NaviiApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return NaviiApplication.context;
    }
}