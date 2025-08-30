package com.vois.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;

public class LocatorReaderUtils {
    private static JsonNode locators;

    static {
        try {
            locators = new ObjectMapper().readTree(new File("src/main/resources/locators.json"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load locators.json", e);
        }
    }

    private LocatorReaderUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getLocatorValue(String page, String elementName) {
        LogsUtil.info("Retrieving locator for page: " + page + ", element: " + elementName);
        JsonNode node = locators.path(page).path(elementName);
        return node.path("value").asText();
    }

    public static By getLocator(String page, String elementName, String... replacements) {
        LogsUtil.info("Retrieving locator for page: " + page + ", element: " + elementName);
        JsonNode node = locators.path(page).path(elementName);
        String type = node.path("type").asText();
        String value = node.path("value").asText();

        // Replace placeholders if provided
        for (String replacement : replacements) {
            value = value.replaceFirst("\\{[^}]+\\}", replacement);
        }

        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            default -> throw new IllegalArgumentException("Unknown locator type: " + type);
        };
    }
}
