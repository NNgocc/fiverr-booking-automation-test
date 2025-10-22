package com.automation.helpers;

import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;

/**
 * Helper class to handle login operations
 * This avoids test dependency issues by providing reusable login logic
 */
public class LoginHelper {

    private static final String DEFAULT_EMAIL = "demo6@tech.com";
    private static final String DEFAULT_PASSWORD = "Tech@123";

    /**
     * Perform login with animation using default credentials
     */
    public static void loginWithAnimation() {
        loginWithAnimation(DEFAULT_EMAIL, DEFAULT_PASSWORD);
    }

    /**
     * Perform login with animation using custom credentials
     * @param email User email
     * @param password User password
     */
    public static void loginWithAnimation(String email, String password) {
        try {
            if (DriverManager.getDriver() == null) {
                throw new RuntimeException("WebDriver not initialized");
            }

            LoginPage loginPage = new LoginPage(DriverManager.getDriver());
            loginPage.loginWithAnimation(email, password);
            WaitUtils.waitShort();
            ExtentTestManager.getTest().log(Status.INFO, "Login successful with email: " + email);
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("Login failed: " + e.getMessage());
            throw new RuntimeException("Login failed", e);
        }
    }

    /**
     * Check if user is already logged in
     * @return true if logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        // Implementation depends on your application
        // For now, return false to always perform login
        return false;
    }
}
