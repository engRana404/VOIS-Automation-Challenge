package com.vois.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;

public class LocatorReaderUtils {
    private static JsonNode locators;

    static {
        try {
            locators = new ObjectMapper().readTree(new File("src/main/resources/locators.json"));
        } catch (IOException e) {
            LogsUtil.error("Failed to load locators.json: " + e.getMessage());
            throw new RuntimeException("Failed to load locators.json", e);
        }
    }

    private LocatorReaderUtils() {
        // Prevent instantiation
        LogsUtil.error("Attempted to instantiate LocatorReaderUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    @Step("Getting locator value for page: {page}, element: {elementName}")
    public static String getLocatorValue(String page, String elementName) {
        LogsUtil.info("Retrieving locator for page: " + page + ", element: " + elementName);
        JsonNode node = locators.path(page).path(elementName);
        return node.path("value").asText();
    }

    @Step("Getting By locator for page: {page}, element: {elementName}")
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
            case "class" -> By.className(value);
            case "tag" -> By.tagName(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            default -> throw new IllegalArgumentException("Unknown locator type: " + type);
        };
    }
}
