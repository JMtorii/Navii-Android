package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.views.PackageOverviewViewHolder;

import java.util.List;

/**
 * Created by JMtorii on 16-06-04.
 */
public class PackageOverviewRecyclerViewAdapter extends RecyclerView.Adapter<PackageOverviewViewHolder> {
    private List<Integer> mItems;
    private Context mContext;
    private List<String> mPhotoURIList;

    public PackageOverviewRecyclerViewAdapter(List<Integer> items) {
        this.mItems = items;
    }

    public PackageOverviewRecyclerViewAdapter(List<Integer> items, Context context, List<String> photoURIList) {
        this.mItems = items;
        this.mContext = context;
        this.mPhotoURIList = photoURIList;
    }

    @Override
    public PackageOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_package_overview, null);
        return new PackageOverviewViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PackageOverviewViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mPhotoURIList.get(position))
                .centerCrop()
                .fit()
                .into(holder.image);
        holder.setImage(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mPhotoURIList.size();
    }
}
