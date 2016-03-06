package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.IntroViewPagerFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

/**
 * Created by JMtorii on 15-08-24.
 */

public class IntroActivity extends NaviiActivity {
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
        switchFragment(
                fragment,
                Constants.NO_ANIM,
                Constants.NO_ANIM,
                "INTRO_FRAGMENT",
                true,
                false,
                false
        );
    }
}