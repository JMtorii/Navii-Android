package com.teamawesome.navii.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.ItineraryRecommendListAdapter;
import com.teamawesome.navii.server.model.HeartAndSoulPackage;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.ToolbarConfiguration;

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
public class ItineraryRecommendActivity extends NaviiToolbarActivity {
    @BindView(R.id.itineraryList)
    RecyclerView itineraryRecyclerView;

    private ItineraryRecommendListAdapter recommendListAdapter;
    private ProgressDialog progressDialog;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.ItineraryRecommend;
    }

    @Override
    public void onLeftButtonClick() {
        super.onBackPressed();
    }

    @Override
    public void onRightButtonClick() {
        // Nothing to do here
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        List<String> tags = getIntent().getStringArrayListExtra(Constants.INTENT_TAGS);
        String tagList;
        if (tags == null) {
            tagList = null;
        } else {
            tagList = TextUtils.join(",", tags);
        }

        int days = 3;

        Observable<HeartAndSoulPackage> itineraryListCall = RestClient.itineraryAPI.getItineraries(tagList, days);

        final Context context = this;
        itineraryListCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HeartAndSoulPackage>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ItineraryActivity", "onError", e);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(HeartAndSoulPackage heartAndSoulPackage) {
                        Log.d("tags", "I made it, I made it");
                        recommendListAdapter = new ItineraryRecommendListAdapter(context, heartAndSoulPackage);
                        itineraryRecyclerView.setAdapter(recommendListAdapter);

                        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
                        itineraryRecyclerView.setLayoutManager(gridLayoutManager);
                    }
                });
        progressDialog = ProgressDialog.show(this, "Just calm down.", "Loading itineraries...");
    }
}
