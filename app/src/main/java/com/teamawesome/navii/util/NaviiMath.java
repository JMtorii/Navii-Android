package com.teamawesome.navii.util;

import java.util.Random;

/**
 * Created by JMtorii on 2015-10-23.
 */
public class NaviiMath {
    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
