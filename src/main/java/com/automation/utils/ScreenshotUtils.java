package com.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destination = System.getProperty("user.dir") + "/target/screenshots/" + screenshotName + "_" + dateName + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            return destination;
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
            return null;
        }
    }

    public static String getScreenshotAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Converts an absolute screenshot path to a relative path for ExtentReport HTML.
     * ExtentReport is located at: target/ExtentReport/Test-Automation-Report.html
     * Screenshots are located at: target/screenshots/testName_timestamp.png
     * Relative path from report to screenshot: ../screenshots/testName_timestamp.png
     *
     * @param absolutePath The absolute path to the screenshot file
     * @return Relative path suitable for ExtentReport HTML, or null if input is null
     */
    public static String getRelativeScreenshotPath(String absolutePath) {
        if (absolutePath == null) {
            return null;
        }

        try {
            // Extract just the filename from absolute path
            File file = new File(absolutePath);
            String filename = file.getName();

            // Return relative path from ExtentReport directory to screenshots directory
            return "../screenshots/" + filename;
        } catch (Exception e) {
            System.err.println("Failed to convert to relative path: " + e.getMessage());
            return absolutePath; // Fallback to absolute path
        }
    }
}
