package com.teamawesome.navii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageOverviewRecyclerViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.ToolbarConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-04.
 */
public class PackageOverviewActivity extends NaviiToolbarActivity {
    @BindView(R.id.package_overview_recycler_view)
    RecyclerView recyclerView;

    private List<Attraction> attractionList;

    @Override
    public ToolbarConfiguration getToolbarConfiguration() {
        return ToolbarConfiguration.PackageOverview;
    }

    @Override
    public void onLeftButtonClick() {
        super.onBackPressed();
    }

    @Override
    public void onRightButtonClick() {
        Intent itineraryScheduleActivity = new Intent(this, ItineraryScheduleActivity.class);
        itineraryScheduleActivity.putParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST, new ArrayList<>(attractionList));
        startActivity(itineraryScheduleActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        attractionList = getIntent().getParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST);

        List<String> photoUriList = new ArrayList<>();

        for (Attraction attraction : attractionList) {
            photoUriList.add(attraction.getPhotoUri());
        }

        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        PackageOverviewRecyclerViewAdapter adapter = new PackageOverviewRecyclerViewAdapter(this, photoUriList);
        recyclerView.setAdapter(adapter);
    }
}
