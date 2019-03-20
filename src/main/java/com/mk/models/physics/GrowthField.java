package com.mk.models.physics;

import com.mk.configuration.Configuration;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class GrowthField {

    INDArray vectorField;

    public void GrowthField(Configuration configuration) {
        System.out.println(">>> Initialize growth vector field");
        vectorField = Nd4j.zeros(new int[]{
                configuration.getMeshSizeX(),
                configuration.getMeshSizeY(),
                configuration.getMeshSizeZ(),
                3
        });
    }
}
