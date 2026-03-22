USE app_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO users (name, email) VALUES ('Nguyen Van A', 'ana@gmail.com'), ('Tran Thi B', 'bti@gmail.com');