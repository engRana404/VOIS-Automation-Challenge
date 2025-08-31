package com.vois.tests;

import com.vois.drivers.BrowserFactory;
import com.vois.drivers.DriverManger;
import com.vois.listeners.TestNGListeners;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import com.vois.utils.actions.BrowserActions;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

@Epic("Search Engine Validation")
@Feature("Bing Search Functionality")
@Story("Vodafone Search Scenario")
@Owner("Rana Gamal")
@Listeners(TestNGListeners.class)
public class VodafoneSearchTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;
    private int page2Count;
    private int page3Count;

    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");
    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_RELATED_TEXT = JsonUtils.getTestData("expectedRelatedText");
    private static final int EXPECTED_RELATED_SECTIONS_COUNT = Integer.parseInt(JsonUtils.getTestData("expectedRelatedCount"));

    SoftAssert softAssert = new SoftAssert();

    @BeforeSuite(alwaysRun = true)
    public void cleanAllure() {
        AllureUtils.cleanAllureResults();
        ScreenShotUtils.cleanScreenshots();
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        LogsUtil.info("Setting up the test environment");

        driver = DriverManger.createInstance();
        driver.get(BASE_URL);
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        // Parameters show up in Allure Environment
        Allure.parameter("Browser", PropertiesUtils.getPropertyValue("browser"));
        Allure.parameter("Base URL", BASE_URL);
        Allure.parameter("OS", System.getProperty("os.name"));
    }

    @Test(priority = 1, groups = {"search"})
    @Description("Test to validate search functionality, related searches, and pagination on Bing")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "Bing Search Docs", url = "https://learn.microsoft.com/en-us/bing/search-apis/")
    @Issue("QA-100")
    public void searchForVodafone() {
        BrowserActions.navigateTo(driver, BASE_URL);
        homePage.search(SEARCH_KEYWORD);

        Allure.addAttachment("Current URL after search", driver.getCurrentUrl());
    }

    @Test(priority = 2, dependsOnGroups = {"search"}, groups = {"search"})
    @Description("Validate that page 1 shows at least one search result")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-101")
    public void validateFirstPageHasResults() {
        int resultsCount = resultsPage.getSearchResultsCount(driver);
        LogsUtil.info("Results found on page 1: " + resultsCount);

        Allure.addAttachment("Page Source - Page 1", "text/html", driver.getPageSource(), "html");

        Assert.assertTrue(resultsCount > 0, "No search results found on page 1!");
    }

    @Test(priority = 3, dependsOnGroups = {"search"}, groups = {"related"})
    @Description("Check that related searches sections contain Vodafone keyword")
    @Severity(SeverityLevel.NORMAL)
    @Issue("QA-102")
    public void validateRelatedSearchSections() {
        LogsUtil.info("Validating related search sections");

        boolean relatedSectionsValid = resultsPage.validateRelatedSearchSections(
                EXPECTED_RELATED_TEXT,
                EXPECTED_RELATED_SECTIONS_COUNT
        );

        Allure.addAttachment("Related Section Check", String.valueOf(relatedSectionsValid));

        Assert.assertTrue(relatedSectionsValid,
                "Related search sections validation failed!");
    }

    @Test(priority = 4, dependsOnGroups = {"search"}, groups = {"pagination"})
    @Description("Navigate to page 2 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-103")
    public void validateSecondPageHasResults() {
        resultsPage.goToPage(2);
        page2Count = resultsPage.getSearchResultsCount(driver);
        LogsUtil.info("Results found on page 2: " + page2Count);

        Allure.addAttachment("Page Source - Page 2", "text/html", driver.getPageSource(), "html");

        Assert.assertTrue(page2Count >= 8 && page2Count <= 10,
                "Expected around 9 results on page 2 but found " + page2Count);
    }

    @Test(priority = 5, dependsOnGroups = {"pagination"}, groups = {"pagination"})
    @Description("Navigate to page 3 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-104")
    public void validateThirdPageHasResults() {
        resultsPage.goToPage(3);
        page3Count = resultsPage.getSearchResultsCount(driver);
        LogsUtil.info("Results found on page 3: " + page3Count);

        Allure.addAttachment("Page Source - Page 3", "text/html", driver.getPageSource(), "html");

        //Soft assertion to allow test continuation
        softAssert.assertTrue(page3Count >= 8 && page3Count <= 10,
                "Expected around 9 results on page 3 but found " + page3Count);
    }

    @Test(priority = 6, dependsOnGroups = {"pagination"}, groups = {"comparison"})
    @Description("Compare results count between page 2 and page 3")
    @Severity(SeverityLevel.MINOR)
    @Issue("QA-105")
    public void validateSearchResultsAcrossPages() {
        LogsUtil.info("Validate that page 2 and page 3 results count match");

        Allure.addAttachment("Comparison",
                "Page 2 Count = " + page2Count + ", Page 3 Count = " + page3Count);

        Assert.assertEquals(page2Count, page3Count,
                "Search results count on page 2 and 3 do not match!");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        //ScreenShotUtils.takeScreenshot("screenshoot.png");
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        LogsUtil.info("Close Browser");
        DriverManger.quitDriver();
    }
}
