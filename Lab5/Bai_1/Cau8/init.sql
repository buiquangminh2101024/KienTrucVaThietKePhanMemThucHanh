-- Tạo bảng đơn giản để kiểm tra
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Chèn dữ liệu mẫu
INSERT INTO users (username, email) VALUES 
('quang_minh', 'minh@example.com'),
('lan_phuong', 'phuong@example.com');