package com.mk.configuration.kernels;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

public class SimpleKernel {
    public final float[][][] kernel3D =
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 1, 0, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 0, 6, 4},
                            {0, 0, 1, 2, 0},
                            {0, 0, 1, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 1, 2, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    }
            };
    private INDArray kernel3Dnd;

    public INDArray kernel3D() {
        if (kernel3Dnd != null) return kernel3Dnd;
        float[] flatKernel = ArrayUtil.flattenFloatArray(kernel3D);
        int[] shape = {kernel3D.length, kernel3D.length, kernel3D.length};
        this.kernel3Dnd = Nd4j.create(flatKernel, shape, 'c');
        return kernel3Dnd;
    }
}
