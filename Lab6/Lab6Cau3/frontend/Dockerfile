# Giai đoạn 1: Build ứng dụng React
FROM node AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Giai đoạn 2: Cấu hình Nginx để serve ứng dụng
FROM nginx:alpine
# Copy các tệp đã build từ giai đoạn 1 vào thư mục chứa web của Nginx
COPY --from=builder /app/dist /usr/share/nginx/html
# COPY FILE CẤU HÌNH NGINX VÀO CONTAINER
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
