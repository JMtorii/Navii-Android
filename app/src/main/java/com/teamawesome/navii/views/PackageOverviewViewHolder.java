package com.teamawesome.navii.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.teamawesome.navii.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-06-05.
 */
public class PackageOverviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.package_overview_item_image)
    public ImageView image;

    public PackageOverviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setImage(int resourceId) {
        this.image.setImageResource(resourceId);
    }

    @OnClick(R.id.card_view)
    public void clicked() {
        Log.i(this.getClass().getName(), "Clicked");
    }
}
