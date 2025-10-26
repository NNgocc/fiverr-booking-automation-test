#!/bin/bash

# Script tự động tạo repository mới cho React + .NET Core project

echo "========================================"
echo "Tạo Repository mới: React + .NET Core 8"
echo "========================================"
echo ""

# Nhập tên repository
read -p "Nhập tên repository mới (mặc định: react-dotnet-core-template): " REPO_NAME
REPO_NAME=${REPO_NAME:-react-dotnet-core-template}

# Nhập GitHub username
read -p "Nhập GitHub username của bạn: " GITHUB_USERNAME

if [ -z "$GITHUB_USERNAME" ]; then
    echo "❌ GitHub username không được để trống!"
    exit 1
fi

# Tạo thư mục mới
NEW_DIR=~/$REPO_NAME
echo ""
echo "📁 Tạo thư mục: $NEW_DIR"
mkdir -p "$NEW_DIR"

# Copy các file cần thiết
echo "📋 Copy files..."
cp -r Backend "$NEW_DIR/"
cp -r Frontend "$NEW_DIR/"
cp MyApp.sln "$NEW_DIR/"
cp README.md "$NEW_DIR/"

# Tạo .gitignore
echo "📝 Tạo .gitignore..."
cat > "$NEW_DIR/.gitignore" << 'EOF'
### .NET Core ###
Backend/bin/
Backend/obj/
Backend/publish/
*.user
*.suo
*.cache

### React/Node ###
Frontend/node_modules/
Frontend/dist/
Frontend/build/
Frontend/.vite/

### IDE ###
.vscode/
.idea/
*.swp
*.swo
*~

### OS ###
.DS_Store
Thumbs.db

### Logs ###
*.log
npm-debug.log*
yarn-debug.log*
yarn-error.log*
pnpm-debug.log*
lerna-debug.log*
EOF

# Khởi tạo git
cd "$NEW_DIR"
echo ""
echo "🔧 Khởi tạo Git repository..."
git init
git add -A
git commit -m "feat: Initial commit - React + .NET Core 8 fullstack template

- Backend: ASP.NET Core 8 Web API with Swagger
- Frontend: React 18 with Vite
- CORS configuration for FE-BE connection
- Visual Studio 2022 solution file
- Sample API endpoint and UI integration
- Comprehensive README documentation
"

# Hướng dẫn tiếp theo
echo ""
echo "✅ Repository mới đã được tạo tại: $NEW_DIR"
echo ""
echo "========================================"
echo "Các bước tiếp theo:"
echo "========================================"
echo ""
echo "1️⃣  Tạo repository mới trên GitHub:"
echo "   Truy cập: https://github.com/new"
echo "   Tên repository: $REPO_NAME"
echo "   ⚠️  KHÔNG chọn 'Initialize with README'"
echo ""
echo "2️⃣  Sau khi tạo xong, chạy các lệnh sau:"
echo ""
echo "   cd $NEW_DIR"
echo "   git remote add origin https://github.com/$GITHUB_USERNAME/$REPO_NAME.git"
echo "   git branch -M main"
echo "   git push -u origin main"
echo ""
echo "3️⃣  Hoặc nếu muốn dùng SSH:"
echo ""
echo "   cd $NEW_DIR"
echo "   git remote add origin git@github.com:$GITHUB_USERNAME/$REPO_NAME.git"
echo "   git branch -M main"
echo "   git push -u origin main"
echo ""
echo "========================================"
echo ""

# Tạo file quick-push.sh để dễ push
cat > "$NEW_DIR/quick-push.sh" << EOF
#!/bin/bash
git remote add origin https://github.com/$GITHUB_USERNAME/$REPO_NAME.git
git branch -M main
git push -u origin main
EOF

chmod +x "$NEW_DIR/quick-push.sh"

echo "💡 Mẹo: Sau khi tạo repo trên GitHub, bạn có thể chạy:"
echo "   cd $NEW_DIR && ./quick-push.sh"
echo ""
echo "🎉 Hoàn tất!"
