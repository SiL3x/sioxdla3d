package com.mk.higher;

import com.mk.SiOxDla3d;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import com.mk.utils.MoellerHughesRotation;
import com.mk.utils.SimulationUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
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

    @Before
    public void setUp() {
        sim = mock(SiOxDla3d.class);
        walker = mock(Walker.class);
        simulationUtils = new SimulationUtils(sim);
        substrate = prepareSubstrate();

        Position position = new Position(4, 4, 4);

        INDArray mesh = Nd4j.zeros(10, 10, 10);
        mesh.putScalar(4, 4, 5, 1);

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
    }

    @Test
    public void rotateToSubstrateNormalTest() {
        setUp();

        System.out.println("substrate.getValue(49, 49) = " + substrate.getValue(49, 49));
        System.out.println("substrate.getOrientation(49, 49) = " + substrate.getOrientation(49, 49));

        MoellerHughesRotation rotator = new MoellerHughesRotation(substrate.getOrientation(49, 49), new Vector3D(0, 0, 1));
        System.out.println("rotated = " + rotator.rotate(new Vector3D(0, 0, 1)));
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
