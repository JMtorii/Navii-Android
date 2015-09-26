package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.IntroViewPagerFragment;
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

        // TODO: consider moving to another method, class
        NaviiPreferenceData.init(getApplicationContext());
        Fragment fragment = new IntroViewPagerFragment();

        // if the user has already logged in, go straight to the main app
        if (!NaviiPreferenceData.getLoggedInUserEmail().isEmpty()) {
            // Go to main app
        }

        setContentView(R.layout.activity_intro);

        fm = new NaviiFragmentManager(getSupportFragmentManager(), R.id.intro_content_frame);

        Log.i("test", "Fragment Manager");
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

    // TODO: delete this piece of shit
    public void loadFragment(Fragment newFragment, boolean isReplace) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        // TODO: consider adding tags to fragments
        if (isReplace) {
            trans.replace(R.id.intro_content_frame, newFragment);
        } else {
            trans.add(R.id.intro_content_frame, newFragment);
        }

        trans.commit();
    }
}
