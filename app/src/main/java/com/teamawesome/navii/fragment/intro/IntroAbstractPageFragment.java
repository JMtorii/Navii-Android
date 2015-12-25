package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;

import com.teamawesome.navii.fragment.main.NaviiFragment;

/**
 * Created by JMtorii on 2015-10-14.
 */
public abstract class IntroAbstractPageFragment extends NaviiFragment {
    protected static final String ARG_POSITION = "position";

    protected int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

}
