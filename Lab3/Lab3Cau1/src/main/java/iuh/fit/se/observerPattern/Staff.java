package iuh.fit.se.observerPattern;

public class Staff implements Observer {
    private String name;
    public Staff(String name) { this.name = name; }
    public void update(String message) {
        System.out.println("Thông báo tới " + name + ": " + message);
    }
}
