package com.vois.utils;

import org.openqa.selenium.WebDriver;


public class ValidationUtils {
    private ValidationUtils() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Compares the number of elements located by two locators.
     */
    public static boolean compareElementCounts(WebDriver driver, int count1, int count2) {
        LogsUtil.info("Comparing element counts: " + count1 + " and " + count2);
        return count1 == count2;
    }
}
