package com.mk.models.physics;

import com.mk.configuration.Configuration;
import com.mk.models.geometries.Position;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GrowthFieldTest {

    private Configuration getConfiguration() {

        Configuration configuration = mock(Configuration.class);
        when(configuration.getWalkerStart()).thenReturn(new Position(1, 2, 3));
        when(configuration.getMeshSizeX()).thenReturn(100);
        when(configuration.getMeshSizeY()).thenReturn(100);
        when(configuration.getMeshSizeZ()).thenReturn(100);

        return configuration;
    }
}