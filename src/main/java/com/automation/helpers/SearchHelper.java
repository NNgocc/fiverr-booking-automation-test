package com.automation.helpers;

import com.automation.constants.TestConstants;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.MouseAnimationUtils;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Helper class for search functionality operations
 */
public class SearchHelper {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final VerificationHelper verificationHelper;

    private static final String SEARCH_KEY = "app";
    private static final String DOMAIN = "https://demo5.cybersoft.edu.vn/";

    public SearchHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.verificationHelper = new VerificationHelper(driver);
    }

    /**
     * Perform search on navigation bar
     * @param inputSearchNav Navigation search input element
     * @param btnSearchNav Navigation search button element
     * @return true if search successful
     */
    public boolean searchOnNavigation(WebElement inputSearchNav, WebElement btnSearchNav) {
        try {
            // Scroll to search input and wait for it to be visible and clickable
            WaitUtils.scrollToElementAndWait(driver, inputSearchNav);

            // Verify both elements are now visible
            if (!btnSearchNav.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy button search trên navigation Home");
                return false;
            }
            if (!inputSearchNav.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy input search trên navigation Home");
                return false;
            }

            String currentUrl = driver.getCurrentUrl();

            MouseAnimationUtils.animateMouseToElement(inputSearchNav);
            MouseAnimationUtils.animateTyping(inputSearchNav, "website");
            MouseAnimationUtils.pause(50);

            // Ensure button is clickable before clicking
            WaitUtils.waitForElementClickable(driver, btnSearchNav);
            MouseAnimationUtils.animateAndClick(btnSearchNav);
            WaitUtils.waitForPageLoad(driver, 10);

            return verificationHelper.verifyAfterClickActions(currentUrl);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "searchOnNavigation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Perform search using carousel search
     * @param btnLogo Logo button element
     * @param inputSearchCarousel Carousel search input element
     * @param btnSubmit Submit button element
     * @param btnCardDescription Card description button element
     */
    public void searchCarousel(WebElement btnLogo, WebElement inputSearchCarousel,
                               WebElement btnSubmit, WebElement btnCardDescription) {
        MouseAnimationUtils.animateMouseToElement(btnLogo);
        btnLogo.click();
        WaitUtils.waitForPageLoad(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(inputSearchCarousel));

        MouseAnimationUtils.animateMouseToElement(inputSearchCarousel);
        MouseAnimationUtils.animateTyping(inputSearchCarousel, "logo");
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnSubmit);
        btnSubmit.click();
        ExtentTestManager.getTest().log(Status.INFO, "Search carousel");
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnCardDescription);
        btnCardDescription.click();
    }

    /**
     * Verify search carousel functionality
     * @param inputSearchCarousel Carousel search input element
     * @param btnSubmit Submit button element
     * @param btnLogo Logo button element
     * @return true if verification successful
     */
    public boolean verifyFunctionSearchCarousel(WebElement inputSearchCarousel, WebElement btnSubmit,
                                                 WebElement btnLogo) {
        try {
            if (!inputSearchCarousel.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy input search trên Carousel Home");
                return false;
            }
            if (!btnSubmit.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy button search trên Carousel Home");
                return false;
            }

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && currentUrl.toLowerCase().equals(DOMAIN.toLowerCase())) {
                MouseAnimationUtils.animateMouseToElement(btnLogo);
                btnLogo.click();
                WaitUtils.waitForPageLoad(driver, 10);
                wait.until(ExpectedConditions.visibilityOf(inputSearchCarousel));
            }

            MouseAnimationUtils.animateMouseToElement(inputSearchCarousel);
            MouseAnimationUtils.animateTyping(inputSearchCarousel, SEARCH_KEY);

            MouseAnimationUtils.animateMouseToElement(btnSubmit);
            btnSubmit.click();
            ExtentTestManager.getTest().log(Status.INFO, "Search carousel");
            WaitUtils.waitForPageLoad(driver, 10);

            return verificationHelper.verifyAfterClickActions(currentUrl);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "verifyFunctionSearchCarousel failed: " + e.getMessage());
            return false;
        }
    }
}
