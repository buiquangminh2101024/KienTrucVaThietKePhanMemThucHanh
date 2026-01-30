package iuh.fit.se.strategyPattern;

import iuh.fit.se.factoryMethodPattern.Book;

import java.util.List;

public class LibraryContext {
    private SearchStrategy strategy;
    public void setStrategy(SearchStrategy strategy) { this.strategy = strategy; }
    public void performSearch(String keyword, List<Book> books) {
        strategy.search(keyword, books);
    }
}
