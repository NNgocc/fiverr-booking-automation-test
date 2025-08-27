package com.automation.listeners;

import com.automation.untils.ExtentManager;
import com.automation.untils.ExtentTestManager;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class ExtentTestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);

        String testName = result.getMethod().getMethodName();
        String description = "";

        if (testAnnotation != null && !testAnnotation.description().isEmpty()) {
            description = testAnnotation.description();
        }

        ExtentTestManager.startTest(testName, description);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS,
                MarkupHelper.createLabel("Test PASSED: " + result.getMethod().getMethodName(),
                        ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure
        ExtentTestManager.getTest().log(Status.FAIL,
                MarkupHelper.createLabel("Test FAILED: " + result.getMethod().getMethodName(),
                        ExtentColor.RED));

        // Add error details
        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());

        // Add screenshot if WebDriver is available
        try {
            // Assuming you have a way to get current WebDriver instance
            // WebDriver driver = DriverManager.getDriver();
            // String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
            // ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);

            // Alternative: Base64 screenshot
            // String base64Screenshot = ScreenshotUtils.getScreenshotAsBase64(driver);
            // ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot);

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP,
                MarkupHelper.createLabel("Test SKIPPED: " + result.getMethod().getMethodName(),
                        ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
