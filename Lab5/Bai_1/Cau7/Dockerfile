# Sử dụng image Python bản slim để tối ưu dung lượng
FROM python:3.9-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy file code vào container
COPY app.py .

# Thiết lập biến môi trường mặc định (Default Value)
ENV APP_ENV=production

# Lệnh khởi chạy ứng dụng
CMD ["python", "app.py"]