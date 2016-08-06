package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.SavedTripsAdapter;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.NavigationConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-15.
 */
public class SavedTripsActivity extends NaviiNavigationalActivity {
    public static List<Itinerary> savedItineraries = new ArrayList<>();

    @BindView(R.id.planned_trips_view)
    RecyclerView plannedTrips;

    @Override
    protected NavigationConfiguration getNavConfig() {
        return NavigationConfiguration.PlannedTrips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Size:", String.valueOf(savedItineraries.size()));
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        SavedTripsAdapter savedTripsAdapter = new SavedTripsAdapter(savedItineraries);
        plannedTrips.setAdapter(savedTripsAdapter);
        plannedTrips.setLayoutManager(new LinearLayoutManager(this));

    }
}
