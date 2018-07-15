package com.mk.utils;


import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Walker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

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

    static public Vector3D ndToVector(INDArray indArray) {
        //TODO: implement test
        return new Vector3D(indArray.getDouble(0), indArray.getDouble(1), indArray.getDouble(2));
    }

    static public INDArray vectorToNd(Vector3D vector3D) {
        //TODO: implement test
        return Nd4j.create(
                new float[]{
                        (float) vector3D.getX(),
                        (float) vector3D.getY(),
                        (float) vector3D.getZ()
                }, new int[]{3});
    }
}
