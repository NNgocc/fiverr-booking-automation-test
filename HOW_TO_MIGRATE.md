# Di chuyển Project sang Repository mới - Hướng dẫn nhanh

## Tên Repository đề xuất: `react-dotnet-core-template`

## Cách 1: Sử dụng Script tự động (Khuyến nghị)

### Bước 1: Chạy script
```bash
cd /home/user/fiverr-booking-automation-test
./create-new-repo.sh
```

Script sẽ:
- Hỏi tên repository (mặc định: `react-dotnet-core-template`)
- Hỏi GitHub username của bạn
- Tạo thư mục mới và copy code
- Khởi tạo Git repository
- Tạo commit đầu tiên
- Hướng dẫn các bước tiếp theo

### Bước 2: Tạo repository trên GitHub
1. Truy cập https://github.com/new
2. Nhập tên repository (ví dụ: `react-dotnet-core-template`)
3. ⚠️ **KHÔNG** chọn "Initialize this repository with a README"
4. Click "Create repository"

### Bước 3: Push code lên
```bash
cd ~/react-dotnet-core-template
./quick-push.sh
```

Hoặc chạy thủ công:
```bash
cd ~/react-dotnet-core-template
git remote add origin https://github.com/YOUR_USERNAME/react-dotnet-core-template.git
git branch -M main
git push -u origin main
```

---

## Cách 2: Thủ công (Nhanh hơn)

Nếu bạn muốn làm thủ công:

### Bước 1: Tạo repository trên GitHub
1. Truy cập https://github.com/new
2. Tên: `react-dotnet-core-template`
3. ⚠️ **KHÔNG** chọn "Initialize with README"
4. Click "Create repository"

### Bước 2: Tạo thư mục mới và copy files
```bash
# Tạo thư mục mới
mkdir ~/react-dotnet-core-template
cd ~/react-dotnet-core-template

# Copy các file cần thiết
cp -r /home/user/fiverr-booking-automation-test/Backend ./
cp -r /home/user/fiverr-booking-automation-test/Frontend ./
cp /home/user/fiverr-booking-automation-test/MyApp.sln ./
cp /home/user/fiverr-booking-automation-test/README.md ./

# Tạo .gitignore
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

### OS ###
.DS_Store

### Logs ###
*.log
npm-debug.log*
yarn-debug.log*
EOF
```

### Bước 3: Khởi tạo Git và push
```bash
git init
git add -A
git commit -m "feat: Initial commit - React + .NET Core 8 fullstack template"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/react-dotnet-core-template.git
git push -u origin main
```

---

## Sau khi push thành công

### Thêm Topics vào repository
Vào repository trên GitHub > Settings > Topics, thêm:
- `react`
- `dotnet-core`
- `dotnet8`
- `aspnet-core`
- `vite`
- `fullstack`
- `web-api`

### Clone và test
```bash
cd ~
git clone https://github.com/YOUR_USERNAME/react-dotnet-core-template.git
cd react-dotnet-core-template

# Test Backend (cần .NET 8 SDK)
cd Backend
dotnet run

# Test Frontend (trong terminal khác)
cd Frontend
npm install
npm run dev
```

---

## Tên repository khác có thể dùng

- `fullstack-react-netcore8`
- `react-aspnet-boilerplate`
- `react-dotnet-starter`
- `myapp-fullstack`

**Khuyến nghị: `react-dotnet-core-template`** - Mô tả rõ ràng và professional!

---

## Cần trợ giúp?

Xem hướng dẫn chi tiết tại: `MIGRATION_GUIDE.md`
