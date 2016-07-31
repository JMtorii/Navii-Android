package com.teamawesome.navii.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.activity.debug.NaviiSuperActivity;
import com.teamawesome.navii.server.api.ItineraryAPI;
import com.teamawesome.navii.server.api.PreferenceAPI;
import com.teamawesome.navii.server.api.TagsAPI;
import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.api.UserPreferenceAPI;
import com.teamawesome.navii.util.NaviiFragmentManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by JMtorii on 2015-11-20.
 */
public abstract class NaviiActivity extends NaviiSuperActivity {

    protected NaviiFragmentManager fm;
    protected Retrofit retrofit;
    protected Retrofit retrofitObservable;

    public final UserAPI userAPI;
    public final UserAPI userAPIObservable;
    public final UserPreferenceAPI userPreferenceAPI;
    public final PreferenceAPI preferenceAPI;
    public final TagsAPI tagsAPI;
    public final ItineraryAPI itineraryAPI;

    public NaviiActivity() {

        retrofit = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        retrofitObservable = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        userAPI = retrofit.create(UserAPI.class);
        userAPIObservable = retrofitObservable.create(UserAPI.class);
        userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);
        preferenceAPI = retrofitObservable.create(PreferenceAPI.class);
        tagsAPI = retrofitObservable.create(TagsAPI.class);
        itineraryAPI = retrofitObservable.create(ItineraryAPI.class);
    }

    public void switchFragment(Fragment newFragment,
                               int enterAnim,
                               int exitAnim,
                               String tag,
                               boolean isReplace,
                               boolean clearBackStack,
                               boolean isAddedToBackStack) {
        fm.switchFragment(
                newFragment,
                enterAnim,
                exitAnim,
                tag,
                isReplace,
                clearBackStack,
                isAddedToBackStack
        );
    }

}