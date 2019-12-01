package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Config;
import com.mk.configuration.ConfigLoader;
import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationUtils {

    private SiOxDla3d sim;
    final Vector3D UNITX = new Vector3D(1, 0, 0);
    final Vector3D UNITY = new Vector3D(0, 1, 0);

    public SimulationUtils(SiOxDla3d simulation) {
        this.sim = simulation;
    }

    public void placeSeeds() {
        final int seedNumber = sim.configuration.getSeedNumber();
        final int perRow = (int) Math.round(Math.sqrt(seedNumber));
        final int distance = (int) Math.round(sim.configuration.getMeshSizeX() / perRow);
        Walker walker;
        int x, y;

        for (int i = 0; i < seedNumber; i++) {
            x = (i % perRow) * distance + distance / 2;
            y = (int) Math.floor((i / perRow) * distance + distance / 2);

            if (x > (sim.configuration.getMeshSizeX() - 1) || y > (sim.configuration.getMeshSizeY() - 1)) {
                System.out.println("seed x or y out of bounds : " + x + ", " + y);
                System.out.println("meshSize X & Y = " + sim.configuration.getMeshSizeX());
                break;
            }

            walker = new Walker(sim.configuration, sim.substrate.getValue(x, y), x, y);

            boolean walking = true;
            while(walking) {
                walker.moveRnd();
                int xWalker = walker.getPosition().getX();
                int yWalker = walker.getPosition().getY();
                int zWalker = walker.getPosition().getZ();
                int value = sim.substrate.getValue(xWalker, yWalker);

                if (zWalker < (value - sim.getSubstrate().getSpread() - sim.getConfiguration().getSpawnOffset())) {
                    walker = new Walker(sim.configuration, sim.substrate.getValue(x, y), x, y);
                }

                if (value == zWalker ) {
                    sim.mesh.putScalar(xWalker, yWalker, zWalker, 10);
                    System.out.println("Seed at = " + walker.getPosition());
                    walking = false;
                }
            }
        }
    }

    public INDArray createMesh(final int meshSizeX, final int meshSizeY, final int meshSizeZ) {
        System.out.println("x = " + meshSizeX +  " y = " + meshSizeY + " z = " + meshSizeZ);
        INDArray outArray = Nd4j.zeros(meshSizeX, meshSizeY, meshSizeZ);
        return outArray;
    }

    public Config loadConfiguration(final String name) {
        sim.configuration = new ConfigLoader(name).getConfig();
        return sim.configuration;
    }

    public void moveGrowthFront() {
        int sum;
        INDArray subArray;
        Substrate substrate = sim.getSubstrate();
        System.out.println("substrate.getFront() = " + substrate.getFront() + "  - spread = " + substrate.getSpread() + "  - kernel = " + Math.round(sim.getConfiguration().getKernel3D().length * 0.866));

        for (int i = (substrate.getFront() - substrate.getSpread() - (int) Math.round(sim.getConfiguration().getKernel3D().length * 0.866)); i <= substrate.getFront(); i++) {
            System.out.println("i = " + i);
            subArray = sim.getMesh().get(
                    NDArrayIndex.all(),
                    NDArrayIndex.all(),
                    NDArrayIndex.interval(i, i + substrate.getSpread() + 1)).dup();
            sum = subArray.muli(substrate.getSubstrateArray()).sumNumber().intValue();

            if (sum >= sim.getConfiguration().getGrowthRatio()) {
                substrate.setFront(i + substrate.getSpread() - 1);
                System.out.println("sum = " + sum + "  front = " + substrate.getFront());
                break;
            }
        }
    }

    public int[] walkerSticks(final Walker walker) {
        final int xWalker = walker.getPosition().getX();
        final int yWalker = walker.getPosition().getY();

        Vector3D substrateNormal = sim.substrate.getOrientation(xWalker, yWalker);
        MoellerHughesRotation rotator = new MoellerHughesRotation(new Vector3D(0, 0, 1), substrateNormal);
        rotateBondPositions(rotator);

        List<int[]> positionsWithinDiffusionLength = calculatePositionsOnSurface(substrateNormal, sim.configuration.getDiffusionLength(), walker);

        Collections.shuffle(positionsWithinDiffusionLength);

        double bondValue = 0;

        for (int[] position : positionsWithinDiffusionLength) {
            bondValue = calculateRotatedKernelOverlap(position);
            if (bondValue > 0) {
                // TODO: include a choice into config. ThreadLocalRandom.current().nextFloat() * Math.exp(bondValue) > sim.getStickingProbability()
                if (ThreadLocalRandom.current().nextDouble(sim.getStickingProbability()) < bondValue) {
                    return position;
                }
            }
        }

        if (ThreadLocalRandom.current().nextDouble() < sim.configuration.getCrystallizationProbability()) {
            return new int[] {walker.getPosition().getX(), walker.getPosition().getY(), walker.getPosition().getZ()};
        }

        return new int[]{-1, -1, -1};
    }

    private List<int[]> calculatePositionsOnSurface(final Vector3D substrateNormal, final double diffusionLength, final Walker walker) {

        final double projectionX = substrateNormal
                .subtract(UNITX.scalarMultiply(substrateNormal.dotProduct(UNITX)))
                .getNorm();

        final double projectionY = substrateNormal
                .subtract(UNITY.scalarMultiply(substrateNormal.dotProduct(UNITY)))
                .getNorm();

        final int walkerX = walker.getPosition().getX();
        final int walkerY = walker.getPosition().getY();

        List<int[]> positions = new ArrayList<>();

        int xStartValue = (int) Math.round(walkerX - projectionX * diffusionLength);
        int xEndValue = (int) Math.round(walkerX + projectionX * diffusionLength);
        int yStartValue = (int) Math.round(walkerY - projectionY * diffusionLength);
        int yEndValue = (int) Math.round(walkerY + projectionY * diffusionLength);

        if (xEndValue > (sim.configuration.getMeshSizeX() - 1 - sim.getBorder())) xEndValue = sim.configuration.getMeshSizeX() - sim.getBorder();
        if (yEndValue > (sim.configuration.getMeshSizeY() - sim.getBorder())) yEndValue = sim.configuration.getMeshSizeY() - sim.getBorder();
        if (xStartValue < sim.getBorder()) xStartValue = sim.getBorder();
        if (yStartValue < sim.getBorder()) yStartValue = sim.getBorder();

        for (int x = xStartValue; x <= xEndValue ; x++) {
            for (int y = yStartValue; y <= yEndValue; y++) {
                positions.add(new int[]{x, y, sim.getSubstrate().getValueWithFront(x, y)});
            }
        }
        return positions;
    }

    public void rotateBondPositions(final double turnPol, final double turnAzi) {
        for (BondPosition bondPosition : sim.getBondPositions()) {
            bondPosition.tilt3D(turnPol, turnAzi);
        }
    }

    public void rotateBondPositions(final MoellerHughesRotation rotator) {
        for (BondPosition bondPosition : sim.getBondPositions()) {
            bondPosition.tilt3D(rotator);
        }
    }

    public double calculateRotatedKernelOverlap(final Walker walker) {
        double sum = 0;
        Position walkerPosition = walker.getPosition();

        for (BondPosition bondPosition : sim.getBondPositions()) {
            final int xBond = (int) Math.round(bondPosition.getX() + walkerPosition.getX());
            final int yBond = (int) Math.round(bondPosition.getY() + walkerPosition.getY());
            final int zBond = (int) Math.round(bondPosition.getZ() + walkerPosition.getZ());

            sum += sim.getMesh().getInt(xBond, yBond, zBond);
        }
        return sum;
    }

    public double calculateRotatedKernelOverlap(final int[] position) {
        double sum = 0;

        for (BondPosition bondPosition : sim.getBondPositions()) {
            final int xBond = (int) Math.round(bondPosition.getX() + position[0]);
            final int yBond = (int) Math.round(bondPosition.getY() + position[1]);
            final int zBond = (int) Math.round(bondPosition.getZ() + position[2]);

            sum += sim.getMesh().getInt(xBond, yBond, zBond);
        }
        return sum;
    }

    public List<BondPosition> calculateBondpositions(final float[][][] kernel) {
        List<BondPosition> bondPositions = new LinkedList<>();

        final int length = kernel.length;
        final int half = (int) Math.floor(length / 2);

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                for (int z = 0; z < length; z++) {
                    if (kernel[x][y][z] > 0) {
                        bondPositions.add(new BondPosition(x - half, y - half, z - half, 1));
                        System.out.println("(x, y, z) = (" + z + ", " + y + ", " + z + ")");
                    }
                }
            }
        }
        System.out.println("bondPositions = " + bondPositions);
        return bondPositions;
    }
}
