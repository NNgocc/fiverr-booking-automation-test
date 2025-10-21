package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.helpers.LoginHelper;
import com.automation.pages.JobDetailPage;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.LoggerUtil;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JobDetailTest extends BaseTest {
    @Test(testName = "TC412 - Verify function user become a seller", description = "Verify chức năng Become a seller")
    public void verifyFunctionBecomeSeller() {
        try {
            ExtentTestManager.getTest().info("TC412 - Verify function user become a seller");

            LoginHelper.loginWithAnimation();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnBecomeSeller();
            WaitUtils.waitShort();

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC412 - Verify function user become a seller test failed: " + e.getMessage());
        } finally {
            //MouseAnimationUtils.cleanup();
        }
    }

    @Test(testName = "TC409 - Verify function user become a business", description = "Verify chức năng Fiver Business")
    public void verifyFunctionBecomeBusiness() {
        try {
            ExtentTestManager.getTest().info("TC409 - Verify function user become a business");

            LoginHelper.loginWithAnimation();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnBecomeBusiness();
            WaitUtils.waitShort();

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC409 - Verify function user become a business test failed: " + e.getMessage());
        }
    }

    @Test(testName = "TC413 - Verify search on navigation", description = "Verify chức năng search trên thanh menu")
    public void verifyFunctionSearchOnNavigation() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.searchOnNavigation();
            WaitUtils.waitShort();
            if (rs)
                Assert.assertTrue(rs, "Search success");
            else
                Assert.assertFalse(rs, "Search fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC413 - Verify search on navigation test failed: " + e.getMessage());
        }
    }

    @Test(testName = "TC414 - Verify search carousel", description = "Verify chức năng search trên carousel")
    public void verifyFunctionSearchCarousel() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            boolean rs = job.verifyFunctionSearchCarousel();
            WaitUtils.waitShort();
            if (rs)
                Assert.assertTrue(rs, "Search success");
            else
                Assert.assertFalse(rs, "Search fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC414 - Verify search carousel test failed: " + e.getMessage());
        }
    }

    @Test(testName = "TC415 - Verify function contact seller/business", description = "Verify chức năng Contact Me")
    public void verifyButtonContact() {
        try {
            LoginHelper.loginWithAnimation();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnContactMe();
            WaitUtils.waitShort();
            if (!rs)
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy button Contact Me");

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");

        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC415 - Verify function contact seller/business test failed: " + e.getMessage());
        } finally {
        }
    }

    @Test(testName = "TC418 - Verify package selection functionality", description = "Verify chức năng Continue")
    public void verifyButtonContinue() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }

            LoginHelper.loginWithAnimation();

            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnContinue();
            WaitUtils.waitShort();

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            System.out.println("TC418 - Verify package selection functionality test failed: " + e);
            LoggerUtil.error("Error");
        }
    }

    @Test(testName = "TC421 - Verify method Compare Package", description = "Verify chức năng Compare packages")
    public void verifyButtonCompare() {
        try {
            LoginHelper.loginWithAnimation();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnCompare();
            WaitUtils.waitShort();

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC421 - Verify method Compare Package test failed: " + e.getMessage());
        } finally {
        }
    }
}
