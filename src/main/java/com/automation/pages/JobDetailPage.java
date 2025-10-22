package com.automation.pages;

import com.automation.utils.ExtentTestManager;
import com.automation.utils.MouseAnimationUtils;
import com.automation.utils.WaitUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class JobDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String searchKey = "app";
    String domain = "https://demo5.cybersoft.edu.vn/";

    public JobDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
    }

    /* Find element */
    @FindBy(xpath = "//li[contains(text(),'Become a Seller')]")
    private WebElement liBecomeSeller;

    @FindBy(xpath = "//li[contains(text(),'Fiverr Business')]")
    private WebElement liBecomeBusiness;

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

    @FindBy(xpath = "//img[@class='avatar']")
    private WebElement btnAvatar;

    @FindBy(className = "job-title")
    private WebElement jobTitle;

    @FindBy(className = "gigs_card_content")
    public List<WebElement> listBookings;

    /* Function */
    public boolean clickBtnBecomeBusiness() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            if (liBecomeBusiness.isDisplayed())
                ExtentTestManager.getTest().log(Status.FAIL, "Fiver Business hiển thị khi chưa login");
            else {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy element menu Fiver Business");
                return false;
            }
            searchCarousel();
            String currentUrl = driver.getCurrentUrl();
            if (liBecomeBusiness.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(liBecomeBusiness);
                MouseAnimationUtils.pause(50);
                liBecomeBusiness.click();

                return verifyAfterClickActions(currentUrl);
            } else {
                ExtentTestManager.getTest().log(Status.SKIP, "Không tìm thấy menu Fiver Business");
                return false;
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnBecomeBusiness failed: " + e.getMessage());
            return false;
        }
    }

    public boolean clickBtnBecomeSeller() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            if (liBecomeSeller.isDisplayed())
                ExtentTestManager.getTest().log(Status.FAIL, "Become a Seller hiển thị khi chưa login");
            else {
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy element menu Become a Seller");
                return false;
            }
            searchCarousel();
            String currentUrl = driver.getCurrentUrl();
            if (liBecomeSeller.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(liBecomeSeller);
                MouseAnimationUtils.pause(50);
                liBecomeSeller.click();

                return verifyAfterClickActions(currentUrl);
            } else {
                ExtentTestManager.getTest().log(Status.SKIP, "Không tìm thấy menu Become a seller");
                return false;
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnBecomeSeller failed: " + e.getMessage());
            return false;
        }
    }

    public boolean clickBtnCompare() {
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            searchCarousel();

            String currentUrl = driver.getCurrentUrl();
            if (btnComparePackages.isDisplayed()) {
                MouseAnimationUtils.animateMouseToElement(btnComparePackages);
                MouseAnimationUtils.pause(50);
                btnComparePackages.click();
                boolean actionResult = verifyAfterClickActions(currentUrl);
                ExtentTestManager.getTest().log(Status.INFO, "actionResult: " + actionResult);
                if (actionResult) {
                    ExtentTestManager.getTest().log(Status.PASS, "Có action Compare.");
                } else {
                    ExtentTestManager.getTest().log(Status.FAIL, "Không có action nào được cài đặt.");
                }
            }
            return true;
        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnCompare failed: " + ex.getMessage());
            return false;
        }
    }

    public boolean clickBtnContactMe() {
        try {
            searchCarousel();
            if (btnContactMe.isDisplayed()) {
                js.executeScript("window.scrollBy(0, 300);");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[normalize-space()='Contact Me']")));
            if (!btnContactMe.isDisplayed()) {
                return false;
            }
            MouseAnimationUtils.animateMouseToElement(btnContactMe);
            MouseAnimationUtils.pause(50);
            MouseAnimationUtils.animateAndClick(btnContactMe);
            return true;
        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, "clickBtnContactMe failed: " + ex.getMessage());
            return false;
        }
    }

    public boolean clickBtnContinue() {
        var jobName = "";
        try {
            WaitUtils.waitForPageLoad(driver, 10);
            searchCarousel();
            if (btnContinue.isDisplayed()) {
                if (btnContinue.isEnabled()) {
                    MouseAnimationUtils.animateAndClick(btnContinue);
                    jobName = jobTitle.getText();
                }
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toarstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='Toastify']")));
            ExtentTestManager.getTest().info("Toast success: " + toarstResult.getText());
            MouseAnimationUtils.animateMouseToElement(toarstResult);
            MouseAnimationUtils.pause(50);
            if (btnContinue.isEnabled()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Lỗi UI: Không disable sau khi booking thành công");
                return false;
            }
            MouseAnimationUtils.animateAndClick(btnAvatar);
            WaitUtils.waitForPageLoad(driver, 10);
            if (!listBookings.isEmpty()) {
                ExtentTestManager.getTest().log(Status.INFO, "List: " + listBookings.size());
                ExtentTestManager.getTest().log(Status.INFO, "List: " + listBookings);
                for (WebElement gigCard : listBookings) {
                    try {
                        // Dựa vào HTML, title nằm trong h1 tag
                        WebElement titleElement = gigCard.findElement(By.tagName("h1"));
                        String gigTitle = titleElement.getText().trim();

                        // So sánh tên (có thể dùng contains hoặc equals)
                        if (gigTitle.toLowerCase().contains(jobName.toLowerCase())) {
                            ExtentTestManager.getTest().log(Status.PASS,
                                    "Tìm thấy gig với tên: " + jobName);
                            return true;
                        }

                    } catch (NoSuchElementException e) {
                        // Thử tìm bằng cách khác nếu không có h1
                        String cardText = gigCard.getText();
                        if (cardText.toLowerCase().contains(jobName.toLowerCase())) {
                            ExtentTestManager.getTest().log(Status.PASS,
                                    "Tìm thấy gig chứa text: " + jobName);
                            return true;
                        }
                    }
                }

                ExtentTestManager.getTest().log(Status.FAIL,
                        "Không tìm thấy gig với tên: " + jobName);
                return false;
            }
            return true;
        } catch (Exception ex) {
            ExtentTestManager.getTest().log(Status.FAIL, ex.getMessage());
            return false;
        }
    }

    public boolean searchOnNavigation() {
        js.executeScript("window.scrollBy(0, 200);");
        if(!btnSearchNav.isDisplayed()){
            ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy input search trên navigation Home");
            return false;
        }
        if(!inputSearchNav.isDisplayed()){
            ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy button search trên navigation Home");
            return false;
        }
        String currentUrl = driver.getCurrentUrl();

        String key = getRandomKeyword();
        ExtentTestManager.getTest().log(Status.INFO, "search keyword: " + key);
        MouseAnimationUtils.animateMouseToElement(inputSearchNav);
        MouseAnimationUtils.animateTyping(inputSearchNav, key);
        MouseAnimationUtils.pause(50);
        MouseAnimationUtils.animateAndClick(btnSearchNav);
        WaitUtils.waitForPageLoad(driver, 10);
        return verifyAfterClickActions(currentUrl);
    }

    public void searchCarousel() {
        MouseAnimationUtils.animateMouseToElement(btnLogo);
        btnLogo.click();
        WaitUtils.waitForPageLoad(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(inputSearchCarousel));

        String key = getRandomKeyword();
        ExtentTestManager.getTest().log(Status.INFO, "search keyword: " + key);
        MouseAnimationUtils.animateMouseToElement(inputSearchCarousel);
        MouseAnimationUtils.animateTyping(inputSearchCarousel, key);
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnSubmit);
        btnSubmit.click();
        ExtentTestManager.getTest().log(Status.INFO, "Search carousel");
        WaitUtils.waitForPageLoad(driver, 10);

        MouseAnimationUtils.animateMouseToElement(btnCardDescription);
        btnCardDescription.click();
    }

    public boolean verifyFunctionSearchCarousel() {
        try{
            if(!inputSearchCarousel.isDisplayed()){
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy input search trên Carousel Home");
                return false;
            }
            if(!btnSubmit.isDisplayed()){
                ExtentTestManager.getTest().log(Status.FAIL, "Không tìm thấy button search trên Carousel Home");
                return false;
            }
            String currentUrl = driver.getCurrentUrl();
            if(currentUrl != null && currentUrl.toLowerCase().equals(domain.toLowerCase()))
            {
                MouseAnimationUtils.animateMouseToElement(btnLogo);
                btnLogo.click();
                WaitUtils.waitForPageLoad(driver, 10);
                wait.until(ExpectedConditions.visibilityOf(inputSearchCarousel));
            }

            String key = getRandomKeyword();
            ExtentTestManager.getTest().log(Status.INFO, "search keyword: " + key);
            MouseAnimationUtils.animateMouseToElement(inputSearchCarousel);
            MouseAnimationUtils.animateTyping(inputSearchCarousel, key);

            MouseAnimationUtils.animateMouseToElement(btnSubmit);
            btnSubmit.click();
            ExtentTestManager.getTest().log(Status.INFO, "Search carousel");
            WaitUtils.waitForPageLoad(driver, 10);

            return verifyAfterClickActions(currentUrl);
        }
        catch(Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "verifyFunctionSearchCarousel failed: " + e.getMessage());
            return false;
        }
    }

    public void isDisplayedBtn() {
        if (btnContinue.isDisplayed())
            ExtentTestManager.getTest().log(Status.FAIL, "Button Continue hiển thị khi chưa login");
        if (btnComparePackages.isDisplayed())
            ExtentTestManager.getTest().log(Status.FAIL, "Button Compare hiển thị khi chưa login");
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

    private boolean verifyAfterClickActions(String originalUrl) {
        boolean urlChanged = false;
        boolean toastAppeared = false;
        boolean modalAppeared = false;

        try {
            urlChanged = checkUrlChange(originalUrl);

            toastAppeared = checkToastAppearance();

            modalAppeared = checkModalAppearance();

            logVerificationResults(urlChanged, toastAppeared, modalAppeared);

            return urlChanged || toastAppeared || modalAppeared;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Lỗi khi verify sau khi click: " + e.getMessage());
            return false;
        }
    }

    private boolean checkUrlChange(String originalUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            boolean urlChanged = wait.until(driver -> {
                String currentUrl = driver.getCurrentUrl();
                return !currentUrl.equals(originalUrl);
            });

            if (urlChanged) {
                String newUrl = driver.getCurrentUrl();
                ExtentTestManager.getTest().log(Status.PASS,
                        "URL đã thay đổi từ: " + originalUrl + " sang: " + newUrl);
                return true;
            }

        } catch (TimeoutException e) {
            ExtentTestManager.getTest().log(Status.INFO, "URL không thay đổi sau 5 giây");
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra URL: " + e.getMessage());
        }

        return false;
    }

    private boolean checkToastAppearance() {
        try {
            List<String> toastSelectors = Arrays.asList(
                    ".toast",
                    ".notification",
                    ".alert",
                    ".message",
                    "[data-toast]",
                    ".toast-container .toast",
                    ".swal2-container",
                    ".toastr"
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            for (String selector : toastSelectors) {
                try {
                    WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    if (toast.isDisplayed()) {
                        String toastMessage = toast.getText();
                        ExtentTestManager.getTest().log(Status.PASS,
                                "Toast xuất hiện với message: " + toastMessage);
                        return true;
                    }
                } catch (TimeoutException e) {
                    continue;
                }
            }

            ExtentTestManager.getTest().log(Status.INFO, "Không có toast nào xuất hiện");
            return false;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra toast: " + e.getMessage());
            return false;
        }
    }

    private boolean checkModalAppearance() {
        try {
            List<String> modalSelectors = Arrays.asList(
                    ".modal",
                    ".popup",
                    ".dialog",
                    ".overlay",
                    "[data-modal]",
                    ".modal.show",
                    ".modal.fade.show",
                    ".ui-dialog",
                    ".fancybox-container"
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            for (String selector : modalSelectors) {
                try {
                    WebElement modal = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    if (modal.isDisplayed()) {
                        ExtentTestManager.getTest().log(Status.PASS,
                                "Modal xuất hiện với selector: " + selector);
                        checkModalContent(modal);
                        return true;
                    }
                } catch (TimeoutException e) {
                    continue;
                }
            }

            ExtentTestManager.getTest().log(Status.INFO, "Không có modal nào xuất hiện");
            return false;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Lỗi khi kiểm tra modal: " + e.getMessage());
            return false;
        }
    }

    private void checkModalContent(WebElement modal) {
        try {
            WebElement modalTitle = modal.findElement(By.cssSelector(".modal-title, .dialog-title, h1, h2, h3"));
            if (modalTitle != null && modalTitle.isDisplayed()) {
                ExtentTestManager.getTest().log(Status.INFO,
                        "Modal title: " + modalTitle.getText());
            }

            WebElement modalBody = modal.findElement(By.cssSelector(".modal-body, .dialog-content, .content"));
            if (modalBody != null && modalBody.isDisplayed()) {
                String bodyText = modalBody.getText();
                if (bodyText.length() > 100) {
                    bodyText = bodyText.substring(0, 100) + "...";
                }
                ExtentTestManager.getTest().log(Status.INFO,
                        "Modal content: " + bodyText);
            }

        } catch (Exception e) {
            // Ignore nếu không tìm thấy content
        }
    }

    private void logVerificationResults(boolean urlChanged, boolean toastAppeared, boolean modalAppeared) {
        StringBuilder result = new StringBuilder("Kết quả kiểm tra sau khi click element: ");

        if (urlChanged) result.append("URL đã thay đổi ");
        if (toastAppeared) result.append("Toast xuất hiện ");
        if (modalAppeared) result.append("Modal xuất hiện ");

        if (!urlChanged && !toastAppeared && !modalAppeared) {
            result.append("Không có thay đổi nào được phát hiện");
            ExtentTestManager.getTest().log(Status.WARNING, result.toString());
        } else {
            ExtentTestManager.getTest().log(Status.PASS, result.toString());
        }
    }

    //random keyword
    private static Random random = new Random();
    public static String getRandomKeyword() {
        int randomIndex = random.nextInt(SEARCH_KEYWORDS.length);
        return SEARCH_KEYWORDS[randomIndex];
    }
    private static final String[] SEARCH_KEYWORDS = {
            // Web Development
            "website", "web design", "frontend", "backend", "fullstack",
            "react", "angular", "vue", "nodejs", "javascript",
            "html", "css", "bootstrap", "responsive design",

            // Mobile Development
            "mobile app", "android", "ios", "flutter", "react native",
            "app development", "mobile ui", "app design",

            // Design & Graphics
            "logo design", "graphic design", "ui design", "ux design",
            "photoshop", "illustrator", "figma", "sketch",
            "banner", "brochure", "business card", "flyer",

            // Digital Marketing
            "seo", "social media", "facebook ads", "google ads",
            "content marketing", "email marketing", "digital marketing",
            "instagram marketing", "youtube marketing",

            // Business Services
            "data entry", "virtual assistant", "customer service",
            "translation", "copywriting", "proofreading",
            "market research", "business plan", "consulting",

            // Technology
            "automation", "api integration", "database design",
            "cloud services", "aws", "azure", "devops",
            "machine learning", "ai", "chatbot",

            // Creative Services
            "video editing", "animation", "voice over",
            "music production", "podcast editing", "presentation design",
            "infographic", "illustration", "3d modeling"
    };
}
