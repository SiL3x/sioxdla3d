package com.mk.models.physics;

import com.mk.utils.MoellerHughesRotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;

public class BondPosition {

    private int x;
    private int y;
    private int z;
    private final int bondValue;
    private final double r;
    private double angle;
    private double xTilt;
    private double yTilt;
    private double azimuth;
    private double polar;
    private double zTilt;

    public BondPosition(final int x, final int y, final int bondValue) {
        this.x = x;
        this.y = y;
        this.bondValue = bondValue;
        xTilt = x;
        yTilt = y;
        r = Math.sqrt(x * x + y * y);
        angle = Math.asin(x / r); // In radians - only valid for below the center
    }

    public BondPosition(final int x, final int y, final int z, final int bondValue) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.bondValue = bondValue;
        xTilt = x;
        yTilt = y;
        zTilt = z;
        r = Math.sqrt(x*x + y*y + z*z);  // In the xy plane
        this.polar = Math.asin(y / Math.sqrt(x*x + y*y));
        this.azimuth = Math.asin(z / r);
    }

    public void tilt(final double turnAngle) {
        // turn angle in radians
        xTilt = r * Math.sin(angle + turnAngle);
        yTilt = -r * Math.cos(angle + turnAngle);
    }

    public void tilt3D(final double turnPol, final double turnAzi) {
        // turn angles in radians
        // first rotate around z axis
        xTilt = Math.cos(turnPol) * x - Math.sin(turnPol) * y;
        yTilt = Math.sin(turnPol) * x + Math.cos(turnPol) * y;

        // next rotate around y axis
        zTilt =  - Math.sin(turnAzi) * xTilt + Math.cos(turnAzi) * z;
        xTilt =   Math.cos(turnAzi) * xTilt + Math.sin(turnAzi) * z;

    }

    public void tilt3D(MoellerHughesRotation rotator) {
        Vector3D vector = rotator.rotate(new Vector3D(x, y, z));

        xTilt = vector.getX();
        yTilt = vector.getY();
        zTilt = vector.getZ();

        //System.out.println("Vec = " + new Vector3D(x, y, z) + "  ==>  (" + xTilt + ", " +yTilt+", "+zTilt);
    }

    public double getX() {
        return xTilt;
    }

    public double getY() {
        return yTilt;
    }

    public double getZ() {
        return zTilt;
    }

    public double getAngle() {
        return angle;
    }

    public int getValue() {
        return bondValue;
    }

    @Override
    public String toString() {
        return ("Value = " + bondValue + "  @ (" + x + ", " + y + ")");
    }
}
