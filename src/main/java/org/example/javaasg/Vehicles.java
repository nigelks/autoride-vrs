package org.example.javaasg;

public class Vehicles {
    private String vehicleName;
    private String brand;
    private String imageName;
    private String price;
    private String segment;
    private String seats;
    private String fuelEconomy;
    private String gearbox;

    // Getters
    public String getVehicleName() {
        return vehicleName;
    }

    public String getBrand() {
        return brand;
    }

    public String getImageName() {
        return "/assets/vehicles/" + imageName + ".png";
    }

    public String getPrice() {
        return price;
    }

    public String getSegment() {
        return segment;
    }

    public String getSegmentImage() {
        return "/assets/static/" + segment + ".png";
    }

    public String getSeats() {
        return seats;
    }

    public String getFuelEconomy() {
        return fuelEconomy;
    }

    public String getFuelEconomyImage() {
        return "/assets/static/gas.png";
    }

    public String getTranmission() {
        return gearbox;
    }

    public String getTranmissionImage() {
        return "/assets/static/transmission.png";
    }

    // Static getters
    public static String getSeatsImage() {
        return "/assets/static/vehicle_seats.png";
    }

    // Setters
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setBrand(String brandName) {
        this.brand = brandName;
    }

    public void setDirectoryToImage(String imageName) {
        this.imageName = imageName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setFuelEconomy(String fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }
}
