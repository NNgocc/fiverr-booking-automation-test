# Fiverr Booking Automation Test

Automated testing framework for Fiverr Clone Platform using Selenium WebDriver and TestNG.

## 🚀 Features

- **Page Object Model (POM)** design pattern
- **Mouse Animation** for realistic test execution
- **ExtentReports** for comprehensive HTML reporting
- **Log4j2** for detailed logging
- **Cross-browser testing** (Chrome, Firefox, Edge)
- **Retry mechanism** for flaky tests
- **Code quality checks** with Checkstyle and SpotBugs
- **CI/CD pipeline** with GitHub Actions

## 📋 Prerequisites

- Java 21
- Maven 3.6+
- Chrome/Firefox/Edge browsers

## 🛠️ Installation

```bash
# Clone the repository
git clone https://github.com/NNgocc/fiverr-booking-automation-test.git
cd fiverr-booking-automation-test

# Install dependencies
mvn clean install
```

## ▶️ Running Tests

### Run all tests
```bash
mvn clean test
```

### Run with specific browser
```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run cross-browser tests
```bash
mvn clean test -DsuiteXmlFile=src/main/java/resources/testNG-cross-browser.xml
```

### Run code quality checks
```bash
# Checkstyle
mvn checkstyle:check

# SpotBugs
mvn spotbugs:check
```

## 📁 Project Structure

```
fiverr-booking-automation-test/
├── src/main/java/com/automation/
│   ├── base/           # Base test classes
│   ├── config/         # Configuration files
│   ├── constants/      # Test constants
│   ├── helpers/        # Helper classes
│   │   ├── ElementHelper.java
│   │   ├── LoginHelper.java
│   │   ├── SearchHelper.java
│   │   └── VerificationHelper.java
│   ├── listeners/      # TestNG listeners
│   │   ├── ExtentTestListener.java
│   │   ├── RetryAnalyzer.java
│   │   └── RetryListener.java
│   ├── pages/          # Page Objects
│   │   ├── JobDetailPage.java
│   │   └── LoginPage.java
│   ├── tests/          # Test cases
│   │   ├── JobDetailTest.java
│   │   └── LoginTest.java
│   └── utils/          # Utility classes
│       ├── DriverManager.java
│       ├── ExtentManager.java
│       ├── LoggerUtil.java
│       ├── MouseAnimationUtils.java
│       ├── ScreenshotUtils.java
│       └── WaitUtils.java
├── .github/workflows/  # CI/CD workflows
├── checkstyle.xml      # Checkstyle configuration
├── pom.xml             # Maven configuration
└── README.md
```

## 📊 Test Reports

After running tests, reports are generated in:
- **ExtentReports**: `target/ExtentReport/Test-Automation-Report.html`
- **Logs**: `target/logs/`
- **Screenshots**: `target/screenshots/`

## 🔧 Configuration

Configuration file: `src/main/java/com/automation/config/config/config.properties`

```properties
browser=edge
headless=false
implicit.wait=10
base.url=https://demo5.cybersoft.edu.vn/
```

## 🧪 Test Cases

### Login Tests (TC)
- **testAnimatedValidLogin**: Test login with valid credentials

### Job Detail Tests
- **TC412**: Verify "Become a Seller" functionality
- **TC409**: Verify "Become Business" functionality
- **TC413**: Verify search on navigation
- **TC414**: Verify search carousel
- **TC415**: Verify "Contact Me" button
- **TC418**: Verify "Continue" button (package selection)
- **TC421**: Verify "Compare Packages" functionality

## 🔄 Retry Mechanism

Tests automatically retry up to 2 times on failure using `RetryAnalyzer`.

## 🌐 CI/CD Pipeline

GitHub Actions workflow runs on every push/PR:
1. Code quality checks (Checkstyle, SpotBugs)
2. Cross-browser testing (Chrome, Firefox, Edge)
3. Build and artifact generation
4. Test reports upload

## 📝 Best Practices

- ✅ Use Page Object Model
- ✅ Use helper classes for reusable logic
- ✅ Use explicit waits instead of Thread.sleep()
- ✅ Use constants for magic numbers
- ✅ Proper exception handling with logging
- ✅ Independent tests (no test dependencies)

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- **NNgocc** - Initial work

## 🙏 Acknowledgments

- Selenium WebDriver
- TestNG
- ExtentReports
- Log4j2
- Maven
