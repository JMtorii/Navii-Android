package com.teamawesome.navii.util;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * This must be initialized at the start of the app, or everything will die.
 * Excellent design, I know.
 * Created by ecrothers on 2016-08-07.
 */
public abstract class AnalyticsManager {
    private static String projectToken = "3ff82d13d92fab6d2a258f9ea8a62747";
    private static MixpanelAPI mixpanel;

    public static void init(Context context) {
        mixpanel = MixpanelAPI.getInstance(context, projectToken);
    }

    public static MixpanelAPI getMixpanel() {
        return mixpanel;
    }
}
