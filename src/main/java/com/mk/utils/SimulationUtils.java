package com.mk.utils;

import com.mk.SiOxDla3d;
import com.mk.configuration.Configuration;
import com.mk.models.physics.Walker;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

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

    public void moveWalker() {
        int xWalker = sim.walker.getPosition().getX();
        int yWalker = sim.walker.getPosition().getY();
        int zWalker = sim.walker.getPosition().getZ();
        int zBorder = sim.substrate.getHighestPoint();

        sim.walker.moveRnd();

        //if (zWalker < (zBorder - 30)) sim.walker.respawn(sim.substrate);
        if (zWalker < 0) sim.walker.respawn(sim.substrate);
        if (zWalker >= (sim.substrate.values.getInt(xWalker, yWalker))) sim.walker.respawn(sim.substrate);
    }

    public void moveSeed() {
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

        //System.out.println("Walker = " + sim.walker.getPosition());

        INDArray subArray = sim.mesh.get(
                NDArrayIndex.interval(xWalker - 1, xWalker + 1),
                NDArrayIndex.interval(yWalker - 1, yWalker + 1),
                NDArrayIndex.interval(zWalker - 1, zWalker + 1));

        if ((int) subArray.sumNumber().intValue() > 0) {
            sim.mesh.putScalar(xWalker, yWalker, zWalker, 1);
            //System.out.println("Sticked at = " + sim.walker.getPosition());
        }
    }

    public void moveGrowthFront() {
        //TODO: move growth front
        //TODO: use nd-array multiplication for summation
        INDArray slice;
        int sum;
        for (int i = sim.substrate.getFront() - 1; i > sim.substrate.getFront() - 6; i--) { //sim.substrate.getSpread()
            slice = sim.mesh.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(i));
            sum = slice.sumNumber().intValue();

            if (sum >= sim.configuration.getGrowthRatio()) {
                sim.substrate.setFront(i);
                System.out.println("slice.sumNumber() = " + sum + "  front = " + i);
            }

            /*
            sum = 0;
            for (int x = 0; x < sim.meshSize; x++) {
                for (int y = 0; y < sim.meshSize; y++) {
                    sum += sim.mesh.getInt(x, y, sim.substrate.getValue(x, y) + (i - sim.substrate.getFront()));
                }
            }

            if(sum != 0) {
                System.out.println("sum = " + sum);
            }
            if ((100 * sum / sim.meshSize * sim.meshSize) >=  sim.configuration.getGrowthRatio()) {
                sim.substrate.setFront(i);
                System.out.println("New front = " + i);
            }
            */
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
}
