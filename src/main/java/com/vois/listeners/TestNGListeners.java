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

    private String threadInfo() {
        return "[Thread-" + Thread.currentThread().getId() + "]";
    }

    @Override
    public void onExecutionStart() {
        LogsUtil.info(threadInfo() + "Test execution started.");
        AllureUtils.cleanAllureResults();
        ScreenShotUtils.cleanScreenshots();
    }

    @Override
    public void onExecutionFinish() {
        LogsUtil.info(threadInfo() + "Test execution finished.");
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
                    ScreenShotUtils.takeScreenshot("passed-" + testResult.getName() + ".png");
                    break;
                case ITestResult.FAILURE:
                    ScreenShotUtils.takeScreenshot("failed-" + testResult.getName() + ".png");
                    break;
                case ITestResult.SKIP:
                    ScreenShotUtils.takeScreenshot("skipped-" + testResult.getName() + ".png");
                    break;
                default:
                    LogsUtil.warn(threadInfo() + "Test method " + testResult.getName() + " finished with status: " + testResult.getStatus());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogsUtil.info(threadInfo() + "Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info(threadInfo() + "Test method " + result.getName() + " passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.error(threadInfo() + "Test method " + result.getMethod().getMethodName() + " failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtil.warn(threadInfo() + "Test method " + result.getName() + " was skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LogsUtil.warn(threadInfo() + "Test method " + result.getName() + " failed but is within success percentage.");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        LogsUtil.fatal(threadInfo() + "Test method " + result.getName() + " failed due to timeout.");
    }
}
