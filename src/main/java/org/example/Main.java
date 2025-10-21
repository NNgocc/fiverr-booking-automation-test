package org.example;

import com.automation.utils.LoggerUtil;
import org.testng.TestNG;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LoggerUtil.info("Starting Automation Test Application - PROGRAM MAIN");
        TestNG testng = new TestNG();
        testng.setTestSuites(Arrays.asList("src/test/resources/testng.xml"));
        testng.run();
    }
}