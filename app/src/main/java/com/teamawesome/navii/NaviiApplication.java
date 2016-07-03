package com.teamawesome.navii;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.teamawesome.navii.server.api.ItineraryAPI;
import com.teamawesome.navii.server.api.PreferenceAPI;
import com.teamawesome.navii.server.api.TagsAPI;
import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.api.UserPreferenceAPI;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiPreferenceData;
import com.teamawesome.navii.util.RxBus;

import java.util.concurrent.TimeUnit;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by JMtorii on 2015-11-21.
 */
public class NaviiApplication extends Application {
    private static NaviiApplication sInstance;
    private static Context context;
    private static RxBus bus;
    private static Retrofit retrofit;
    private static UserAPI userAPI;
    private static UserPreferenceAPI userPreferenceAPI;
    private static PreferenceAPI preferenceAPI;
    private static TagsAPI tagsAPI;
    private static ItineraryAPI itineraryAPI;

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

    public Context getAppContext() {
        return context;
    }

    public RxBus getBus() {
        if (bus == null) {
            bus = new RxBus();
        }
        return bus;
    }

    public UserAPI getUserAPI() {
        if (userAPI == null) {
            userAPI = retrofit.create(UserAPI.class);
        }

        return userAPI;
    }

    public UserPreferenceAPI getUserPreferenceAPI() {
        if (userPreferenceAPI == null) {
            userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);
        }

        return userPreferenceAPI;
    }

    public PreferenceAPI getPreferenceAPI() {
        if (preferenceAPI == null) {
            preferenceAPI = retrofit.create(PreferenceAPI.class);
        }

        return preferenceAPI;
    }

    public TagsAPI getTagsAPI() {
        if (tagsAPI == null) {
            tagsAPI = retrofit.create(TagsAPI.class);
        }

        return tagsAPI;
    }

    public ItineraryAPI getItineraryAPI() {
        if (itineraryAPI == null) {
            itineraryAPI = retrofit.create(ItineraryAPI.class);
        }

        return itineraryAPI;
    }
}