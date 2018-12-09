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
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.util.ArrayUtil;
import org.nd4j.serde.binary.BinarySerde;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.toList;
import static org.nd4j.serde.binary.BinarySerde.writeArrayToDisk;

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
    private int border;
    private String name = "test4";
    //private String name = "1000_large_test";   //"realistic";


    public SiOxDla3d() throws Exception {
        double time = System.currentTimeMillis();
        System.out.println(">>> Start time : " + time);
        simulationUtils = new SimulationUtils(this);
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println(">>> Cores detected : " + cores);
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "32");

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(">>> Thread pool : " + commonPool.getParallelism());

        System.out.println(">>> Loading configuration: " + name);
        configuration = simulationUtils.loadConfiguration(name);
        meshSize = 0;

        System.out.println(">>> creating mesh");
        mesh = simulationUtils.createMesh(
                configuration.getMeshSizeX(),
                configuration.getMeshSizeY(),
                configuration.getMeshSizeZ()
        );

        System.out.println(">>> Creating substrate");
        substrate = new Substrate(configuration.getMeshSizeX(), configuration.getMeshSizeY());
        substrate.createSubstrate(configuration.getSubstrate());

        System.out.println(">>> Calculate bond position");
        bondPositions = simulationUtils.calculateBondpositions(configuration.getKernel3D());
        border = (int) Math.round(configuration.getKernel3D().length * 0.8);

        System.out.println(">>> Place seeds");
        simulationUtils.placeSeeds();
        System.out.println("<<< seeds placed");


        //TODO: set iteration variables
        int i = 0;

        List<Walker> walkers = new ArrayList<>();

        for (int j = 0; j < configuration.getSectorNumber(); j++) {
            walkers.add(new Walker(configuration, substrate.getFront(), j));
        }

        while (run) {

            //run = false;

            System.out.println(">>> Start parallel stream - Iteration = " + i);
            List<Position> positions = walkers.parallelStream()
                    .map(w -> spawnMoveAndStick(w).getPosition())
                    .collect(toList());

            for (Position position : positions) {
                if (position.getX() != -1) {
                    mesh.putScalar(position.getX(), position.getY(), position.getZ(), 1);
                }
            }

            simulationUtils.moveGrowthFront();

            //TODO: check break conditions (number of crystallites, front, no of iterations)
            if (i > 1e5) {
                System.out.println("<<< stopped after " + i + " iterations");
                run = false;
            }
            if (substrate.getFront() <= 650 + substrate.getSpread()) {
                System.out.println("<<< stopped because because front is at " + substrate.getFront());
                run = false;
            }
            i++;
        }

        System.out.println(">>> Computing time = "  + (System.currentTimeMillis() - time));
        System.out.println(">>> Saving INDarray");
        System.out.println("  >>> saving slice z = {0, 499}");
        saveMesh(0, 499);
        System.out.println("  >>> saving slice z = {500, 999}");
        saveMesh(500, 999);

        /*
        System.out.println(">>> Create mesh");
        PlotMesh plotMesh = new PlotMesh();

        System.out.println(">>> Plot mesh");
        plotMesh.plot3d(mesh);

        System.out.println(">>> Open mesh");
        AnalysisLauncher.open(plotMesh);
        */
    }

    private void saveMesh(int from, int to) throws IOException {
        INDArray small = mesh.get(
                NDArrayIndex.interval(0, configuration.getMeshSizeX()),
                NDArrayIndex.interval(0, configuration.getMeshSizeY()),
                NDArrayIndex.interval(from, to)
        );
        File file = new File(System.getProperty("user.home") + "/output/" + String.format("out_%d-%d.dat", from, to));
        file.createNewFile();
        try {
            writeArrayToDisk(small, file);
        } catch (IOException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    private Walker spawnMoveAndStick(Walker walker) {
        //TODO: @Max Wie kann man die Walker position visualisieren???
        boolean notSticked = true;
        walker.respawn(substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset());

        int[] stickingPosition = new int[]{-1, -1, -1};
        int i = 0;

        while (notSticked) {
            walker.moveRnd(configuration.getZdrift());
            if (walkerIsTooFarOrBelowSurface(walker)) walker.respawn(substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset());

            stickingPosition = simulationUtils.walkerSticks(walker);
            if (stickingPosition[0] != -1) {
                notSticked = false;
            }

            if (i == 1000) {
                System.out.println("killed a walker");
                return new Walker(configuration, -1, -1, -1);
            }
            i++;
        }
        System.out.println("walker = " + (substrate.getValue(walker.getPosition().getX(), walker.getPosition().getY()) - walker.getPosition().getZ()));
        walker.setPosition(stickingPosition[0], stickingPosition[1], stickingPosition[2]);
        return walker;
    }

    private boolean walkerIsTooFarOrBelowSurface(final Walker walker) {
        return walker.getPosition().getZ() < (substrate.getFront() - substrate.getSpread() - configuration.getSpawnOffset() - 10) ||
                walker.getPosition().getZ() >= substrate.getValueWithFront(walker.getPosition().getX(), walker.getPosition().getY());
    }

    private boolean walkerIsNearToSurface(final Walker walker) {
        // TODO: check why this isn't used
        return walker.getPosition().getZ() >= (substrate.getValueWithFront(walker.getPosition().getX(), walker.getPosition().getY()) - configuration.getKernel3D().length * 0.6928);
    }

    public float[][][] getKernel3D() {
        return configuration.getKernel3D();
    }

    public INDArray getKernel3Dnd() {
        final float[][][] kernel = getKernel3D();
        final float[] flatKernel = ArrayUtil.flattenFloatArray(kernel);
        final int[] shape = {kernel.length, kernel.length, kernel.length};

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

    public int getBorder() {
        return border;
    }

    public static void main( String[] args ) throws Exception {
        System.out.println("### New DLA Simulation");
        if (System.getProperty("outfile") != null) {
            // TODO: implement command line arguments
            String outFile = System.getProperty("outfile");
            SiOxDla3d siOxDla3d = new SiOxDla3d();
        }
        SiOxDla3d siOxDla3d = new SiOxDla3d();
    }
}
