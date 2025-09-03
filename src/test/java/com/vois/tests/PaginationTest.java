package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import com.vois.utils.actions.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class PaginationTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;
    public static int page2Count;
    public static int page3Count;

    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");
    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");

    @BeforeClass(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        homePage.search(SEARCH_KEYWORD);
    }

    @Test( priority = 1, groups = {"pagination"})
    public void validateSecondPageHasResults() {
        resultsPage.goToPage(2);
        page2Count = resultsPage.getSearchResultsCount(driver);
        Assert.assertTrue(page2Count >= 8 && page2Count <= 10,
                "Expected ~9 results on page 2 but found " + page2Count);
    }

    @Test( priority = 2, dependsOnMethods = {"validateSecondPageHasResults"}, groups = {"pagination"})
    public void validateThirdPageHasResults() {
        resultsPage.goToPage(3);
        page3Count = resultsPage.getSearchResultsCount(driver);
        Assert.assertTrue(page3Count >= 8 && page3Count <= 10,
                "Expected ~9 results on page 3 but found " + page3Count);
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
