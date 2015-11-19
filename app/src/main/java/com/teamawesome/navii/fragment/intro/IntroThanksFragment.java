package com.teamawesome.navii.fragment.intro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JMtorii on 2015-10-17.
 */
public class IntroThanksFragment extends IntroFragment {
    private Button mNextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_thanks, container, false);
        mNextButton = (Button) v.findViewById(R.id.intro_thanks_next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesFragment fragment = new PreferencesFragment();
                parentActivity.switchFragment(
                        fragment,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.PREFERENCES_FRAGMENT_TAG,
                        true,
                        true,
                        true
                );

//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
            }
        });
        return v;
    }

}
