package com.mk.utils;

import org.apache.commons.cli.*;

public class CliUtils {

    private String configFilePath = "config.json";

    public CliUtils(String[] args) throws ParseException {
        //Create GNU like options
        Options gnuOptions = new Options();

        gnuOptions.addOption("config", true, "ConfigFilePath");

        CommandLineParser gnuParser = new GnuParser();
        CommandLine cmd = gnuParser.parse(gnuOptions, args);

        if(cmd.hasOption("config")) {
            System.out.println("setting config");
            configFilePath = cmd.getOptionValue("config");
        }
    }

    public String configFilePath() {
        return configFilePath;
    }
}
