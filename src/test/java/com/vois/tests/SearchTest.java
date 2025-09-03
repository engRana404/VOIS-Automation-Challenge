package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.JsonUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class SearchTest {
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
    public void validateFirstPageHasResults() {
        new ResultsPage(driver)
                .validatePageHasResults();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
