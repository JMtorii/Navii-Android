package com.teamawesome.navii.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.PackageScheduleListItem;

import java.util.List;

/**
 * Created by sjung on 05/08/16.
 */
public class PackageSelectorViewAdapter extends PackageScheduleViewAdapter {

    Context mContext;
    List<PackageScheduleListItem> mItemList;
    List<Attraction> mAttractions;
    public PackageSelectorViewAdapter(Context context, List<PackageScheduleListItem> mItemList, List<Attraction> attractions) {
        super(context, mItemList);
        this.mContext = context;
        this.mItemList = mItemList;
        this.mAttractions = attractions;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        PackageItemViewHolder itemViewHolder  = (PackageItemViewHolder) holder;
        itemViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected", "Selected");
                Attraction attraction = mAttractions.get(position);
                Intent data = new Intent();
                data.putExtra(Constants.INTENT_ATTRACTION, attraction);

                Activity activity = ((Activity) mContext);
                activity.setResult(Constants.RESPONSE_ATTRACTION_SELECTED, data);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}
