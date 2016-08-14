package com.teamawesome.navii.util;

import android.graphics.Bitmap;

import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;
import com.teamawesome.navii.server.model.Attraction;

/**
 * Created by sjung on 01/07/16.
 *
 * Object to hold the attraction items for the PackageScheduleViewAdapter to distinguish between header/item
 * {@link PackageScheduleViewAdapter}
 */

public class PackageScheduleAttractionItem implements PackageScheduleListItem {

    private Attraction attraction;
    private Bitmap bitmap;
    private int day;
    private int position;

    public PackageScheduleAttractionItem(Attraction attraction, int day, int position) {
        this.attraction = attraction;
        this.day = day;
        this.position = position;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public boolean isDayHeader() {
        return false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getDay() {
        return day;
    }

    public int getPosition() {
        return position;
    }
}
