package iuh.fit.se.strategyAndDecorator;

// Component
interface Product {
    double getPrice();
    String getDescription();
}

class BasicProduct implements Product {
    private double price;
    public BasicProduct(double price) { this.price = price; }
    public double getPrice() { return price; }
    public String getDescription() { return "Sản phẩm gốc"; }
}

// --- DECORATOR PATTERN (Cho phép cộng dồn nhiều loại thuế) ---
abstract class TaxDecorator implements Product {
    protected Product product;
    public TaxDecorator(Product product) { this.product = product; }
}

class VATDecorator extends TaxDecorator {
    public VATDecorator(Product product) { super(product); }
    public double getPrice() { return product.getPrice() * 1.1; } // Thuế 10%
    public String getDescription() { return product.getDescription() + " + Thuế VAT (10%)"; }
}

class LuxuryTaxDecorator extends TaxDecorator {
    public LuxuryTaxDecorator(Product product) { super(product); }
    public double getPrice() { return product.getPrice() * 1.3; } // Thuế 30%
    public String getDescription() { return product.getDescription() + " + Thuế Tiêu thụ đặc biệt (30%)"; }
}

// Main Test
public class TaxCalculation {
    public static void main(String[] args) {
        Product phone = new BasicProduct(1000);
        // Áp dụng chồng thuế bằng Decorator
        phone = new VATDecorator(phone);
        phone = new LuxuryTaxDecorator(phone);

        System.out.println("Mô tả: " + phone.getDescription());
        System.out.println("Tổng giá sau thuế: " + phone.getPrice());
    }
}
