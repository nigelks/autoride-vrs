package org.example.javaasg;

// Java imports
import java.util.List;

// JavaFX imports
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;

public class VehicleGrid {
    public void initialize(GridPane homeGridPane, List<Vehicles> cars) {
        // Clear the existing nodes and constraints
        homeGridPane.getChildren().clear();
        homeGridPane.getColumnConstraints().clear();
        homeGridPane.getRowConstraints().clear();

        // Define the number of columns
        final int numColumns = 3; // Number of columns to display

        // Set consistent column constraints
        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / numColumns);
            columnConstraints.setHgrow(Priority.ALWAYS); // Allow column to grow
            homeGridPane.getColumnConstraints().add(columnConstraints);
        }

        // Add vehicles to the grid
        int rowIndex = 0, colIndex = 0;
        for (Vehicles car : cars) {
            VBox vbox = new VehicleVBox().create(car);

            // Ensure VBoxes are uniformly sized
            vbox.setMinWidth(homeGridPane.getWidth() / numColumns);
            vbox.setMaxWidth(homeGridPane.getWidth() / numColumns);
            vbox.setPrefWidth(homeGridPane.getWidth() / numColumns);

            GridPane.setHgrow(vbox, Priority.ALWAYS);
            homeGridPane.add(vbox, colIndex, rowIndex);
            colIndex++;

            if (colIndex >= numColumns) {
                colIndex = 0;
                rowIndex++;
            }
        }

        // Fix Vehicle VBox excessive height issue
        homeGridPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Node node : homeGridPane.getChildren()) {
                if (node instanceof VBox) {
                    ((VBox) node).setMinWidth(newVal.doubleValue() / numColumns);
                    ((VBox) node).setMaxWidth(newVal.doubleValue() / numColumns);
                    ((VBox) node).setPrefWidth(newVal.doubleValue() / numColumns);
                }
            }
        });
    }

    public void update(GridPane homeGridPane, VBox brandVBox, VBox segmentVBox, Slider vehicleSeatsFilterSlider, List<Vehicles> cars) {
        // Filter the list of cars based on the checked brands and segments
        VehicleFilter vehicleFilter = new VehicleFilter();
        List<Vehicles> filteredCars = vehicleFilter.filter(cars, brandVBox, vehicleSeatsFilterSlider, Vehicles::getBrand);
        filteredCars = vehicleFilter.filter(filteredCars, segmentVBox, vehicleSeatsFilterSlider, Vehicles::getSegment);

        // Update the homeGridPane to display only the vehicles that match all active filters
        VehicleGrid vehicleGrid = new VehicleGrid();
        vehicleGrid.initialize(homeGridPane, filteredCars);
    }
}
