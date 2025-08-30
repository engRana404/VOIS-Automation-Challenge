package com.vois.utils.actions;

import com.vois.utils.LogsUtil;
import com.vois.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserActions {

    private BrowserActions() {
        // Prevent instantiation
        LogsUtil.error("Attempted to instantiate utility class BrowserActions");
        throw new UnsupportedOperationException("Utility class");
    }

    @Step("Navigate to URL: {url}")
    // Method to open a URL
    public static void navigateTo(WebDriver driver,String url) {
        LogsUtil.info("Navigating to URL: " + url);
        driver.get(url);
    }

    @Step("Get current URL: {url}")
    //Get current URL
    public static String getCurrentUrl(WebDriver driver) {
        LogsUtil.info("Getting current URL: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    @Step("Get page title: {title}")
    //Get page title
    public static String getPageTitle(WebDriver driver) {
        LogsUtil.info("Getting page title: " + driver.getTitle());
        return driver.getTitle();
    }

    @Step("Refresh the page")
    //Refresh page
    public static void refreshPage(WebDriver driver) {
        LogsUtil.info("Refreshing the page");
        driver.navigate().refresh();
    }

    @Step("Close the browser")
    //Close browser
    public static void closeBrowser(WebDriver driver) {
        LogsUtil.info("Quit the browser");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navigate to a specific page number using an XPath template with placeholder {pageNumber}.
     */
    @Step("Go to page number: {pageNumber}")
    public static void goToPage(WebDriver driver, String nextPageXPathTemplate, int pageNumber) {
        LogsUtil.info("Navigating to page number: " + pageNumber);
        String xpath = nextPageXPathTemplate.replace("{pageNumber}", String.valueOf(pageNumber));
        By nextPageButton = By.xpath(xpath);
        ElementActions.clickElement(driver, nextPageButton);
    }
}
