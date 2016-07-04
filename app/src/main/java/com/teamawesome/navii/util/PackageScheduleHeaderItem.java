package com.teamawesome.navii.util;

import com.teamawesome.navii.adapter.PackageScheduleViewAdapter;

/**
 * Created by sjung on 02/07/16.
 *
 * Object to hold the header items for the PackageScheduleViewAdapter to distinguish between header/item
 * {@link PackageScheduleViewAdapter}
 *
 */
public class PackageScheduleHeaderItem implements PackageScheduleListItem {

    private String name;
    private int resId;

    public PackageScheduleHeaderItem(HeartAndSoulHeaderConfiguration headerConfiguration) {
        this.name = headerConfiguration.getName();
        this.resId = headerConfiguration.getResId();
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public boolean isHeader() {
        return true;
    }
}
