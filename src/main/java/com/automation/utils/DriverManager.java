package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                try {
                    System.out.println("Setting up Microsoft Edge driver...");
                    WebDriverManager.edgedriver().setup();

                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    edgeOptions.addArguments("--disable-extensions");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--remote-allow-origins=*");

                    // Set preferences to disable notifications and popups
                    edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    edgeOptions.setExperimentalOption("useAutomationExtension", false);

                    System.out.println("Creating Edge driver instance...");
                    driver.set(new EdgeDriver(edgeOptions));
                    System.out.println("Edge driver initialized successfully!");
                } catch (Exception e) {
                    System.err.println("Failed to initialize Edge driver: " + e.getMessage());
                    System.err.println("Please ensure Microsoft Edge browser is installed.");
                    System.err.println("You can download it from: https://www.microsoft.com/edge");
                    throw new RuntimeException("Edge driver initialization failed. " +
                        "Make sure Microsoft Edge browser is installed on your system.", e);
                }
                break;

            default:
                System.err.println("Browser not supported: " + browserName);
                throw new RuntimeException("Browser not supported: " + browserName + ". Supported browsers: chrome, firefox, edge");
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("WebDriver quit successfully");
        }
    }

    public static String captureScreenshot(String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = screenshotName + "_" + timestamp + ".png";
        String screenshotPath = System.getProperty("user.dir") + "/target/screenshots/" + fileName;

        try {
            File screenshotDir = new File(System.getProperty("user.dir") + "/target/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(sourceFile, destFile);

            System.out.println("Screenshot saved: " + screenshotPath);
            return screenshotPath;

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String getScreenshotAsBase64() {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
            return takesScreenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to get screenshot as base64: " + e.getMessage());
            return null;
        }
    }
    public static void navigateToUrl(String url) {
        try {
            WebDriver webDriver = getDriver();
            if (webDriver == null) {
                throw new RuntimeException("Driver is not initialized. Call setDriver() first.");
            }
            webDriver.get(url);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("Navigation FAILED: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to URL: " + url, e);
        }
    }
}
