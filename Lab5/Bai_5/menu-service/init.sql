CREATE DATABASE IF NOT EXISTS delivery_food
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE delivery_food;

-- 1. Xóa các bảng cũ nếu tồn tại để tránh lỗi khi khởi tạo lại (Thứ tự xóa ngược với thứ tự tạo)
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;

-- 2. Bảng Nhà hàng (Dành cho Menu Module)
CREATE TABLE restaurants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    is_active BOOLEAN DEFAULT TRUE
);

-- 3. Bảng Món ăn (Dành cho Menu Module)
CREATE TABLE dishes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    available_quantity INT DEFAULT 100,
    CONSTRAINT fk_dish_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

-- ==========================================
-- CHÈN DỮ LIỆU MẪU (SEED DATA)
-- ==========================================

-- Chèn Nhà hàng
INSERT INTO restaurants (name, address) VALUES 
('Tiệm Cơm Sinh Viên IUH', 'Nguyễn Văn Bảo, Gò Vấp'),
('Bún Bò Huế O Lan', 'Phan Văn Trị, Gò Vấp');

-- Chèn Món ăn
INSERT INTO dishes (restaurant_id, name, price, description) VALUES 
(1, 'Cơm Tấm Sườn Bì Chả', 35000, 'Món đặc sản sinh viên'),
(1, 'Cơm Gà Xối Mỡ', 40000, 'Gà giòn rụm'),
(2, 'Bún Bò Đặc Biệt', 55000, 'Đầy đủ chả, nạm, gân');