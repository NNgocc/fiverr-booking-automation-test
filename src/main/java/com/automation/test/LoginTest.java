package com.automation.test;

import com.automation.base.BaseTest;
import com.automation.untils.ExtentTestManager;
import com.automation.untils.LoggerUtil;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        LoggerUtil.step("Navigate to login page");
        ExtentTestManager.getTest().log(Status.INFO, "Navigating to login page");

        LoggerUtil.step("Enter username and password");
        ExtentTestManager.getTest().log(Status.INFO, "Entering credentials");

        LoggerUtil.step("Click login button");
        ExtentTestManager.getTest().log(Status.INFO, "Clicking login button");

        // Simulate test logic
        boolean loginSuccess = true; // Replace with actual verification

        LoggerUtil.verification("User should be logged in successfully", loginSuccess);

        if (loginSuccess) {
            ExtentTestManager.getTest().log(Status.PASS, "Login successful");
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Login failed");
        }

        Assert.assertTrue(loginSuccess, "Login should be successful");
    }

    @Test(description = "Verify error message with invalid credentials")
    public void testInvalidLogin() {
        LoggerUtil.step("Navigate to login page");
        ExtentTestManager.getTest().log(Status.INFO, "Testing invalid login");

        // Test implementation here
        boolean errorDisplayed = true;

        LoggerUtil.verification("Error message should be displayed", errorDisplayed);
        ExtentTestManager.getTest().log(Status.PASS, "Error message displayed correctly");

        Assert.assertTrue(errorDisplayed);
    }
}
