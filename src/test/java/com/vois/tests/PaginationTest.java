package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

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
    public void validateSecondPageHasResults() {
        new ResultsPage(driver)
                .goToPage(2)
                .validatePageHasResults();
    }

    @Test( priority = 2, dependsOnMethods = {"validateSecondPageHasResults"}, groups = {"pagination"})
    public void validateThirdPageHasResults() {
        new ResultsPage(driver)
                .goToPage(3)
                .validatePageHasResults();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
