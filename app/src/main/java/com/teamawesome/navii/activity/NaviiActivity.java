package com.teamawesome.navii.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.util.NaviiFragmentManager;

/**
 * Created by JMtorii on 2015-11-20.
 */
public abstract class NaviiActivity extends AppCompatActivity {

    protected NaviiFragmentManager fm;

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