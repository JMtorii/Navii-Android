package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by JMtorii on 2015-10-13.
 */

public class ChooseLocationFragment extends NaviiFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_location, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.choose_location_next_button)
    public void nextButtonPressed() {
        TravelParameterFragment fragment = new TravelParameterFragment();
    }
}
