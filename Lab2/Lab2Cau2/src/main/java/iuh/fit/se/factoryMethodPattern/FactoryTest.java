package iuh.fit.se.factoryMethodPattern;

// 1. Interface chung cho các loại thông báo
interface Notification {
    void sendMessage(String message);
}

// 2. Các lớp cụ thể thực thi Interface
class EmailNotification implements Notification {
    public void sendMessage(String message) {
        System.out.println("Gửi Email với nội dung: " + message);
    }
}

class SMSNotification implements Notification {
    public void sendMessage(String message) {
        System.out.println("Gửi SMS với nội dung: " + message);
    }
}

// 3. Lớp Factory để tạo đối tượng
class NotificationFactory {
    public Notification createNotification(String type) {
        if (type == null || type.isEmpty()) return null;

        switch (type.toUpperCase()) {
            case "EMAIL":
                return new EmailNotification();
            case "SMS":
                return new SMSNotification();
            default:
                throw new IllegalArgumentException("Loại thông báo không hợp lệ!");
        }
    }
}

// Kiểm tra
public class FactoryTest {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();

        // Bạn chỉ cần truyền "tên" loại muốn tạo, không cần 'new EmailNotification()'
        Notification notify1 = factory.createNotification("EMAIL");
        notify1.sendMessage("Chào mừng bạn đến với Java!");

        Notification notify2 = factory.createNotification("SMS");
        notify2.sendMessage("Mã OTP của bạn là 1234");
    }
}
