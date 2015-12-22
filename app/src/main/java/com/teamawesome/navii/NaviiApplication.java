package com.teamawesome.navii;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return NaviiApplication.context;
    }
}