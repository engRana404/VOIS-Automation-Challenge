package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.listeners.TestNGListeners;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.JsonUtils;
import com.vois.utils.actions.BrowserActions;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Epic("Search Engine Validation")
@Feature("Bing Search Functionality")
@Story("Vodafone Search Scenario")
@Owner("Rana Gamal")
@Listeners(TestNGListeners.class)
public class SearchTest {
    private WebDriver driver;

    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_TITLE = JsonUtils.getTestData("expectedTitle");

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();
        new HomePage(driver)
                .navigateToHomePage()
                .search(SEARCH_KEYWORD);
    }

    @Test(priority = 1, groups = {"search"})
    @Description("Test to validate search functionality on Bing")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "Bing Search Docs", url = "https://learn.microsoft.com/en-us/bing/search-apis/")
    @Issue("QA-100")
    public void searchForVodafone() {
        new HomePage(driver)
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

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
