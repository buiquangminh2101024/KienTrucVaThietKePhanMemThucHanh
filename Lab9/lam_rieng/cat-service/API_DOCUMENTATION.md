# Cat Management Service - API Documentation

Hệ thống quản lý thông tin mèo sử dụng kiến trúc Microservices với mô hình tách biệt Read/Write (CQRS), bộ nhớ đệm (Cache) và RabbitMQ.

## 1. Thêm mới mèo (Create Cat)

Sử dụng luồng ghi (Write Path). Dữ liệu sẽ được gửi vào `write-mq`, sau đó `write-service` sẽ lưu vào MariaDB và cập nhật Redis.

- **Endpoint:** `POST /cats`
- **Content-Type:** `application/json`

### Body Data:
| Trường | Kiểu dữ liệu | Mô tả |
| :--- | :--- | :--- |
| `name` | String | Tên của mèo (Bắt buộc) |
| `age` | Number | Tuổi của mèo (Bắt buộc) |
| `breed` | String | Giống mèo (Tùy chọn) |

### Ví dụ Request (cURL):
```bash
curl -X POST http://localhost:3000/cats \
     -H "Content-Type: application/json" \
     -d '{"name": "Mimi", "age": 2, "breed": "Mèo Anh lông ngắn"}'
```

### Ví dụ Phản hồi (Response):
```json
{
  "message": "Create request accepted",
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 2. Lấy thông tin mèo (Get Cat by ID)

Sử dụng luồng đọc (Read Path) tối ưu hóa với:
1. **Bloom Filter**: Chặn các ID không tồn tại ngay lập tức.
2. **Local Cache**: Lưu trữ tại bộ nhớ RAM của API Gateway.
3. **Redis Cache**: Bộ nhớ đệm phân tán.
4. **MariaDB (Event-Driven)**: Truy xuất từ DB nếu cache miss.

- **Endpoint:** `GET /cats/:id`

### URL Parameters:
| Tham số | Mô tả |
| :--- | :--- |
| `id` | UUID của mèo cần lấy thông tin |

### Ví dụ Request (cURL):
```bash
curl -X GET http://localhost:3000/cats/550e8400-e29b-41d4-a716-446655440000
```

### Ví dụ Phản hồi (Success):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Mimi",
  "age": 2,
  "breed": "Mèo Anh lông ngắn"
}
```

### Ví dụ Phản hồi (Not Found):
```json
{
  "error": "Cat not found (Bloom Filter)"
}
```

---

## 3. Các quy tắc xử lý High Load & Cache Penetration

- **Bloom Filter**: Nếu bạn yêu cầu một ID chưa từng được tạo, hệ thống sẽ từ chối ngay mà không cần truy vấn Database hay Cache.
- **Cache Null Values**: Nếu một ID hợp lệ (vượt qua Bloom Filter) nhưng không có trong Database, hệ thống sẽ lưu giá trị `NULL` vào Redis và Local Cache trong 30 giây để ngăn chặn việc spam yêu cầu vào Database.
- **Automatic Expiration**: Dữ liệu trong Redis sẽ tự động hết hạn sau 5 phút để đảm bảo tính cập nhật.
