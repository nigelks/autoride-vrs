package org.example.javaasg;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VehicleVBox {
    VBox create(Vehicles car) {
        // Create a new VBox
        VBox vbox = new VBox();
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Label label = new Label(car.getVehicleName());
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 18px; -fx-font-family: Arial; -fx-underline: true");

        // Create an ImageView for the car image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // "Rent Now" button with the price of the car
        Button rentButton = new Button("Rent Now (RM" + car.getPrice() + "/Day)");
        rentButton.setMaxWidth(Double.MAX_VALUE);
        rentButton.setAlignment(Pos.CENTER);
        rentButton.setStyle("-fx-font-size: 14px; -fx-font-family: Arial; -fx-background-color: #ebe6e6; -fx-padding: 10px; -fx-border-width: 1px; -fx-border-color: #008000;-fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Add an action to the button
        rentButton.setOnAction(e -> {
            try {
                RentVehiclesController.setCar(car);

                ActionEvent event = new ActionEvent(rentButton, null);
                SceneSwitcher.switchScene(event, "rent-vehicles-view.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Hover
        rentButton.setOnMouseEntered(e -> rentButton.setOpacity(0.7));
        rentButton.setOnMouseExited(e -> rentButton.setOpacity(1.0));

        // Add top margin
        VBox.setMargin(rentButton, new Insets(10, 0, 0, 0));

        try {
            // Load the image, set dimensions, preserve ratio, and smooth the image
            Image image = new Image(getClass().getResourceAsStream(car.getImageName()), 250, 250, true, true);
            imageView.setImage(image);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Image not found for car: " + car.getVehicleName() + " || Image directory: " + car.getImageName());
        }

        // Create a new HBox and VBox for vehicle details
        HBox vehicleDetailsHbox = new HBox();

        // Set the spacing between each child in the HBox
        vehicleDetailsHbox.setSpacing(10);

        // Create VBox for each vehicle detail
        VehicleDetails vehicleDetails = new VehicleDetails();
        VBox vehicleSegmentVbox = vehicleDetails.createVBox(vbox, car.getSegment(), car.getSegmentImage(), "-fx-font-size: 12px; -fx-font-family: Arial");
        VBox vehicleSeatsVbox = vehicleDetails.createVBox(vbox, car.getSeats(), car.getSeatsImage(), "-fx-font-size: 12px; -fx-font-family: Arial");
        VBox vehicleFuelEconomyVbox = vehicleDetails.createVBox(vbox, car.getFuelEconomy(), car.getFuelEconomyImage(), "-fx-font-size: 12px; -fx-font-family: Arial");
        VBox vehicleTransmissionVbox = vehicleDetails.createVBox(vbox, car.getTranmission(), car.getTranmissionImage(), "-fx-font-size: 12px; -fx-font-family: Arial");

        // Add the segment image and label to the HBox
        vehicleDetailsHbox.getChildren().addAll(vehicleSegmentVbox, vehicleSeatsVbox, vehicleFuelEconomyVbox, vehicleTransmissionVbox);
        vehicleDetailsHbox.setAlignment(Pos.CENTER);

        // Add the label, image, and HBox to the VBox
        vbox.getChildren().addAll(label, imageView, vehicleDetailsHbox, rentButton);

        // Apply border styling if VBox is not empty
        if (!vbox.getChildren().isEmpty()) {
            vbox.setStyle("-fx-border-color: #808080; -fx-border-width: 0 1 1 0");
        }

        return vbox;
    }
}
