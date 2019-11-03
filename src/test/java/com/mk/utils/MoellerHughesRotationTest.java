package com.mk.utils;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.jupiter.api.Test;

public class MoellerHughesRotationTest {

    private Vector3D from;
    private Vector3D to;

    @Test
    public void rotateVectorTest() {
        System.out.println("\n");
        from = new Vector3D(1, 0, 0);
        to = new Vector3D(0, 1, 0);

        MoellerHughesRotation rotator = new MoellerHughesRotation(from, to);
        Vector3D vector3D = new Vector3D(1, 0, 0);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

        /*
        vector3D = new Vector3D(0, 1, 0);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

        vector3D = new Vector3D(1, 1, 0);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

        vector3D = new Vector3D(0, 0, 1);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

        vector3D = new Vector3D(0, -0.5, 1);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );
        */

        from = new Vector3D(0, 0, 1);
        to = new Vector3D(1, 1, 1);
        rotator = new MoellerHughesRotation(from, to);
        vector3D = new Vector3D(0, 0, 1);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );
    }

    @Test
    public void rotateNearlyParallelVectors() {
        System.out.println("\n");
        from = new Vector3D(1, 2.5, 0);
        to = new Vector3D(1, 3, 0);

        MoellerHughesRotation rotator = new MoellerHughesRotation(from, to);

        Vector3D vector3D = new Vector3D(1, 1, 0);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

        vector3D = new Vector3D(0, 0, 1);
        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );
    }
}
