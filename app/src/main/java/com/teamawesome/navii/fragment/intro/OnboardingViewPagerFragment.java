package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-07-24.
 */
public class OnboardingViewPagerFragment extends Fragment {
    private static final String ARG_POSITION = "onboarding_arg_position";
    private int mPosition;

    public static OnboardingViewPagerFragment newInstance(int position) {
        OnboardingViewPagerFragment f = new OnboardingViewPagerFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_onboarding_view_pager, container, false);
        return v;
    }
}
