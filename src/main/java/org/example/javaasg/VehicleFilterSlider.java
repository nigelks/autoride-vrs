package org.example.javaasg;

// Java imports
import java.util.List;
import java.util.stream.Collectors;

// JavaFX imports
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class VehicleFilterSlider {
    public void create(Slider seatsSlider, GridPane homeGridPane, VBox brandVBox, VBox segmentVBox, List<Vehicles> cars) {
        seatsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the list of cars based on the number of seats
            List<Vehicles> filteredCars = cars.stream()
                    .filter(car -> {
                        String seats = car.getSeats().replaceAll("\\D+", "");
                        return !seats.isEmpty() && Integer.parseInt(seats) <= newValue.intValue();
                    })
                    .collect(Collectors.toList());

            // Update the homeGridPane to display only the vehicles that match the number of seats
            VehicleGrid vehicleGrid = new VehicleGrid();
            vehicleGrid.update(homeGridPane, brandVBox, segmentVBox, seatsSlider, filteredCars);
        });
    }
}