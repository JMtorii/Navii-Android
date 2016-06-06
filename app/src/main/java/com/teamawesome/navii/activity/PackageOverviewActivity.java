package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.teamawesome.navii.R;
import com.teamawesome.navii.adapter.PackageOverviewRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-04.
 */

// TODO: implement Butterknife
public class PackageOverviewActivity extends NaviiActivity {
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_overview);
        ButterKnife.bind(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.package_overview_recycler_view);
        recyclerView.setHasFixedSize(true);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        List<String> items = getListItemData();

        PackageOverviewRecyclerViewAdapter adapter = new PackageOverviewRecyclerViewAdapter(PackageOverviewActivity.this, items);
        recyclerView.setAdapter(adapter);
    }

    private List<String> getListItemData() {
        List<String> items = new ArrayList<>();
        items.add("Hello");
        items.add("Bye");
        items.add("What are you doing?");
        items.add("Hm");

        return items;
    }
}
