package iuh.fit.se.statePattern;

// --- STATE PATTERN ---
interface OrderState {
    void handle(Order context);
}

class NewOrder implements OrderState {
    public void handle(Order context) {
        System.out.println("Trạng thái: Mới tạo. Đang kiểm tra thông tin đơn hàng...");
        context.setState(new Processing());
    }
}

class Processing implements OrderState {
    public void handle(Order context) {
        System.out.println("Trạng thái: Đang xử lý. Đang đóng gói và vận chuyển...");
        context.setState(new Shipped());
    }
}

class Shipped implements OrderState {
    public void handle(Order context) {
        System.out.println("Trạng thái: Đã giao. Cập nhật trạng thái hoàn tất.");
    }
}

class Cancelled implements OrderState {
    public void handle(Order context) {
        System.out.println("Trạng thái: Đã hủy. Đang hoàn tiền cho khách hàng.");
    }
}

// Context
class Order {
    private OrderState state;
    public Order() { state = new NewOrder(); }
    public void setState(OrderState state) { this.state = state; }
    public void proceed() { state.handle(this); }
}

// Main Test
public class OrderManagement {
    public static void main(String[] args) {
        Order order = new Order();
        order.proceed(); // Kiểm tra thông tin
        order.proceed(); // Đóng gói
        order.proceed(); // Hoàn tất
    }
}
