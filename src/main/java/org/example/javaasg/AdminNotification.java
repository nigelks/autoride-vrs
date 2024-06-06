package org.example.javaasg;

import java.util.List;

public class AdminNotification extends NotificationService {
    private AllOrderReader allOrderReader;

    public AdminNotification() {
        this.allOrderReader = new AllOrderReader();
    }

    @Override
    public List<Order> getNotifications() {
        try {
            List<Order> orders = allOrderReader.readAllOrders();
            return filterNotifications(orders, null); // No specific filtering for admin
        } catch (Exception e) {
            handleError(e);
            return null;
        }
    }
}
