package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoEditText;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jtorii on 16-05-13.
 */
public class TravelDestinationFragment extends NaviiParallaxFragment {

    @BindView(R.id.travel_destination_header_text)
    MainLatoTextView mHeaderTextView;

    @BindView(R.id.travel_destination_text)
    MainLatoEditText mDestinationEditView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_destination, container, false);

        ButterKnife.bind(this, v);

        mDestinationEditView.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        mDestinationEditView.setKeyListener(null);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDestinationEditView.setSelected(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDestinationEditView.setSelected(true);
    }
}
