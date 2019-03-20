package com.mk;

import com.mk.graphic.PlotMesh;
import org.jzy3d.analysis.AnalysisLauncher;
import org.nd4j.linalg.api.ndarray.INDArray;
import java.io.File;
import java.util.Arrays;

import static org.nd4j.serde.binary.BinarySerde.readFromDisk;

public class MeasurementDisplay790 {

    INDArray mesh;
    File[] files;

    public  MeasurementDisplay790() throws Exception {
        //String directory = "/home/max/dev/projects/3d-dla-output/20181208_karlsruhe"; // neues substrat
        //String directory = "/home/max/dev/projects/3d-dla-output/20181225_karlsruhe";
        //String directory = "/home/max/dev/projects/3d-dla-output/20190102_karlsruhe";
        //String directory = "/home/max/dev/projects/3d-dla-output/20190202_karlsruhe";
        //String directory = "/home/max/dev/projects/3d-dla-output/20190202_karlsruhe_2";
        //String directory = "/home/max/dev/projects/3d-dla-output/20190203_karlsruhe_1";
        String directory = "/home/max/dev/projects/3d-dla-output/20190210_karlsruhe";
        //String directory = "/home/max/dev/projects/3d-dla-output/20190210_karlsruhe_02";

        loadFiles(directory);

        System.out.println(">>> Load file : " + files[0]);
        mesh = readFromDisk(files[0]);

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
