package com.mk.utils;


import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Walker;

public class MathUtils {

    static public double distance(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        //TODO: implement test
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
    }

    static public double distance(final Position position, final int x2, final int y2, final int z2) {
        //TODO: implement test
        int x1 = position.getX();
        int y1 = position.getY();
        int z1 = position.getZ();

        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
    }

    static public double distance(final Position position1, final Position position2) {
        //TODO: implement test
        int x1 = position1.getX();
        int y1 = position1.getY();
        int z1 = position1.getZ();

        int x2 = position2.getX();
        int y2 = position2.getY();
        int z2 = position2.getZ();

        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
    }

    static public double distance(final Walker walker, final BondPosition position, final int x2, final int y2, final int z2) {
        //TODO: implement test
        double x1 = walker.getPosition().getX() + position.getX();
        double y1 = walker.getPosition().getY() + position.getY();
        double z1 = walker.getPosition().getZ() + position.getZ();

        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
    }
}
