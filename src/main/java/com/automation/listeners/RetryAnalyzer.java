package com.automation.listeners;

import com.automation.utils.LoggerUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer for flaky tests
 * Automatically retries failed tests up to a maximum count
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            LoggerUtil.warn(String.format(
                    "Retrying test '%s' for the %d time(s).",
                    result.getName(),
                    retryCount
            ));
            return true;
        }
        return false;
    }

    /**
     * Get max retry count
     * @return max retry count
     */
    public static int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }
}
