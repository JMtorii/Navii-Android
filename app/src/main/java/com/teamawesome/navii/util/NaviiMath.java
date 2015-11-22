package com.teamawesome.navii.util;

import java.util.Random;

/**
 * Created by JMtorii on 2015-10-23.
 */
public class NaviiMath {

    private NaviiMath() {}

    /**
     * Produces a pseudo-random uniformly distributed integer given a range
     * @param min   Minimum number
     * @param max   Maximum number
     * @return      Random number in range [min, max)
     */
    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
