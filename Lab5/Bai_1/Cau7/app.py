import os

# Đọc biến môi trường APP_ENV, nếu không có thì mặc định là 'development'
app_env = os.getenv('APP_ENV', 'development')

print(f"Ứng dụng đang chạy trong môi trường: {app_env}")