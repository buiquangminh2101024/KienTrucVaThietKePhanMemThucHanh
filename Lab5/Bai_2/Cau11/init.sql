CREATE TABLE users (
    id SERIAL PRIMARY KEY,        -- ID tự tăng, khóa chính
    username VARCHAR(50) NOT NULL, -- Tên người dùng, không để trống
    email VARCHAR(100),           -- Email
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Thời gian tạo
);

INSERT INTO users (username, email)
VALUES ('nguyena', 'nguyena@example.com');