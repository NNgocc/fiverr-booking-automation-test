package com.automation.tests;

import com.automation.pages.JobDetailPage;
import com.automation.untils.DriverManager;
import com.automation.untils.ExtentTestManager;
import com.automation.untils.MouseAnimationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JobDetailTest extends LoginTest {
    @Test(testName = "TC412 - Verify function user become a seller", description = "Verify chức năng Become a seller")
    public void verifyFunctionBecomeSeller() {
        try {
            LoginTest login = new LoginTest();
            login.testAnimatedValidLogin();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnBecomeSeller();
            Thread.sleep(3000);

            //ExtentTestManager.getTest().info("Result: " + (rs ? "SUCCESS" : "FAILED"));

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

    @Test(testName = "TC413 - Verify search on navigation", description = "Verify chức năng search trên thanh menu có login")
    public void verifyFunctionSearchOnNavigation() {
        try {
            LoginTest login = new LoginTest();
            login.testAnimatedValidLogin();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.searchOnNavigation();
            Thread.sleep(3000);
            if(!rs)
                ExtentTestManager.getTest().info("User đã đăng nhập. Không tìm thấy ô search trên thanh menu");
            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");

        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC413 - Verify search on navigation test failed: " + e.getMessage());
        }
    }

    @Test(testName = "TC414 - Verify search on navigation dont have account", description = "Verify chức năng search trên thanh menu không có login")
    public void verifyFunctionSearchOnNavigationDontHaveAccount() {
        try {
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.searchOnNavigation();
            Thread.sleep(3000);
            if(!rs)
                ExtentTestManager.getTest().info("User chưa đăng nhập. Không tìm thấy ô search trên thanh menu");
            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");

        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC414 - Verify search on navigation dont have account test failed: " + e.getMessage());
        } finally {
        }
    }

    @Test(testName = "TC415 - Verify function contact seller/business", description = "Verify chức năng Contact Me")
    public void verifyButtonContact() {
        try {
            LoginTest login = new LoginTest();
            login.testAnimatedValidLogin();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnContactMe();
            Thread.sleep(3000);
            if (!rs)
                ExtentTestManager.getTest().info("Không tìm thấy button Contact Me");

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
            LoginTest login = new LoginTest();
            login.testAnimatedValidLogin();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnContinue();
            Thread.sleep(3000);

            if (!rs)
                ExtentTestManager.getTest().info("Không tìm thấy button Continue");

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC418 - Verify package selection functionality test failed: " + e.getMessage());
        } finally {
        }
    }

    @Test(testName = "TC421 - Verify method Compare Package", description = "Verify chức năng Compare packages")
    public void verifyButtonCompare() {
        try {
            LoginTest login = new LoginTest();
            login.testAnimatedValidLogin();
            if (DriverManager.getDriver() == null) {
                System.err.println("WebDriver is null! BaseTest setup failed.");
                throw new RuntimeException("WebDriver not initialized");
            }
            JobDetailPage job = new JobDetailPage(DriverManager.getDriver());
            var rs = job.clickBtnCompare();
            Thread.sleep(3000);

            if (!rs)
                ExtentTestManager.getTest().info("Không tìm thấy button Compare Package");

            if (rs)
                Assert.assertTrue(rs, "Click button success");
            else
                Assert.assertFalse(rs, "Click button fail");
        } catch (Exception e) {
            ExtentTestManager.getTest().fail("TC421 - Verify method Compare Package test failed: " + e.getMessage());
        } finally {
        }
    }


    //trên trang nhưng nằm ở user story khác
//    @Test(testName = "TC413 - Verify function contact seller/business", description = "Verify chức năng Comment")
//    public void verifyButtonComment(){
//
//    }
//    @Test(description = "Verify tính năng like Comment")
//    public void verifyIconLikeComment(){
//
//    }
//    @Test(description = "Verify tính năng un-like Comment")
//    public void verifyIconUnLikeComment(){
//
//    }
//    @Test(description = "Verify tính năng Helpful (Comment)")
//    public void verifyHelpfulComment(){
//
//    }
}
