package org.example.javaasg;

public class CustomerRentalHistory {
    private String customerName;
    private String customerEmail;
    private String vehicleName;
    private String rentalDuration;
    private String rentalPrice;
    private String rentalStartDate;
    private String rentalEndDate;
    private String totalRentalPrice;

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getRentalDuration() {
        return rentalDuration +" days";
    }

    public String getRentalPrice() {
        return "RM " +rentalPrice;
    }

    public String getRentalStartDate() {
        return rentalStartDate;
    }

    public String getRentalEndDate() {
        return rentalEndDate;
    }

    public Double getTotalRentalPrice() {
        return Double.parseDouble(totalRentalPrice);
    }

    // Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setRentalDuration(String rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setRentalStartDate(String rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public void setRentalEndDate(String rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public void setTotalRentalPrice(String totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }
}
