package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.IntroViewPagerFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

/**
 * Created by JMtorii on 15-08-24.
 */

public class IntroActivity extends AppCompatActivity {

    private NaviiFragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if the user has already logged in, go straight to the main app
        if (!NaviiPreferenceData.getLoggedInUserEmail().isEmpty()) {
            // Go to main app
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        }

        IntroViewPagerFragment fragment = new IntroViewPagerFragment();
        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.intro_activity_content_frame);

        setContentView(R.layout.activity_intro);

        // TODO: move INTRO_FRAGMENT to constants
        fm.switchFragment(
                fragment,
                Constants.NO_ANIM,
                Constants.NO_ANIM,
                "INTRO_FRAGMENT",
                true,
                false,
                false
        );
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
