# --- Stage 1: Build (Dùng Image đầy đủ để có đủ công cụ build) ---
FROM node:20 AS builder

WORKDIR /app

# Copy các file định nghĩa thư viện trước để tận dụng cache
COPY package*.json ./
RUN npm install

# Copy toàn bộ mã nguồn vào
COPY . .

# Build ứng dụng React ra thư mục /app/dist
RUN npm run build

# Xóa bỏ các thư viện devDependencies để Stage sau nhẹ hơn
RUN npm prune --production


# --- Stage 2: Run (Dùng Image Alpine siêu nhẹ để chạy) ---
FROM node:20-alpine

WORKDIR /app

# Chỉ copy những thứ thực sự cần thiết từ stage builder sang
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package*.json ./

# Cài đặt một công cụ nhỏ để phục vụ file tĩnh (vì stage này không có vite)
RUN npm install -g serve

# Mở cổng 5173 (cổng mặc định của Vite/Serve)
EXPOSE 5173

# Chạy ứng dụng bằng lệnh serve để tối ưu hiệu năng
CMD ["serve", "-s", "dist", "-l", "5173"]