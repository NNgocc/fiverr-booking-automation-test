package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
    private static final Logger testResultsLogger = LogManager.getLogger("TestResults");

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void startTest(String testName) {
        logger.info("========== STARTING TEST: {} ==========", testName);
        testResultsLogger.info("TEST STARTED: {}", testName);
    }

    public static void endTest(String testName, String result) {
        logger.info("========== ENDING TEST: {} - {} ==========", testName, result);
        testResultsLogger.info("TEST FINISHED: {} - RESULT: {}", testName, result);
    }

    public static void step(String stepDescription) {
        logger.info("STEP: {}", stepDescription);
    }

    public static void verification(String verification, boolean result) {
        if (result) {
            logger.info("✓ VERIFICATION PASSED: {}", verification);
        } else {
            logger.error("✗ VERIFICATION FAILED: {}", verification);
        }
    }
}
