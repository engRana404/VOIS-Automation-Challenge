package com.vois.utils;

import com.vois.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ValidationUtils {
    private ValidationUtils() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Checks that all child elements under a given parent locator contain the expected text.
     */
    public static boolean allChildElementsContainText(WebDriver driver, By parentLocator, String expectedText) {
        WebElement parent = ElementActions.findElement(driver, parentLocator); // find parent first
        List<WebElement> children = parent.findElements(By.xpath(".//*")); // all descendants

        for (WebElement child : children) {
            if (!child.getText().toLowerCase().trim().contains(expectedText.toLowerCase().trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the number of elements located by two locators.
     */
    public static boolean compareElementCounts(WebDriver driver, int count1, int count2) {
        return count1 == count2;
    }

    /**
     * Checks if at least one element contains the expected text.
     */
    public static boolean anyElementContainsText(WebDriver driver, By locator, String expectedText) {
        List<WebElement> elements = ElementActions.findElements(driver, locator); // all matched elements
        for (WebElement el : elements) {
            if (el.getText().toLowerCase().contains(expectedText.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if at least one element within each parent section contains the expected text.
     * This is more robust for Bing's related searches, where multiple items exist inside a single section.
     */
    public static boolean validateItemsInSections(WebDriver driver, By sectionsLocator, String expectedText) {
        List<WebElement> sections = ElementActions.findElements(driver, sectionsLocator);
        if (sections.isEmpty()) {
            return false; // No sections found
        }

        for (WebElement section : sections) {
            List<WebElement> items = section.findElements(By.xpath(".//*")); // all children
            boolean found = false;
            for (WebElement item : items) {
                if (item.getText().toLowerCase().contains(expectedText.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) return false; // no item in this section contains expected text
        }
        return true;
    }
}
