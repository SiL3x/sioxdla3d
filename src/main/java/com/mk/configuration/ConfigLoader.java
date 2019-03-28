package com.mk.configuration;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class ConfigLoader {

    public ConfigLoader(String fileName) {

        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(fileName)) {
            Object object = jsonParser.parse(fileReader);
            JSONArray config = (JSONArray) object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

