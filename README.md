# MyApp - React + .NET Core 8 Application

Project fullstack với React (Frontend) và .NET Core 8 (Backend) được xây dựng trên Visual Studio 2022.

## Cấu trúc Project

```
MyApp/
├── Backend/                 # .NET Core 8 Web API
│   ├── Controllers/        # API Controllers
│   ├── Models/            # Data Models
│   ├── Properties/        # Launch Settings
│   ├── appsettings.json   # Configuration
│   └── Program.cs         # Entry Point
│
├── Frontend/              # React Application
│   ├── src/              # Source Code
│   ├── public/           # Static Assets
│   └── package.json      # NPM Dependencies
│
└── MyApp.sln             # Visual Studio Solution
```

## Yêu cầu hệ thống

- **Visual Studio 2022** (hoặc cao hơn)
- **.NET 8.0 SDK**
- **Node.js** (v18 hoặc cao hơn)
- **npm** hoặc **yarn**

## Cài đặt

### Backend (.NET Core 8)

1. Mở file `MyApp.sln` trong Visual Studio 2022
2. Restore NuGet packages (tự động khi mở solution)
3. Build project Backend

### Frontend (React)

1. Mở Terminal/Command Prompt
2. Di chuyển vào thư mục Frontend:
   ```bash
   cd Frontend
   ```
3. Cài đặt dependencies:
   ```bash
   npm install
   ```

## Chạy ứng dụng

### Cách 1: Chạy từ Visual Studio 2022

1. **Chạy Backend:**
   - Mở `MyApp.sln` trong Visual Studio
   - Chọn Backend project làm StartUp Project
   - Nhấn F5 hoặc click "Start Debugging"
   - Backend sẽ chạy tại: `http://localhost:5000` hoặc `https://localhost:5001`
   - Swagger UI: `https://localhost:5001/swagger`

2. **Chạy Frontend:**
   - Mở Terminal trong Visual Studio (View > Terminal)
   - Di chuyển vào thư mục Frontend:
     ```bash
     cd Frontend
     ```
   - Chạy development server:
     ```bash
     npm run dev
     ```
   - Frontend sẽ chạy tại: `http://localhost:5173`

### Cách 2: Chạy từ Command Line

1. **Backend:**
   ```bash
   cd Backend
   dotnet run
   ```

2. **Frontend (trong terminal khác):**
   ```bash
   cd Frontend
   npm run dev
   ```

## Kiểm tra kết nối

1. Mở trình duyệt và truy cập `http://localhost:5173`
2. Bạn sẽ thấy giao diện React với bảng Weather Forecast
3. Dữ liệu được lấy từ Backend API: `http://localhost:5000/api/WeatherForecast`

## API Endpoints

Backend cung cấp các API endpoints sau:

- **GET** `/api/WeatherForecast` - Lấy danh sách dự báo thời tiết (demo)
- **Swagger UI:** `https://localhost:5001/swagger` - Xem tất cả API endpoints

## Cấu hình CORS

Backend đã được cấu hình CORS để cho phép kết nối từ Frontend:
- Cho phép origins: `http://localhost:3000`, `http://localhost:5173`
- File cấu hình: `Backend/Program.cs:10-18`

## Build cho Production

### Backend
```bash
cd Backend
dotnet publish -c Release -o ./publish
```

### Frontend
```bash
cd Frontend
npm run build
```
Build output sẽ nằm trong thư mục `Frontend/dist`

## Công nghệ sử dụng

### Backend
- .NET Core 8.0
- ASP.NET Core Web API
- Swagger/OpenAPI
- Entity Framework Core (ready to integrate)

### Frontend
- React 18
- Vite
- Modern JavaScript (ES6+)

## Cấu trúc Code

### Backend Controllers

Controller mẫu: `Backend/Controllers/WeatherForecastController.cs:1`

```csharp
[ApiController]
[Route("api/[controller]")]
public class WeatherForecastController : ControllerBase
{
    // API endpoints here
}
```

### Frontend Components

Component chính: `Frontend/src/App.jsx:1`

```jsx
function App() {
  // Fetch data from API
  const fetchWeatherData = async () => {
    const response = await fetch('http://localhost:5000/api/WeatherForecast')
    // ...
  }
}
```

## Troubleshooting

### Backend không khởi động được
- Kiểm tra .NET 8.0 SDK đã được cài đặt: `dotnet --version`
- Kiểm tra port 5000/5001 có bị chiếm dụng không

### Frontend không kết nối được API
- Kiểm tra Backend đã chạy chưa
- Kiểm tra CORS configuration trong `Backend/Program.cs`
- Kiểm tra URL API trong `Frontend/src/App.jsx:16`

### Lỗi khi build
- Backend: Chạy `dotnet restore` trong thư mục Backend
- Frontend: Xóa `node_modules` và chạy lại `npm install`

## Phát triển thêm

### Thêm API Controller mới

1. Tạo file mới trong `Backend/Controllers/`
2. Kế thừa từ `ControllerBase`
3. Thêm attributes `[ApiController]` và `[Route]`

### Thêm Component React mới

1. Tạo file `.jsx` mới trong `Frontend/src/components/`
2. Import và sử dụng trong `App.jsx`

### Kết nối Database

1. Cài đặt Entity Framework Core
2. Tạo DbContext trong Backend
3. Cấu hình connection string trong `appsettings.json`

## License

MIT License
