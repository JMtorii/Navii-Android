package com.teamawesome.navii.util;

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

    public PackageScheduleAttractionItem(Attraction attraction) {
        this.attraction = attraction;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
