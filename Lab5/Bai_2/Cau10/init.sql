CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Chèn dữ liệu mẫu
INSERT INTO users (username, email) VALUES 
('quang_minh1', 'minh@example.com'),
('lan_phuong1', 'phuong@example.com');