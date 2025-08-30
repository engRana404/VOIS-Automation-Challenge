package com.vois.utils;

import io.qameta.allure.Step;

public class JsonUtils {
    private static final String JSON_FILE_PATH = "src/test/resources/searchData.json";

    private JsonUtils() {
        // Prevent instantiation
        LogsUtil.error("Attempted to instantiate JsonUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getJsonFilePath() {
        LogsUtil.info("JSON file path: " + JSON_FILE_PATH);
        return JSON_FILE_PATH;
    }

    public static String getTestData(String key) {
        LogsUtil.info("Retrieving test data for key: " + key);
        String data = "";
        try (java.io.FileReader reader = new java.io.FileReader(JSON_FILE_PATH)) {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseReader(reader).getAsJsonObject();
            if (jsonObject.has(key)) {
                data = jsonObject.get(key).getAsString();
            } else {
                LogsUtil.error("Key not found in JSON: " + key);
                throw new RuntimeException("Key not found in JSON: " + key);
            }
        } catch (Exception e) {
            LogsUtil.error("Failed to read JSON file: " + e.getMessage());
            throw new RuntimeException("Failed to read JSON file: " + e.getMessage(), e);
        }
        return data;
    }
}
