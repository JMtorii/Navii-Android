package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;

/**
 * Created by JMtorii on 15-08-25.
 */
public class IntroPageFourFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private int mPosition;

    public static IntroPageFourFragment newInstance(int position) {
        IntroPageFourFragment f = new IntroPageFourFragment();
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
        View v = inflater.inflate(R.layout.fragment_intro_page4, container, false);
        return v;
    }
}
