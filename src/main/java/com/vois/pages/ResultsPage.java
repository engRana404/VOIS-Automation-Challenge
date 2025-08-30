package com.vois.pages;

import com.vois.utils.LocatorReaderUtils;
import com.vois.utils.ValidationUtils;
import com.vois.utils.WaitUtils;
import com.vois.utils.actions.BrowserActions;
import com.vois.utils.actions.ElementActions;
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

    public boolean validateRelatedSearchSections(String expectedText, int expectedSectionCount) {
        List<WebElement> sections = driver.findElements(relatedSectionsLocator);
        if (sections.isEmpty()) {
            System.out.println("No related search sections found");
            return false; // No sections found
        }
        int sectionIndex = 0;

        List<WebElement> items = sections.stream()
                .flatMap(section -> section.findElements(By.xpath(".//li")).stream())
                .toList();
        for (WebElement item : items) {
            System.out.println("Item: " + item.getText());
            if(sectionIndex == expectedSectionCount){
                return true;
            }
            else if (item.getText().toLowerCase().contains(expectedText.toLowerCase())) {
                sectionIndex++;
            }
        }
        return false;
    }

    public static int getSearchResultsCount(WebDriver driver) {
        return WaitUtils.waitForAllElementsVisible(driver, searchResultsLocator).size();
    }

    public void goToPage(int pageNumber) {
        BrowserActions.goToPage(driver, nextPageButtonTemplate, pageNumber);
    }
}
