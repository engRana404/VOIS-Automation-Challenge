package com.vois.tests;

import com.vois.drivers.DriverManager;
import com.vois.pages.HomePage;
import com.vois.pages.ResultsPage;
import com.vois.utils.*;
import com.vois.utils.actions.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class RelatedSearchTest {
    private WebDriver driver;
    private HomePage homePage;
    private ResultsPage resultsPage;

    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");
    private static final String SEARCH_KEYWORD = JsonUtils.getTestData("searchKeyword");
    private static final String EXPECTED_RELATED_TEXT = JsonUtils.getTestData("expectedRelatedText");
    private static final int EXPECTED_RELATED_SECTIONS_COUNT = Integer.parseInt(JsonUtils.getTestData("expectedRelatedCount"));

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = DriverManager.createInstance();
        homePage = new HomePage(driver);
        resultsPage = new ResultsPage(driver);

        homePage.search(SEARCH_KEYWORD);
    }

    @Test
    public void validateRelatedSearchSections() {
        boolean relatedSectionsValid = resultsPage.validateRelatedSearchSections(
                EXPECTED_RELATED_TEXT,
                EXPECTED_RELATED_SECTIONS_COUNT
        );
        Assert.assertTrue(relatedSectionsValid, "Related search sections validation failed!");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverManager.quitDriver();
    }
}
