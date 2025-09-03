package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.listeners.TestNGListeners;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Epic("Search Engine Validation")
@Feature("Bing Search Functionality")
@Story("Vodafone Search Scenario")
@Owner("Rana Gamal")
@Listeners(TestNGListeners.class)
public class RelatedSearchTest {
    private WebDriver driver;

    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_RELATED_TEXT = JsonUtils.getTestData("expectedRelatedText");
    private static final int EXPECTED_RELATED_SECTIONS_COUNT = Integer.parseInt(JsonUtils.getTestData("expectedRelatedCount"));

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();

        new HomePage(driver)
                .navigateToHomePage()
                .search(SEARCH_KEYWORD);
    }

    @Test(groups = {"related"})
    @Description("Check that related searches sections contain Vodafone keyword")
    @Severity(SeverityLevel.NORMAL)
    @Issue("QA-102")
    public void validateRelatedSearchSections() {
        new ResultsPage(driver)
                .validateRelatedSearchSections(
                    EXPECTED_RELATED_TEXT,
                    EXPECTED_RELATED_SECTIONS_COUNT
                );
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
