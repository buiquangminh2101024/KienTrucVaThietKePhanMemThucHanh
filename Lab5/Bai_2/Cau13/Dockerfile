# Stage 1: Build ứng dụng
FROM node:20 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
RUN npm prune --production

# Stage 2: Cấu hình Nginx để phục vụ các file tĩnh
FROM nginx:latest
# Copy các file đã build từ thư mục /app/dist của stage builder 
# vào thư mục mặc định của Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]