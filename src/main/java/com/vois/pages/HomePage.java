package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.LogsUtil;
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

    // Locators
    private static final By searchBox = LocatorReaderUtils.getLocator("home", "searchBox");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Convenience method: perform a search in one step
    @Step("Search for keyword: {keyword}")
    public void search(String keyword) {
        LogsUtil.info("Performing search for: " + keyword);
        WebElement box = driver.findElement(searchBox);
        box.sendKeys(keyword);
        box.submit(); // This triggers the form submission
    }
}
