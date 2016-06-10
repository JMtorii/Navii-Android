package com.teamawesome.navii.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.PackageOverviewViewHolder;

import java.util.List;

/**
 * Created by JMtorii on 16-06-04.
 */
public class PackageOverviewRecyclerViewAdapter extends RecyclerView.Adapter<PackageOverviewViewHolder> {
    private List<String> mItems;

    public PackageOverviewRecyclerViewAdapter(List<String> items) {
        this.mItems = items;
    }

    @Override
    public PackageOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_package_overview, null);
        return new PackageOverviewViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PackageOverviewViewHolder holder, int position) {
        holder.setName(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}
