package com.teamawesome.navii.activity;

import android.os.Bundle;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NavigationConfiguration;

/**
 * Created by JMtorii on 16-06-15.
 */
public class PlannedTripsActivity extends NaviiNavigationalActivity {
    @Override
    protected NavigationConfiguration getNavConfig() {
        return NavigationConfiguration.PlannedTrips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_planned_trips);
    }
}
