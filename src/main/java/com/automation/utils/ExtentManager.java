package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static String reportFileName = "Test-Automation-Report" + ".html";
    private static String reportFilePath = System.getProperty("user.dir") + "/target/ExtentReport/" + reportFileName;

    public static ExtentReports createInstance() {
        if (extent == null) {
            createInstance(reportFilePath);
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);

        // Cấu hình HTML reporter
        sparkReporter.config().setTheme(Theme.DARK); // DARK hoặc STANDARD
        sparkReporter.config().setReportName("Selenium Automation Test Results");
        sparkReporter.config().setDocumentTitle("Test Report");
        sparkReporter.config().setEncoding("utf-8");

        // Thêm CSS custom (optional)
        sparkReporter.config().setCss("body { font-family: Arial, sans-serif; }");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Thêm system info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        extent.setSystemInfo("Test Date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

        return extent;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
