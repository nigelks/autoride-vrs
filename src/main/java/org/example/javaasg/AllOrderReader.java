package org.example.javaasg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllOrderReader {
    public List<Order> readAllOrders() {
        List<Order> orders = new ArrayList<>();
        String filePath = Directories.getOrderDB();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order order = parseOrder(line);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading orders: " + e.getMessage());
        }

        return orders;
    }

    private Order parseOrder(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length >= 9) { // Ensure that the line has enough parts
                String orderId = parts[0];
                String email = parts[2];
                String vehicle = parts[3];
                String startDate = parts[6];
                String endDate = parts[7];
                String timestamp = parts[8];
                int rentalDuration = Integer.parseInt(parts[4]);
                int totalPrice = Integer.parseInt(parts[5]);

                return new Order(orderId, email, vehicle, startDate, endDate, timestamp, rentalDuration, totalPrice);
            } else {
                System.err.println("Invalid order format: " + line);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing order: " + e.getMessage());
        }
        return null; // Return null if parsing fails
    }
}
