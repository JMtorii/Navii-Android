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
    public TextView textView;
    public ImageView imageView;

    public PackageViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.description_name);
//        imageView = (ImageView) itemView.findViewById(R.id.description_photo);
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG","tag");
    }
}
