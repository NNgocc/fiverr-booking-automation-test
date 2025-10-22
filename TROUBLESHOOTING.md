# Hướng dẫn Fix Maven TestNG Issues

## Vấn đề phổ biến và cách khắc phục

### 1. Lỗi "Package org.testng does not exist"

**Nguyên nhân:** TestNG có `<scope>test</scope>` nhưng code test lại ở `src/main/java`

**Đã fix:** Remove `<scope>test</scope>` từ TestNG dependency trong pom.xml

**Verify:**
```xml
<!-- pom.xml line 35-39 -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <!-- NO scope=test here! -->
</dependency>
```

### 2. Lỗi "Cannot resolve symbol" cho Helper classes

**Nguyên nhân:** Helper classes chưa được compile hoặc imports thiếu

**Fix:**
```bash
# Clean và rebuild project
mvn clean install -DskipTests

# Hoặc trong IDE:
# IntelliJ IDEA: File > Invalidate Caches > Invalidate and Restart
# Eclipse: Project > Clean
```

### 3. TestNG không tìm thấy test classes

**Nguyên nhân:** testNG.xml configuration sai hoặc listener chưa được add

**Đã fix:** Update testNG.xml với:
- Add RetryListener
- Uncomment LoginTest
- Set parallel="false" (có thể change lại "true" nếu cần)

### 4. Lỗi compile khi chạy Maven

**Các bước troubleshoot:**

```bash
# Step 1: Clear Maven cache
rm -rf ~/.m2/repository

# Step 2: Clean project
mvn clean

# Step 3: Download dependencies
mvn dependency:resolve

# Step 4: Compile
mvn compile

# Step 5: Run tests
mvn test
```

### 5. Chạy tests bằng cách khác

#### A. Chạy từ IDE (Recommended cho development)

**IntelliJ IDEA:**
1. Right-click trên `testNG.xml`
2. Select "Run '...testNG.xml'"

**Eclipse:**
1. Right-click trên `testNG.xml`
2. Select "Run As" > "TestNG Suite"

#### B. Chạy từ Maven

```bash
# Default (sử dụng testNG.xml)
mvn test

# Với specific browser
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge

# Cross-browser testing
mvn test -DsuiteXmlFile=src/main/java/resources/testNG-cross-browser.xml

# Run specific test class
mvn test -Dtest=LoginTest
mvn test -Dtest=JobDetailTest

# Run specific test method
mvn test -Dtest=LoginTest#testAnimatedValidLogin
```

#### C. Chạy trực tiếp từ Java

```bash
# Compile first
mvn clean compile

# Run Main class (if you created one)
java -cp "target/classes:~/.m2/repository/*" org.example.Main
```

### 6. Kiểm tra cấu trúc project

Chạy verification script:
```bash
./verify-setup.sh
```

Hoặc kiểm tra manually:
```bash
# Check if all helper classes exist
ls -la src/main/java/com/automation/helpers/

# Check if test classes exist
ls -la src/main/java/com/automation/tests/

# Check TestNG configuration
cat src/main/java/resources/testNG.xml
```

### 7. Dependencies đã được fix

**Các changes trong pom.xml:**

1. ✅ TestNG: Remove `<scope>test</scope>`
2. ✅ Build plugins: Added Maven Compiler, Surefire
3. ✅ Retry mechanism: rerunFailingTestsCount=2
4. ✅ Code quality: Checkstyle + SpotBugs

### 8. Verify sau khi fix

```bash
# 1. Clean và compile
mvn clean compile

# 2. Nếu compile OK, run tests
mvn test

# 3. Check reports
ls -la target/ExtentReport/
ls -la target/logs/
ls -la target/screenshots/
```

### 9. Common Maven commands

```bash
# Clean project
mvn clean

# Compile only
mvn compile

# Run tests only
mvn test

# Compile + tests
mvn clean test

# Full build (compile + tests + package)
mvn clean install

# Skip tests
mvn clean install -DskipTests

# Run with debug
mvn test -X

# Check dependencies
mvn dependency:tree

# Update dependencies
mvn clean install -U
```

### 10. Nếu vẫn còn lỗi

**Bước 1:** Kiểm tra Java version
```bash
java -version
# Should be: Java 21
```

**Bước 2:** Kiểm tra Maven version
```bash
mvn -version
# Should be: Maven 3.6+
```

**Bước 3:** Re-import project trong IDE
- IntelliJ: File > Reload All from Disk
- Eclipse: Right-click project > Maven > Update Project

**Bước 4:** Rebuild từ đầu
```bash
mvn clean
rm -rf target/
mvn clean install -DskipTests
mvn test
```

**Bước 5:** Check network connection
- Đảm bảo có internet để download Maven dependencies
- Hoặc sử dụng Maven local repository

---

## Quick Commands Summary

```bash
# ===== Setup =====
mvn clean install -DskipTests    # First time setup

# ===== Run Tests =====
mvn test                         # Run all tests
mvn test -Dbrowser=chrome        # Chrome browser
mvn test -Dbrowser=edge          # Edge browser

# ===== Verify Setup =====
./verify-setup.sh                # Run verification script

# ===== Troubleshooting =====
mvn clean compile                # Check compilation
mvn dependency:tree              # Check dependencies
mvn test -X                      # Debug mode
```

---

## Cấu trúc project sau khi fix

```
fiverr-booking-automation-test/
├── pom.xml                      ✅ TestNG scope fixed
├── verify-setup.sh              ✅ NEW: Verification script
├── TROUBLESHOOTING.md           ✅ NEW: This guide
├── src/main/java/com/automation/
│   ├── helpers/                 ✅ NEW: Helper classes
│   │   ├── ElementHelper.java
│   │   ├── LoginHelper.java
│   │   ├── SearchHelper.java
│   │   └── VerificationHelper.java
│   ├── listeners/               ✅ RetryListener added
│   │   ├── ExtentTestListener.java
│   │   ├── RetryAnalyzer.java
│   │   └── RetryListener.java
│   ├── tests/                   ✅ Test classes
│   └── ...
└── src/main/java/resources/
    ├── testNG.xml               ✅ Updated with RetryListener
    └── testNG-cross-browser.xml ✅ NEW: Cross-browser config
```

---

Nếu vẫn gặp vấn đề, vui lòng share error message cụ thể để tôi có thể hỗ trợ tốt hơn!
