package org.example.javaasg;

import java.util.List;

public abstract class NotificationService {
    public abstract List<Order> getNotifications();

    protected List<Order> filterNotifications(List<Order> orders, String criterion) {
        // Default implementation: return all orders (no filtering)
        return orders;
    }

    protected void handleError(Exception e) {
        e.printStackTrace();  
    }
}
