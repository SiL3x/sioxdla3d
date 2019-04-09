package com.mk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileUtils {

    public static String resourcesFileToString(final String path) {
        byte[] encoded;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(path)) { // TODO: this should accept ab

            if (is == null) throw new IllegalArgumentException("Could not find specified file");

            byte[] buffer = new byte[4096];
            while (true) {
                int readInt = is.read(buffer);
                if (readInt <= 0) {
                    break;
                }
                baos.write(buffer, 0, readInt);
            }
            encoded = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return new String(encoded, Charset.forName("UTF-8"));
    }
}
