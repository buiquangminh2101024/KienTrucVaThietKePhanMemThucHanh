package iuh.fit.se.factoryMethodPattern;

public class BookFactory {
    public static Book createBook(String type, String title) {
        if (type.equalsIgnoreCase("Physical")) return new PhysicalBook(title);
        if (type.equalsIgnoreCase("Ebook")) return new EBook(title);
        return null;
    }
}
