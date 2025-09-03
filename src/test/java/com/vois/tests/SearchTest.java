package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.actions.BrowserActions;
import com.vois.utils.JsonUtils;
import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;


    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        homePage.search(SEARCH_KEYWORD);
    }

    @Test
    public void validateFirstPageHasResults() {
        int resultsCount = resultsPage.getSearchResultsCount(driver);
        LogsUtil.info("Results found on page 1: " + resultsCount);
        Assert.assertTrue(resultsCount > 0, "No search results found on page 1!");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
