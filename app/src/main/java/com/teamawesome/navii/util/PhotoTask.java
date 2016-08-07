package com.teamawesome.navii.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

public abstract class PhotoTask extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;

    public PhotoTask(Context context) {
        this.mContext = context;
    }

    /**
     * Loads the first photo for a place id from the Geo Data API.
     * The place id must be the first (and only) parameter.
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }
        final String placeId = params[0];

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        PlacePhotoMetadataResult metadataResult = Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId).await();
        Bitmap image = null;
        if (metadataResult != null && metadataResult.getStatus().isSuccess()) {
            PlacePhotoMetadataBuffer photoMetadataBuffer = metadataResult.getPhotoMetadata();
            PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
            // Get a full-size bitmap for the photo.
            image = photo.getPhoto(mGoogleApiClient).await()
                    .getBitmap();
            // Get the attribution text.
            photoMetadataBuffer.release();
        }
        return image;
    }

}