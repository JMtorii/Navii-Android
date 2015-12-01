package com.teamawesome.navii.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.util.NaviiFragmentManager;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by JMtorii on 2015-11-20.
 */
public abstract class NaviiActivity extends AppCompatActivity {

    protected NaviiFragmentManager fm;
    protected Retrofit retrofit;

    public final UserAPI userAPI;

    public NaviiActivity() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.57.1:8080")    // THIS ONLY WORKS IN JUN'S CASE
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        userAPI = retrofit.create(UserAPI.class);
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
