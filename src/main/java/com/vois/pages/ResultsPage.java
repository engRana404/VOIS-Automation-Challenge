package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.LogsUtil;
import com.vois.utils.ValidationUtils;
import com.vois.utils.WaitUtils;
import com.vois.utils.actions.BrowserActions;
import com.vois.utils.actions.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ResultsPage {

    private WebDriver driver;

    private static final By relatedSectionsLocator = LocatorReaderUtils.getLocator("results", "relatedSearchSections");
    private static final By searchResultsLocator = LocatorReaderUtils.getLocator("results", "searchResults");
    private static final String nextPageButtonTemplate = LocatorReaderUtils.getLocatorValue("results", "nextPageButton");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Validate that the related search section have the expected number")
    public boolean validateRelatedSearchSections(String expectedText, int expectedSectionCount) {
        List<WebElement> sections = driver.findElements(relatedSectionsLocator);
        if (sections.isEmpty()) {
            LogsUtil.warn("No related search sections found.");
            return false; // No sections found
        }
        int sectionIndex = 0;

        List<WebElement> items = sections.stream()
                .flatMap(section -> section.findElements(By.xpath(".//li")).stream())
                .toList();
        for (WebElement item : items) {
            LogsUtil.info("Related section item text: " + item.getText());
            if(sectionIndex == expectedSectionCount){
                return true;
            }
            else if (item.getText().toLowerCase().contains(expectedText.toLowerCase())) {
                sectionIndex++;
            }
        }
        LogsUtil.info("Total related sections matching '" + expectedText + "': " + sectionIndex);
        return false;
    }

    @Step("Get the count of search results on the current page")
    public static int getSearchResultsCount(WebDriver driver) {
        LogsUtil.info("Counting search results on the current page.");
        return WaitUtils.waitForAllElementsVisible(driver, searchResultsLocator).size();
    }

    @Step("Navigate to page number: {pageNumber}")
    public void goToPage(int pageNumber) {
        LogsUtil.info("Navigating to page number: " + pageNumber);
        BrowserActions.goToPage(driver, nextPageButtonTemplate, pageNumber);
    }
}
