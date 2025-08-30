package com.vois.utils;

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
}
