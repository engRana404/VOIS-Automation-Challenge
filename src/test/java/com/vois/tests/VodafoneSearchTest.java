package com.vois.tests;

import com.vois.drivers.BrowserFactory;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.JsonUtils;
import com.vois.utils.PropertiesUtils;
import com.vois.utils.ValidationUtils;
import com.vois.utils.actions.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VodafoneSearchTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;

    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");
    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_RELATED_TEXT = JsonUtils.getTestData("expectedRelatedText");
    private static final int EXPECTED_RELATED_SECTIONS_COUNT = Integer.parseInt(JsonUtils.getTestData("expectedRelatedCount"));

    @BeforeClass
    public void setup() {
        driver = BrowserFactory.getDriver();
        driver.get(PropertiesUtils.getPropertyValue("baseUrl"));
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);
    }

    @Test(priority = 1)
    public void searchForVodafone() {
        BrowserActions.navigateTo(driver, BASE_URL);

        // Perform search
        homePage.search(SEARCH_KEYWORD);
    }

    @Test(priority = 2, dependsOnMethods = "searchForVodafone")
    public void validateFirstPageHasResults() {
        int resultsCount = resultsPage.getSearchResultsCount(driver);
        System.out.println("Results found on page 1: " + resultsCount);
        Assert.assertTrue(resultsCount > 0, "No search results found on page 1!");

        boolean relatedSectionsValid = resultsPage.validateRelatedSearchSections(EXPECTED_RELATED_TEXT, EXPECTED_RELATED_SECTIONS_COUNT);
        Assert.assertTrue(relatedSectionsValid, "Related search sections validation failed!");
    }

    @Test(priority = 3, dependsOnMethods = "validateFirstPageHasResults")
    public void validateSearchResultsAcrossPages() {
        resultsPage.goToPage(2);
        int page2Count = resultsPage.getSearchResultsCount(driver);
        System.out.println("Results found on page 2: " + page2Count);
        Assert.assertTrue(page2Count > 0, "No search results found on page 2!");

        resultsPage.goToPage(3);
        int page3Count = resultsPage.getSearchResultsCount(driver);
        System.out.println("Results found on page 3: " + page3Count);
        Assert.assertTrue(page3Count > 0, "No search results found on page 3!");

        boolean resultCount = ValidationUtils.compareElementCounts(driver, page2Count, page3Count);
        Assert.assertTrue(resultCount, "Search results count on page 2 and 3 do not match!");
    }

    @AfterClass
    public void teardown() {
        BrowserFactory.quitDriver();
    }
}
