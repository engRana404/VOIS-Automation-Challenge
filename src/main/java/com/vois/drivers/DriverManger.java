package com.vois.drivers;

import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

public class DriverManger {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final String BrowserType = PropertiesUtils.getPropertyValue("browserType");

    private DriverManger() {
        super();
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            LogsUtil.error("Driver is not initialized. Please call createInstance() before using getDriver().");
            fail("Driver is not set. Please initialize the driver before using it.");
        }
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    @Step("Create driver instance for THE browser")
    public static WebDriver createInstance() {
        if (BrowserType == null) {
            LogsUtil.error("browserType property is not set. Please check your properties files.");
            fail("browserType property is missing. Cannot create WebDriver instance.");
        }
        LogsUtil.info("Launching browser: " + BrowserType);
        WebDriver driver = BrowserFactory.createDriver(BrowserType);
        LogsUtil.info("Driver created successfully: " + driver);
        setDriver(driver);
        return getDriver();
    }

    @Step("Quit and remove driver instance")
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
