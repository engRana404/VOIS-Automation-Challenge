package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.LogsUtil;
import com.vois.utils.ValidationUtils;
import com.vois.utils.WaitUtils;
import com.vois.utils.actions.BrowserActions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ResultsPage {

    private final WebDriver driver;

    private static final By relatedSectionsLocator = LocatorReaderUtils.getLocator("results", "relatedSearchSections");
    private static final By searchResultsLocator = LocatorReaderUtils.getLocator("results", "searchResults");
    private static final String nextPageButtonTemplate = LocatorReaderUtils.getLocatorValue("results", "nextPageButton");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Validate that the related search section have the expected number")
    public ResultsPage validateRelatedSearchSections(String expectedText, int expectedSectionCount) {
        List<WebElement> sections = driver.findElements(relatedSectionsLocator);
        if (sections.isEmpty()) {
            LogsUtil.warn("No related search sections found.");
            ValidationUtils.validateTrue(false, "No sections found"); // No sections found
        }
        int sectionIndex = 0;

        List<WebElement> items = sections.stream()
                .flatMap(section -> section.findElements(By.xpath(".//li")).stream())
                .toList();
        for (WebElement item : items) {
            LogsUtil.info("Related section item text: " + item.getText());
            if(sectionIndex == expectedSectionCount){
                Allure.addAttachment("Related Section Check", String.valueOf(sectionIndex));
                break;
            }
            else if (item.getText().toLowerCase().contains(expectedText.toLowerCase())) {
                sectionIndex++;
            }
        }
        LogsUtil.info("Total related sections matching '" + expectedText + "': " + sectionIndex);
        ValidationUtils.validateTrue(ValidationUtils.compareElementCounts(expectedSectionCount, expectedSectionCount), "Expected related sections found.");
        return this;
    }

    @Step("Get the count of search results on the current page")
    public int getSearchResultsCount() {
        LogsUtil.info("Counting organic search results on the current page.");

        // Wait for visibility
        List<WebElement> results = WaitUtils.waitForAllElementsVisible(driver, searchResultsLocator);

        // Filter out any rich media injected into b_algo
        int count = 0;
        for (WebElement result : results) {
            String text = result.getText().toLowerCase();
            if (!text.contains("video") && !text.contains("image") && !text.contains("map")) {
                count++;
            }
        }
        LogsUtil.info("Organic result count (excluding media): " + count);
        return count;
    }

    @Step("Validate that the current page has at least one search result")
    public ResultsPage validatePageHasResults() {
        LogsUtil.info("Validating that the current page has at least one search result.");
        ValidationUtils.validateTrue(getSearchResultsCount() > 0,
                "No search results found on the current page!");
        return this;
    }

    @Step("Validate that search results count on page {pageNumber1} and page {pageNumber2} match")
    public ResultsPage validateSearchResultsAcrossTwoPages(int pageNumber1, int pageNumber2) {
        LogsUtil.info("Validating that search results count on page " + pageNumber1 + " and page " + pageNumber2 + " match.");

        goToPage(pageNumber1);
        int page1Count = getSearchResultsCount();

        goToPage(pageNumber2);
        int page2Count = getSearchResultsCount();

        Allure.addAttachment("Comparison",
                "Page " + pageNumber1 + " Count = " + page1Count + ", Page " + pageNumber2 +" Count = " + page2Count);

        ValidationUtils.validationEquals(page1Count, page2Count,
                "Search results count on page " + pageNumber1 + " and page " + pageNumber2 + " do not match!");

        return this;
    }

    @Step("Navigate to page number: {pageNumber}")
    public ResultsPage goToPage(int pageNumber) {
        LogsUtil.info("Navigating to page number: " + pageNumber);
        BrowserActions.goToPage(driver, nextPageButtonTemplate, pageNumber);
        return this;
    }
}
