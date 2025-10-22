package com.automation.base;

import com.automation.utils.*;
import com.aventstack.extentreports.Status;
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
        // Screenshot capture is now handled by ExtentTestListener
        // to avoid duplicate screenshots and ensure proper attachment to ExtentReport
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentManager.flush();
    }
}
