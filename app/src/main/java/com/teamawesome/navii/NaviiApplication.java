package com.teamawesome.navii;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.RxBus;

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

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        NaviiPreferenceData.setIPAddress(Constants.SERVER_URL);

        retrofit = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
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