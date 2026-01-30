package iuh.fit.se.strategyPattern;

import iuh.fit.se.factoryMethodPattern.Book;

import java.util.List;

public class SearchByTitle implements SearchStrategy {
    public void search(String keyword, List<Book> books) {
        System.out.println("Đang tìm theo tên: " + keyword);
    }
}
