FROM postgres:16-alpine3.22

# Thiết lập các biến môi trường bắt buộc để khởi tạo DB
# Bạn có thể thay đổi các giá trị này tùy ý
ENV POSTGRES_DB=mydb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=123

# Copy file init.sql vào thư mục đặc biệt để tự động thực thi
COPY init.sql /docker-entrypoint-initdb.d/

# Cổng mặc định của PostgreSQL
EXPOSE 5432