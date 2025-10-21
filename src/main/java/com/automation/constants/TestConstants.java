package com.automation.constants;

/**
 * Constants class to centralize all magic numbers and hardcoded values
 */
public class TestConstants {

    // Timeout constants (in seconds)
    public static final int DEFAULT_EXPLICIT_WAIT = 10;
    public static final int LONG_WAIT = 30;
    public static final int SHORT_WAIT = 5;
    public static final int ELEMENT_VISIBILITY_WAIT = 15;

    // Mouse animation constants
    public static final int MAX_Z_INDEX = 2147483647;
    public static final int MIN_TYPING_DELAY_MS = 50;
    public static final int MAX_TYPING_DELAY_MS = 100;
    public static final int ANIMATION_DURATION_MS = 1000;
    public static final int CLICK_ANIMATION_DURATION_MS = 800;
    public static final int TYPING_ANIMATION_DURATION_MS = 600;
    public static final int PAUSE_BEFORE_CLICK_MS = 500;
    public static final int PAUSE_AFTER_ACTION_MS = 300;
    public static final int HIGHLIGHT_DURATION_MS = 1500;
    public static final int CLICK_EFFECT_DURATION_MS = 600;

    // Scroll constants
    public static final int SCROLL_OFFSET = 300;
    public static final int SCROLL_OFFSET_SMALL = 200;

    // Animation step constants
    public static final int ANIMATION_STEP_MS = 50;

    // Wait after test action (milliseconds)
    public static final int WAIT_AFTER_TEST_ACTION_MS = 3000;

    // Z-index offset for overlays
    public static final int OVERLAY_Z_INDEX_OFFSET = 2;

    private TestConstants() {
        // Private constructor to prevent instantiation
        throw new AssertionError("TestConstants class cannot be instantiated");
    }
}
