package com.teamawesome.navii.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by sjung on 04/07/16.
 */
public class VectorDrawableWorkerTask extends AsyncTask<Integer, Void, Drawable> {
    private static final int TYPE_URL = -1;
    private final WeakReference<ImageView> imageViewReference;
    private Context mContext;
    private int resId = 0;
    private String url;

    public VectorDrawableWorkerTask(ImageView imageView, Context context, int resId) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<>(imageView);
        this.resId = resId;
        mContext = context;
    }

    // Decode image in background.
    @Override
    protected Drawable doInBackground(Integer... params) {
        resId = params[0];
        return decodeVector(resId);
    }

    private Drawable decodeVector(int resId) {
        VectorDrawable drawable = (VectorDrawable) ContextCompat.getDrawable(mContext, resId);

        return drawable;
    }

    private Drawable getImage(String url) {
//        Picasso.with(mContext).load(url).get();

        return null;
    }

    // Once complete, see if ImageView is still around and set drawable.
    @Override
    protected void onPostExecute(Drawable drawable) {
        if (isCancelled()) {
            drawable = null;
        }
        final ImageView imageView = imageViewReference.get();

        if (imageViewReference != null && drawable != null) {
            final VectorDrawableWorkerTask vectorDrawableWorkerTask =
                    getBitmapWorkerTask(imageView);
            if (this == vectorDrawableWorkerTask && imageView != null) {
                imageView.setImageDrawable(drawable);
            }
        }
    }

    public static VectorDrawableWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final VectorDrawableWorkerTask vectorDrawableWorkerTask = VectorDrawableWorkerTask.getBitmapWorkerTask(imageView);

        if (vectorDrawableWorkerTask != null) {
            final int bitmapData = vectorDrawableWorkerTask.getResId();
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == 0 || bitmapData != data) {
                // Cancel previous task
                vectorDrawableWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    public int getResId() {
        return resId;
    }
}
