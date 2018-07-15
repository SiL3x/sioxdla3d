package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.mk.utils.MathUtils.distance;

public class SimulationUtils {

    private SiOxDla3d sim;

    public SimulationUtils(SiOxDla3d simulation) {
        this.sim = simulation;

    }

    public void placeSeeds() {
        System.out.println("place seeds");
        int seedNumber = sim.configuration.getSeedNumber();
        Walker walker;
        int perRow = (int) Math.round(Math.sqrt(seedNumber));
        int distance = (int) Math.round(sim.meshSize / perRow);
        int x;
        int y;

        for (int i = 0; i < seedNumber; i++) {
            x = (i % perRow) * distance + distance / 2;
            y = (int) Math.floor((i / perRow) * distance + distance / 2);

            if (x > (sim.meshSize - 1) || y > (sim.meshSize - 1)) {
                System.out.println("seed x or y out of bounds : " + x + ", " + y);
                System.out.println("meshSize = " + sim.meshSize);
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
                    sim.mesh.putScalar(xWalker, yWalker, zWalker, 1);
                    System.out.println("Seed at = " + walker.getPosition());
                    walking = false;
                }
            }
        }
    }

    public INDArray createMesh() {
        int meshSize = sim.configuration.getMeshSize();
        INDArray outArray = Nd4j.zeros(meshSize, meshSize, meshSize);
        return outArray;
    }

    public Configuration loadConfiguration(String name) {
        sim.configuration = new Configuration(name);
        //TODO: load configuration from resources
        return sim.configuration;
    }

    public void moveWalker(Walker walker) {
        int xWalker = walker.getPosition().getX();
        int yWalker = walker.getPosition().getY();
        int zWalker = walker.getPosition().getZ();
        int zBorder = sim.substrate.getHighestPoint();

        walker.moveRnd();

        //if (zWalker < (zBorder - 30)) sim.walker.respawn(sim.substrate);
        if (zWalker < 0) walker.respawn(sim.substrate);
        if (zWalker >= (sim.substrate.values.getInt(xWalker, yWalker))) walker.respawn(sim.substrate);
    }

    public void moveSeed() {
        //TODO: Take substrate into account
        int xWalker = sim.walker.getPosition().getX();
        int yWalker = sim.walker.getPosition().getY();
        int zWalker = sim.walker.getPosition().getZ();
        int zBorder = sim.substrate.getHighestPoint();

        sim.walker.moveRnd();

        if (zWalker < (zBorder - 30)) sim.walker.respawn(sim.substrate);
        if (zWalker < 0) sim.walker.respawn(sim.substrate);
    }

    public void calculateSticking() {
        //TODO: implement calculate sticking with kernel
        int xWalker = sim.walker.getPosition().getX();
        int yWalker = sim.walker.getPosition().getY();
        int zWalker = sim.walker.getPosition().getZ();

        INDArray subArray = sim.mesh.get(
                NDArrayIndex.interval(xWalker - 1, xWalker + 1),
                NDArrayIndex.interval(yWalker - 1, yWalker + 1),
                NDArrayIndex.interval(zWalker - 1, zWalker + 1));

        if ((int) subArray.sumNumber().intValue() > 0) {
            sim.mesh.putScalar(xWalker, yWalker, zWalker, 1);
        }
    }

    public void moveGrowthFront() {

        int sum;
        INDArray subArray;
        Substrate substrate = sim.getSubstrate();

        for (int i = substrate.getFront() - substrate.getSpread(); i <= substrate.getFront(); i++) {
            subArray = sim.getMesh().get(
                    NDArrayIndex.all(),
                    NDArrayIndex.all(),
                    NDArrayIndex.interval(i, i + substrate.getSpread() + 1)).dup();

            sum = subArray.muli(substrate.getSubstrateArray()).sumNumber().intValue();
            //System.out.println("i = " + i + "  z = " + (i + substrate.getSpread()) + "  sum = " + sum);

            if (sum >= sim.getConfiguration().getGrowthRatio()) {
                substrate.setFront(i + substrate.getSpread() - 1);
                System.out.println("sum = " + sum + "  front = " + substrate.getFront());
                break;
            }
        }
    }

    public boolean walkerSticks() {
        int xWalker = sim.walker.getPosition().getX();
        int yWalker = sim.walker.getPosition().getY();
        int zWalker = sim.walker.getPosition().getZ();

        INDArray subArray = sim.mesh.get(
                NDArrayIndex.interval(xWalker - 1, xWalker + 2),
                NDArrayIndex.interval(yWalker - 1, yWalker + 2),
                NDArrayIndex.interval(zWalker - 1, zWalker + 2));

        return subArray.sumNumber().intValue() > 0;
    }


    /* Old Version
    public boolean walkerSticks(final Walker walker) {
        int halfsize = sim.getKernel3D().length / 2;
        int xWalker = walker.getPosition().getX();
        int yWalker = walker.getPosition().getY();
        int zWalker = walker.getPosition().getZ();

        INDArray subArray = sim.getMesh().get(
                NDArrayIndex.interval(xWalker - halfsize, xWalker + halfsize + 1),
                NDArrayIndex.interval(yWalker - halfsize, yWalker + halfsize + 1),
                NDArrayIndex.interval(zWalker - halfsize, zWalker + halfsize + 1)
        );

        //float bondValue = subArray.mul(sim.getKernel3Dnd()).sumNumber().intValue();
        double bondValue = calculateRotatedKernelOverlap(walker);
        double rnd = ThreadLocalRandom.current().nextFloat() * Math.pow((double) bondValue, 2);

        System.out.println("bondValue = " + bondValue);
        //System.out.println("bondValue = " + bondValue + "  bondValueKernelOverlap = " + calculateRotatedKernelOverlap(walker));

        return rnd > sim.getStickingProbability();
    }
    */

    public boolean walkerSticks(final Walker walker) {
        int halfsize = sim.getKernel3D().length / 2;
        int xWalker = walker.getPosition().getX();
        int yWalker = walker.getPosition().getY();
        int zWalker = walker.getPosition().getZ();

        Vector3D substrateNormal = sim.substrate.getOrientation(xWalker, yWalker);
        MoellerHughesRotation rotator = new MoellerHughesRotation(new Vector3D(0, 0, 1), substrateNormal);
        rotateBondPositions(rotator);
        double bondValue = calculateRotatedKernelOverlap(walker);
        double rnd = ThreadLocalRandom.current().nextFloat() * Math.pow((double) bondValue, 2);

        //return rnd > sim.getStickingProbability();

        return bondValue > 0;
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
        //TODO: implement test
        //TODO: optimize the loops
        double sum = 0;
        int halfDiag = (int) Math.floor(sim.getKernel3D().length * Math.sqrt(3) / 2);
        // int half = (int) Math.floor(sim.getKernel3D().length / 2);

        //System.out.println("caclulate rotated kernel overlap: " + sim.getBondPositions());
        Position walkerPosition = walker.getPosition();
        int xWalk = walkerPosition.getX();
        int yWalk = walkerPosition.getY();
        int zWalk = walkerPosition.getZ();

        for (BondPosition bondPosition : sim.getBondPositions()) {
            //System.out.println("Walker pos = " + walker.getPosition() + "  halfDiag = " + half);
            for (int x = xWalk - halfDiag; x <= xWalk + halfDiag; x++) {
                for (int y = yWalk - halfDiag; y <= yWalk + halfDiag; y++) {
                    for (int z = zWalk - halfDiag; z <= zWalk + halfDiag; z++) {

                        if (inBoundary(bondPosition, walker.getPosition(), x, y, z)) {
                            //System.out.println("walker_pos = " + walker.getPosition() + " nearest = (" + x + ", " + y + ", " + z + ")  value = " +sim.getMesh().getInt(x, y, z) + "  bp = (" + bondPosition.getX() + ", " + bondPosition.getY() + ", " + bondPosition.getZ() + ")" );
                            sum += sim.getMesh().getInt(x, y, z);
                        }
                    }
                }
            }
        }
        return sum;
    }

    private boolean inBoundary(BondPosition bondPosition, Position walker, int x, int y, int z) {
        double xBond = bondPosition.getX() + walker.getX();
        double yBond = bondPosition.getY() + walker.getY();
        double zBond = bondPosition.getZ() + walker.getZ();

        //System.out.println("pos = (" + x + ", " + y + ", " + z + ")");
        //System.out.println("bond = (" + xBond + ", " + yBond + ", " + zBond + ")");
        return  xBond >= (x - 0.5) && xBond < (x + 0.5) &&
                yBond >= (y - 0.5) && yBond < (y + 0.5) &&
                zBond >= (z - 0.5) && zBond < (z + 0.5);

    }

    public List<BondPosition> calculateBondpositions(final float[][][] kernel) {
        List<BondPosition> bondPositions = new LinkedList<>();

        int length = kernel.length;
        int half = (int) Math.floor(length / 2);

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                for (int z = 0; z < length; z++) {
                    if (kernel[x][y][z] > 0) bondPositions.add(new BondPosition(x - half, y - half, z - half, 1));
                }
            }
        }

        return bondPositions;
    }
}
