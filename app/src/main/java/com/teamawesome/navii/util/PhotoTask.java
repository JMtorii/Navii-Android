package com.teamawesome.navii.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

public abstract class PhotoTask extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private GoogleApiClient googleApiClient;
    private int mWidth;
    private int mHeight;

    public PhotoTask(Context context, GoogleApiClient googleApiClient, int width, int height) {
        this.mContext = context;
        this.googleApiClient = googleApiClient;
        this.mWidth = width;
        this.mHeight = height;
    }

    /**
     * Loads the first photo for a place id from the Geo Data API.
     * The place id must be the first (and only) parameter.
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;

        if (params.length != 1) {
            return null;
        }
        final String placeId = params[0];
        PlacePhotoMetadataResult result = Places.GeoDataApi.getPlacePhotos(googleApiClient, placeId).await();

        if (result.getStatus().isSuccess()) {
            PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0 ) {
                final PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                // Get a full-size bitmap for the photo.
                Log.d("Width", "w:"+mWidth +" h:"+mHeight);
                Bitmap temp = photo.getScaledPhoto(googleApiClient, mWidth, mHeight).await().getBitmap();
                if (temp != null) {
                    bitmap = temp;
                }
            }
            photoMetadataBuffer.release();
        }

        return bitmap;
    }
}