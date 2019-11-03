package com.mk.graphic;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlotProjections {
    public void plotProjection(final INDArray array) throws IOException {
        for (int i = 0; i < 3; i++) {
            plotDirection(array, i);
        }
    }

    private void plotDirection(INDArray array, int i) throws IOException {
        INDArray projection = array.sum(i);
        BufferedImage image = plotArray(projection);
        ImageIO.write(image, "bmp", new File("projection-" + i + ".bmp"));
    }

    private BufferedImage plotArray(INDArray array) {
        int[] shape = array.shape();
        int maximum = array.maxNumber().intValue();
        BufferedImage image = new BufferedImage(shape[0], shape[1], BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < shape[0]; x++) {
            for (int y = 0; y < shape[1]; y++) {
                int grayValue = grayScaled(maximum, array.getInt(x, y));
                Color color = new Color(grayValue, grayValue, grayValue);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private int grayScaled(int maximum, int value) {
         return (int) (255 * (1 - Math.exp(-value * 80 / maximum )));
    }
}
