package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.listeners.TestNGListeners;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import com.vois.utils.actions.BrowserActions;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Epic("Search Engine Validation")
@Feature("Bing Search Functionality")
@Story("Vodafone Search Scenario")
@Owner("Rana Gamal")
@Listeners(TestNGListeners.class)
public class VodafoneSearchTest {
    private WebDriver driver;

    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_TITLE = JsonUtils.getTestData("expectedTitle");
    private static final String EXPECTED_RELATED_TEXT = JsonUtils.getTestData("expectedRelatedText");
    private static final int EXPECTED_RELATED_SECTIONS_COUNT = Integer.parseInt(JsonUtils.getTestData("expectedRelatedCount"));

    @BeforeClass(alwaysRun = true)
    public void setup() {
        LogsUtil.info("Setting up the test environment");

        driver = DriverManager.createInstance();
    }

    @Test(priority = 1, groups = {"search"})
    @Description("Test to validate search functionality on Bing")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "Bing Search Docs", url = "https://learn.microsoft.com/en-us/bing/search-apis/")
    @Issue("QA-100")
    public void searchForVodafone() {
        new HomePage(driver)
                .navigateToHomePage()
                .search(SEARCH_KEYWORD)
                .validatePageTitle(EXPECTED_TITLE);

        Allure.addAttachment("Current URL after search", BrowserActions.getCurrentUrl(driver));
    }

    @Test(priority = 2, dependsOnGroups = {"search"}, groups = {"search"})
    @Description("Validate that page 1 shows at least one search result")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-101")
    public void validateFirstPageHasResults() {
        new ResultsPage(driver)
                .validatePageHasResults();

        Allure.addAttachment("Page Source - Page 1", "text/html", BrowserActions.getPageSource(driver), "html");
    }

    @Test(priority = 3, dependsOnGroups = {"search"}, groups = {"related"})
    @Description("Check that related searches sections contain Vodafone keyword")
    @Severity(SeverityLevel.NORMAL)
    @Issue("QA-102")
    public void validateRelatedSearchSections() {
        LogsUtil.info("Validating related search sections");

        new ResultsPage(driver)
                .validateRelatedSearchSections(
                    EXPECTED_RELATED_TEXT,
                    EXPECTED_RELATED_SECTIONS_COUNT
                );
    }

    @Test(priority = 4, dependsOnGroups = {"search"}, groups = {"pagination"})
    @Description("Navigate to page 2 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-103")
    public void validateSecondPageHasResults() {
        new ResultsPage(driver)
                .goToPage(2)
                .validatePageHasResults();

        Allure.addAttachment("Page Source - Page 2", "text/html", BrowserActions.getPageSource(driver), "html");
    }

    @Test(priority = 5, dependsOnGroups = {"pagination"}, groups = {"pagination"})
    @Description("Navigate to page 3 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-104")
    public void validateThirdPageHasResults() {
        new ResultsPage(driver)
                .goToPage(3)
                .validatePageHasResults();

        Allure.addAttachment("Page Source - Page 3", "text/html", BrowserActions.getPageSource(driver), "html");
    }

    @Test(priority = 6, dependsOnGroups = {"pagination"}, groups = {"comparison"})
    @Description("Compare results count between page 2 and page 3")
    @Severity(SeverityLevel.MINOR)
    @Issue("QA-105")
    public void validateSearchResultsAcrossPages() {
        LogsUtil.info("Validate that page 2 and page 3 results count match");

        new ResultsPage(driver)
                .validateSearchResultsAcrossTwoPages(2, 3);
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        LogsUtil.info("Close Browser");
        DriverManager.quitDriver();
    }
}
