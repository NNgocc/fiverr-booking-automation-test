package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    @FindBy(name = "email")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[normalize-space()='Register now ?']")
    private WebElement registerButton;

    public void enterUsername(String username) {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            wait.until(ExpectedConditions.elementToBeClickable(usernameField));
            usernameField.clear();
            usernameField.sendKeys(username);
            System.out.println("Entered username: " + username);
        } catch (Exception e) {
            System.err.println("Failed to enter username: " + e.getMessage());
            throw e;
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Entered password");
        } catch (Exception e) {
            System.err.println("Failed to enter password: " + e.getMessage());
            throw e;
        }
    }

    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
            System.out.println("Clicked login button");
            // Wait a moment for page to process
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to click login button: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void login(String username, String password) {
        System.out.println("Performing login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void clearUsername() {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        System.out.println("Cleared username field");
    }

    public void clearPassword() {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        System.out.println("Cleared password field");
    }
}
