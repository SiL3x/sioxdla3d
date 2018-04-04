package com.mk;

import com.mk.configuration.Configuration;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import com.mk.utils.SimulationUtils;
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


    public SiOxDla3d() {
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

        walker = new Walker(configuration, substrate.getHighestPoint() - 10);

        while (run) {
            simulationUtils.moveWalker();

            simulationUtils.calculateSticking();
            //TODO: implement no further sticking than 2 from growth front

            if (simulationUtils.walkerSticks()) {
                mesh.putScalar(
                        walker.getPosition().getX(),
                        walker.getPosition().getY(),
                        walker.getPosition().getZ(), 1);
            }

            simulationUtils.moveGrowthFront();

            //TODO: check break conditions (number of crystallites, front, no of iterations)
            if(i > 1e5) run = false;
            i++;
        }

        //TODO: prepare mesh for visualization
        //TODO: display results
    }

    public static void main( String[] args ) {
        System.out.println("### New DLA Simulation");
        SiOxDla3d siOxDla3d = new SiOxDla3d();
    }
}
