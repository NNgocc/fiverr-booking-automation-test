package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.MouseAnimationUtils;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(testName = "Test (animation) Valid Login", description = "Demo login với mouse animation")
    public void testAnimatedValidLogin() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }

            LoginPage loginPage = new LoginPage(DriverManager.getDriver());
            loginPage.loginWithAnimation("demo5@tech.com", "Tech@123");
            WaitUtils.waitShort();
            ExtentTestManager.getTest().log(Status.INFO, "Login successful");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("Animated test failed: " + e.getMessage());
        } finally {
            //MouseAnimationUtils.cleanup();
        }
    }
}
