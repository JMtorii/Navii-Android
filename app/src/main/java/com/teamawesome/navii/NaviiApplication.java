package com.teamawesome.navii;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.RxBus;
import com.teamawesome.navii.util.SessionManager;

/**
 * Created by JMtorii on 2015-11-21.
 */
public class NaviiApplication extends MultiDexApplication {
    private static NaviiApplication sInstance;
    private static Context context;
    private static RxBus bus;

    public static NaviiApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
        RestClient.init();
        SessionManager.init(context);
        FacebookSdk.sdkInitialize(context);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Context getAppContext() {
        return context;
    }

    public RxBus getBus() {
        if (bus == null) {
            bus = new RxBus();
        }
        return bus;
    }
}