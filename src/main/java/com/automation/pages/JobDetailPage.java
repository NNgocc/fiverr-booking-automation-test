package com.automation.pages;

import com.automation.untils.DriverManager;
import com.automation.untils.ExtentTestManager;
import com.automation.untils.MouseAnimationUtils;
import com.automation.untils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v134.network.Network;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class JobDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public JobDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /* Find element */
    @FindBy(xpath = "//li[contains(text(),'Become a Seller')]")
    private WebElement liBecomeSeller;

    @FindBy(xpath = "//div[@class='check-out mobile mt-5']//a[@class='compare'][normalize-space()='Compare Packages']")
    private WebElement btnComparePackages;

    @FindBy(xpath = "//button[@type='button'][contains(normalize-space(),'Continue')]")
    private WebElement btnContinue;

    @FindBy(xpath = "//button[normalize-space()='Contact Me']")
    private WebElement btnContactMe;

    @FindBy(name = "searchInputCarousel")
    private WebElement inputSearchCarousel;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;

    @FindBy(xpath = "//a[@class='logo active']")
    private WebElement btnLogo;

    @FindBy(css = "a[href='/jobDetail/2']")
    private WebElement btnCardDescription;

    @FindBy(xpath = "//input[@placeholder='Find Services']")
    private WebElement inputSearchNav;

    @FindBy(xpath = "//button[@class='btn']")
    private WebElement btnSearchNav;

    /* Function */
    public boolean clickBtnBecomeSeller() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            searchCarousel();
            if (liBecomeSeller.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(liBecomeSeller);
                if (checkElementClickable(liBecomeSeller) == false) {
                    return false;
                }

                liBecomeSeller.click();

                DevTools devTools = ((ChromeDriver) driver).getDevTools();
                devTools.createSession();
                devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

                List<String> networkRequests = new ArrayList<>();

                devTools.addListener(Network.requestWillBeSent(), request -> {
                    networkRequests.add(request.getRequest().getUrl());
                });

                driver.findElement(By.linkText("Become a Seller")).click();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                boolean hasSellerRequest = networkRequests.stream()
                        .anyMatch(url -> url.contains("seller") || url.contains("register"));

                Assert.assertTrue(hasSellerRequest, "Should have network request related to seller");
                return true;
            } else {
                ExtentTestManager.getTest().log(Status.SKIP, "Không tìm thấy menu Become a seller");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickBtnCompare() {
        try {
            searchCarousel();
            if (btnComparePackages.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(btnComparePackages);
                if (checkElementClickable(btnComparePackages) == false) {
                    //ExtentTestManager.getTest().log(Status.INFO, "Become a Seller doesnt have event");
                    return false;
                }
            }
            btnComparePackages.click();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean clickBtnContactMe() {
        try {
            searchCarousel();
            if (btnContactMe.isDisplayed()) {
                js.executeScript("window.scrollBy(0, 500);");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[normalize-space()='Contact Me']")));
            if (btnContactMe.isDisplayed()) {
                return false;
            }
            MouseAnimationUtils.animateMouseToElement(btnContactMe);
            MouseAnimationUtils.pause(50);
            MouseAnimationUtils.animateAndClick(btnContactMe);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean clickBtnContinue() {
        try {
            searchCarousel();
            if (btnContinue.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(btnContinue);
                MouseAnimationUtils.pause(50);
                if (btnContinue.isEnabled()) {
                    MouseAnimationUtils.animateAndClick(btnContinue);
                }
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toarstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='Toastify']")));
            MouseAnimationUtils.animateMouseToElement(toarstResult);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean searchOnNavigation() {
        js.executeScript("window.scrollBy(0, 200);");

        if (!btnSearchNav.isDisplayed() || !inputSearchNav.isDisplayed()) {
            return false;
        }

        MouseAnimationUtils.animateMouseToElement(inputSearchNav);
        MouseAnimationUtils.animateTyping(inputSearchNav, "website");
        MouseAnimationUtils.pause(50);
        MouseAnimationUtils.animateAndClick(btnSearchNav);
        WaitUtils.waitForPageLoad(driver, 10);
        return true;
    }

    public void searchCarousel() {
        MouseAnimationUtils.animateMouseToElement(btnLogo);
        btnLogo.click();
        WaitUtils.waitForPageLoad(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(inputSearchCarousel));

        MouseAnimationUtils.animateMouseToElement(inputSearchCarousel);
        MouseAnimationUtils.animateTyping(inputSearchCarousel, "logo");
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnSubmit);
        btnSubmit.click();
        ExtentTestManager.getTest().log(Status.INFO, "Search carousel");
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnCardDescription);
        btnCardDescription.click();
    }

    /* Helper */
    private WebElement findElementByText(String labelName) {
        List<By> locators = Arrays.asList(
                // Tìm trong link text
                By.linkText(labelName),
                By.partialLinkText(labelName),

                // Tìm trong button
                By.xpath("//button[normalize-space(text())='" + labelName + "']"),
                By.xpath("//button[contains(normalize-space(text()), '" + labelName + "')]"),

                // Tìm trong list item
                By.xpath("//li[normalize-space(text())='" + labelName + "']"),
                By.xpath("//li[contains(normalize-space(text()), '" + labelName + "')]"),

                // Tìm trong div, span
                By.xpath("//*[normalize-space(text())='" + labelName + "']"),

                // Tìm theo attribute
                By.xpath("//*[@title='" + labelName + "' or @aria-label='" + labelName + "']")
        );

        for (By locator : locators) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    System.out.println("✓ Found element using: " + locator);
                    return element;
                }
            } catch (NoSuchElementException e) {
                // Continue to next locator
            }
        }

        return null;
    }

    private boolean checkElementClickable(WebElement element) {
        try {
            // Check 1: Inherently clickable tags
            String tagName = element.getTagName().toLowerCase();
            if ("a".equals(tagName) || "button".equals(tagName)) {
                System.out.println("✓ Inherently clickable tag: " + tagName);
                return true;
            }

            // Check 2: Input buttons
            if ("input".equals(tagName)) {
                String type = element.getAttribute("type");
                if ("button".equals(type) || "submit".equals(type)) {
                    System.out.println("✓ Clickable input type: " + type);
                    return true;
                }
            }

            // Check 3: Has click attributes
            String[] clickAttrs = {"onclick", "ng-click", "@click", "v-on:click"};
            for (String attr : clickAttrs) {
                String value = element.getAttribute(attr);
                if (value != null && !value.trim().isEmpty()) {
                    System.out.println("✓ Found click attribute: " + attr + "=" + value);
                    return true;
                }
            }

            // Check 4: CSS cursor pointer
            String cursor = element.getCssValue("cursor");
            if ("pointer".equals(cursor)) {
                System.out.println("✓ Has cursor: pointer");
                return true;
            }

            // Check 5: Role button
            String role = element.getAttribute("role");
            if ("button".equals(role) || "link".equals(role)) {
                System.out.println("✓ Has clickable role: " + role);
                return true;
            }

            // Check 6: Parent is clickable (như li > a)
            WebElement parent = element.findElement(By.xpath(".."));
            if (parent != null && checkParentClickable(parent)) {
                System.out.println("✓ Parent element is clickable");
                return true;
            }

            return false;

        } catch (Exception e) {
            System.out.println("❌ Error checking clickable: " + e.getMessage());
            return false;
        }
    }

    private boolean checkParentClickable(WebElement parent) {
        String tagName = parent.getTagName().toLowerCase();
        return "a".equals(tagName) ||
                "button".equals(tagName) ||
                parent.getAttribute("onclick") != null ||
                "pointer".equals(parent.getCssValue("cursor"));
    }

    public boolean isElementClickable(String labelName) {
        WebElement element = findElementByText(labelName);
        if (element == null) {
            System.out.println("❌ Element not found: " + labelName);
            return false;
        }

        return checkElementClickable(element);
    }
}
