package com.mk.higher;

import com.mk.SiOxDla3d;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import com.mk.utils.MoellerHughesRotation;
import com.mk.utils.SimulationUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KernelRotationTest {
    List<BondPosition> bondPositions;
    SiOxDla3d sim;
    Walker walker;
    SimulationUtils simulationUtils;
    Substrate substrate;

    private final double DELTA = 1e-3;

    @Test
    public void rotateToSubstrateNormalTest() {
        sim = mock(SiOxDla3d.class);
        walker = mock(Walker.class);
        simulationUtils = new SimulationUtils(sim);
        substrate = prepareSubstrate();

        Position position = new Position(49, 48, 49);

        INDArray mesh = Nd4j.zeros(100, 100, 100);
        mesh.putScalar(49, 49, 50, 1);

        float[][][] kernel =
                {
                        {{0, 0, 0},
                                {0, 0, 0},
                                {0, 0, 0}},
                        {{0, 0, 0},
                                {0, 0, 1},
                                {0, 0, 0}},
                        {{0, 0, 0},
                                {0, 0, 0},
                                {0, 0, 0}}
                };

        bondPositions = simulationUtils.calculateBondpositions(kernel);

        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        int[] shape = {kernel.length, kernel.length, kernel.length};

        when(sim.getKernel3D()).thenReturn(kernel);
        when(sim.getMesh()).thenReturn(mesh);
        when(sim.getKernel3Dnd()).thenReturn(Nd4j.create(flatKernel, shape, 'c'));
        when(sim.getBondPositions()).thenReturn(bondPositions);
        when(walker.getPosition()).thenReturn(position);
        System.out.println("substrate.getValue(49, 49) = " + substrate.getValue(49, 49));
        System.out.println("substrate.getOrientation(49, 49) = " + substrate.getOrientation(49, 49));

        MoellerHughesRotation rotator = new MoellerHughesRotation(new Vector3D(0, 0, 1), substrate.getOrientation(49, 49));
        System.out.println("rotated = " + rotator.rotate(new Vector3D(0, 0, 1)));

        simulationUtils.rotateBondPositions(rotator);
        Assert.assertEquals(simulationUtils.calculateRotatedKernelOverlap(walker), 1, DELTA);

        System.out.println("rotated = " + rotator.rotate(new Vector3D(1, 1, 1)));
        System.out.println("rotated = " + rotator.rotate(new Vector3D(1, -1, 1)));

        simulationUtils.rotateBondPositions(rotator);

    }

    private Substrate prepareSubstrate() {
        Substrate substrate = new Substrate(100);

        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D(0 , 0, 99),
                        new Vector3D(99, 0, 99),
                        new Vector3D(0, 99, 1),
                        new Vector3D(99, 99, 1)
                )
        );

        substrate.createSubstrate(vertices);
        substrate.calculateOrientationMap();

        return substrate;
    }


}
