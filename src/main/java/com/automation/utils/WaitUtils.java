package com.automation.utils;

import com.automation.constants.TestConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    /**
     * Chờ cho đến khi trang được tải hoàn toàn.
     * @param driver WebDriver instance
     * @param timeOutInSeconds Thời gian chờ tối đa tính bằng giây
     */
    public static void waitForPageLoad(WebDriver driver, int timeOutInSeconds) {
        if(timeOutInSeconds == 0)
            timeOutInSeconds = TestConstants.LONG_WAIT;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Chờ cho đến khi trang được tải hoàn toàn với default timeout.
     * @param driver WebDriver instance
     */
    public static void waitForPageLoad(WebDriver driver) {
        waitForPageLoad(driver, TestConstants.LONG_WAIT);
    }

    /**
     * Wait for element to be visible
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutInSeconds timeout in seconds
     */
    public static void waitForElementVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible with default timeout
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     */
    public static void waitForElementVisible(WebDriver driver, WebElement element) {
        waitForElementVisible(driver, element, TestConstants.DEFAULT_EXPLICIT_WAIT);
    }

    /**
     * Wait for element to be clickable
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutInSeconds timeout in seconds
     */
    public static void waitForElementClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable with default timeout
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     */
    public static void waitForElementClickable(WebDriver driver, WebElement element) {
        waitForElementClickable(driver, element, TestConstants.DEFAULT_EXPLICIT_WAIT);
    }

    /**
     * Smart wait - waits a specified time in milliseconds
     * Use this sparingly, prefer explicit waits
     * @param milliseconds time to wait
     */
    public static void hardWait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.warn("Wait interrupted: " + e.getMessage());
        }
    }

    /**
     * Wait for a short period using constant
     */
    public static void waitShort() {
        hardWait(TestConstants.WAIT_AFTER_TEST_ACTION_MS);
    }

    /**
     * Scroll to element and wait for it to be visible and clickable
     * This is the recommended method for scroll + find pattern
     *
     * @param driver WebDriver instance
     * @param element WebElement to scroll to
     */
    public static void scrollToElementAndWait(WebDriver driver, WebElement element) {
        scrollToElementAndWait(driver, element, TestConstants.DEFAULT_EXPLICIT_WAIT);
    }

    /**
     * Scroll to element and wait for it to be visible and clickable with custom timeout
     *
     * @param driver WebDriver instance
     * @param element WebElement to scroll to
     * @param timeoutInSeconds timeout in seconds
     */
    public static void scrollToElementAndWait(WebDriver driver, WebElement element, int timeoutInSeconds) {
        try {
            // Step 1: Scroll element into view (center of viewport for better visibility)
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", element);

            // Step 2: Small delay to allow smooth scroll animation to complete
            hardWait(300);

            // Step 3: Wait for element to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));

            // Step 4: Wait for element to be clickable (not obscured by other elements)
            wait.until(ExpectedConditions.elementToBeClickable(element));

            LoggerUtil.info("Successfully scrolled to element and confirmed it's visible and clickable");
        } catch (Exception e) {
            LoggerUtil.error("Failed to scroll to element: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Scroll to element using JavaScript without waiting
     * Use this when you need instant scroll without verification
     *
     * @param driver WebDriver instance
     * @param element WebElement to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            // Small delay to allow scroll to complete
            hardWait(200);
        } catch (Exception e) {
            LoggerUtil.warn("Failed to scroll to element: " + e.getMessage());
        }
    }

    /**
     * Scroll page by fixed offset and wait for page to stabilize
     * Use scrollToElementAndWait() instead when you have a WebElement reference
     *
     * @param driver WebDriver instance
     * @param pixelOffset Number of pixels to scroll (positive = down, negative = up)
     */
    public static void scrollByOffset(WebDriver driver, int pixelOffset) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, arguments[0]);", pixelOffset);
            // Wait for page to stabilize after scroll
            hardWait(300);
            LoggerUtil.info("Scrolled by " + pixelOffset + " pixels");
        } catch (Exception e) {
            LoggerUtil.warn("Failed to scroll by offset: " + e.getMessage());
        }
    }
}