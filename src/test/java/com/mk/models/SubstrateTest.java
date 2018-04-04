package com.mk.models;

import com.mk.models.physics.Substrate;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.List;


class SubstrateTest {
    private final double DELTA = 1e-3;

    @Test
    public void createSubstrate() {
        Substrate substrate = new Substrate(100);

        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D(0 , 0, 90),
                        new Vector3D(99, 0, 90),
                        new Vector3D(0, 49, 90),
                        new Vector3D(99, 49, 90)
                ),
                Arrays.asList(
                        new Vector3D(0, 49, 90),
                        new Vector3D(0, 99, 90),
                        new Vector3D(99, 49, 90),
                        new Vector3D(99, 99, 90))
        );

        substrate.createSubstrate(vertices);

        Assert.assertEquals(90D, (double) substrate.values.minNumber(), DELTA);
        Assert.assertEquals(90D, (double) substrate.values.maxNumber(), DELTA);

    }

}