package com.vois.utils.actions;

import com.vois.utils.LogsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Scrolling {
    private Scrolling() {
        // Prevent instantiation
        LogsUtil.error("Attempted to instantiate utility class Scrolling");
        throw new UnsupportedOperationException("Utility class");
    }

    public static void scrollToElement(WebDriver driver, By locator) {
        LogsUtil.info("Scrolling to element:" + locator.toString());
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", ElementActions.findElement(driver, locator));
    }
}
