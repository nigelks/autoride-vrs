package org.example.javaasg;

public class Order {
    private String orderId;
    private String email;
    private String vehicle;
    private String startDate;
    private String endDate;
    private String timestamp;
    private int rentalDuration;
    private int totalPrice;

    public Order(String orderId, String email, String vehicle, String startDate, String endDate, String timestamp, int rentalDuration, int totalPrice) {
        this.orderId = orderId;
        this.email = email;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timestamp = timestamp;
        this.rentalDuration = rentalDuration;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRentalDuration() {
        return rentalDuration + " days";
    }

    public String getTotalPrice() {
        return "RM " + totalPrice;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}