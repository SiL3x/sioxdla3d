package com.mk.configuration;

import com.mk.configuration.kernels.*;

public class KernelFactory {
    public static final String SIMPLE  = "SimpleKernel";
    public static final String KERNEL111_1  = "Kernel111_1";
    public static final String KERNEL111_2  = "Kernel111_2";
    public static final String BLOCKKERNEL = "BlockKernel";
    public static final String SPHEREKERNEL = "SphereKernel";

    public static KernelInterface get(final String kernel) {
        switch(kernel) {
            case SIMPLE:
                return new SimpleKernel();
            case KERNEL111_1:
                return new Kernel111_1();
            case KERNEL111_2:
                return new Kernel111_2();
            case BLOCKKERNEL:
                return new BlockKernel();
            case SPHEREKERNEL:
                return new SphereKernel();
            default:
                throw new IllegalStateException("Don't know about this kernel yet!");
        }
    }
}
