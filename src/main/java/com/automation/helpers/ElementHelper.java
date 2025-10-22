package com.automation.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for element finding and validation operations
 */
public class ElementHelper {

    private final WebDriver driver;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Find element by text using multiple locator strategies
     * @param labelName The text to search for
     * @return WebElement if found, null otherwise
     */
    public WebElement findElementByText(String labelName) {
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

    /**
     * Check if element is clickable based on various attributes
     * @param element The element to check
     * @return true if clickable, false otherwise
     */
    public boolean checkElementClickable(WebElement element) {
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

    /**
     * Check if parent element is clickable
     * @param parent The parent element to check
     * @return true if parent is clickable
     */
    public boolean checkParentClickable(WebElement parent) {
        String tagName = parent.getTagName().toLowerCase();
        return "a".equals(tagName) ||
                "button".equals(tagName) ||
                parent.getAttribute("onclick") != null ||
                "pointer".equals(parent.getCssValue("cursor"));
    }

    /**
     * Check if element with given label is clickable
     * @param labelName The label text to find
     * @return true if element is found and clickable
     */
    public boolean isElementClickable(String labelName) {
        WebElement element = findElementByText(labelName);
        if (element == null) {
            System.out.println("❌ Element not found: " + labelName);
            return false;
        }

        return checkElementClickable(element);
    }
}
