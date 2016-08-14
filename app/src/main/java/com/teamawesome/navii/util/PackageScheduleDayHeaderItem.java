package com.teamawesome.navii.util;

/**
 * Created by sjung on 14/08/16.
 */
public class PackageScheduleDayHeaderItem implements PackageScheduleListItem {

    private String name;

    public PackageScheduleDayHeaderItem(String name) {
        this.name = name;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public boolean isDayHeader() {
        return true;
    }

    public String getName() {
        return name;
    }
}
