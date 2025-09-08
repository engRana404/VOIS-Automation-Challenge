package com.vois.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class ValidationUtils {
    private ValidationUtils() {
        // Prevent instantiation
        LogsUtil.error("Trying to instantiate utility class ValidationUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Compares the number of elements located by two locators.
     */
    @Step("Comparing element counts: {count1} and {count2}")
    public static boolean compareElementCounts(int count1, int count2) {
        LogsUtil.info("Comparing element counts: " + count1 + " and " + count2);
        return count1 == count2;
    }

    @Step("Validate condition: {condition}")
    public static void validateTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Step("Validate actual: {actual} equals expected: {expected}")
    public static void validationEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
