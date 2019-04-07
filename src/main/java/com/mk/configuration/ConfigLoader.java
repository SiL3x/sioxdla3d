package com.mk.configuration;

import com.google.gson.Gson;
import com.mk.utils.FileUtils;

public class ConfigLoader {
    private Config config;

    public ConfigLoader(String fileName) {
        String json = FileUtils.resourcesFileToString(fileName);
        Gson gson = new Gson();
        config = gson.fromJson(json, Config.class);
    }

    public Config getConfig() {
        return config;
    }
}

