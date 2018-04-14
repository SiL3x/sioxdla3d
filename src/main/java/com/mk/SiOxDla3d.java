package com.mk;

import com.mk.configuration.Configuration;
import com.mk.graphic.PlotMesh;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import com.mk.utils.SimulationUtils;
import org.jzy3d.analysis.AnalysisLauncher;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

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

    public SimulationUtils simulationUtils;

    private boolean run = true;
    private String name = "test";


    public SiOxDla3d() throws Exception {
        simulationUtils = new SimulationUtils(this);

        System.out.println(">>> Loading configuration: " + name);
        configuration = simulationUtils.loadConfiguration(name);
        meshSize = configuration.getMeshSize();
        System.out.println(">>> creating mesh");
        mesh = simulationUtils.createMesh();

        System.out.println(">>> Creating substrate");
        substrate = new Substrate(configuration.getMeshSize());
        substrate.createSubstrate(configuration.getSubstrate());

        //TODO: calculate vector field for substrate normals and store them in an array
        //TODO: calculate bond positions
        simulationUtils.placeSeeds();

        //TODO: set iteration variables
        int i = 0;
        int sticked = 0;

        //walker = new Walker(configuration, substrate.getHighestPoint() - 10);
        walker = new Walker(configuration, substrate.getFront() - 10);

        while (run) {
            simulationUtils.moveWalker();

            if (walker.getPosition().getZ() < substrate.getFront() - 20
                    || walker.getPosition().getZ() > substrate.getFront()) walker.respawn(substrate.getFront() - 10);

            //simulationUtils.calculateSticking();
            //TODO: implement no further sticking than 2 from growth front

            if (simulationUtils.walkerSticks()) {
                mesh.putScalar(
                        walker.getPosition().getX(),
                        walker.getPosition().getY(),
                        walker.getPosition().getZ(), 1);
                //System.out.println("sticked = " + walker.getPosition() + "  i = " + i + "  sector = " + walker.getSector());
                walker.nextSector();
                walker.respawn(substrate.getFront() - 10);
                sticked++;
            }

            if (sticked % 20 == 0) simulationUtils.moveGrowthFront();

            //TODO: check break conditions (number of crystallites, front, no of iterations)
            //if (i > 1e8) run = false;
            if (substrate.getFront() <= 20) run = false;
            i++;
        }

        PlotMesh plotMesh = new PlotMesh();
        plotMesh.plot3d(mesh);
        AnalysisLauncher.open(plotMesh);

        //TODO: prepare mesh for visualization
        //TODO: display results
    }

    public static void main( String[] args ) throws Exception {
        System.out.println("### New DLA Simulation");
        SiOxDla3d siOxDla3d = new SiOxDla3d();
    }
}
