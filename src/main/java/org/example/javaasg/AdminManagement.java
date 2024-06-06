package org.example.javaasg;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminManagement {
    private User selectedUser;
    private static VBox selectedUserBox = null;

    public User getSelectedUser() {
        return selectedUser;
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = new UserReader().readAllUsers();
        } catch (Exception e) {
            System.out.println("Failed to read users due to unexpected error: " + e.getMessage());
        }
        return users;
    }

    public VBox createUsersBox(User user, VBox managementDetailsVBox) {
        VBox userBox = new VBox();


        userBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (userBox != selectedUserBox) {
                userBox.setStyle("-fx-background-color: #fef9f3;");
            }
        });

        userBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (userBox != selectedUserBox) {
                userBox.setStyle("-fx-background-color: transparent;");
            }
        });

        Label emailLabel = new Label("Account: " +user.getEmail());
        emailLabel.setFont(Font.font("System Bold", 18.0));
        Label usernameLabel = new Label("Username: " +user.getFullName());
        usernameLabel.setFont(Font.font(11.0));

        VBox.setMargin(emailLabel, new Insets(10, 0, 0, 10));
        VBox.setMargin(usernameLabel, new Insets(0, 0, 10, 10));

        userBox.getChildren().addAll(emailLabel, usernameLabel);

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(10, 0, 0, 0));
        userBox.getChildren().add(separator);

        // Handle user box click event
        userBox.setOnMouseClicked(event -> {
            if (selectedUserBox != null) {
                selectedUserBox.setStyle("-fx-background-color: transparent;");
            }
            userBox.setStyle("-fx-background-color: grey;");
            selectedUserBox = userBox;

            handleUserSelection(user, managementDetailsVBox);
        });
    

        return userBox;
    }

    public void setUserDetails(User user) {
        // Set the user details
        System.out.println("User details: " + user);
        selectedUser = user;
    }

    private void handleUserSelection(User user, VBox managementDetailsVBox) {
        managementDetailsVBox.getChildren().clear();
        managementDetailsVBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        managementDetailsVBox.setSpacing(10);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-management-details.fxml"));

            // Get the controller and set the AdminManagement instance
            AdminManagementDetailsController adminManagementDetailsController = new AdminManagementDetailsController();
            loader.setController(adminManagementDetailsController);
            adminManagementDetailsController.setAdminManagement(this);

            VBox notificationDetails = loader.load();

            // Set the user details and make the user details VBox visible
            setUserDetails(user);

            // Load user details in the controller
            adminManagementDetailsController.loadUserDetails();

            // Set the user details to the managementDetailsVBox
            managementDetailsVBox.getChildren().setAll(notificationDetails.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
