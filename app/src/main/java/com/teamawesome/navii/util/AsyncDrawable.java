package com.teamawesome.navii.util;

import android.graphics.drawable.VectorDrawable;

import java.lang.ref.WeakReference;

public class AsyncDrawable extends VectorDrawable {
    private final WeakReference<VectorDrawableWorkerTask> vectorDrawableWorkerTaskWeakReference;

    public AsyncDrawable(VectorDrawableWorkerTask vectorDrawableWorkerTask) {
        super();
        vectorDrawableWorkerTaskWeakReference = new WeakReference<>(vectorDrawableWorkerTask);
    }

    public VectorDrawableWorkerTask getBitmapWorkerTask() {
        return vectorDrawableWorkerTaskWeakReference.get();
    }
}