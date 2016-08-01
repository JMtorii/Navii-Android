package com.teamawesome.navii.views;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teamawesome.navii.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JMtorii on 16-06-05.
 */
public class PackageOverviewViewHolder extends RecyclerView.ViewHolder implements Target {
    @BindView(R.id.package_overview_item_image)
    public DynamicHeightImageView image;

    public PackageOverviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        // Calculate the image ratio of the loaded bitmap
        float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
        // Set the ratio for the image
        image.setHeightRatio(ratio);
        // Load the image into the view
        image.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        // Nothing to do here
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        // Nothing to do here. For now...
    }

    public void setImage(int resourceId) {
        this.image.setImageResource(resourceId);
    }

    @OnClick(R.id.card_view)
    public void clicked() {
        Log.i(this.getClass().getName(), "Clicked");
    }
}
