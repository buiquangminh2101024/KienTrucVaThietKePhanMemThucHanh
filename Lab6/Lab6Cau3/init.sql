CREATE DATABASE IF NOT EXISTS delivery_food
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE delivery_food;

-- 1. Xóa các bảng cũ nếu tồn tại để tránh lỗi khi khởi tạo lại (Thứ tự xóa ngược với thứ tự tạo)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
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

-- 4. Bảng Đơn hàng (Dành cho Order Module)
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, PAID, CANCELLED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Chi tiết đơn hàng (Link giữa Order và Dish)
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    dish_id INT,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_items_dish FOREIGN KEY (dish_id) REFERENCES dishes(id) ON DELETE SET NULL
);

-- 6. Bảng Thanh toán (Dành cho Payment Module)
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT UNIQUE,
    payment_method VARCHAR(50), -- MOMO, BANK_TRANSFER, CASH
    transaction_id VARCHAR(100),
    status VARCHAR(50) DEFAULT 'SUCCESS',
    paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
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

-- Giả lập một Đơn hàng đang chờ thanh toán
INSERT INTO orders (customer_name, total_amount, status) VALUES 
('Thảo Nguyễn', 75000, 'PENDING');

INSERT INTO order_items (order_id, dish_id, quantity, unit_price) VALUES 
(1, 1, 1, 35000), -- 1 Cơm tấm
(1, 2, 1, 40000); -- 1 Cơm gà

-- Giả lập một Đơn hàng đã thanh toán xong
INSERT INTO orders (customer_name, total_amount, status) VALUES 
('Hoàng Nam', 55000, 'PAID');

INSERT INTO order_items (order_id, dish_id, quantity, unit_price) VALUES 
(2, 3, 1, 55000); -- 1 Bún bò

INSERT INTO payments (order_id, payment_method, transaction_id) VALUES 
(2, 'MOMO', 'TRANS-123456789');