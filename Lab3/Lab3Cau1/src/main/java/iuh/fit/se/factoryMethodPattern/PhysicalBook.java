package iuh.fit.se.factoryMethodPattern;

public class PhysicalBook implements Book {
    private String title;
    public PhysicalBook(String title) { this.title = title; }
    public String getTitle() { return "[Sách Giấy] " + title; }
}
