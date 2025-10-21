package com.automation.pages;

import com.automation.constants.TestConstants;
import com.automation.helpers.SearchHelper;
import com.automation.helpers.VerificationHelper;
import com.automation.utils.ExtentTestManager;
import com.automation.utils.MouseAnimationUtils;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Page Object for Job Detail Page
 * Simplified version using helper classes
 */
public class JobDetailPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final SearchHelper searchHelper;
    private final VerificationHelper verificationHelper;

    /* Find element */
    @FindBy(xpath = "//li[contains(text(),'Become a Seller')]")
    private WebElement liBecomeSeller;

    @FindBy(xpath = "//li[contains(text(),'Fiverr Business')]")
    private WebElement liBecomeBusiness;

    @FindBy(xpath = "//div[@class='check-out mobile mt-5']//a[@class='compare'][normalize-space()='Compare Packages']")
    private WebElement btnComparePackages;

    @FindBy(xpath = "//button[@type='button'][contains(normalize-space(),'Continue')]")
    private WebElement btnContinue;

    @FindBy(xpath = "//button[normalize-space()='Contact Me']")
    private WebElement btnContactMe;

    @FindBy(name = "searchInputCarousel")
    private WebElement inputSearchCarousel;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;

    @FindBy(xpath = "//a[@class='logo active']")
    private WebElement btnLogo;

    @FindBy(css = "a[href='/jobDetail/2']")
    private WebElement btnCardDescription;

    @FindBy(xpath = "//input[@placeholder='Find Services']")
    private WebElement inputSearchNav;

    @FindBy(xpath = "//button[@class='btn']")
    private WebElement btnSearchNav;

    @FindBy(xpath = "//img[@class='avatar']")
    private WebElement btnAvatar;

    @FindBy(className = "job-title")
    private WebElement jobTitle;

    @FindBy(className = "gigs_card_content")
    public List<WebElement> listBookings;

    public JobDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);

        // Initialize helpers
        this.searchHelper = new SearchHelper(driver);
        this.verificationHelper = new VerificationHelper(driver);
    }

    /**
     * Click "Become a Business" menu item
     * @return true if action successful
     */
    public boolean clickBtnBecomeBusiness() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            if (liBecomeBusiness.isDisplayed())
                ExtentTestManager.getTest().log(Status.FAIL, "Fiver Business hiển thị khi chưa login");
            else {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy element menu Fiver Business");
                return false;
            }

            searchCarousel();
            String currentUrl = driver.getCurrentUrl();

            if (liBecomeBusiness.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(liBecomeBusiness);
                MouseAnimationUtils.pause(50);
                liBecomeBusiness.click();

                return verificationHelper.verifyAfterClickActions(currentUrl);
            } else {
                ExtentTestManager.getTest().log(Status.SKIP, "Không tìm thấy menu Fiver Business");
                return false;
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnBecomeBusiness failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Become a Seller" menu item
     * @return true if action successful
     */
    public boolean clickBtnBecomeSeller() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            if (liBecomeSeller.isDisplayed())
                ExtentTestManager.getTest().log(Status.FAIL, "Become a Seller hiển thị khi chưa login");
            else {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy element menu Become a Seller");
                return false;
            }

            searchCarousel();
            String currentUrl = driver.getCurrentUrl();

            if (liBecomeSeller.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(liBecomeSeller);
                MouseAnimationUtils.pause(50);
                liBecomeSeller.click();

                return verificationHelper.verifyAfterClickActions(currentUrl);
            } else {
                ExtentTestManager.getTest().log(Status.SKIP, "Không tìm thấy menu Become a seller");
                return false;
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnBecomeSeller failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click "Compare Packages" button
     * @return true if action successful
     */
    public boolean clickBtnCompare() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            searchCarousel();

            String currentUrl = driver.getCurrentUrl();
            if (btnComparePackages.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(btnComparePackages);
                MouseAnimationUtils.pause(50);
                btnComparePackages.click();

                boolean actionResult = verificationHelper.verifyAfterClickActions(currentUrl);
                ExtentTestManager.getTest().log(Status.INFO, "actionResult: " + actionResult);

                if (actionResult) {
                    ExtentTestManager.getTest().log(Status.PASS, "Có action Compare.");
                } else {
                    ExtentTestManager.getTest().log(Status.FAIL, "Không có action nào được cài đặt.");
                }
            }
            return true;
        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnCompare failed: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Click "Contact Me" button
     * @return true if action successful
     */
    public boolean clickBtnContactMe() {
        try {
            searchCarousel();
            if (btnContactMe.isDisplayed()) {
                js.executeScript("window.scrollBy(0, " + TestConstants.SCROLL_OFFSET + ");");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[normalize-space()='Contact Me']")));

            if (!btnContactMe.isDisplayed()) {
                return false;
            }

            MouseAnimationUtils.animateMouseToElement(btnContactMe);
            MouseAnimationUtils.pause(50);
            MouseAnimationUtils.animateAndClick(btnContactMe);
            return true;
        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnContactMe failed: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Click "Continue" button and verify booking
     * @return true if booking successful
     */
    public boolean clickBtnContinue() {
        var jobName = "";
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            searchCarousel();

            if (btnContinue.isDisplayed()) {
                if (btnContinue.isEnabled()) {
                    MouseAnimationUtils.animateAndClick(btnContinue);
                    jobName = jobTitle.getText();
                }
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toastResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='Toastify']")));
            ExtentTestManager.getTest().info("Toast success: " + toastResult.getText());
            MouseAnimationUtils.animateMouseToElement(toastResult);
            MouseAnimationUtils.pause(50);

            if (btnContinue.isEnabled()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Lỗi UI: Không disable sau khi booking thành công");
                return false;
            }

            MouseAnimationUtils.animateAndClick(btnAvatar);
            WaitUtils.waitForPageLoad(driver, 10);

            return verifyBookingInList(jobName);

        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, ex.getMessage());
            return false;
        }
    }

    /**
     * Verify booking appears in list
     * @param jobName The job name to verify
     * @return true if found
     */
    private boolean verifyBookingInList(String jobName) {
        if (!listBookings.isEmpty()) {
            ExtentTestManager.getTest().log(Status.INFO, "List: " + listBookings.size());

            for (WebElement gigCard : listBookings) {
                try {
                    WebElement titleElement = gigCard.findElement(By.tagName("h1"));
                    String gigTitle = titleElement.getText().trim();

                    if (gigTitle.toLowerCase().contains(jobName.toLowerCase())) {
                        ExtentTestManager.getTest().log(Status.PASS, "Tìm thấy gig với tên: " + jobName);
                        return true;
                    }
                } catch (NoSuchElementException e) {
                    String cardText = gigCard.getText();
                    if (cardText.toLowerCase().contains(jobName.toLowerCase())) {
                        ExtentTestManager.getTest().log(Status.PASS, "Tìm thấy gig chứa text: " + jobName);
                        return true;
                    }
                }
            }

            ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy gig với tên: " + jobName);
            return false;
        }
        return true;
    }

    /**
     * Search using navigation bar
     * @return true if search successful
     */
    public boolean searchOnNavigation() {
        return searchHelper.searchOnNavigation(inputSearchNav, btnSearchNav);
    }

    /**
     * Search using carousel
     */
    public void searchCarousel() {
        searchHelper.searchCarousel(btnLogo, inputSearchCarousel, btnSubmit, btnCardDescription);
    }

    /**
     * Verify carousel search functionality
     * @return true if verification successful
     */
    public boolean verifyFunctionSearchCarousel() {
        return searchHelper.verifyFunctionSearchCarousel(inputSearchCarousel, btnSubmit, btnLogo);
    }

    /**
     * Check if buttons are displayed (shouldn't be before login)
     */
    public void isDisplayedBtn() {
        if (btnContinue.isDisplayed())
            ExtentTestManager.getTest().log(Status.FAIL, "Button Continue hiển thị khi chưa login");
        if (btnComparePackages.isDisplayed())
            ExtentTestManager.getTest().log(Status.FAIL, "Button Compare hiển thị khi chưa login");
    }
}
