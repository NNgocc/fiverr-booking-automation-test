# Hướng dẫn di chuyển Project sang Repository mới

## Tên Repository đề xuất

Dựa trên nội dung project, tôi đề xuất một trong các tên sau:

1. **react-dotnet-core-template** (Khuyến nghị) - Tên mô tả rõ stack công nghệ
2. **fullstack-react-netcore8** - Nhấn mạnh fullstack và .NET Core 8
3. **react-aspnet-boilerplate** - Nếu dùng làm template cho các project khác
4. **myapp-fullstack** - Giữ tên đơn giản
5. **react-dotnet-starter** - Nếu dùng làm starter project

## Cách 1: Tạo Repository mới trên GitHub Web

### Bước 1: Tạo Repository trên GitHub

1. Truy cập https://github.com/new
2. Nhập tên repository (ví dụ: `react-dotnet-core-template`)
3. Chọn **Public** hoặc **Private**
4. **KHÔNG** chọn "Initialize this repository with a README"
5. Click **Create repository**

### Bước 2: Di chuyển code sang Repository mới

Sau khi tạo repository, GitHub sẽ hiển thị hướng dẫn. Bạn chạy các lệnh sau:

```bash
# Di chuyển vào thư mục project
cd /home/user/fiverr-booking-automation-test

# Thay đổi remote origin sang repository mới
# Thay <YOUR_USERNAME> và <NEW_REPO_NAME> bằng thông tin của bạn
git remote set-url origin https://github.com/<YOUR_USERNAME>/<NEW_REPO_NAME>.git

# Hoặc nếu muốn giữ remote cũ và thêm remote mới:
git remote add new-origin https://github.com/<YOUR_USERNAME>/<NEW_REPO_NAME>.git

# Đổi tên branch chính thành main (nếu cần)
git branch -M main

# Push tất cả code lên repository mới
git push -u origin main

# Hoặc nếu dùng new-origin:
git push -u new-origin main
```

### Bước 3: Dọn dẹp (Tùy chọn)

Nếu bạn muốn xóa các file không liên quan đến React + .NET Core:

```bash
# Xóa các file Maven (Java)
rm -f pom.xml
rm -rf src/  # src cũ của Maven project

# Commit và push
git add -A
git commit -m "chore: Remove old Maven project files"
git push
```

## Cách 2: Clone riêng phần React + .NET Core vào Repository mới

Nếu bạn muốn tách hoàn toàn khỏi repository cũ:

```bash
# Tạo thư mục mới
mkdir ~/react-dotnet-core-template
cd ~/react-dotnet-core-template

# Copy các file cần thiết
cp -r /home/user/fiverr-booking-automation-test/Backend ./
cp -r /home/user/fiverr-booking-automation-test/Frontend ./
cp /home/user/fiverr-booking-automation-test/MyApp.sln ./
cp /home/user/fiverr-booking-automation-test/README.md ./

# Tạo .gitignore mới
cat > .gitignore << 'EOF'
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
EOF

# Khởi tạo git mới
git init
git add -A
git commit -m "feat: Initial commit - React + .NET Core 8 fullstack template"

# Liên kết với repository mới trên GitHub
# Thay <YOUR_USERNAME> và <NEW_REPO_NAME> bằng thông tin của bạn
git remote add origin https://github.com/<YOUR_USERNAME>/<NEW_REPO_NAME>.git
git branch -M main
git push -u origin main
```

## Cách 3: Sử dụng GitHub CLI (Nếu có cài đặt)

```bash
# Di chuyển vào thư mục project
cd /home/user/fiverr-booking-automation-test

# Tạo repository mới trực tiếp từ CLI
gh repo create react-dotnet-core-template --public --source=. --remote=new-origin

# Push code lên
git push -u new-origin main
```

## Sau khi di chuyển

### 1. Cập nhật README

Cập nhật thông tin trong README.md nếu cần, đặc biệt là:
- Tên project
- Repository URL
- Clone instructions

### 2. Thêm Topics vào Repository

Trên GitHub, vào **Settings** > **Topics** và thêm:
- `react`
- `dotnet-core`
- `dotnet8`
- `aspnet-core`
- `vite`
- `fullstack`
- `web-api`
- `template`

### 3. Tạo Release đầu tiên (Tùy chọn)

```bash
git tag -a v1.0.0 -m "Initial release - React + .NET Core 8 template"
git push origin v1.0.0
```

## Kiểm tra sau khi di chuyển

1. Clone repository mới về máy khác để test
2. Kiểm tra README hiển thị đúng
3. Test build Backend và Frontend
4. Kiểm tra .gitignore hoạt động đúng

## Tên Repository cuối cùng

Tôi khuyên dùng: **react-dotnet-core-template**

Lý do:
- Mô tả rõ stack công nghệ (React + .NET Core)
- Dễ tìm kiếm trên GitHub
- Phù hợp nếu muốn chia sẻ làm template cho người khác
- Professional và dễ nhớ
