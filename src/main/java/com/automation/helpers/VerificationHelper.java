package com.automation.helpers;

import com.automation.utils.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class for post-action verification operations
 */
public class VerificationHelper {

    private final WebDriver driver;

    public VerificationHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Verify actions after clicking an element (URL change, toast, modal)
     * @param originalUrl The original URL before action
     * @return true if any verification passed
     */
    public boolean verifyAfterClickActions(String originalUrl) {
        boolean urlChanged = false;
        boolean toastAppeared = false;
        boolean modalAppeared = false;

        try {
            urlChanged = checkUrlChange(originalUrl);
            toastAppeared = checkToastAppearance();
            modalAppeared = checkModalAppearance();

            logVerificationResults(urlChanged, toastAppeared, modalAppeared);

            return urlChanged || toastAppeared || modalAppeared;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Lỗi khi verify sau khi click: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if URL has changed after action
     * @param originalUrl The original URL
     * @return true if URL changed
     */
    public boolean checkUrlChange(String originalUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            boolean urlChanged = wait.until(driver -> {
                String currentUrl = driver.getCurrentUrl();
                return !currentUrl.equals(originalUrl);
            });

            if (urlChanged) {
                String newUrl = driver.getCurrentUrl();
                ExtentTestManager.getTest().log(Status.PASS,
                        "URL đã thay đổi từ: " + originalUrl + " sang: " + newUrl);
                return true;
            }

        } catch (TimeoutException e) {
            ExtentTestManager.getTest().log(Status.INFO, "URL không thay đổi sau 5 giây");
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra URL: " + e.getMessage());
        }

        return false;
    }

    /**
     * Check if toast notification appeared
     * @return true if toast appeared
     */
    public boolean checkToastAppearance() {
        try {
            List<String> toastSelectors = Arrays.asList(
                    ".toast",
                    ".notification",
                    ".alert",
                    ".message",
                    "[data-toast]",
                    ".toast-container .toast",
                    ".swal2-container",
                    ".toastr"
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            for (String selector : toastSelectors) {
                try {
                    WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    if (toast.isDisplayed()) {
                        String toastMessage = toast.getText();
                        ExtentTestManager.getTest().log(Status.PASS,
                                "Toast xuất hiện với message: " + toastMessage);
                        return true;
                    }
                } catch (TimeoutException e) {
                    continue;
                }
            }

            ExtentTestManager.getTest().log(Status.INFO, "Không có toast nào xuất hiện");
            return false;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra toast: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if modal dialog appeared
     * @return true if modal appeared
     */
    public boolean checkModalAppearance() {
        try {
            List<String> modalSelectors = Arrays.asList(
                    ".modal",
                    ".popup",
                    ".dialog",
                    ".overlay",
                    "[data-modal]",
                    ".modal.show",
                    ".modal.fade.show",
                    ".ui-dialog",
                    ".fancybox-container"
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            for (String selector : modalSelectors) {
                try {
                    WebElement modal = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    if (modal.isDisplayed()) {
                        ExtentTestManager.getTest().log(Status.PASS,
                                "Modal xuất hiện với selector: " + selector);
                        checkModalContent(modal);
                        return true;
                    }
                } catch (TimeoutException e) {
                    continue;
                }
            }

            ExtentTestManager.getTest().log(Status.INFO, "Không có modal nào xuất hiện");
            return false;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra modal: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check and log modal content
     * @param modal The modal element
     */
    private void checkModalContent(WebElement modal) {
        try {
            WebElement modalTitle = modal.findElement(By.cssSelector(".modal-title, .dialog-title, h1, h2, h3"));
            if (modalTitle != null && modalTitle.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.INFO,
                        "Modal title: " + modalTitle.getText());
            }

            WebElement modalBody = modal.findElement(By.cssSelector(".modal-body, .dialog-content, .content"));
            if (modalBody != null && modalBody.isDisplayed()) {
                String bodyText = modalBody.getText();
                if (bodyText.length() > 100) {
                    bodyText = bodyText.substring(0, 100) + "...";
                }
                ExtentTestManager.getTest().log(Status.INFO,
                        "Modal content: " + bodyText);
            }

        } catch (Exception e) {
            // Ignore nếu không tìm thấy content
        }
    }

    /**
     * Log verification results
     * @param urlChanged URL change status
     * @param toastAppeared Toast appearance status
     * @param modalAppeared Modal appearance status
     */
    private void logVerificationResults(boolean urlChanged, boolean toastAppeared, boolean modalAppeared) {
        StringBuilder result = new StringBuilder("Kết quả kiểm tra sau khi click element: ");

        if (urlChanged) result.append("URL đã thay đổi ");
        if (toastAppeared) result.append("Toast xuất hiện ");
        if (modalAppeared) result.append("Modal xuất hiện ");

        if (!urlChanged && !toastAppeared && !modalAppeared) {
            result.append("Không có thay đổi nào được phát hiện");
            ExtentTestManager.getTest().log(Status.WARNING, result.toString());
        } else {
            ExtentTestManager.getTest().log(Status.PASS, result.toString());
        }
    }
}
