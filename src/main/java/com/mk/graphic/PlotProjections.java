package com.mk.graphic;

import org.nd4j.linalg.api.ndarray.INDArray;
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
        double scaling = 255 / maximum;
        System.out.println("   maximum found = " + maximum);
        BufferedImage image = new BufferedImage(shape[0], shape[1], BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < shape[0]; x++) {
            for (int y = 0; y < shape[1]; y++) {
                Color color = new Color(
                        (int) scaling * array.getInt(x, y),
                        (int) scaling * array.getInt(x, y),
                        (int) scaling * array.getInt(x, y)
                );
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }
}
