package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Separator;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class NotificationController {
    @FXML
    private VBox notificationDetailsVBox;

    @FXML
    private VBox notificationsBox;

    private VBox selectedNotificationBox = null;

    private NotificationDetailsController notificationDetailsController;
    private NotificationService notificationService;

    private Order selectedNotification;

    public NotificationController() {
        notificationDetailsController = new NotificationDetailsController();

        if (User.getIsAdmin()) {
            notificationService = new AdminNotification();
        } else {
            String loggedInUserEmail = User.getLoggedInUserEmail();
            notificationService = new CustomerNotification(loggedInUserEmail);
        }
    }

    @FXML
    public void initialize() {
        loadNotifications();
    }

    private void loadNotifications() {
        List<Order> notifications = notificationService.getNotifications();
        if (notifications == null) {
            return;
        }

        notificationsBox.getChildren().clear();
        Collections.reverse(notifications);

        for (Order notification : notifications) {
            VBox notificationBox = createNotificationBox(notification);
            notificationsBox.getChildren().add(notificationBox);
        }
    }

    private VBox createNotificationBox(Order order) {
        VBox notificationBox = new VBox();

        notificationBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (notificationBox != selectedNotificationBox) {
                notificationBox.setStyle("-fx-background-color: #fef9f3;");
            }
        });

        notificationBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (notificationBox != selectedNotificationBox) {
                notificationBox.setStyle("-fx-background-color: transparent;");
            }
        });

        String title;
        if (User.getIsAdmin()) {
            title = "You received an Order: " + order.getVehicle();
        } else {
            title = "Booking Confirmation: " + order.getVehicle();
        }
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System Bold", 18.0));
        Label timestampLabel = new Label(order.getTimestamp());
        timestampLabel.setFont(Font.font(10.0));

        VBox.setMargin(titleLabel, new Insets(10, 0, 0, 10));
        VBox.setMargin(timestampLabel, new Insets(0, 0, 10, 10));

        notificationBox.getChildren().addAll(titleLabel, timestampLabel);

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(10, 0, 0, 0));
        notificationBox.getChildren().add(separator);

        notificationBox.setOnMouseClicked(event -> {
            if (selectedNotificationBox != null) {
                selectedNotificationBox.setStyle("-fx-background-color: transparent;");
            }
            notificationBox.setStyle("-fx-background-color: grey;");
            selectedNotificationBox = notificationBox;
            handleNotificationSelection(order);
        });

        return notificationBox;
    }

    private void handleNotificationSelection(Order notification) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notification-details.fxml"));
            VBox notificationDetails = loader.load();

            selectedNotification = notification;

            NotificationDetailsController notificationDetailsController = loader.getController();
            notificationDetailsController.setNotificationDetails(selectedNotification, User.getIsAdmin());

            notificationDetailsVBox.getChildren().setAll(notificationDetails.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewNotification() {
        selectedNotification = null;
        notificationDetailsController.clearNotificationDetails();
    }
}
