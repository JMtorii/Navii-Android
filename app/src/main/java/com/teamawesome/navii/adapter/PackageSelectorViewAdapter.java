package com.teamawesome.navii.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.teamawesome.navii.activity.HeartAndSoulDetailsActivity;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.PackageScheduleListItem;
import com.teamawesome.navii.util.Constants;

import java.util.List;

/**
 * Created by sjung on 05/08/16.
 */
public class PackageSelectorViewAdapter extends PackageScheduleViewAdapter {

    Context mContext;
    List<PackageScheduleListItem> mItemList;

    public PackageSelectorViewAdapter(Context context, List<PackageScheduleListItem> mItemList, int width, int height) {
        super(context, mItemList, width, height);
        this.mContext = context;
        this.mItemList = mItemList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final PackageItemViewHolder itemViewHolder  = (PackageItemViewHolder) holder;
        itemViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected", "Selected");
                Intent heartAndSoulDetailsActivity = new Intent(mContext, HeartAndSoulDetailsActivity.class);
                Attraction attraction = mItemList.get(position).getAttraction();
                Bundle extras = new Bundle();
                extras.putParcelable(Constants.INTENT_ATTRACTION, attraction);
                heartAndSoulDetailsActivity.putExtra(Constants.INTENT_VIEW_PREFETCH, true);

                if (attraction.getLocation() != null) {
                    extras.putString(Constants.INTENT_ATTRACTION_LOCATION, attraction.getLocation().getAddress());
                }
                heartAndSoulDetailsActivity.putExtras(extras);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, itemViewHolder.imageView, "heartAndSoulImage");
                ((Activity) mContext).startActivityForResult(heartAndSoulDetailsActivity, Constants.GET_ATTRACTION_EXTRA_REQUEST_CODE, options.toBundle());
            }
        });
    }



    @Override
    public int getItemViewType(int position) {
        return 4;
    }

}
