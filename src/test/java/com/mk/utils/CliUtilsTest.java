package com.mk.utils;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CliUtilsTest {

    private CliUtils cliUtils;
    private String[] args;

    @Test public void shouldParseKeyword() throws ParseException {
        args = new String[]{"-config", "file-path"};
        cliUtils = new CliUtils(args);

        assertThat(cliUtils.configFilePath()).isEqualTo("file-path");
    }
}