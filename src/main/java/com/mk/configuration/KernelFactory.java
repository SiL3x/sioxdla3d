package com.mk.configuration;

import com.mk.configuration.kernels.SimpleKernel;

public class KernelFactory {
    public static final String SIMPLE  = "SimpleKernel";

    public static SimpleKernel get(final String kernel) {
        switch(kernel) {
            case SIMPLE:
                return new SimpleKernel();
            default:
                throw new IllegalStateException("Don't know about this kernel yet!");
        }
    }
}
