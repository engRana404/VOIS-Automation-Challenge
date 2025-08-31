package com.vois.utils;

import com.vois.drivers.DriverManger;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static org.openqa.selenium.OutputType.FILE;
import static org.openqa.selenium.io.FileHandler.copy;
import static org.openqa.selenium.io.FileHandler.createDir;

public class ScreenShotUtils {
    private static final String filePath = System.getProperty("user.dir") + "/screenshots";

    private ScreenShotUtils() {
        // Prevent instantiation
        LogsUtil.error("Trying to instantiate utility class ScreenShotUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    public static void takeScreenshot(String fileName) {
        if (DriverManger.getDriver() == null) {
            LogsUtil.error("WebDriver is null. Cannot take screenshot.");
            return;
        }
        WebDriver driver = DriverManger.getDriver();
        LogsUtil.info("Taking screenshot and saving to: " + filePath + "/" + fileName);
        File src = ((TakesScreenshot) driver).getScreenshotAs(FILE);
        try {
            File dest = new File(filePath + "/" + fileName);
            createDir(new File(filePath));
            copy(src, dest);
            LogsUtil.info("Screenshot saved successfully: " + dest.getAbsolutePath());
            AllureUtils.attachScreenshot("Screenshot - " + fileName, dest.getAbsolutePath());
        } catch (Exception e) {
            LogsUtil.error("Failed to save screenshot: " + e.getMessage());
        }
    }

    public static void cleanScreenshots() {
        LogsUtil.info("Cleaning up screenshots in: " + filePath);
        try {
            FilesUtils.cleanDirectory(filePath);
            LogsUtil.info("Screenshots cleaned successfully.");
        } catch (Exception e) {
            LogsUtil.error("Error while cleaning screenshots: " + e.getMessage());
        }
    }
}
