package com.vois.listeners;

import com.vois.utils.AllureUtils;
import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import com.vois.utils.ScreenShotUtils;
import io.qameta.allure.Allure;
import org.testng.*;
import org.testng.annotations.Test;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestNGListener, ITestListener {
    private static final String BASE_URL = PropertiesUtils.getPropertyValue("baseUrl");

    @Override
    public void onExecutionStart() {
        LogsUtil.info("Test execution started.");
        AllureUtils.cleanAllureResults();
        ScreenShotUtils.cleanScreenshots();
    }

    @Override
    public void onExecutionFinish() {
        LogsUtil.info("Test execution finished.");
        // Shutdown logging after everything
        org.apache.logging.log4j.LogManager.shutdown();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if (method.isTestMethod()) {
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS:
                    ScreenShotUtils.takeScreenshot("passed-" + testResult.getName());
                    break;
                case ITestResult.FAILURE:
                    ScreenShotUtils.takeScreenshot("failed-" + testResult.getName());
                    break;
                case ITestResult.SKIP:
                    ScreenShotUtils.takeScreenshot("skipped-" + testResult.getName());
                    break;
                default:
                    LogsUtil.warn("Test method " + testResult.getName() + " finished with status: " + testResult.getStatus());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogsUtil.info("Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test method " + result.getName() + " passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.error("Test method " + result.getMethod().getMethodName() + " failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtil.warn("Test method " + result.getName() + " was skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LogsUtil.warn("Test method " + result.getName() + " failed but is within success percentage.");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        LogsUtil.fatal("Test method " + result.getName() + " failed due to timeout.");
    }
}
