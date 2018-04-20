package com.mk.models.physics;

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
        //TODO: modify for 3D
        this.x = x;
        this.y = y;
        this.z = z;
        this.bondValue = bondValue;
        xTilt = x;
        yTilt = y;
        r = Math.sqrt(x * x + y * y);
        this.azimuth = Math.asin(x / r); //TODO: implement
        this.polar = r; //TODO: implement
    }

    public void tilt(final double turnAngle) {
        // turn angle in radians
        xTilt = r * Math.sin(angle + turnAngle);
        yTilt = -r * Math.cos(angle + turnAngle);
    }

    public double getX() {
        return xTilt;
    }

    public double getY() {
        return yTilt;
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
