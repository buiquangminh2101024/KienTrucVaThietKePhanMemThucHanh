const http = require('http');
const fs = require('fs');

const server = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'text/html' });
    // Đọc và trả về file index.html
    fs.createReadStream('./index.html').pipe(res);
});

server.listen(3000, () => console.log('Server running at http://localhost:3000'));
