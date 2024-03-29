package com.teamawesome.navii.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.SavedTripsAdapter;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.NavigationConfiguration;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.WifiCheck;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.no_trips_view)
    View noTrips;

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
        if (WifiCheck.isConnected(this))
            executeGetTripsQuery();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if (WifiCheck.isConnected(this))
            executeGetTripsQuery();
    }

    private void executeGetTripsQuery(){
        Observable<List<Itinerary>> saveCall = RestClient.itineraryAPI.getSavedItineraries();
        saveCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Itinerary>>() {
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
                    public void onNext(List<Itinerary> lists) {
                        progressDialog.dismiss();
                        if (lists.get(0).getPackageScheduleListItems().isEmpty()){
                            noTrips.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.make_trips_button)
    public void onClick(){
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        this.startActivity(gotoMainActivity);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
