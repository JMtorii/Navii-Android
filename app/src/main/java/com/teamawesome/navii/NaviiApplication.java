package com.teamawesome.navii;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.LruCache;

import com.facebook.FacebookSdk;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.RestClient;
import com.teamawesome.navii.util.RxBus;

/**
 * Created by JMtorii on 2015-11-21.
 */
public class NaviiApplication extends MultiDexApplication {
    private static NaviiApplication sInstance;
    private static Context context;
    private static RxBus bus;

    private LruCache<String, Bitmap> mMemoryCache;

    public static NaviiApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
        RestClient.init();
        FacebookSdk.sdkInitialize(context);
        AnalyticsManager.init(context);
        setupLruCache();
    }

    private void setupLruCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Context getAppContext() {
        return context;
    }

    public RxBus getBus() {
        if (bus == null) {
            bus = new RxBus();
        }
        return bus;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        } else {
            mMemoryCache.remove(key);
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}