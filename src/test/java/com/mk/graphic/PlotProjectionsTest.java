package com.mk.graphic;

import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;


public class PlotProjectionsTest {

    @Test
    public void plotProjectionTest() throws IOException {
        INDArray oneDArray = Nd4j.create(new float[]{1, 1, 1, 1, 2, 2, 2, 2} , new int[]{2, 2, 2});

        PlotProjections plotProjections = new PlotProjections();
        plotProjections.plotProjection(oneDArray);
    }
}