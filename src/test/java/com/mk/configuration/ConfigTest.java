package com.mk.configuration;

import com.google.gson.Gson;
import com.mk.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest {
    private Gson gson = new Gson();
    private Config config;

    @Before public void setUp() {
        String json = FileUtils.resourcesFileToString("config.json");
        config = gson.fromJson(json, Config.class);
    }

    @Test public void shouldContainMeshX_whenConstructed() {
        assertThat(config.meshSizeX).isEqualTo(790);
    }

    @Test public void shouldContainMeshY_whenConstructed() {
        assertThat(config.meshSizeY).isEqualTo(790);
    }

    @Test public void shouldContainKernel() {
        //TODO: make the test more meaningful
        assertThat(config.kernel().length).isEqualTo(5);
    }

    @Test public void shouldContainSubstrate() {
        assertThat(config.substrate().size()).isEqualTo(2);
    }

    @Test public void shouldInitializeTheConfig_whenCalle() {
        config.initialize();
        System.out.println("kerne3d = " + config.getKernel3D());
        assertThat(config.getKernel3D().length).isEqualTo(5);
        assertThat(config.getKernel3Dnd().length()).isEqualTo(125);
    }
}