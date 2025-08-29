package com.vois.utils.actions;

import com.vois.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class ElementActions {

    private ElementActions() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static WebElement findElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    // Method to find multiple elements
    public static List<WebElement> findElements(WebDriver driver, By locator) {
        return driver.findElements(locator);
    }

    public static WebElement clickElement(WebDriver driver, By locator) {
        WebElement element = WaitUtils.waitForElementToBeClickable(driver, locator);
        element.click();
        return element;
    }

    public static void sendKeys(WebDriver driver, By locator, String text) {
        WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    public static String getText(WebDriver driver, By locator) {
        return WaitUtils.waitForElementToBeVisible(driver, locator).getText();
    }

    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        return findElement(driver, locator).getAttribute(attribute);
    }

    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        return findElement(driver, locator).isDisplayed();
    }

    public static boolean isElementEnabled(WebDriver driver, By locator) {
        return findElement(driver, locator).isEnabled();
    }

    public static boolean isElementSelected(WebDriver driver, By locator) {
        return findElement(driver, locator).isSelected();
    }

    public static String getValue(WebDriver driver, By locator) {
        return findElement(driver, locator).getDomAttribute("value");
    }
}
