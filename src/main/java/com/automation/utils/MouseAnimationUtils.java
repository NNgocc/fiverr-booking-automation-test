package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class MouseAnimationUtils {

    private static WebDriver driver;
    private static Actions actions;
    private static JavascriptExecutor js;

    /**
     * Initialize MouseAnimationUtils với WebDriver
     */
    public static void initialize(WebDriver webDriver) {
        driver = webDriver;
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

        injectMouseCursor();
    }

    /**
     * Animate mouse movement tới element
     */
    public static void animateMouseToElement(WebElement element) {
        animateMouseToElement(element, 1000, true);
    }

    /**
     * Animate mouse movement tới element với options
     */
    public static void animateMouseToElement(WebElement element, int duration, boolean showPath) {
        try {
            System.out.println("🎯 Animating mouse to element: " + getElementDescription(element));

            // Get target position
            Point targetPos = getElementCenter(element);
            System.out.println("🎯 Target position: " + targetPos.x + ", " + targetPos.y);

            // Move mouse với animation
            if (showPath) {
                animateWithPath(targetPos, duration);
            } else {
                animateDirectly(element);
            }

            // Highlight target element
            highlightElement(element);

        } catch (Exception e) {
            System.err.println("Mouse animation failed: " + e.getMessage());
        }
    }

    /**
     * Animate và click element
     */
    public static void animateAndClick(WebElement element) {
        try {
            animateMouseToElement(element, 800, true);
            Thread.sleep(500); // Pause before click

            // Visual click effect
            showClickEffect(element);

            // Actual click
            element.click();
            System.out.println("🖱️ Clicked element after animation");

        } catch (Exception e) {
            System.err.println("Animated click failed: " + e.getMessage());
            // Fallback to normal click
            element.click();
        }
    }

    /**
     * Animate typing với visual effect
     */
    public static void animateTyping(WebElement element, String text) {
        try {
            animateMouseToElement(element, 600, true);
            Thread.sleep(300);

            element.click(); // Focus element
            element.clear();
            Thread.sleep(200);

            // Type với animation
            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                Thread.sleep(50 + (int)(Math.random() * 100)); // Random typing speed
            }

            System.out.println("⌨️ Animated typing completed: " + text);

        } catch (Exception e) {
            System.err.println("❌ Animated typing failed: " + e.getMessage());
            // Fallback to normal typing
            element.clear();
            element.sendKeys(text);
        }
    }

    /**
     * Animate mouse movement với path
     */
    private static void animateWithPath(Point targetPos, int duration) {
        try {
            int steps = duration / 50; // 50ms per step

            for (int i = 0; i <= steps; i++) {
                double progress = (double) i / steps;

                // Simple linear movement
                int currentX = (int)(targetPos.x * progress);
                int currentY = (int)(targetPos.y * progress);

                // Move visual cursor
                moveVisualCursor(currentX, currentY);
                Thread.sleep(50);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Animate directly to element
     */
    private static void animateDirectly(WebElement element) {
        try {
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            System.err.println("❌ Direct animation failed: " + e.getMessage());
        }
    }

    /**
     * Get element center position
     */
    private static Point getElementCenter(WebElement element) {
        try {
            Point location = element.getLocation();
            Dimension size = element.getSize();

            return new Point(
                    location.x + size.width / 2,
                    location.y + size.height / 2
            );
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    /**
     * Inject mouse cursor CSS vào page
     */
    private static void injectMouseCursor() {
        try {
            String cursorScript =
                    // Remove existing cursor if any
                    "var existingCursor = document.getElementById('automation-cursor');" +
                            "if (existingCursor) existingCursor.remove();" +

                            // Create CSS styles with MAXIMUM z-index
                            "var style = document.createElement('style');" +
                            "style.innerHTML = `" +
                            "  #automation-cursor {" +
                            "    position: fixed !important;" +
                            "    width: 28px !important;" +
                            "    height: 28px !important;" +
                            "    background: radial-gradient(circle, #ff0000 0%, #cc0000 50%, #990000 100%) !important;" +
                            "    border: 4px solid #ffffff !important;" +
                            "    border-radius: 50% !important;" +
                            "    box-shadow: 0 0 20px rgba(255, 0, 0, 1), 0 0 40px rgba(255, 0, 0, 0.6) !important;" +
                            "    pointer-events: none !important;" +
                            "    z-index: 2147483647 !important;" + // ← MAXIMUM z-index value
                            "    transition: all 0.2s ease-out !important;" +
                            "    transform: translate(-50%, -50%) !important;" +
                            "    display: block !important;" +
                            "    visibility: visible !important;" +
                            "    opacity: 1 !important;" +
                            "  }" +
                            "  #automation-cursor::before {" +
                            "    content: '' !important;" +
                            "    position: absolute !important;" +
                            "    top: -10px !important;" +
                            "    left: -10px !important;" +
                            "    right: -10px !important;" +
                            "    bottom: -10px !important;" +
                            "    border: 3px solid rgba(255, 255, 255, 0.8) !important;" +
                            "    border-radius: 50% !important;" +
                            "    animation: pulse 2s infinite !important;" +
                            "    z-index: 2147483646 !important;" +
                            "  }" +
                            "  #automation-cursor::after {" +
                            "    content: '🎯' !important;" +
                            "    position: absolute !important;" +
                            "    top: 50% !important;" +
                            "    left: 50% !important;" +
                            "    transform: translate(-50%, -50%) !important;" +
                            "    font-size: 12px !important;" +
                            "    z-index: 2147483648 !important;" +
                            "  }" +
                            "  @keyframes pulse {" +
                            "    0% { transform: scale(1); opacity: 1; }" +
                            "    100% { transform: scale(1.8); opacity: 0; }" +
                            "  }" +
                            "  .automation-highlight {" +
                            "    outline: 4px solid #ff0000 !important;" +
                            "    outline-offset: 3px !important;" +
                            "    background-color: rgba(255, 0, 0, 0.15) !important;" +
                            "    box-shadow: 0 0 15px rgba(255, 0, 0, 0.5) !important;" +
                            "    z-index: 2147483640 !important;" +
                            "    position: relative !important;" +
                            "  }" +
                            "`;" +
                            "document.head.appendChild(style);" +

                            // Create cursor element with maximum z-index
                            "var cursor = document.createElement('div');" +
                            "cursor.id = 'automation-cursor';" +
                            "cursor.style.cssText = `" +
                            "  position: fixed !important;" +
                            "  z-index: 2147483647 !important;" +
                            "  pointer-events: none !important;" +
                            "  display: none !important;" +
                            "`;" +
                            "document.body.appendChild(cursor);" +

                            // Force cursor to front
                            "setTimeout(() => {" +
                            "  cursor.style.display = 'block';" +
                            "  cursor.style.zIndex = '2147483647';" +
                            "  console.log('Red cursor injected with max z-index!');" +
                            "}, 100);" +

                            "console.log('Animation cursor CSS injected');";

            js.executeScript(cursorScript);

            forceOverrideZIndex();

        } catch (Exception e) {
            System.err.println("Failed to inject cursor: " + e.getMessage());
        }
    }
    private static void forceOverrideZIndex() {
        try {
            String forceScript =
                    // Find and modify high z-index elements
                    "var highZElements = Array.from(document.querySelectorAll('*')).filter(el => {" +
                            "  var zIndex = window.getComputedStyle(el).zIndex;" +
                            "  return zIndex !== 'auto' && parseInt(zIndex) > 1000000;" +
                            "});" +

                            "highZElements.forEach(el => {" +
                            "  var originalZ = el.style.zIndex;" +
                            "  el.setAttribute('data-original-z', originalZ);" +
                            "  el.style.zIndex = '2147483645';" + // Lower than cursor
                            "});" +

                            "console.log('📉 Lowered z-index for ' + highZElements.length + ' high-z elements');";

            js.executeScript(forceScript);

        } catch (Exception e) {
            // Ignore z-index override errors
        }
    }
    /**
     * Move visual cursor tới position
     */
    private static void moveVisualCursor(int x, int y) {
        try {
            String script =
                    "var cursor = document.getElementById('automation-cursor');" +
                            "if (cursor) {" +
                            "  cursor.style.left = '" + x + "px';" +
                            "  cursor.style.top = '" + y + "px';" +
                            "}";

            js.executeScript(script);
        } catch (Exception e) {
            // Ignore cursor movement errors
        }
    }

    /**
     * Highlight element khi mouse hover
     */
    private static void highlightElement(WebElement element) {
        try {
            // Force element to be visible and highlighted
            String highlightScript =
                    "var el = arguments[0];" +
                            "var rect = el.getBoundingClientRect();" +

                            // Create highlight overlay
                            "var overlay = document.createElement('div');" +
                            "overlay.id = 'element-highlight-overlay';" +
                            "overlay.style.cssText = `" +
                            "  position: fixed !important;" +
                            "  left: ${rect.left - 5}px !important;" +
                            "  top: ${rect.top - 5}px !important;" +
                            "  width: ${rect.width + 10}px !important;" +
                            "  height: ${rect.height + 10}px !important;" +
                            "  border: 4px solid #ff0000 !important;" +
                            "  background: rgba(255, 0, 0, 0.1) !important;" +
                            "  z-index: 2147483646 !important;" +
                            "  pointer-events: none !important;" +
                            "  animation: highlightPulse 0.8s ease-out !important;" +
                            "`;" +

                            "document.body.appendChild(overlay);" +

                            // Remove after animation
                            "setTimeout(() => overlay.remove(), 1500);";

            js.executeScript(highlightScript, element);
            Thread.sleep(1000);

        } catch (Exception e) {
            System.err.println("⚠️ Highlight failed: " + e.getMessage());
        }
    }

    /**
     * Show click effect
     */
    private static void showClickEffect(WebElement element) {
        try {
            Point center = getElementCenter(element);

            String clickEffect =
                    "var effect = document.createElement('div');" +
                            "effect.style.position = 'fixed';" +
                            "effect.style.left = '" + center.x + "px';" +
                            "effect.style.top = '" + center.y + "px';" +
                            "effect.style.width = '0px';" +
                            "effect.style.height = '0px';" +
                            "effect.style.border = '2px solid #ff4444';" +
                            "effect.style.borderRadius = '50%';" +
                            "effect.style.pointerEvents = 'none';" +
                            "effect.style.zIndex = '10001';" +
                            "document.body.appendChild(effect);" +

                            // Remove effect after animation
                            "setTimeout(function() { effect.remove(); }, 600);";

            js.executeScript(clickEffect);
        } catch (Exception e) {
            // Ignore click effect errors
        }
    }

    /**
     * Get element description for logging
     */
    private static String getElementDescription(WebElement element) {
        try {
            String tag = element.getTagName();
            String id = element.getDomAttribute("id");
            String className = element.getDomAttribute("class");
            String text = element.getText();

            StringBuilder desc = new StringBuilder(tag);
            if (id != null && !id.isEmpty()) desc.append("#").append(id);
            if (className != null && !className.isEmpty()) desc.append(".").append(className.split(" ")[0]);
            if (!text.isEmpty() && text.length() < 20) desc.append(" '").append(text).append("'");

            return desc.toString();
        } catch (Exception e) {
            return "unknown element";
        }
    }

    /**
     * Remove mouse cursor when done
     */
    public static void cleanup() {
        try {
            if (js != null) {
                js.executeScript(
                        "var cursor = document.getElementById('automation-cursor');" +
                                "if (cursor) cursor.remove();"
                );
            }
            System.out.println("🧹 Mouse animation cleanup completed");
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    /**
     * Pause animation (for demo purposes)
     */
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
