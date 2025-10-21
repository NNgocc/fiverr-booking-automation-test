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
}