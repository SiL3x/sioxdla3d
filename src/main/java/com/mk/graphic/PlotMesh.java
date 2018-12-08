package com.mk.graphic;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.nd4j.linalg.api.ndarray.INDArray;
import java.util.ArrayList;
import java.util.List;

public class PlotMesh extends AbstractAnalysis{
    public Coord3d[] points;
    public Color[]   colors;
    Scatter scatter;

    @Override
    public void init() throws Exception {
    }

    public void plot3d(final INDArray array) {
        int[] size = array.shape();
        List<Coord3d> pointList = new ArrayList<>();

        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                for (int z = 0; z < size[2]; z++) {
                    if (array.getInt(x, y, z) > 0) {
                        pointList.add(new Coord3d(x, y, 100 - z));
                    }
                }
            }
        }

        System.out.println("pointList.size() = " + pointList.size());

        points = new Coord3d[pointList.size() + 4];
        final Color plotColor = new Color(1, 1, 1, (float) 0.3);

        int i = 0;
        for (Coord3d coord3d : pointList) {
            points[i] = coord3d;
            i++;
        }

        points[i] = new Coord3d(0, 0, 0);
        points[i+1] = new Coord3d(100, 0, 0);
        points[i+2] = new Coord3d(100, 100, 0);
        points[i+3] = new Coord3d(100, 100, 100);

        scatter = new Scatter(points, plotColor);
        scatter.setWidth(10);
        chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
        chart.getAxeLayout().setMainColor(plotColor);
        chart.getView().setBackgroundColor(Color.BLACK);
        chart.getScene().add(scatter);
    }

    public void plot() {
        Scatter scatter = new Scatter(points, colors);
        scatter.setWidth(4);
        chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
        chart.getScene().add(scatter);
    }

    public void createData2() {
        int size = 5;

        points = new Coord3d[size];
        colors = new Color[size];

        points[0] = new Coord3d(0, 0, 0);
        points[1] = new Coord3d(1, 1, 1);
        points[2] = new Coord3d(1, 1, 0.5);
        points[3] = new Coord3d(2, 5, 4);
        points[4] = new Coord3d(3, 3, 3);

        colors[0] = Color.BLACK;
        colors[1] = Color.BLACK;
        colors[2] = Color.BLACK;
        colors[3] = Color.BLACK;
        colors[4] = Color.BLACK;
    }

    public static void main(String[] args) throws Exception {
        PlotMesh plotMesh = new PlotMesh();
        plotMesh.createData2();
        plotMesh.plot();
        AnalysisLauncher.open(plotMesh);
    }
}
