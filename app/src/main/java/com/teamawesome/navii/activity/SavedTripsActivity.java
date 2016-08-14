package com.teamawesome.navii.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.SavedTripsAdapter;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.util.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JMtorii on 16-06-15.
 */
public class SavedTripsActivity extends NaviiNavigationalActivity {

    @BindView(R.id.planned_trips_view)
    RecyclerView plannedTrips;

    private ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected NavigationConfiguration getNavConfig() {
        return NavigationConfiguration.PlannedTrips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Observable<List<List<Itinerary>>> saveCall = RestClient.itineraryAPI.getSavedItineraries();
        saveCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<List<Itinerary>>>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SavedTripsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(List<List<Itinerary>> lists) {
                        if (lists.get(0).get(0).getAttractions().isEmpty()){
                            Toast.makeText(SavedTripsActivity.this, "Make some Trips!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SavedTripsAdapter savedTripsAdapter = new SavedTripsAdapter(lists, SavedTripsActivity.this);
                            plannedTrips.setAdapter(savedTripsAdapter);
                            plannedTrips.setLayoutManager(new LinearLayoutManager(SavedTripsActivity.this));
                        }
                    }
                });
        progressDialog = ProgressDialog.show(this, "Building the perfect trip", "Loading trips...");
        AnalyticsManager.getMixpanel().track("SavedTripsActivity - onCreate");
    }
}
