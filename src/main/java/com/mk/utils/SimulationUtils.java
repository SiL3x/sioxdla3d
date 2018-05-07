package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Configuration;
import com.mk.models.physics.Walker;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.concurrent.ThreadLocalRandom;

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
        //TODO: implement calculate sticking with kernel
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
        //TODO: implement probability
        int halfsize = sim.configuration.getKernel3D().length / 2;
        int xWalker = walker.getPosition().getX();
        int yWalker = walker.getPosition().getY();
        int zWalker = walker.getPosition().getZ();

        INDArray subArray = sim.mesh.get(
                NDArrayIndex.interval(xWalker - halfsize, xWalker + halfsize + 1),
                NDArrayIndex.interval(yWalker - halfsize, yWalker + halfsize + 1),
                NDArrayIndex.interval(zWalker - halfsize, zWalker + halfsize + 1));

        float bondValue = subArray.mul(sim.configuration.getKernel3Dnd()).sumNumber().intValue();
        double rnd = ThreadLocalRandom.current().nextFloat() * Math.pow((double) bondValue, 2);
        return rnd > sim.configuration.getStickingProbability();
    }
}
