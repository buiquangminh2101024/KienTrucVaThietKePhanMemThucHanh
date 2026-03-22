const express = require('express');
const mysql = require('mysql2');

const app = express();
app.use(express.json()); // Để đọc dữ liệu JSON từ body

// 1. Cấu hình kết nối MySQL
const db = mysql.createConnection({
    host: 'db-service',
    user: process.env.MYSQL_USER,      // Thay bằng user của bạn
    password: process.env.MYSQL_PASSWORD,      // Thay bằng password của bạn
    database: process.env.MYSQL_DATABASE
});

db.connect(err => {
    if (err) {
        console.error('Lỗi kết nối MySQL:', err);
        return;
    }
    console.log('Đã kết nối MySQL thành công!');
});

// 2. Định nghĩa Route (API)
// Lấy danh sách tất cả người dùng
app.get('/users', async (req, res) => {
    const sql = 'SELECT * FROM users';
    db.query(sql, (err, results) => {
        if (err) return res.status(500).send(err);
        res.json(results);
    });
});

// Thêm người dùng mới
app.post('/users', async (req, res) => {
    const { name, email } = req.body;
    const sql = 'INSERT INTO users (name, email) VALUES (?, ?)';
    db.query(sql, [name, email], (err, result) => {
        if (err) return res.status(500).send(err);
        res.json({ message: 'Thêm thành công!', id: result.insertId });
    });
});

// 3. Chạy Server
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Server đang chạy tại http://localhost:${PORT}`);
});