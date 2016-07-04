package com.teamawesome.navii.util;

import com.teamawesome.navii.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sjung on 03/07/16.
 */
public enum HeartAndSoulHeaderConfiguration {
    MORNING(R.drawable.heart_and_soul_morning, "Morning"),
    AFTERNOON(R.drawable.heart_and_soul_afternoon, "Afternoon"),
    NIGHT(R.drawable.heart_and_soul_night, "Night");

    private int resId;
    private String name;

    HeartAndSoulHeaderConfiguration(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public static HeartAndSoulHeaderConfiguration getConfiguration(int id) {
        return configurationMap.get(id);
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }

    private static Map<Integer, HeartAndSoulHeaderConfiguration> configurationMap;
    static {
        configurationMap = new HashMap<>();
        configurationMap.put(0, MORNING);
        configurationMap.put(1, AFTERNOON);
        configurationMap.put(2, NIGHT);
    }
}
