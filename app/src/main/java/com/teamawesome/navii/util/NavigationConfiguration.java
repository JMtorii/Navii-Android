package com.teamawesome.navii.util;

import com.teamawesome.navii.R;

/**
 * Created by JMtorii on 16-06-13.
 */
public enum NavigationConfiguration {
    Home(R.layout.activity_main2, R.string.drawer_home, R.string.drawer_home, R.drawable.ic_menu_send,
            R.drawable.ic_menu_send, R.id.nav_home),
    PlannedTrips(R.layout.activity_planned_trips, R.string.drawer_planned_trips, R.string.drawer_planned_trips, R.drawable.ic_menu_send,
            R.drawable.ic_menu_send, R.id.nav_planned_trips);

    private int layoutResId;
    private int navTitleResId;
    private int toolbarTitleResId;
    private int iconResId;
    private int selectedIconResId;
    private int drawerItemId;

    NavigationConfiguration(int layoutId, int navTitleId, int toolbarTitleId, int iconId, int selectedId, int drawerId) {
        layoutResId = layoutId;
        navTitleResId = navTitleId;
        toolbarTitleResId = toolbarTitleId;
        iconResId = iconId;
        selectedIconResId = selectedId;
        drawerItemId = drawerId;
    }

    public int getNavTitleResId() {
        return navTitleResId;
    }

    public int getToolbarTitleResId() {
        return toolbarTitleResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getSelectedIconResId() {
        return selectedIconResId;
    }

    public int getDrawerItemId() {
        return drawerItemId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }
}
