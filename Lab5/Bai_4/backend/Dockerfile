# --- Stage 1: Build ---
FROM golang:alpine3.23 AS builder

WORKDIR /app

# 1. Copy các file quản lý module trước để tận dụng Docker Cache
COPY go.mod go.sum* ./
RUN go mod download

# 2. Copy TOÀN BỘ thư mục backend (bao gồm database, handlers, models...)
COPY . .

# 3. Build ứng dụng
# Lệnh này sẽ quét file main.go và tự động tìm các package liên quan trong thư mục
RUN CGO_ENABLED=0 GOOS=linux go build -o myapp main.go

# --- Stage 2: Run ---
FROM alpine:latest
WORKDIR /root/
COPY --from=builder /app/myapp .
CMD ["./myapp"]