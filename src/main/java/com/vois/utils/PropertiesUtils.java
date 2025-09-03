package com.vois.utils;

import io.qameta.allure.Step;

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
            LogsUtil.error("Failed to load config.properties: " + e.getMessage());
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private PropertiesUtils() {
        // Prevent instantiation
        LogsUtil.error("Attempted to instantiate PropertiesUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    @Step("Get property for key: {key}")
    public static String getPropertyValue(String key) {
        LogsUtil.info("Retrieving property for key: " + key);
        return properties.getProperty(key);
    }

    @Step("Get integer property for key: {key}")
    public static int getIntPropertyValue(String key) {
        LogsUtil.info("Retrieving integer property for key: " + key);
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                LogsUtil.error("Property value is not a valid integer: " + value);
                throw new RuntimeException("Property value is not a valid integer: " + value, e);
            }
        }
        LogsUtil.error("Property key not found: " + key);
        throw new RuntimeException("Property key not found: " + key);
    }

    @Step("Detect Operating System")
    public static String getOSName() {
        String os = System.getProperty("os.name").toLowerCase();
        LogsUtil.info("Operating System detected: " + os);
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("mac")) {
            return "mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return "linux";
        } else {
            LogsUtil.warn("Unknown Operating System: " + os);
            return "unknown";
        }
    }

    @Step("Get Browser Type from properties")
    public static String getBrowserType() {
        String browserType = System.getProperty("browser", PropertiesUtils.getPropertyValue("browserType"));
        LogsUtil.info("Browser type from properties: " + browserType);
        return browserType;
    }

    @Step("Get Application URL from properties")
    public static String getBaseUrl() {
        String baseUrl = PropertiesUtils.getPropertyValue("baseUrl");
        LogsUtil.info("Application URL from properties: " + baseUrl);
        return baseUrl;
    }

    @Step("Get execution type from properties")
    public static String getExecutionType() {
        String executionType = System.getProperty("executionType", PropertiesUtils.getPropertyValue("executionType"));
        LogsUtil.info("Execution type from properties: " + executionType);
        return executionType;
    }

    @Step("Get timeout value from properties")
    public static long getTimeout() {
        long timeout = Long.parseLong(PropertiesUtils.getPropertyValue("timeout"));
        LogsUtil.info("Timeout value from properties: " + timeout);
        return timeout;
    }
}
