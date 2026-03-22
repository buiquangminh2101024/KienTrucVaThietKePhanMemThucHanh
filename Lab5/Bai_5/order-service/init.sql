CREATE DATABASE IF NOT EXISTS delivery_food
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE delivery_food;

-- 1. Xóa các bảng cũ nếu tồn tại để tránh lỗi khi khởi tạo lại (Thứ tự xóa ngược với thứ tự tạo)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;

-- 2. Bảng Đơn hàng (Dành cho Order Module)
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, PAID, CANCELLED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Chi tiết đơn hàng (Link giữa Order và Dish)
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    dish_id INT,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- ==========================================
-- CHÈN DỮ LIỆU MẪU (SEED DATA)
-- ==========================================

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