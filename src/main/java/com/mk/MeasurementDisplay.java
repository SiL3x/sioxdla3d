package com.mk;

import com.mk.graphic.PlotMesh;
import org.jzy3d.analysis.AnalysisLauncher;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.nd4j.serde.binary.BinarySerde.readFromDisk;

public class MeasurementDisplay {

    INDArray mesh;
    File[] files;

    public  MeasurementDisplay() throws Exception {
        //String directory = "/home/max/dev/projects/3d-dla-output/20181123_karlsruhe_10/"; // 10er
        //String directory = "/home/max/dev/projects/3d-dla-output/20181111_karlsruhe_diff_20"; // 20
        //String directory = "/home/max/dev/projects/3d-dla-output/20181111_eater_diff_50"; // 50
        //String directory = "/home/max/dev/projects/3d-dla-output/20181122_karlsruhe_100"; // 100
        String directory = "/home/max/dev/projects/3d-dla-output/20181201_karlsruhe"; // 100

        loadFiles(directory);
/*
        System.out.println("FIrst = " + files[0].getName());
        INDArray array = readFromDisk(files[0]);
        System.out.println("array = " + array.length());
        System.out.println("array.getInt(0, 0, 0) = " + array.getInt(0, 0, 0));
        */

        addFileToMesh(files[0], new int[]{0, 0, 0}, new int[]{1000, 1000, 499});
        addFileToMesh(files[1], new int[]{0, 0, 500}, new int[]{1000, 1000, 999});

        System.out.println(">>> Create mesh");
        PlotMesh plotMesh = new PlotMesh();

        System.out.println(">>> Plot mesh");
        plotMesh.plot3d(mesh);

        System.out.println(">>> Open mesh");
        AnalysisLauncher.open(plotMesh);

    }

    private void loadFiles(String directory) {
        mesh = Nd4j.zeros(1000, 1000, 1000);
        files = new File(directory).listFiles();
        Arrays.sort(files);
        for (File file : files) {
            System.out.println("file.getName() = " + file.getName());
        }
    }

    private void addFileToMesh(File file, int[] startPoint, int[] endPoint) throws IOException {
        System.out.println(">>> Read from disk : " + file.getName());
        INDArray toInsert = readFromDisk(file);

        for (int x = startPoint[0]; x < endPoint[0]; x++) {
            for (int y = startPoint[1]; y < endPoint[1]; y++) {
                for (int z = startPoint[2]; z < endPoint[2]; z++) {
                    //System.out.println("x = " + x + "  y = " + y + "  z = " + z);
                    //System.out.println("toInsert.getInt(x, y, (z - startPoint[2])) = " + toInsert.getInt(x, y, (z - startPoint[2])));
                    mesh.putScalar(x, y, z, toInsert.getInt(x - startPoint[0], y - startPoint[1], (z - startPoint[2])));
                }
            }
            System.out.println("x = " + x);
        }
    }

    public static void main(String[] args) throws Exception {
        MeasurementDisplay measurementDisplay = new MeasurementDisplay();
    }
}
