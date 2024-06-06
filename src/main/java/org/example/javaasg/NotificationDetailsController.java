package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NotificationDetailsController {

    private Order notification;
    private boolean isAdmin;

    @FXML
    private Label titleLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label vehiclesLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label pickupLabel;

    @FXML
    private Label returnLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label priceLabel;

    // No-argument constructor
    public NotificationDetailsController() {
    }

    public NotificationDetailsController(Order notification, boolean isAdmin) {
        this.notification = notification;
        this.isAdmin = isAdmin;
    }

    public void initialize() {
        if (notification != null) {
            setNotificationDetails(notification, isAdmin);
        }
    }

    public void setNotificationDetails(Order notification, boolean isAdmin) {
        if (notification == null) {
            clearNotificationDetails();
            return;
        }

        if (isAdmin) {
            titleLabel.setText("You have a new order! " + notification.getVehicle());
            messageLabel.setText("You have received a new order. Please check the details below:");
        } else {
            titleLabel.setText("Booking Confirmation for " + notification.getVehicle());
            messageLabel.setText("Thank you for your order, your order details are as below:");
        }
        vehiclesLabel.setText(notification.getVehicle());
        locationLabel.setText("Pickup & Return Location: Asia Pacific University");

        pickupLabel.setText("Pickup Date: "+ notification.getStartDate());
        returnLabel.setText("Return Date: " +notification.getEndDate());
        durationLabel.setText("Rental Duration: "+ String.valueOf(notification.getRentalDuration()));
        priceLabel.setText("Total Price: " + String.valueOf(notification.getTotalPrice()));
    }

    public void clearNotificationDetails() {
        titleLabel.setText("");
        messageLabel.setText("");
        vehiclesLabel.setText("");
        locationLabel.setText("");
        pickupLabel.setText("");
        returnLabel.setText("");
        durationLabel.setText("");
        priceLabel.setText("");
    }
}