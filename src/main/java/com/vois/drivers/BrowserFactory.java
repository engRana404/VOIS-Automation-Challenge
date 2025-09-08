package com.vois.drivers;

import com.vois.utils.LogsUtil;
import com.vois.utils.PropertiesUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BrowserFactory {
    private static final String executionType = PropertiesUtils.getExecutionType();

    private BrowserFactory() {
        super();
    }

    @Step("Create driver instance for browser: {browserType}")
    public static WebDriver createDriver(String browserType) {
        LogsUtil.info("Creating WebDriver instance for browser: " + browserType);
        WebDriver driver;

        switch (browserType.toUpperCase()) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("start-maximized", "disable-infobars", "--disable-extensions", "--disable-notifications", "--remote-allow-origins=*");
                chromeOptions.addArguments("user-agent=Mozilla/5.0 ...");
                //chromeOptions.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                chromeOptions.setExperimentalOption("useAutomationExtension", false);

                if (!executionType.equalsIgnoreCase("Local")) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }

                driver = new ChromeDriver(chromeOptions);

                ((ChromeDriver) driver).executeCdpCommand(
                        "Page.addScriptToEvaluateOnNewDocument",
                        Map.of("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
                );
                break;

            case "EDGE":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("start-maximized", "disable-infobars", "--disable-extensions", "--disable-notifications", "--remote-allow-origins=*");
                edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
                edgeOptions.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.setCapability("ms:edgeOptions", Map.of("args", List.of("--disable-blink-features=AutomationControlled")));
                if (!executionType.equalsIgnoreCase("Local")) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    edgeOptions.addArguments("--disable-blink-features=AutomationControlled"); // Avoid detection
                    edgeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"); // Fake user-agent
                }
                driver = new EdgeDriver(edgeOptions);
                ((EdgeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                        Map.of("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"));
                break;

            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);
                if (!executionType.equalsIgnoreCase("Local")) {
                    firefoxOptions.addArguments("--headless");
                }

                // Firefox tweak to reduce detection
                firefoxOptions.addPreference("dom.webdriver.enabled", false);
                firefoxOptions.addPreference("useAutomationExtension", false);
                firefoxOptions.addPreference("media.navigator.enabled", true);
                firefoxOptions.addPreference("general.useragent.override",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/113.0.0.0 Safari/537.36");

                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        long timeout = PropertiesUtils.getTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        return driver;
    }
}