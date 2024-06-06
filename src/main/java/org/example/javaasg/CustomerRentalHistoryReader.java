package org.example.javaasg;

// Java imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRentalHistoryReader {
    private List<CustomerRentalHistory> rentalHistories;

    public List<CustomerRentalHistory> readRentalHistoriesFromFile(){
        List<CustomerRentalHistory> vehicles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getCustomerRentalHistoryDB()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                CustomerRentalHistory history = new CustomerRentalHistory();
                history.setCustomerName(parts[0]);
                history.setCustomerEmail(parts[1]);
                history.setVehicleName(parts[2]);
                history.setRentalDuration(parts[3]);
                history.setRentalPrice(parts[4]);
                history.setRentalStartDate(parts[5]);
                history.setRentalEndDate(parts[6]);

                double rentalPrice = Double.parseDouble(parts[4]);
                int rentalDuration = Integer.parseInt(parts[3]);
                double totalRentalPrice = rentalPrice / rentalDuration;
                history.setTotalRentalPrice(String.valueOf(totalRentalPrice));

                vehicles.add(history);
            }
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Error reading customer rental history file: " +e.getMessage());
        }
        return vehicles;
    }
}
