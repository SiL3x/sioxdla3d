package com.mk.configuration;

import com.mk.configuration.substrates.Plane790;
import com.mk.configuration.substrates.Realistic1000;
import com.mk.configuration.substrates.SubstrateInterface;
import com.mk.configuration.substrates.Test5_790;

public class SubstrateFactory {
    public static final String PLANE790 = "Plane790";
    public static final String REALISTIC1000 = "Realistic1000";
    public static final String TEST5_790 = "Test5_790";

    public static SubstrateInterface get(final String kernel) {
        switch (kernel) {
            case PLANE790:
                return new Plane790();
            case REALISTIC1000:
                return new Realistic1000();
            case TEST5_790:
                return new Test5_790();
            default:
                throw new IllegalStateException("Don't know about this substrate yet!");
        }
    }
}
