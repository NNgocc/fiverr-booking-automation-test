package com.automation.base;

import com.automation.untils.ExtentManager;
import com.automation.untils.ExtentTestManager;
import com.automation.untils.LoggerUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class BaseTest {
    @BeforeSuite
    public void setUpSuite() {
        // Khởi tạo ExtentReports một lần cho toàn bộ test suite
        ExtentManager.createInstance();
    }

    @BeforeMethod
    public void setUp(Method method) {
        String testName = method.getName();
        LoggerUtil.startTest(testName);

        // Setup WebDriver
        LoggerUtil.info("Setting up WebDriver");
        // WebDriver initialization code here

        LoggerUtil.info("Test setup completed for: " + testName);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testResult = result.isSuccess() ? "PASSED" : "FAILED";

        LoggerUtil.endTest(testName, testResult);

        // Cleanup
        LoggerUtil.info("Cleaning up WebDriver");
        // WebDriver cleanup code here

        ExtentTestManager.endTest();
    }

    @AfterSuite
    public void tearDownSuite() {
        // Flush ExtentReports
        ExtentManager.flush();
    }
}
