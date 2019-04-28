package com.mk.configuration;

import com.mk.configuration.substrates.*;

public class SubstrateFactory {
    public static final String PLANE790 = "Plane790";
    public static final String REALISTIC1000 = "Realistic1000";
    public static final String TEST5_790 = "Test5_790";
    public static final String VALLEY15 = "Valley15";
    public static final String VALLEY30 = "Valley30";
    public static final String VALLEY45 = "Valley45";

    public static SubstrateInterface get(final String kernel) {
        switch (kernel) {
            case PLANE790:
                return new Plane790();
            case REALISTIC1000:
                return new Realistic1000();
            case TEST5_790:
                return new Test5_790();
            case VALLEY15:
                return new Valley15();
            case VALLEY30:
                return new Valley30();
            case VALLEY45:
                return new Valley45();

            default:
                throw new IllegalStateException("Don't know about this substrate yet!");
        }
    }
}
