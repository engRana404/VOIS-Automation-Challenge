package com.vois.utils;

public class JsonUtils {
    private static final String JSON_FILE_PATH = "src/test/resources/searchData.json";

    private JsonUtils() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getJsonFilePath() {
        return JSON_FILE_PATH;
    }

    public static String getTestData(String key) {
        String data = "";
        try (java.io.FileReader reader = new java.io.FileReader(JSON_FILE_PATH)) {
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseReader(reader).getAsJsonObject();
            if (jsonObject.has(key)) {
                data = jsonObject.get(key).getAsString();
            } else {
                throw new RuntimeException("Key not found in JSON: " + key);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + e.getMessage(), e);
        }
        return data;
    }
}
