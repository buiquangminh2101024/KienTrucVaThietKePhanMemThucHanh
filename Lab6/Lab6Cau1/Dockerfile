# --- STAGE 1: Tải Dependencies ---
FROM golang:1.25-alpine AS deps
WORKDIR /app
# Copy file quản lý thư viện trước để tận dụng Docker Cache
COPY go.mod go.sum ./
RUN go mod download

# --- STAGE 2: Build Application ---
FROM deps AS builder
COPY . .
# Build file binary tĩnh (static), tắt CGO để chạy được trên môi trường không có thư viện C
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-s -w" -o main .

# --- STAGE 3: Final Runtime (Siêu nhẹ) ---
# Sử dụng 'scratch' (image rỗng 0MB) hoặc 'alpine' (5MB)
FROM alpine:3.19
WORKDIR /root/

# Cài đặt CA-certificates nếu ứng dụng cần gọi API HTTPS
RUN apk --no-cache add ca-certificates

# Chỉ copy đúng file thực thi từ stage builder sang
COPY --from=builder /app/main .

# Chạy với quyền non-root để bảo mật và tiết kiệm tài nguyên hệ thống
USER 1001

EXPOSE 8080

CMD ["./main"]