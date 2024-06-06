package org.example.javaasg;

import java.util.ArrayList;
import java.util.List;

public class CustomerNotification extends NotificationService {
    private String customerEmail;
    private List<Order> allOrders;

    public CustomerNotification(String customerEmail) {
        this.customerEmail = customerEmail;
        AllOrderReader allOrderReader = new AllOrderReader();
        this.allOrders = allOrderReader.readAllOrders();
    }

    @Override
    public List<Order> getNotifications() {
        try {
            List<Order> orders = allOrders;
            return filterNotifications(orders, customerEmail);
        } catch (Exception e) {
            handleError(e);
            return null;
        }
    }

    @Override
    protected List<Order> filterNotifications(List<Order> orders, String criterion) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getEmail().equals(criterion)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }
}
