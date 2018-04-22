package com.mk.models;

import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;
import com.mk.models.physics.Substrate;
import com.mk.models.physics.Walker;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalkerTest {
    private final double DELTA = 1e-3;

    @Test
    void createWalkers() {
        Configuration configuration = getConfiguration();
        Position expectedStart = new Position(1, 2, 3);

        Walker walker = new Walker(configuration);
        Assert.assertEquals(expectedStart.getX(), walker.getPosition().getX());
        Assert.assertEquals(expectedStart.getY(), walker.getPosition().getY());
        Assert.assertEquals(expectedStart.getZ(), walker.getPosition().getZ());

        for (int i = 0; i < 100; i++) {
            walker = new Walker(configuration, 10); //TODO: Fix this test
            Assert.assertTrue((walker.getPosition().getX() <= 99) || (walker.getPosition().getX() >= 1));
            Assert.assertTrue((walker.getPosition().getY() <= 99) || (walker.getPosition().getY() >= 1));
        }
        Assert.assertEquals(5, walker.getPosition().getZ());
    }

    @Test
    void respawn() {
        Configuration configuration = getConfiguration();
        Substrate substrate = getSubstrate();

        Walker walker = new Walker(configuration, 10);

        for (int i = 0; i < 100; i++) {
            walker.respawn(substrate);
            Assert.assertTrue((walker.getPosition().getX() <= 99) || (walker.getPosition().getX() >= 1));
            Assert.assertTrue((walker.getPosition().getY() <= 99) || (walker.getPosition().getY() >= 1));
        }
        Assert.assertEquals(5, walker.getPosition().getZ());
    }

    @Test
    void moveRnd() {
        Configuration configuration = getConfiguration();
        Walker walker = new Walker(configuration, 10);

        int xActual = walker.getPosition().getX();
        int yActual = walker.getPosition().getY();
        int zActual = walker.getPosition().getZ();

        walker.moveRnd();

        Position moved = walker.getPosition();

        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(
                    (moved.getX() == xActual + 1) ||
                            (moved.getX() == xActual - 1) ||
                            (moved.getY() == yActual + 1) ||
                            (moved.getY() == yActual - 1) ||
                            (moved.getZ() == zActual + 1) ||
                            (moved.getZ() == zActual - 1) &&
                            !(moved.getX() == xActual) &&
                            !(moved.getY() == yActual));
        }
    }

    private Configuration getConfiguration() {
        int[][] kernel = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        Configuration configuration = mock(Configuration.class);
        when(configuration.getWalkerStart()).thenReturn(new Position(1, 2, 3));
        when(configuration.getMeshSize()).thenReturn(100);
        when(configuration.getKernel()).thenReturn(kernel);
        when(configuration.getSpawnOffset()).thenReturn(5);

        return configuration;
    }

    private Substrate getSubstrate() {
        Substrate substrate = mock(Substrate.class);
        when(substrate.getValue(any(int.class), any(int.class))).thenReturn(10);

        return substrate;
    }

}

