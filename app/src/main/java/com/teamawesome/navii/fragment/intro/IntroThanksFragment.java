package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.Constants;

/**
 * Created by JMtorii on 2015-10-17.
 */
public class IntroThanksFragment extends IntroFragment {
    private BootstrapButton mNextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_thanks, container, false);
        mNextButton = (BootstrapButton) v.findViewById(R.id.intro_thanks_next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesFragment fragment = PreferencesFragment.newInstance(Constants.PREFERENCE_TYPE_1);
                parentActivity.switchFragment(
                        fragment,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.PREFERENCES_FRAGMENT_TAG,
                        true,
                        true,
                        true
                );

            }
        });
        return v;
    }

}
