package com.teamawesome.navii.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teamawesome.navii.NaviiApplication;
import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ItineraryRecommendListAdapter;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.RestClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjung on 11/06/16.
 */
public class ItineraryRecommendActivity extends NaviiActivity {

    @BindView(R.id.itineraryList)
    RecyclerView itineraryRecyclerView;

    ItineraryRecommendListAdapter recommendListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_recommend);

        ButterKnife.bind(this);

        List<String> tags = getIntent().getStringArrayListExtra("TAGS");

        Log.d("TAGS", tags.toString());


        Observable<List<Itinerary>> itineraryListCall = RestClient.itineraryAPI.getItineraries(tags);

        final Context context = this;
        itineraryListCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Itinerary>>() {
                    @Override
                    public void onCompleted() {
                        //nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ItineraryActivity", "onError", e);
                    }

                    @Override
                    public void onNext(List<Itinerary> itineraries) {
                        Log.d("tags", "I made it, I made it");
                        recommendListAdapter = new ItineraryRecommendListAdapter(context, itineraries);
                        itineraryRecyclerView.setAdapter(recommendListAdapter);

                        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
                        itineraryRecyclerView.setLayoutManager(gridLayoutManager);
                    }
                });
    }
}
