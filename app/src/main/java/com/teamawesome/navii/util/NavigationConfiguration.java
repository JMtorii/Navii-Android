package com.teamawesome.navii.util;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-06-13.
 */
public enum NavigationConfiguration {
    Profile(R.layout.activity_profile, R.string.toolbar_home, -1),
    Home(R.layout.activity_main2, R.string.toolbar_home, R.id.nav_home),
    PlannedTrips(R.layout.activity_planned_trips, R.string.toolbar_planned_trips, R.id.nav_planned_trips);

    public static final int PROFILE_DRAWER_ID = -1;

    private int layoutResId;
    private int toolbarTitleResId;
    private int drawerItemId;


    NavigationConfiguration(int layoutId, int toolbarTitleId, int drawerId) {
        layoutResId = layoutId;
        toolbarTitleResId = toolbarTitleId;
        drawerItemId = drawerId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public int getToolbarTitleResId() {
        return toolbarTitleResId;
    }

    public int getDrawerItemId() {
        return drawerItemId;
    }
}
