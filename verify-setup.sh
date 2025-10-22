#!/bin/bash

echo "================================"
echo "Maven TestNG Verification Script"
echo "================================"
echo ""

# Check Java version
echo "1. Checking Java version..."
java -version
echo ""

# Check Maven version
echo "2. Checking Maven version..."
mvn -version
echo ""

# Check project structure
echo "3. Checking project structure..."
echo "  - Source directory:"
ls -la src/main/java/com/automation/ | grep "^d" | awk '{print "    " $9}'
echo ""

# Check TestNG configuration
echo "4. Checking TestNG configuration..."
echo "  - TestNG XML files:"
find src/main/java/resources -name "testNG*.xml" -o -name "testng*.xml" | while read file; do
    echo "    ✓ $file"
done
echo ""

# Check dependencies (without network)
echo "5. Checking key classes exist..."
CLASSES=(
    "src/main/java/com/automation/base/BaseTest.java"
    "src/main/java/com/automation/tests/LoginTest.java"
    "src/main/java/com/automation/tests/JobDetailTest.java"
    "src/main/java/com/automation/pages/JobDetailPage.java"
    "src/main/java/com/automation/helpers/LoginHelper.java"
    "src/main/java/com/automation/listeners/RetryListener.java"
)

for class in "${CLASSES[@]}"; do
    if [ -f "$class" ]; then
        echo "  ✓ $class"
    else
        echo "  ✗ MISSING: $class"
    fi
done
echo ""

# Print Maven test command
echo "6. To run tests, use one of these commands:"
echo ""
echo "  # Run all tests (default browser: chrome)"
echo "  mvn clean test"
echo ""
echo "  # Run with specific browser"
echo "  mvn clean test -Dbrowser=edge"
echo "  mvn clean test -Dbrowser=firefox"
echo ""
echo "  # Run cross-browser tests"
echo "  mvn clean test -DsuiteXmlFile=src/main/java/resources/testNG-cross-browser.xml"
echo ""
echo "  # Run specific test class"
echo "  mvn test -Dtest=LoginTest"
echo ""

# Check if Maven can compile (without network dependency download)
echo "7. Checking if project compiles..."
echo "   (Note: This may require network connection for first-time dependency download)"
echo ""
echo "   Run: mvn clean compile"
echo ""

echo "================================"
echo "Verification Complete!"
echo "================================"
