package iuh.fit.se.strategyPattern;

import iuh.fit.se.factoryMethodPattern.Book;

import java.util.List;

public interface SearchStrategy {
    void search(String keyword, List<Book> books);
}

