package com.mk.configuration;

import com.mk.configuration.substrates.Plane790;

public class SubstrateFactory {
    public static final String PLANE790 = "Plane790";

    public static Plane790 get(final String kernel) {
        switch(kernel) {
            case PLANE790:
                return new Plane790();
            default:
                throw new IllegalStateException("Don't know about this substrate yet!");
        }
    }
}
