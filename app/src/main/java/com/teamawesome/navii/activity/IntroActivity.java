package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.IntroViewPagerFragment;
import com.teamawesome.navii.fragment.intro.PreferencesFragment;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiFragmentManager;
import com.teamawesome.navii.util.NaviiPreferenceData;

/**
 * Created by JMtorii on 15-08-24.
 */

// TODO: remove PreferencesFragment reference in this class
public class IntroActivity extends AppCompatActivity {

    private NaviiFragmentManager fm;

    private PreferencesFragment mPreferencesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NaviiPreferenceData.init(getApplicationContext());
        IntroViewPagerFragment fragment = new IntroViewPagerFragment();
        // if the user has already logged in, go straight to the main app
        if (!NaviiPreferenceData.getLoggedInUserEmail().isEmpty()) {
            // Go to main app
        }

        setContentView(R.layout.activity_intro);

        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.intro_activity_content_frame);

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

        // TODO: Remove this. WTF. We shouldn't need to make an exception for just one fragment.
        // TODO: == instead of equals() is unacceptable. == checks references; not contents
        if (tag == Constants.PREFERENCES_FRAGMENT_TAG) {
            mPreferencesFragment = (PreferencesFragment) newFragment;
        }
    }

    public void preferenceOnClick(View view) {
        mPreferencesFragment.preferencesOnClick(view);
    }
}
