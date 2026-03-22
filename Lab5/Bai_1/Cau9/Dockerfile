# Sử dụng image Redis mới nhất
FROM redis:latest

# Thiết lập thư mục làm việc
WORKDIR /usr/local/etc/redis/

# Copy file cấu hình vào container
COPY redis.conf .

# Mở cổng 6380 (vì chúng ta đã đổi cổng trong file config)
EXPOSE 6380

# Lệnh khởi chạy Redis và chỉ định file cấu hình
CMD ["redis-server", "redis.conf"]