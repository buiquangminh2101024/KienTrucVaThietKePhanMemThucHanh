package iuh.fit.se.factoryMethodPattern;

public class EBook implements Book {
    private String title;
    public EBook(String title) { this.title = title; }
    public String getTitle() { return "[E-Book] " + title; }
}
