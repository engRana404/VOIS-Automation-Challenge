package com.vois.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private static final int DEFAULT_WAIT_TIME_SECONDS = PropertiesUtils.getIntPropertyValue("timeout");

    private WaitUtils() {
        // Prevent instantiation
        LogsUtil.error("Trying to instantiate utility class WaitUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    // Method to wait for an element to be visible
    @Step("Waiting for element to be visible: {locator}")
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        LogsUtil.info("Waiting for element to be visible: " + locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Method to wait until all elements are visible
    @Step("Waiting for all elements to be visible: {locator}")
    public static java.util.List<WebElement> waitForAllElementsVisible(WebDriver driver, By locator) {
        LogsUtil.info("Waiting for all elements to be visible: " + locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // Method to wait for an element to be clickable
    @Step("Waiting for element to be clickable: {locator}")
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        LogsUtil.info("Waiting for element to be clickable: " + locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
}
