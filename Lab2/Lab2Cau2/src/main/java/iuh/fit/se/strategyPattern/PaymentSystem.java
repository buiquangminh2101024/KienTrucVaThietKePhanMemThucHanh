package iuh.fit.se.strategyPattern;

// --- STRATEGY PATTERN ---
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " qua Thẻ tín dụng.");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " qua ví PayPal.");
    }
}

// Shopping Cart (Context)
class ShoppingCart {
    private PaymentStrategy paymentMethod;

    public void setPaymentMethod(PaymentStrategy method) {
        this.paymentMethod = method;
    }

    public void checkout(double amount) {
        paymentMethod.pay(amount);
    }
}

// Main Test
public class PaymentSystem {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Người dùng chọn PayPal
        cart.setPaymentMethod(new PayPalPayment());
        cart.checkout(500.0);

        // Người dùng đổi ý sang Credit Card
        cart.setPaymentMethod(new CreditCardPayment());
        cart.checkout(500.0);
    }
}
