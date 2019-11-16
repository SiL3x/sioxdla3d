package com.mk;

import com.mk.graphic.PlotMesh;
import com.mk.graphic.PlotProjections;
import org.jzy3d.analysis.AnalysisLauncher;
import org.nd4j.linalg.api.ndarray.INDArray;
import java.io.File;
import java.util.Arrays;

import static org.nd4j.serde.binary.BinarySerde.readFromDisk;

public class MeasurementDisplay790 {

    INDArray mesh;
    File[] files;

    public  MeasurementDisplay790() throws Exception {
        String baseDirectory = "/home/max/dev/projects/3d-dla-output/";
        String simDirectory;

        //simDirectory = "20181208_karlsruhe"; // neues substrat
        //simDirectory = "20181225_karlsruhe";
        //simDirectory = "20190102_karlsruhe";
        //simDirectory = "20190202_karlsruhe";
        //simDirectory = "20190202_karlsruhe_2";
        //simDirectory = "20190203_karlsruhe_1";
        //simDirectory = "20190210_karlsruhe";
        //simDirectory = "20190409_test";
        //simDirectory = "20190410_config2";
        //simDirectory = "20190416_config3";
        //simDirectory = "20190417_config4";
        //simDirectory = "20190420_config5";
        //simDirectory = "201904_stick_prob/stick_prob_2";

        // Valley series 01
        //simDirectory = "20190501_valleys/valley_15";
        //simDirectory = "20190501_valleys/valley_30";
        //simDirectory = "20190501_valleys/valley_45";
        //simDirectory = "201905_sector/sector_36";
        //simDirectory = "201905_sector/sector_64";
        //simDirectory = "201905_sector/sector_100";
        //simDirectory = "201905_diffusion_length/length_20";
        //simDirectory = "201905_diffusion_length/length_05";
        //simDirectory = "presentation/2019_05_presentation_1";
        //simDirectory = "kernel/20190525_kernel111_2";
        //simDirectory = "20190616_kernel111_valley45";
        //simDirectory = "20190617_kernel111_valley30";
        //simDirectory = "anisotropy/20191108_cube_kernel";
        simDirectory = "anisotropy/20191116_octaeder";

        loadFiles(baseDirectory + simDirectory);

        System.out.println(">>> Load file : " + files[0]);
        mesh = readFromDisk(files[0]);

        System.out.println(">>> Plot projections");
        PlotProjections plotProjections = new PlotProjections();
        plotProjections.plotProjection(mesh);

        System.out.println(">>> Create mesh");
        PlotMesh plotMesh = new PlotMesh();

        System.out.println(">>> Plot mesh");
        plotMesh.plot3d(mesh);

        System.out.println(">>> Open mesh");
        AnalysisLauncher.open(plotMesh);
    }

    private void loadFiles(String directory) {
        files = new File(directory).listFiles();
        Arrays.sort(files);

        if (files.length > 1) System.out.println("ERROR: Too many files in directory!!!");
    }

    public static void main(String[] args) throws Exception {
        MeasurementDisplay790 measurementDisplay = new MeasurementDisplay790();
    }
}
