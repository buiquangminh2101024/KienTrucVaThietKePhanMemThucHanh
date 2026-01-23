package iuh.fit.se.singletonPattern;

class DatabaseConnector {
    // 1. Tạo biến static duy nhất để lưu trữ instance
    private static DatabaseConnector instance;

    // 2. Để private constructor để ngăn không cho tạo instance từ bên ngoài bằng 'new'
    private DatabaseConnector() {
        System.out.println("Đã khởi tạo kết nối Database (chỉ một lần duy nhất).");
    }

    // 3. Phương thức static để truy cập instance
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            // Sử dụng synchronized để an toàn trong môi trường đa luồng (Multi-threading)
            synchronized (DatabaseConnector.class) {
                if (instance == null) {
                    instance = new DatabaseConnector();
                }
            }
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Đang thực thi lệnh: " + sql);
    }
}

// Kiểm tra
public class SingletonTest {
    public static void main(String[] args) {
        DatabaseConnector db1 = DatabaseConnector.getInstance();
        db1.query("SELECT * FROM Users");

        DatabaseConnector db2 = DatabaseConnector.getInstance();

        // Kiểm tra xem db1 và db2 có phải là một không
        System.out.println("db1 và db2 là một? " + (db1 == db2));
    }
}
