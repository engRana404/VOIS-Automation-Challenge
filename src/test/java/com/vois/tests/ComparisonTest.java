package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class ComparisonTest {
    private WebDriver driver;

    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();

        new HomePage(driver)
                .navigateToHomePage()
                .search(SEARCH_KEYWORD);
    }

    @Test
    public void validateSearchResultsAcrossPages() {
        new ResultsPage(driver)
                .validateSearchResultsAcrossTwoPages(2, 3);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
