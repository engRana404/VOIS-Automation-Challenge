package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import com.vois.utils.actions.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ComparisonTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;

    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");
    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        BrowserActions.navigateTo(driver, BASE_URL);
        homePage.search(SEARCH_KEYWORD);
    }

    @Test
    public void validateSearchResultsAcrossPages() {
        resultsPage.goToPage(2);
        int page2Count = resultsPage.getSearchResultsCount(driver);

        resultsPage.goToPage(3);
        int page3Count = resultsPage.getSearchResultsCount(driver);

        Assert.assertEquals(page2Count, page3Count,
                "Search results count on page 2 and 3 do not match!");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
