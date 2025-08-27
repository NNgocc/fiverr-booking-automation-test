package com.automation.base;

import com.automation.untils.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;

public class BaseTest {

    @BeforeSuite
    public void setUpSuite() {
        ExtentManager.createInstance();
    }

    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setUp(@Optional("chrome") String browser, @Optional("https://demo5.cybersoft.edu.vn/") String url, Method method) {
        String testName = method.getName();

        String description = "";
        if (method.isAnnotationPresent(Test.class)) {
            Test testAnnotation = method.getAnnotation(Test.class);
            description = testAnnotation.description();
        }
        DriverManager.setDriver(browser);
        DriverManager.navigateToUrl(url);
        MouseAnimationUtils.initialize(DriverManager.getDriver());

        File logsDir = new File("target/logs");
        if (!logsDir.exists()) {
            boolean created = logsDir.mkdirs();
            System.out.println("📁 Logs directory created: " + created);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testResult = result.isSuccess() ? "PASSED" : "FAILED";

        LoggerUtil.endTest(testName, testResult);

        if (result.isSuccess()) {
            ExtentTestManager.getTest().pass("Test PASSED: " + testName);
        } else {
            ExtentTestManager.getTest().fail("Test FAILED: " + testName);
            ExtentTestManager.getTest().fail(result.getThrowable());

            try {
                String screenshot = ScreenshotUtils.captureScreenshot(DriverManager.getDriver(), testName);
                if (screenshot != null) {
                    ExtentTestManager.getTest().addScreenCaptureFromPath(screenshot);
                    LoggerUtil.info("Screenshot captured: " + screenshot);
                }
            } catch (Exception e) {
                LoggerUtil.error("Failed to capture screenshot: " + e.getMessage());
            }
        }

        LoggerUtil.info("Cleaning up WebDriver");
        ExtentTestManager.getTest().info("Cleaning up WebDriver");

        ExtentTestManager.endTest();
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentManager.flush();
    }
}
