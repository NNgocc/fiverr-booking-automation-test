#!/bin/bash

echo "================================================"
echo "Edge Browser Troubleshooting Script"
echo "================================================"
echo ""

# Check if Edge is installed
echo "1. Checking if Microsoft Edge is installed..."

if command -v microsoft-edge &> /dev/null; then
    echo "  ✓ Microsoft Edge found (Linux)"
    microsoft-edge --version
elif command -v msedge &> /dev/null; then
    echo "  ✓ Microsoft Edge found"
    msedge --version
elif [ -f "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge" ]; then
    echo "  ✓ Microsoft Edge found (macOS)"
    "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge" --version
elif [ -f "/c/Program Files (x86)/Microsoft/Edge/Application/msedge.exe" ] || [ -f "/c/Program Files/Microsoft/Edge/Application/msedge.exe" ]; then
    echo "  ✓ Microsoft Edge found (Windows)"
else
    echo "  ✗ Microsoft Edge NOT FOUND!"
    echo ""
    echo "  Please install Microsoft Edge from:"
    echo "  https://www.microsoft.com/edge"
    echo ""
    echo "  Installation instructions:"
    echo "  - Windows: Download and install from Microsoft website"
    echo "  - macOS: brew install --cask microsoft-edge"
    echo "  - Linux Ubuntu/Debian: "
    echo "    wget https://packages.microsoft.com/repos/edge/pool/main/m/microsoft-edge-stable/microsoft-edge-stable_xxx_amd64.deb"
    echo "    sudo dpkg -i microsoft-edge-stable_xxx_amd64.deb"
    echo ""
    exit 1
fi
echo ""

# Check WebDriverManager cache
echo "2. Checking WebDriverManager cache for Edge driver..."
if [ -d "$HOME/.cache/selenium" ]; then
    echo "  WebDriverManager cache directory exists"
    if find "$HOME/.cache/selenium" -name "*edge*" -o -name "*msedge*" 2>/dev/null | grep -q .; then
        echo "  ✓ Edge driver found in cache"
        find "$HOME/.cache/selenium" -name "*edge*" -o -name "*msedge*" 2>/dev/null
    else
        echo "  ⚠ No Edge driver in cache (will be downloaded on first run)"
    fi
else
    echo "  ⚠ WebDriverManager cache not found (will be created on first run)"
fi
echo ""

# Check Java version
echo "3. Checking Java version..."
java -version 2>&1 | head -1
echo ""

# Check Maven
echo "4. Checking Maven..."
mvn -version 2>&1 | head -1
echo ""

# Test Edge driver with Maven
echo "5. Testing Edge driver with Maven..."
echo "   Running: mvn test -Dbrowser=edge -Dtest=LoginTest"
echo ""
echo "   This will:"
echo "   - Download Edge driver if needed"
echo "   - Initialize Edge browser"
echo "   - Run LoginTest"
echo ""
read -p "   Do you want to run this test? (y/n) " -n 1 -r
echo ""

if [[ $REPLY =~ ^[Yy]$ ]]; then
    mvn test -Dbrowser=edge -Dtest=LoginTest
    TEST_RESULT=$?
    echo ""
    if [ $TEST_RESULT -eq 0 ]; then
        echo "  ✓ Edge test completed successfully!"
    else
        echo "  ✗ Edge test failed!"
        echo ""
        echo "  Common issues and solutions:"
        echo "  1. Edge not installed → Install from https://www.microsoft.com/edge"
        echo "  2. Edge version mismatch → Update Edge to latest version"
        echo "  3. Driver download failed → Check internet connection"
        echo "  4. Permission denied → Run with appropriate permissions"
        echo ""
    fi
else
    echo "  Skipped test"
fi
echo ""

# Provide manual test command
echo "6. Manual test commands:"
echo ""
echo "   # Test with Edge browser"
echo "   mvn test -Dbrowser=edge"
echo ""
echo "   # Test specific class"
echo "   mvn test -Dbrowser=edge -Dtest=LoginTest"
echo ""
echo "   # Clean and test"
echo "   mvn clean test -Dbrowser=edge"
echo ""
echo "   # Run in IDE (IntelliJ/Eclipse)"
echo "   1. Open testNG.xml"
echo "   2. Change browser parameter to 'edge'"
echo "   3. Right-click > Run"
echo ""

# Check logs for errors
echo "7. If tests fail, check these logs:"
echo "   - target/logs/automation.log"
echo "   - target/logs/error.log"
echo "   - Maven console output"
echo ""

echo "================================================"
echo "Troubleshooting Complete!"
echo "================================================"
