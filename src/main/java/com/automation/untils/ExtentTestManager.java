package com.automation.untils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void startTest(String testName, String description) {
        ExtentTest test = ExtentManager.getInstance().createTest(testName, description);
        extentTest.set(test);
    }

    public static synchronized void endTest() {
        extentTest.remove();
    }
}
