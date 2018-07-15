package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimulationUtilsTest {
    //TODO: implement tests

    @Test
    public void doesItStickTest() {
        //TODO: rework for new walkerSticks method
        SiOxDla3d sim = mock(SiOxDla3d.class);
        Walker walker = mock(Walker.class);

        Position position = new Position(4, 4, 4);

        INDArray mesh = Nd4j.zeros(10, 10, 10);
        mesh.putScalar(4, 4, 5, 1);

        when(walker.getPosition()).thenReturn(position);
        sim.walker = walker;
        sim.mesh = mesh;

        SimulationUtils simulationUtils = new SimulationUtils(sim);

        //Assert.assertTrue(simulationUtils.walkerSticks());

        position = new Position(4, 4, 3);
        when(walker.getPosition()).thenReturn(position);
        sim.walker = walker;

        //Assert.assertFalse(simulationUtils.walkerSticks());
    }

    @Test
    public void stickingKernelTest() {
        SiOxDla3d sim = mock(SiOxDla3d.class);
        Walker walker = mock(Walker.class);
        SimulationUtils simulationUtils = new SimulationUtils(sim);

        Position position = new Position(4, 4, 4);

        INDArray mesh = Nd4j.zeros(10, 10, 10);
        mesh.putScalar(4, 4, 5, 1);

        float[][][] kernel =
                        {{{0, 0, 0},
                          {0, 0, 0},
                          {0, 0, 0}},
                         {{0, 0, 0},
                          {0, 1, 0},
                          {0, 0, 0}},
                         {{0, 0, 0},
                          {0, 0, 0},
                          {0, 0, 0}}};

        List<BondPosition> bondPositions = simulationUtils.calculateBondpositions(kernel);

        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        int[] shape = {kernel.length, kernel.length, kernel.length};

        when(sim.getKernel3D()).thenReturn(kernel);
        when(sim.getMesh()).thenReturn(mesh);
        when(sim.getKernel3Dnd()).thenReturn(Nd4j.create(flatKernel, shape, 'c'));
        when(sim.getBondPositions()).thenReturn(bondPositions);
        when(walker.getPosition()).thenReturn(position);

        sim.walker = walker;
        sim.mesh = mesh;

        Assert.assertFalse(simulationUtils.walkerSticks(walker));

        position = new Position(4, 4, 5);
        when(walker.getPosition()).thenReturn(position);
        sim.walker = walker;

        Assert.assertTrue(simulationUtils.walkerSticks(walker));
    }

    @Test
    public void stickingRotatedKernelTest() {
        //TODO: finish test
        SiOxDla3d sim = mock(SiOxDla3d.class);
        Walker walker = mock(Walker.class);
        SimulationUtils simulationUtils = new SimulationUtils(sim);

        Position position = new Position(4, 4, 4);

        INDArray mesh = Nd4j.zeros(10, 10, 10);
        mesh.putScalar(4, 5, 5, 1);

        float[][][] kernel =
                {
                        {
                            {0, 0, 0},
                            {1, 0, 0},
                            {0, 0, 0}
                        },
                        {
                            {0, 0, 0},
                            {0, 0, 1},
                            {0, 0, 0}
                        },
                        {
                            {0, 0, 0},
                            {0, 0, 0},
                            {0, 0, 0}
                        }
                };

        List<BondPosition> bondPositions = simulationUtils.calculateBondpositions(kernel);

        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        int[] shape = {kernel.length, kernel.length, kernel.length};

        when(sim.getKernel3D()).thenReturn(kernel);
        when(sim.getMesh()).thenReturn(mesh);
        when(sim.getKernel3Dnd()).thenReturn(Nd4j.create(flatKernel, shape, 'c'));
        when(sim.getBondPositions()).thenReturn(bondPositions);
        when(walker.getPosition()).thenReturn(position);

        sim.walker = walker;
        sim.mesh = mesh;

        simulationUtils.rotateBondPositions(0, Math.toRadians(90));

        Assert.assertFalse(simulationUtils.walkerSticks(walker));

        /*
        position = new Position(4, 4, 5);
        when(walker.getPosition()).thenReturn(position);
        sim.walker = walker;

        Assert.assertTrue(simulationUtils.walkerSticks(walker));
        */
    }

    @Test
    public void moveGrowthFrontTest() {
        SiOxDla3d sim = mock(SiOxDla3d.class);
        Configuration configuration = mock(Configuration.class);
        Substrate substrate = createTestSubstrate();
        when(configuration.getGrowthRatio()).thenReturn(4);
        when(sim.getConfiguration()).thenReturn(configuration);
        when(sim.getSubstrate()).thenReturn(substrate);

        INDArray mesh = Nd4j.zeros(10, 10, 10);
        mesh.putScalar(2, 0, 7, 1);
        mesh.putScalar(6, 0, 7, 1);
        mesh.putScalar(2, 9, 5, 1);
        mesh.putScalar(6, 9, 5, 1);

        when(sim.getMesh()).thenReturn(mesh);

        SimulationUtils simulationUtils = new SimulationUtils(sim);

        simulationUtils.moveGrowthFront();

        Assert.assertEquals(sim.getSubstrate().getFront(), 6);
    }

    private Substrate createTestSubstrate() {
        Substrate substrate = new Substrate(10);

        List<List<Vector3D>> vertices = Arrays.asList(
                Arrays.asList(
                        new Vector3D(0, 0, 8),
                        new Vector3D(9, 0, 8),
                        new Vector3D(0, 9, 6),
                        new Vector3D(9, 9, 6))
        );
        substrate.createSubstrate(vertices);
        return substrate;
    }
}
