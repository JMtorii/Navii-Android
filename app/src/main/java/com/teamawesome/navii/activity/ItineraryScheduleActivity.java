package com.teamawesome.navii.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.teamawesome.navii.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 19/06/16.
 */
public class ItineraryScheduleActivity extends Activity {

    @BindView(R.id.itinerary_schedule_fab)
    FloatingActionButton addScheduleFloatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_schedule);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.itinerary_schedule_fab)
    public void onClick(View view) {
        Snackbar.make(view, "Nope. Not yet.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
