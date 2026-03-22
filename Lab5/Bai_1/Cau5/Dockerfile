# --- Stage 1: Build ---
FROM golang:1.21-alpine AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file nguồn vào container
COPY main.go .

# Biên dịch ứng dụng Go thành file thực thi tên là "myapp"
RUN go build -o myapp main.go

# --- Stage 2: Run ---
FROM alpine:latest

WORKDIR /root/

# Chỉ copy duy nhất file thực thi từ stage builder sang
COPY --from=builder /app/myapp .

# Lệnh khởi chạy ứng dụng
CMD ["./myapp"]