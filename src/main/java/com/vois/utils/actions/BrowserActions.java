package com.vois.utils.actions;

import com.vois.utils.LogsUtil;
import com.vois.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserActions {

    private BrowserActions() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    // Method to open a URL
    public static void navigateTo(WebDriver driver,String url) {
        LogsUtil.info("Navigating to URL: " + url);
        driver.get(url);
    }

    //Get current URL
    public static String getCurrentUrl(WebDriver driver) {
        LogsUtil.info("Getting current URL: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    //Get page title
    public static String getPageTitle(WebDriver driver) {
        LogsUtil.info("Getting page title: " + driver.getTitle());
        return driver.getTitle();
    }

    //Refresh page
    public static void refreshPage(WebDriver driver) {
        LogsUtil.info("Refreshing the page");
        driver.navigate().refresh();
    }

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
    public static void goToPage(WebDriver driver, String nextPageXPathTemplate, int pageNumber) {
        LogsUtil.info("Navigating to page number: " + pageNumber);
        String xpath = nextPageXPathTemplate.replace("{pageNumber}", String.valueOf(pageNumber));
        By nextPageButton = By.xpath(xpath);
        ElementActions.clickElement(driver, nextPageButton);
    }
}
