package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Configuration;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Walker;
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

                if (zWalker < (value - 10)) walker = new Walker(sim.configuration, sim.substrate.getValue(x, y), x, y);

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

        for (int i = sim.substrate.getFront() - 6; i < sim.substrate.getFront() - 1 ; i++) {
            subArray = sim.mesh.get(
                    NDArrayIndex.all(),
                    NDArrayIndex.all(),
                    NDArrayIndex.interval(i, i + sim.substrate.getSpread() + 1));

            sum = subArray.mul(sim.substrate.getSubstrateArray()).sumNumber().intValue();

            if (sum >= sim.configuration.getGrowthRatio()) {
                sim.substrate.setFront(i);
                System.out.println("sum = " + sum + "  front = " + i);
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

    public boolean walkerSticks(final Walker walker) {
        int halfsize = sim.getKernel3D().length / 2;
        int xWalker = walker.getPosition().getX();
        int yWalker = walker.getPosition().getY();
        int zWalker = walker.getPosition().getZ();

        INDArray subArray = sim.getMesh().get(
                NDArrayIndex.interval(xWalker - halfsize, xWalker + halfsize + 1),
                NDArrayIndex.interval(yWalker - halfsize, yWalker + halfsize + 1),
                NDArrayIndex.interval(zWalker - halfsize, zWalker + halfsize + 1));

        float bondValue = subArray.mul(sim.getKernel3Dnd()).sumNumber().intValue();
        double rnd = ThreadLocalRandom.current().nextFloat() * Math.pow((double) bondValue, 2);

        System.out.println("bondValue = " + bondValue + "  bondValueKernelOverlap = " + calculateRotatedKernelOverlap(walker));

        return rnd > sim.getStickingProbability();
    }

    private double calculateRotatedKernelOverlap(final Walker walker) {
        //TODO: implement test
        double sum = 0;
        int halfDiag = (int) Math.floor(sim.getKernel3D().length * Math.sqrt(3) / 2);

        System.out.println("caclulate rotated kernel overlap: " + sim.getBondPositions());

        for (BondPosition bondPosition : sim.getBondPositions()) {
            for (int x = walker.getPosition().getX() - halfDiag; x < walker.getPosition().getX() + halfDiag; x++) {
                for (int y = walker.getPosition().getY() - halfDiag; y < walker.getPosition().getY() + halfDiag; y++) {
                    for (int z = walker.getPosition().getZ() - halfDiag; z < walker.getPosition().getZ() + halfDiag; z++) {
                        double distance = distance(walker, bondPosition, x, y, z);
                        System.out.println("(x, y, z) = (" + x + ", " + y + ", " + z + ")");
                        if (distance < 0.5) sum += sim.mesh.getInt(x, y, z);
                    }
                }
            }
        }
        return sum;
    }



    public List<BondPosition> calculateBondpositions(final float[][][] kernel) {
        List<BondPosition> bondPositions = new LinkedList<>();

        for (int x = 0; x < kernel.length; x++) {
            for (int y = 0; y < kernel.length; y++) {
                for (int z = 0; z < kernel.length; z++) {
                    if (kernel[x][y][z] > 0) bondPositions.add(new BondPosition(x, y, z, 1));
                }
            }
        }

        return bondPositions;
    }
}
