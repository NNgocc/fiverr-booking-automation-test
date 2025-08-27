package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.untils.DriverManager;
import com.automation.untils.ExtentTestManager;
import com.automation.untils.LoggerUtil;
import com.automation.untils.MouseAnimationUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {
    @Test(testName = "Test (animation) Valid Login", description = "Demo login với mouse animation")
    public void testAnimatedValidLogin() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }

            LoginPage loginPage = new LoginPage(DriverManager.getDriver());
            loginPage.loginWithAnimation("demo6@tech.com", "Tech@123");
            Thread.sleep(3000);
            ExtentTestManager.getTest().log(Status.INFO, "Login successful");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("Animated test failed: " + e.getMessage());
        } finally {
            MouseAnimationUtils.cleanup();
        }
    }

//    @Test(description = "Verify successful login with valid credentials")
//    public void testValidLogin() {
//        LoginPage loginPage = new LoginPage(driver);
//        LoggerUtil.step("TC01: Navigate to login page");
//        ExtentTestManager.getTest().log(Status.INFO, "Navigating to login page");
//
//        loginPage.enterUsername("demo6@tech.com");
//        loginPage.enterPassword("Tech@123");
//        LoggerUtil.step("Enter email and password");
//        ExtentTestManager.getTest().log(Status.INFO, "Entering credentials");
//
//        loginPage.clickLoginButton();
//        LoggerUtil.step("Click login button");
//        ExtentTestManager.getTest().log(Status.INFO, "Clicking login button");
//
//        // Simulate test logic
//        boolean loginSuccess = true; // Replace with actual verification
//
//        LoggerUtil.verification("User should be logged in successfully", loginSuccess);
//
//        if (loginSuccess) {
//            ExtentTestManager.getTest().log(Status.PASS, "Login successful");
//        } else {
//            ExtentTestManager.getTest().log(Status.FAIL, "Login failed");
//        }
//
//        Assert.assertTrue(loginSuccess, "Login should be successful");
//    }
//
//    @Test(description = "Verify error message with invalid credentials")
//    public void testInvalidLogin() {
//        LoginPage loginPage = new LoginPage(driver);
//        LoggerUtil.step("TC2: Navigate to login page");
//        ExtentTestManager.getTest().log(Status.INFO, "Testing invalid login");
//        loginPage.login("Admin", "admin123");
//        // Test implementation here
//        boolean errorDisplayed = true;
//
//        LoggerUtil.verification("Error message should be displayed", errorDisplayed);
//        ExtentTestManager.getTest().log(Status.PASS, "Error message displayed correctly");
//
//        Assert.assertTrue(errorDisplayed);
//    }
}
