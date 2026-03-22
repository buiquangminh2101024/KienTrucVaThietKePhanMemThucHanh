CREATE DATABASE IF NOT EXISTS delivery_food
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE delivery_food;

-- 1. Xóa các bảng cũ nếu tồn tại để tránh lỗi khi khởi tạo lại (Thứ tự xóa ngược với thứ tự tạo)
DROP TABLE IF EXISTS payments;

-- 2. Bảng Thanh toán (Dành cho Payment Module)
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT UNIQUE,
    payment_method VARCHAR(50), -- MOMO, BANK_TRANSFER, CASH
    transaction_id VARCHAR(100),
    status VARCHAR(50) DEFAULT 'SUCCESS',
    paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- CHÈN DỮ LIỆU MẪU (SEED DATA)
-- ==========================================

INSERT INTO payments (order_id, payment_method, transaction_id) VALUES 
(2, 'MOMO', 'TRANS-123456789');