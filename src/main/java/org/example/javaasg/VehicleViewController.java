package org.example.javaasg;

// Java imports
import java.util.List;
import java.util.Set;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;

public class VehicleViewController {
    // Vehicle pane grid pane
    @FXML
    private GridPane homeGridPane;

    // Vehicle pane scroll pane
    @FXML
    private ScrollPane homeScrollPane;

    // Filter menu VBox for brands
    @FXML
    private VBox brandVBox;

    // Filter menu VBox for segments
    @FXML
    private VBox segmentVBox;

    @FXML
    private Slider vehicleSeatsFilterSlider;

    // VehicleRead object for reading car_list.txt
    private VehicleReader vehicleReader = new VehicleReader();

    @FXML
    public void initialize() {
        // Set the GridPane as the content of the ScrollPane
        homeScrollPane.setContent(homeGridPane);

        homeGridPane.setStyle("-fx-background-color: white;");
        for (Node node : homeGridPane.getChildren()) {
            node.setStyle("-fx-background-color: #808080;");
        }

        // Read and list all vehicles
        List<Vehicles> cars = vehicleReader.readVehiclesFromFile();

        // Display the vehicles in the homeGridPane
        VehicleGrid vehicleGrid = new VehicleGrid();
        vehicleGrid.initialize(homeGridPane, cars);

        // Get the unique brands and segments
        Set<String> uniqueBrands = vehicleReader.getUniqueBrands();
        Set<String> uniqueSegments = vehicleReader.getUniqueSegments();

        // For each unique brand, populate VBox with checkboxes with the brands as the label
        for (String brand : uniqueBrands) {
            new VehicleFilterCheckbox(brand, brandVBox, event -> vehicleGrid.update(homeGridPane, brandVBox, segmentVBox, vehicleSeatsFilterSlider, cars)).create();
        }

        // For each unique segment, populate VBox with checkboxes with the segments as the label
        for (String segment : uniqueSegments) {
            new VehicleFilterCheckbox(segment, segmentVBox, event -> vehicleGrid.update(homeGridPane, brandVBox, segmentVBox, vehicleSeatsFilterSlider, cars)).create();
        }

        // Create a VehicleFilterSlider and add a listener to the seats slider
        VehicleFilterSlider vehicleFilterSlider = new VehicleFilterSlider();
        vehicleFilterSlider.create(vehicleSeatsFilterSlider, homeGridPane, brandVBox, segmentVBox, cars);
    }

    // Mandatory method for resetting filters as this file is the controller for the vehicle-view FXML
    @FXML
    private void resetFilters(ActionEvent event) {
        // Reset the brand and segment filters *MUST BE DONE FIRST*
        VehicleFilter vehicleFilter = new VehicleFilter();
        vehicleFilter.reset(brandVBox, segmentVBox, vehicleSeatsFilterSlider);

        // Read and list all vehicles
        List<Vehicles> vehicles = vehicleReader.readVehiclesFromFile();

        // Display the vehicles in the homeGridPane
        VehicleGrid vehicleGrid = new VehicleGrid();
        vehicleGrid.update(homeGridPane, brandVBox, segmentVBox, vehicleSeatsFilterSlider, vehicles);
    }
}