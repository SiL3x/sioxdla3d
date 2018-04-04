package com.mk.models.physics;

public class BondPosition {

    private final int x;
    private final int y;
    private final int bondValue;
    private final double r;
    private final double angle;
    private double xTilt;
    private double yTilt;

    public BondPosition(final int x, final int y, final int bondValue) {
        //TODO: modify for 3D
        this.x = x;
        this.y = y;
        this.bondValue = bondValue;
        xTilt = x;
        yTilt = y;
        r = Math.sqrt(x * x + y * y);
        angle = Math.asin(x / r); // In radians - only valid for below the center
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
