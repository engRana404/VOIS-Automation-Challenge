package com.vois.drivers;

import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final String browserType = PropertiesUtils.getBrowserType();

    private DriverManager() {
        super();
    }

    private static String threadInfo() {
        return "[Thread-" + Thread.currentThread().getId() + "]";
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            LogsUtil.error(threadInfo() + " Driver is not initialized. Please call createInstance() before using getDriver().");
            fail("Driver is not set. Please initialize the driver before using it.");
        }
        LogsUtil.info(threadInfo() + " Returning driver instance: " + driverThreadLocal.get());
        return driverThreadLocal.get();
    }

    private static void setDriver(WebDriver driver) {
        LogsUtil.info(threadInfo() + " Setting driver instance: " + driver);
        driverThreadLocal.set(driver);
    }

    @Step("Create driver instance for the browser")
    public static WebDriver createInstance() {
        if (browserType == null) {
            LogsUtil.error(threadInfo() + " browserType property is not set. Please check your properties files.");
            fail("browserType property is missing. Cannot create WebDriver instance.");
        }

        LogsUtil.info(threadInfo() + " Launching browser: " + browserType);
        WebDriver driver = BrowserFactory.createDriver(browserType);
        LogsUtil.info(threadInfo() + " Driver created successfully: " + driver);

        setDriver(driver);
        return getDriver();
    }

    @Step("Quit and remove driver instance")
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            LogsUtil.info(threadInfo() + " Quitting driver: " + driver);
            driver.quit();
            driverThreadLocal.remove();
            LogsUtil.info(threadInfo() + " Driver removed from ThreadLocal");
        } else {
            LogsUtil.warn(threadInfo() + " No driver found to quit");
        }
    }

    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}
