package com.mk.models;

import com.mk.models.physics.Substrate;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
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

    @Test
    public void createOrientationMap() {
        //TODO: finish test
        Substrate substrate = new Substrate(100);

        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D( 0, 49, 90),
                        new Vector3D(99, 49, 90),
                        new Vector3D(99, 99, 40),
                        new Vector3D( 0, 99, 40)),
                Arrays.asList(
                        new Vector3D( 0, 0, 90),
                        new Vector3D(99, 0, 90),
                        new Vector3D(99,49, 90),
                        new Vector3D( 0,49, 90)
                ));


        /*
        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D( 0,  0, 90),
                        new Vector3D(99,  0, 90),
                        new Vector3D(99, 99, 90),
                        new Vector3D( 0, 99, 90)));
        */

        /*
        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D( 0,  0, 90),
                        new Vector3D(99,  0, 90),
                        new Vector3D(99, 99, 40),
                        new Vector3D( 0, 99, 40)));
        */

        substrate.createSubstrate(vertices);
        substrate.calculateOrientationMap();
        //System.out.println("substrate.getOrientationMap() = " + substrate.getOrientationMap());
        System.out.println("(49, 0) substrate = " + substrate.getValue(49, 0) + "  normal = " + substrate.getOrientation(49, 0));
        System.out.println("(49, 24) substrate = " + substrate.getValue(49, 24) + "  normal = " + substrate.getOrientation(49, 24));
        System.out.println("(49, 44) substrate = " + substrate.getValue(49, 44) + "  normal = " + substrate.getOrientation(49, 44));
        System.out.println("(49, 49) substrate = " + substrate.getValue(49, 49) + "  normal = " + substrate.getOrientation(49, 49));
        System.out.println("(49, 54) substrate = " + substrate.getValue(49, 54) + "  normal = " + substrate.getOrientation(49, 54));
        System.out.println("(49, 74) substrate = " + substrate.getValue(49, 74) + "  normal = " + substrate.getOrientation(49, 74));
        System.out.println("(49, 99) substrate = " + substrate.getValue(49, 99) + "  normal = " + substrate.getOrientation(49, 99));
    }

    @Test
    public void createSubstrateArrayTest() {
        Substrate substrate = new Substrate(5);

        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D(0, 0, 4),
                        new Vector3D(4, 0, 4),
                        new Vector3D(0, 4, 2),
                        new Vector3D(4, 4, 2))
                );

        substrate.createSubstrate(vertices);
        INDArray substrateArray = substrate.createSubstrateArray();
        Assert.assertEquals(substrateArray.getInt(0, 0, 2), 1);
        Assert.assertEquals(substrateArray.getInt(4, 4, 0), 1);
        Assert.assertEquals(substrateArray.getInt(2, 2, 1), 1);

        System.out.println(substrate.getValueWithFront(0, 0));


    }
}
