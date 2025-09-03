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
public class PaginationTest {
    private WebDriver driver;

    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");

    @BeforeClass(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();

        new HomePage(driver)
                .navigateToHomePage()
                .search(SEARCH_KEYWORD);
    }

    @Test( priority = 1, groups = {"pagination"})
    @Description("Navigate to page 2 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-103")
    public void validateSecondPageHasResults() {
        new ResultsPage(driver)
                .goToPage(2)
                .validatePageHasResults();
        Allure.addAttachment("Page Source - Page 2", "text/html", BrowserActions.getPageSource(driver), "html");
    }

    @Test( priority = 2, dependsOnMethods = {"validateSecondPageHasResults"}, groups = {"pagination"})
    @Description("Navigate to page 3 and validate search results count")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("QA-104")
    public void validateThirdPageHasResults() {
        new ResultsPage(driver)
                .goToPage(3)
                .validatePageHasResults();
        Allure.addAttachment("Page Source - Page 3", "text/html", BrowserActions.getPageSource(driver), "html");
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
