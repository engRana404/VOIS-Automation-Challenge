package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import com.vois.utils.ValidationUtils;
import com.vois.utils.actions.BrowserActions;
import com.vois.utils.actions.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    private WebDriver driver;
    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");

    // Locators
    private static final By searchBox = LocatorReaderUtils.getLocator("home", "searchBox");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate to Home Page")
    public HomePage navigateToHomePage() {
        LogsUtil.info("Navigating to Home Page: " + BASE_URL);
        BrowserActions.navigateTo(driver, BASE_URL);
        return this;
    }

    // Convenience method: perform a search in one step
    @Step("Search for keyword: {keyword}")
    public HomePage search(String keyword) {
        LogsUtil.info("Performing search for: " + keyword);
        WebElement box = driver.findElement(searchBox);
        box.sendKeys(keyword);
        box.submit(); // This triggers the form submission
        return this;
    }

    @Step("Validate that the page title contains: {expectedTitle}")
    public HomePage validatePageTitle(String expectedTitle) {
        LogsUtil.info("Validating page title contains: " + expectedTitle);
        String actualTitle = BrowserActions.getPageTitle(driver);
        ValidationUtils.validationEquals(actualTitle, expectedTitle, "Page title does not match expected value.");
        return this;
    }
}
