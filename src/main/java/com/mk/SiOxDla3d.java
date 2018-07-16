package com.mk;

import com.mk.configuration.Configuration;
import com.mk.graphic.PlotMesh;
import com.mk.models.geometries.Position;
import com.mk.models.physics.BondPosition;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import com.mk.utils.SimulationUtils;
import org.jzy3d.analysis.AnalysisLauncher;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.toList;

/**
 * Diffusion limited aggregation model in 3D
 * for SiOx growth in a PECVD
 *
 */
public class SiOxDla3d {

    public Configuration configuration;
    public INDArray mesh;
    public Substrate substrate;
    public int meshSize;
    public Walker walker;
    public List<BondPosition> bondPositions;
    public SimulationUtils simulationUtils;

    private boolean run = true;
    private String name = "very_large_test";


    public SiOxDla3d() throws Exception {
        simulationUtils = new SimulationUtils(this);
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println(">>> Cores detected : " + cores);

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(">>> Thread pool : " + commonPool.getParallelism());

        System.out.println(">>> Loading configuration: " + name);
        configuration = simulationUtils.loadConfiguration(name);
        meshSize = configuration.getMeshSize();
        System.out.println(">>> creating mesh");
        mesh = simulationUtils.createMesh();

        System.out.println(">>> Creating substrate");
        substrate = new Substrate(configuration.getMeshSize());
        substrate.createSubstrate(configuration.getSubstrate());

        //TODO: calculate bond positions
        bondPositions = simulationUtils.calculateBondpositions(configuration.getKernel3D());

        simulationUtils.placeSeeds();

        //TODO: set iteration variables
        int i = 0;
        int sticked = 0;

        List<Walker> walkers = new ArrayList<>();

        for (int j = 0; j < configuration.getSectorNumber(); j++) {
            walkers.add(new Walker(configuration, substrate.getFront(), j));
        }

        while (run) {

            List<Position> positions = walkers.parallelStream()
                    .map(w -> spawnMoveAndStick(w).getPosition())
                    .collect(toList());

            for (Position position : positions) {
                mesh.putScalar(
                        position.getX(),
                        position.getY(),
                        position.getZ(), 1);
            }

            simulationUtils.moveGrowthFront();

            //TODO: check break conditions (number of crystallites, front, no of iterations)
            if (i > 1e3) run = false;
            if (substrate.getFront() <= 10 + substrate.getSpread()) run = false;
            i++;
        }


        PlotMesh plotMesh = new PlotMesh();
        plotMesh.plot3d(mesh);
        AnalysisLauncher.open(plotMesh);
    }

    private Walker spawnMoveAndStick(Walker walker) {
        boolean notSticked = true;
        walker.respawn(substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset());
        //System.out.println("respawn_z = " + (substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset()));

        while (notSticked) {
            walker.moveRnd();
            if (walkerIsTooFarOrBelowSurface(walker)) walker.respawn(substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset());
            if (walkerIsNearToSurface(walker)) notSticked = !simulationUtils.walkerSticks(walker);
        }
        System.out.println("walker = " + (substrate.getValue(walker.getPosition().getX(), walker.getPosition().getY()) - walker.getPosition().getZ()));
        return walker;
    }

    private boolean walkerIsTooFarOrBelowSurface(Walker walker) {
        return walker.getPosition().getZ() < substrate.getFront() -substrate.getSpread() - 20 ||
                walker.getPosition().getZ() >= substrate.getValueWithFront(walker.getPosition().getX(), walker.getPosition().getY());
    }

    private boolean walkerIsNearToSurface(Walker walker) {
        return walker.getPosition().getZ() >= (substrate.getValueWithFront(walker.getPosition().getX(), walker.getPosition().getY()) - configuration.getKernel3D().length * 0.866);
    }

    public static void main( String[] args ) throws Exception {
        System.out.println("### New DLA Simulation");
        SiOxDla3d siOxDla3d = new SiOxDla3d();
    }

    public float[][][] getKernel3D() {
        return configuration.getKernel3D();
    }

    public INDArray getKernel3Dnd() {
        float[][][] kernel = getKernel3D();
        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        int[] shape = {kernel.length, kernel.length, kernel.length};
        return  Nd4j.create(flatKernel, shape, 'c');
    }

    public INDArray getMesh() {
        return mesh;
    }

    public List<BondPosition> getBondPositions() {
        return bondPositions;
    }

    public int getStickingProbability() {
        return configuration.getStickingProbability();
    }

    public Substrate getSubstrate() {
        return substrate;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
