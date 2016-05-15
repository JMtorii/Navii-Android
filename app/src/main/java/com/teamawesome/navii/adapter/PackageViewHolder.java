package com.teamawesome.navii.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawesome.navii.R;

/**
 * Created by sjung on 04/02/16.
 */
public class PackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView attractionName;
    public TextView attractionPrice;
    public TextView attractionStartTime;
    public ImageView imageView;

    public PackageViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        attractionName = (TextView) itemView.findViewById(R.id.attraction_name);
        attractionPrice = (TextView) itemView.findViewById(R.id.attraction_price);
        attractionStartTime = (TextView) itemView.findViewById(R.id.attraction_start_time);
        imageView = (ImageView) itemView.findViewById(R.id.attraction_photo);
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG","tag");
    }
}
