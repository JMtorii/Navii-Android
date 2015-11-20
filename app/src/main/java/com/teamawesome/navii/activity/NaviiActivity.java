package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NaviiFragmentManager;

/**
 * Created by JMtorii on 2015-11-20.
 */
public abstract class NaviiActivity extends AppCompatActivity {

    private NaviiFragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.intro_activity_content_frame);
    }

    protected void switchFragment(Fragment newFragment, int enterAnim, int exitAnim, String tag,
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
