package com.vois.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private PropertiesUtils() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getPropertyValue(String key) {
        LogsUtil.info("Retrieving property for key: " + key);
        return properties.getProperty(key);
    }

    public static int getIntPropertyValue(String key) {
        LogsUtil.info("Retrieving integer property for key: " + key);
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Property value is not a valid integer: " + value, e);
            }
        }
        throw new RuntimeException("Property key not found: " + key);
    }
}
