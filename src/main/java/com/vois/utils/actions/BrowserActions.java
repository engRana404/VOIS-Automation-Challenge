package com.vois.utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BrowserActions {

    private BrowserActions() {
        // Prevent instantiation
    }

    // Method to open a URL
    public static void navigateTo(WebDriver driver,String url) {
        driver.get(url);
    }

    //Get current URL
    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    //Get page title
    public static String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    //Refresh page
    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    //Close browser
    public static void closeBrowser(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

    // Navigate to a specific page number using a locator with a placeholder for the page number
    public static void goToPage(WebDriver driver, By nextPageLocator, int pageNumber) {
        String xpathWithPage = nextPageLocator.toString().replace("{pageNumber}", String.valueOf(pageNumber));
        By nextPage = By.xpath(xpathWithPage.substring(xpathWithPage.indexOf(":") + 1));
        ElementActions.clickElement(driver, nextPage);
    }
}
