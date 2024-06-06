package org.example.javaasg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GenerateSalesReport {

    private static int lastNumber = 0;
    
    private String number;
    private String vehicle;
    private String customer;
    private String email;
    private String pickup;
    private String returnDate;
    private String days;
    private String price;

    private static ObservableList<GenerateSalesReport> salesReports = FXCollections.observableArrayList();

    public GenerateSalesReport(String line) {
        this.number = String.valueOf(++lastNumber); // Auto-increment number
        line = line.trim(); // Trim newline and other whitespace from the line
        String[] parts = line.split(";"); 
        this.customer = parts[1];
        this.email = parts[2];
        this.vehicle = parts[3];
        this.days = parts[4];
        this.price = parts[5];
        this.pickup = parts[6];
        this.returnDate = parts[7];
    }


    public String getNumber() {
        return number;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmail() {
        return email;
    }

    public String getPickup() {
        return pickup;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getDays() {
        return days;
    }

    public String getPrice() {
        return price;
    }

    public static ObservableList<GenerateSalesReport> getSalesReports() {
        if (salesReports.isEmpty()) {
            try (Stream<String> stream = Files.lines(Paths.get(Directories.getOrderDB()))) {
                stream.forEach(line -> {
                    GenerateSalesReport report = new GenerateSalesReport(line);
                    salesReports.add(report);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return salesReports;
    }
}