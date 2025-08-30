package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.ValidationUtils;
import com.vois.utils.WaitUtils;
import com.vois.utils.actions.BrowserActions;
import com.vois.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResultsPage {

    private WebDriver driver;

    private static final By relatedSectionsLocator = LocatorReaderUtils.getLocator("results", "relatedSearchSections");
    private static final By searchResultsLocator = LocatorReaderUtils.getLocator("results", "searchResults");
    private static final String nextPageButtonTemplate = LocatorReaderUtils.getLocatorValue("results", "nextPageButton");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean validateRelatedSearches(String expectedText) {
        return ValidationUtils.validateItemsInSections(driver, relatedSectionsLocator, expectedText);
    }

    public static int getSearchResultsCount(WebDriver driver) {
        return WaitUtils.waitForAllElementsVisible(driver, searchResultsLocator).size();
    }

    public void goToPage(int pageNumber) {
        BrowserActions.goToPage(driver, nextPageButtonTemplate, pageNumber);
    }
}
