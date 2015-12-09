package com.teamawesome.navii.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.api.UserPreferenceAPI;
import com.teamawesome.navii.util.NaviiFragmentManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 2015-11-20.
 */
public abstract class NaviiActivity extends AppCompatActivity {

    protected NaviiFragmentManager fm;
    protected Retrofit retrofit;

    public final UserAPI userAPI;
    public final UserPreferenceAPI userPreferenceAPI;

    public NaviiActivity() {
        retrofit = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        userAPI = retrofit.create(UserAPI.class);
        userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);
    }

    public void switchFragment(Fragment newFragment, int enterAnim, int exitAnim, String tag,
                               boolean isReplace, boolean clearBackStack,
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
