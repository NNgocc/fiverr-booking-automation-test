package com.automation.untils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
            timeOutInSeconds = 30;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}