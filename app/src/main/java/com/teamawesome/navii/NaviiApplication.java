package com.teamawesome.navii;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.RxBus;

/**
 * Created by JMtorii on 2015-11-21.
 */
public class NaviiApplication extends Application {
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

        // Facebook demographics/analytics
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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