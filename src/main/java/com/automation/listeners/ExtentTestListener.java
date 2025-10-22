package com.automation.listeners;

import com.automation.utils.DriverManager;
import com.automation.utils.ExtentManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
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
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().log(Status.FAIL,
                MarkupHelper.createLabel("Test FAILED: " + result.getMethod().getMethodName(),
                        ExtentColor.RED));
        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());

        // Capture and attach screenshot to ExtentReport
        try {
            if (DriverManager.getDriver() != null) {
                String screenshotPath = ScreenshotUtils.captureScreenshot(
                        DriverManager.getDriver(),
                        result.getMethod().getMethodName());

                if (screenshotPath != null) {
                    String relativePath = ScreenshotUtils.getRelativeScreenshotPath(screenshotPath);
                    // addScreenCaptureFromPath() makes the screenshot clickable/expandable in the report
                    ExtentTestManager.getTest().addScreenCaptureFromPath(relativePath,
                            "Screenshot on failure");
                    System.out.println("Screenshot attached to ExtentReport: " + relativePath);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot to ExtentReport: " + e.getMessage());
            ExtentTestManager.getTest().log(Status.WARNING,
                    "Could not capture screenshot: " + e.getMessage());
        }

        ExtentTestManager.endTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP,
                MarkupHelper.createLabel("Test SKIPPED: " + result.getMethod().getMethodName(),
                        ExtentColor.ORANGE));
        ExtentTestManager.endTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
