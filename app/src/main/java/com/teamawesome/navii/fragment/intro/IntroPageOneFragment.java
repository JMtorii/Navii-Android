package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 15-08-25.
 */
public class IntroPageOneFragment extends IntroAbstractPageFragment {

    public static IntroPageOneFragment newInstance(int position) {
        IntroPageOneFragment f = new IntroPageOneFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_page1, container, false);
        return v;
    }
}
