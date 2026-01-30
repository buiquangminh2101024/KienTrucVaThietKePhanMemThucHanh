package iuh.fit.se.decoratorPattern;

public class ExtensionDecorator extends BorrowDecorator {
    public ExtensionDecorator(Borrowable item) { super(item); }
    public void borrow() {
        item.borrow();
        System.out.println("- Đã thêm tính năng: Gia hạn thời gian mượn.");
    }
}
