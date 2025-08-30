package com.vois.tests;

import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
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

    private static final String BASE_URL = "https://www.bing.com";
    private static final String SEARCH_KEYWORD = "Vodafone";

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 ..."); // set a real UA if needed
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

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

        boolean relatedSectionsValid = resultsPage.validateRelatedSearchSections(SEARCH_KEYWORD, 2);
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
        BrowserActions.closeBrowser(driver);
    }
}
