package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jtorii on 16-05-17.
 */
public class TravelParticipantsFragment extends NaviiParallaxFragment {

    @BindView(R.id.travel_participants_header_text)
    MainLatoTextView mHeaderTextView;

    @BindView(R.id.travel_participants_adult_up_button)
    View mAdultUpButton;

    @BindView(R.id.travel_participants_adult_down_button)
    View mAdultDownButton;

    @BindView(R.id.travel_participants_adult_text)
    MainLatoTextView mAdultTextView;

    @BindView(R.id.travel_participants_child_up_button)
    View mChildUpButton;

    @BindView(R.id.travel_participants_child_down_button)
    View mChildDownButton;

    @BindView(R.id.travel_participants_child_text)
    MainLatoTextView mChildTextView;

    private int adultCounter = 2;
    private int childCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_participants, container, false);

        ButterKnife.bind(this, v);

        mChildUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++childCounter;
                setChildTextViewText();
            }
        });

        mChildDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childCounter > 0) {
                    --childCounter;
                    setChildTextViewText();
                }
            }
        });

        mAdultUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++adultCounter;
                setAdultTextViewText();
            }
        });

        mAdultDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adultCounter > 1) {
                    --adultCounter;
                    setAdultTextViewText();
                } else {
                    Toast.makeText(getActivity(),
                            "You cannot have less than 1 adult.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void setChildTextViewText() {
        if (childCounter != 1)
            mChildTextView.setText(String.format("%s Children", Integer.toString(childCounter)));
        else
            mChildTextView.setText(String.format("%s Child", Integer.toString(childCounter)));
    }

    private void setAdultTextViewText() {
        if (adultCounter != 1)
            mAdultTextView.setText(String.format("%s Adults", Integer.toString(adultCounter)));
        else
            mAdultTextView.setText(String.format("%s Adult", Integer.toString(adultCounter)));
    }
}