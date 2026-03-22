# Sử dụng image PHP 8.2 kết hợp với web server Apache
FROM php:8.2-apache

# Thiết lập thư mục làm việc trong container
WORKDIR /var/www/html/

# (Tùy chọn) Copy code vào để dự phòng nếu không mount
COPY . /var/www/html/

# Apache mặc định chạy cổng 80
EXPOSE 80