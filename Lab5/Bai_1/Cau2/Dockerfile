# Sử dụng Python phiên bản nhẹ (Alpine)
FROM python:3.9-alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file app.py vào thư mục làm việc
COPY app.py .

# Cài đặt thư viện Flask trực tiếp
RUN pip install --no-cache-dir flask

# Thông báo container sẽ lắng nghe trên cổng 5000
EXPOSE 5000

# Lệnh khởi chạy ứng dụng
CMD ["python", "app.py"]