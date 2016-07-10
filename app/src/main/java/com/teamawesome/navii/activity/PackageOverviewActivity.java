package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageOverviewRecyclerViewAdapter;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-04.
 */
public class PackageOverviewActivity extends NaviiActivity {
    @BindView(R.id.package_overview_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);
        ButterKnife.bind(this);

        List<Attraction> attractionList = getIntent().getParcelableArrayListExtra(Constants.INTENT_ATTRACTION_LIST);

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
