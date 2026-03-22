# Sử dụng bản Nginx mới nhất
FROM nginx:1.29.6-alpine-slim

# Copy file index.html từ máy host vào thư mục mặc định của Nginx trong container
COPY index.html /usr/share/nginx/html/index.html

# Nginx mặc định chạy cổng 80
EXPOSE 80