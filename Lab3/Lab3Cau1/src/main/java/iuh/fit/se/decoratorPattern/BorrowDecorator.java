package iuh.fit.se.decoratorPattern;

public abstract class BorrowDecorator implements Borrowable {
    protected Borrowable item;
    public BorrowDecorator(Borrowable item) { this.item = item; }
}
