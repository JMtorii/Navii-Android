package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 2015-10-30.
 */
public class PlannedTripsFragment extends NaviiFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_planned_trips, container, false);

        return v;
    }
}
