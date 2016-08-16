package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.teamawesome.navii.server.model.PackageScheduleListItem;

import java.util.List;

/**
 * Created by sjung on 05/08/16.
 */
public class PackageSelectorViewAdapter extends PackageScheduleViewAdapter {

    Context mContext;
    List<PackageScheduleListItem> mItemList;

    public PackageSelectorViewAdapter(Context context, List<PackageScheduleListItem> mItemList) {
        super(context, mItemList);
        this.mContext = context;
        this.mItemList = mItemList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return 4;
    }

}
