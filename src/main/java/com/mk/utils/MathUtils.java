package com.mk.utils;


public class MathUtils {

    static public double distance(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
    }

}
