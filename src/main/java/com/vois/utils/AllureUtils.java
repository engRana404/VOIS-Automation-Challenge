package com.vois.utils;

import io.qameta.allure.Allure;

import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {
    private AllureUtils() {
        // Prevent instantiation
        LogsUtil.error("Trying to instantiate utility class AllureUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    public static void cleanAllureResults() {
        FilesUtils.cleanDirectory("allure-results");
        LogsUtil.info("Cleaned Allure results directory.");
    }

    public static void attachScreenshot(String screenshotName, String screenshotPath) {
        LogsUtil.info("Attaching screenshot to Allure report: " + screenshotName);
        try {
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotPath)));
            LogsUtil.info("Attached screenshot to Allure report.");
        } catch (Exception e) {
            LogsUtil.error("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }
}
