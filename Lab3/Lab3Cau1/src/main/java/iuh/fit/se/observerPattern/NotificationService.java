package iuh.fit.se.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer observer) { observers.add(observer); }
    public void notifyAll(String message) {
        for (Observer obs : observers) obs.update(message);
    }
}
