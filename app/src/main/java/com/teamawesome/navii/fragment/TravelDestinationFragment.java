package com.teamawesome.navii.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;

/**
 * Created by jtorii on 16-05-13.
 */
public class TravelDestinationFragment extends Fragment {

    private TextView headerTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_destination, container, false);
        headerTextView = (TextView) v.findViewById(R.id.travel_destination_header_text);

        return v;
    }
}
