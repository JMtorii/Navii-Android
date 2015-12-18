package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.activity.NaviiActivity;

/**
 * Created by JMtorii on 2015-11-18.
 */
public abstract class MainFragment extends Fragment {
    protected NaviiActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Get rid of once flow is defined for preferences
        if (getActivity().getClass().equals(MainActivity.class)) {
            parentActivity = (MainActivity) getActivity();
        } else {
            parentActivity = (IntroActivity) getActivity();
        }
    }
}
