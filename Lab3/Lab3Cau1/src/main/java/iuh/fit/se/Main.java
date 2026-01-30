package iuh.fit.se;

import iuh.fit.se.decoratorPattern.BasicBorrow;
import iuh.fit.se.decoratorPattern.Borrowable;
import iuh.fit.se.decoratorPattern.ExtensionDecorator;
import iuh.fit.se.factoryMethodPattern.Book;
import iuh.fit.se.factoryMethodPattern.BookFactory;
import iuh.fit.se.observerPattern.NotificationService;
import iuh.fit.se.observerPattern.Staff;
import iuh.fit.se.singletonPattern.Library;
import iuh.fit.se.strategyPattern.LibraryContext;
import iuh.fit.se.strategyPattern.SearchByTitle;

public class Main {
    public static void main(String[] args) {
        // 1. Singleton
        Library myLibrary = Library.getInstance();

        // 2. Factory
        Book b1 = BookFactory.createBook("Physical", "Lập trình Java");
        myLibrary.addBook(b1);

        // 3. Strategy
        LibraryContext searchContext = new LibraryContext();
        searchContext.setStrategy(new SearchByTitle());
        searchContext.performSearch("Java", myLibrary.getBooks());

        // 4. Observer
        NotificationService notifier = new NotificationService();
        notifier.addObserver(new Staff("Nhân viên A"));
        notifier.notifyAll("Sách mới 'Lập trình Java' vừa được thêm!");

        // 5. Decorator
        Borrowable borrowAction = new ExtensionDecorator(new BasicBorrow());
        borrowAction.borrow();
    }
}