package com.automation.pages;

import com.automation.untils.MouseAnimationUtils;
import com.automation.untils.WaitUtils;
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

    @FindBy(xpath = "(//a[normalize-space()='Sign in'])[2]")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@href='/profile']")
    private WebElement avatarButton;

    @FindBy(css = ".oxd-alert-content-text")
    private WebElement errorMessage;

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
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to click login button: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void login(String username, String password) {
        if(signInButton.isDisplayed()){
            System.out.println("Button sign in is display");
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
        }
        else if(avatarButton.isDisplayed()){
            System.out.println("User avatar is display");
            avatarButton.click();
        }
        else
        {
            System.out.println("Button sign in is not displayed");
        }
    }

    public void loginWithAnimation(String username, String password) {
        System.out.println("🎭 Starting animated login process...");
        WaitUtils.waitForPageLoad(driver, 10);
        try {
            if(signInButton.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(signInButton);
                Thread.sleep(2000);
                signInButton.click();
                wait.until(ExpectedConditions.visibilityOf(usernameField));

                System.out.println("🎯 Moving to username field...");
                MouseAnimationUtils.animateTyping(usernameField, username);
                MouseAnimationUtils.pause(500);

                System.out.println("🎯 Moving to password field...");
                MouseAnimationUtils.animateTyping(passwordField, password);
                MouseAnimationUtils.pause(500);

                System.out.println("🎯 Moving to login button...");
                MouseAnimationUtils.animateAndClick(loginButton);
            }
            else{
                System.out.println("Đã login");
            }

        } catch (Exception e) {
            System.out.println(e);
            //throw e;
        }
    }

    public boolean verifyButtonSignIn(){
        return signInButton.isDisplayed();
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

    public void cleanup() {
        MouseAnimationUtils.cleanup();
    }
    public void animatedEnterUsername(String username) {
        MouseAnimationUtils.animateTyping(usernameField, username);
    }

    public void animatedEnterPassword(String password) {
        MouseAnimationUtils.animateTyping(passwordField, password);
    }

    public void animatedClickLogin() {
        MouseAnimationUtils.animateAndClick(loginButton);
    }
    public void animatedHighlightError() {
        if (isErrorMessageDisplayed()) {
            MouseAnimationUtils.animateMouseToElement(errorMessage, 1000, true);
        }
    }
    // ========== REGULAR METHODS (unchanged) ==========

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }

    public boolean isLoginSuccessful() {
        try {
            Thread.sleep(2000);
            return driver.getCurrentUrl().contains("dashboard");
        } catch (Exception e) {
            return false;
        }
    }
}
