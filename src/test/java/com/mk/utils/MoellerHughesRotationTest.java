package com.mk.utils;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class MoellerHughesRotationTest {

    private Vector3D from;
    private Vector3D to;

    @Test
    public void rotateVectorTest() {
        from = new Vector3D(1, 0, 0);
        to = new Vector3D(0, 1, 0);

        Vector3D vector3D = new Vector3D(1, 1, 0);

        MoellerHughesRotation rotator = new MoellerHughesRotation(from, to);

        System.out.println("Vector = " + vector3D + "  => " + rotator.rotate(vector3D) );

    }
}
