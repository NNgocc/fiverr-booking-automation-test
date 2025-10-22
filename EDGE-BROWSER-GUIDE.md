# Edge Browser Configuration Guide

## Vấn đề không run được trên Edge browser

### 🔍 Nguyên nhân phổ biến

1. **Edge browser chưa được cài đặt**
2. **Edge driver version không tương thích với Edge browser**
3. **WebDriverManager không download được Edge driver**
4. **Thiếu permissions hoặc Edge options không đúng**

---

## ✅ Giải pháp đã được cải thiện

### 1. Cải thiện Edge Options trong DriverManager.java

**Đã thêm các options quan trọng:**
```java
EdgeOptions edgeOptions = new EdgeOptions();
edgeOptions.addArguments("--start-maximized");
edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
edgeOptions.addArguments("--disable-extensions");
edgeOptions.addArguments("--no-sandbox");
edgeOptions.addArguments("--disable-dev-shm-usage");
edgeOptions.addArguments("--disable-gpu");
edgeOptions.addArguments("--remote-allow-origins=*");

// Disable automation detection
edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
edgeOptions.setExperimentalOption("useAutomationExtension", false);
```

**Lợi ích:**
- ✅ Tắt automation detection
- ✅ Tương thích với các hệ thống khác nhau
- ✅ Tránh các popup và notifications
- ✅ Chạy stable hơn

### 2. Thêm Error Handling và Logging

```java
try {
    System.out.println("Setting up Microsoft Edge driver...");
    WebDriverManager.edgedriver().setup();

    System.out.println("Creating Edge driver instance...");
    driver.set(new EdgeDriver(edgeOptions));
    System.out.println("Edge driver initialized successfully!");
} catch (Exception e) {
    System.err.println("Failed to initialize Edge driver: " + e.getMessage());
    System.err.println("Please ensure Microsoft Edge browser is installed.");
    throw new RuntimeException("Edge driver initialization failed...", e);
}
```

**Lợi ích:**
- ✅ Thông báo lỗi rõ ràng
- ✅ Logging từng bước
- ✅ Hướng dẫn fix ngay trong error message

---

## 📋 Hướng dẫn cài đặt Edge Browser

### Windows
1. Download từ: https://www.microsoft.com/edge
2. Chạy installer và follow hướng dẫn
3. Verify installation: Mở Edge browser

### macOS
```bash
# Using Homebrew
brew install --cask microsoft-edge

# Verify
open -a "Microsoft Edge"
```

### Linux (Ubuntu/Debian)
```bash
# Add Microsoft GPG key
curl https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > microsoft.gpg
sudo install -o root -g root -m 644 microsoft.gpg /etc/apt/trusted.gpg.d/

# Add repository
sudo sh -c 'echo "deb [arch=amd64] https://packages.microsoft.com/repos/edge stable main" > /etc/apt/sources.list.d/microsoft-edge.list'

# Install
sudo apt update
sudo apt install microsoft-edge-stable

# Verify
microsoft-edge --version
```

### Linux (Fedora/RHEL)
```bash
# Add repository
sudo dnf config-manager --add-repo https://packages.microsoft.com/yumrepos/edge

# Install
sudo dnf install microsoft-edge-stable

# Verify
microsoft-edge --version
```

---

## 🧪 Test Edge Browser

### Option 1: Using test script (Recommended)
```bash
./test-edge-browser.sh
```

این script sẽ:
- Check xem Edge đã được cài chưa
- Verify WebDriverManager cache
- Offer để run test demo
- Cung cấp troubleshooting tips

### Option 2: Manual Maven test
```bash
# Clean và test với Edge
mvn clean test -Dbrowser=edge

# Test specific class
mvn test -Dbrowser=edge -Dtest=LoginTest

# Verbose mode để see errors
mvn test -Dbrowser=edge -X
```

### Option 3: Run trong IDE

**IntelliJ IDEA:**
1. Mở `src/main/java/resources/testNG.xml`
2. Change parameter:
   ```xml
   <parameter name="browser" value="edge"/>
   ```
3. Right-click > Run 'testNG.xml'

**Eclipse:**
1. Mở `testNG.xml`
2. Change browser parameter to `edge`
3. Right-click > Run As > TestNG Suite

---

## 🔧 Troubleshooting Common Issues

### Issue 1: "Edge driver not found" hoặc "SessionNotCreatedException"

**Nguyên nhân:** Edge driver version không match với Edge browser version

**Solution 1 - Update Edge browser:**
```bash
# Windows: Mở Edge > Settings > About Microsoft Edge > Update
# macOS:
brew upgrade --cask microsoft-edge

# Linux:
sudo apt update && sudo apt upgrade microsoft-edge-stable
```

**Solution 2 - Clear WebDriverManager cache:**
```bash
# Clear cache và download lại
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/org/seleniumhq/selenium

# Run test again
mvn clean test -Dbrowser=edge
```

### Issue 2: "Cannot start Edge browser"

**Nguyên nhân:** Edge chưa được cài hoặc path không đúng

**Solution:**
```bash
# Check if Edge is installed
# Windows:
where msedge

# macOS:
which microsoft-edge

# Linux:
which microsoft-edge

# If not found, install Edge (see installation guide above)
```

### Issue 3: "org.openqa.selenium.WebDriverException: unknown error: cannot find Edge binary"

**Nguyên nhân:** Edge binary path không đúng

**Solution - Specify Edge binary path:**

Create file: `src/main/java/com/automation/config/edge-config.properties`
```properties
edge.binary.path=/path/to/edge
```

Update DriverManager.java:
```java
case "edge":
    System.setProperty("webdriver.edge.driver", "/path/to/msedgedriver");
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setBinary("/path/to/msedge"); // Specify binary
    driver.set(new EdgeDriver(edgeOptions));
    break;
```

**Common Edge binary paths:**
- Windows: `C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe`
- macOS: `/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge`
- Linux: `/usr/bin/microsoft-edge`

### Issue 4: "Permission denied" errors

**Solution:**
```bash
# Give execute permission to Edge
sudo chmod +x /usr/bin/microsoft-edge

# Or for macOS
sudo chmod +x "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge"
```

### Issue 5: Edge opens but crashes immediately

**Nguyên nhân:** Missing dependencies hoặc conflicting options

**Solution 1 - Check dependencies (Linux):**
```bash
# Install required libraries
sudo apt-get install -y libgbm1 libasound2 libatk-bridge2.0-0 libgtk-3-0 libxss1
```

**Solution 2 - Simplify Edge options:**

Temporarily remove some options để test:
```java
EdgeOptions edgeOptions = new EdgeOptions();
edgeOptions.addArguments("--start-maximized");
edgeOptions.addArguments("--no-sandbox");
// Remove other options temporarily
```

### Issue 6: Tests pass với Chrome nhưng fail với Edge

**Nguyên nhân:** Edge có behaviors khác Chrome một chút

**Solution:**
1. Check if elements loaded properly
2. Increase wait times cho Edge
3. Use explicit waits thay vì implicit waits
4. Check console logs trong Edge DevTools

---

## 📊 Edge vs Chrome vs Firefox

| Feature | Chrome | Edge | Firefox |
|---------|--------|------|---------|
| Speed | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐ |
| Stability | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| WebDriver Support | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Setup Ease | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

**Recommendation:**
- Use **Chrome** cho development (fastest & most stable)
- Test trên **Edge** cho cross-browser compatibility
- Include **Firefox** trong CI/CD pipeline

---

## 🚀 Best Practices cho Edge Testing

### 1. Use WebDriverManager (Already configured)
```java
WebDriverManager.edgedriver().setup(); // Auto downloads correct version
```

### 2. Set proper timeouts
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
```

### 3. Use explicit waits
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.elementToBeClickable(element));
```

### 4. Handle Edge-specific issues
```java
// Edge sometimes needs extra time to load
Thread.sleep(500); // Only if really needed
// Better: Use WaitUtils.waitForPageLoad(driver)
```

### 5. Run tests parallel with caution
```xml
<!-- testNG.xml -->
<suite parallel="tests" thread-count="2"> <!-- Lower for Edge -->
```

---

## 📝 Quick Commands Reference

```bash
# ===== Check Edge Installation =====
microsoft-edge --version           # Linux
msedge --version                   # Windows
"/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge" --version  # macOS

# ===== Run Tests =====
mvn test -Dbrowser=edge            # Run all tests
mvn test -Dbrowser=edge -Dtest=LoginTest  # Specific test

# ===== Troubleshoot =====
./test-edge-browser.sh             # Run diagnostics
mvn test -Dbrowser=edge -X         # Verbose mode

# ===== Clear Cache =====
rm -rf ~/.cache/selenium           # Clear WebDriverManager cache
mvn clean                          # Clean Maven build

# ===== Update Edge =====
# Windows: Edge > Settings > About
brew upgrade --cask microsoft-edge # macOS
sudo apt upgrade microsoft-edge-stable  # Linux
```

---

## 🆘 Still Having Issues?

If Edge still doesn't work after trying all above:

1. **Check Edge version:**
   ```bash
   microsoft-edge --version
   # Should be: 100+
   ```

2. **Check WebDriverManager version:**
   ```xml
   <!-- pom.xml -->
   <dependency>
       <groupId>io.github.bonigarcia</groupId>
       <artifactId>webdrivermanager</artifactId>
       <version>6.1.0</version> <!-- Should be latest -->
   </dependency>
   ```

3. **Check Selenium version:**
   ```xml
   <dependency>
       <groupId>org.seleniumhq.selenium</groupId>
       <artifactId>selenium-java</artifactId>
       <version>4.32.0</version> <!-- Should be 4.x+ -->
   </dependency>
   ```

4. **Provide these details for support:**
   - Edge version: `microsoft-edge --version`
   - OS version: `uname -a` (Linux/Mac) or `ver` (Windows)
   - Java version: `java -version`
   - Maven version: `mvn -version`
   - Full error message from console

---

## ✅ Verification Checklist

After fixes, verify:

- [ ] Edge browser installed and updated
- [ ] Can run: `mvn test -Dbrowser=edge`
- [ ] Tests pass with Edge
- [ ] No "SessionNotCreatedException" errors
- [ ] Screenshots generated properly
- [ ] ExtentReports show Edge tests

---

**Need more help?** Share the output of:
```bash
./test-edge-browser.sh
```

And the error message you're seeing.
