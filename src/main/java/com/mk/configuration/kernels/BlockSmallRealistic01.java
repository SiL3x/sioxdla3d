package com.mk.configuration.kernels;

public class BlockSmallRealistic01 implements KernelInterface {
    /* Realistic Kernel with broken bonds per area
    (111) : 0.08 -> 1
    (110) : 0.096 -> 1.2
    (001) : 0.139 -> 1.7375
     */
    public final float[][][] kernel3D =
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 1.2f, 1, 0},
                            {0, 1.2f, 1.74f, 1.2f, 0},
                            {0, 1, 1.2f, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1.2f, 1.74f, 1.2f, 0},
                            {0, 1.74f, 1, 1.74f, 0},
                            {0, 1.2f, 1.74f, 1.2f, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 1.2f, 1, 0},
                            {0, 1.2f, 1.74f, 1.2f, 0},
                            {0, 1, 1.2f, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    }
            };

    public float[][][] getKernel3D() { return kernel3D; }
}
