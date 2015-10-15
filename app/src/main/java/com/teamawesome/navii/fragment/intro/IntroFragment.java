package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.teamawesome.navii.activity.IntroActivity;

/**
 * Created by JMtorii on 2015-10-14.
 */
public abstract class IntroFragment extends Fragment {
    protected IntroActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (IntroActivity) getActivity();
    }
}
