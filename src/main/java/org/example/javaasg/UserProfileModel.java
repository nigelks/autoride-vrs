package org.example.javaasg;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

public class UserProfileModel {
    // Create a new UserRepository object for validation
    UserRepository userRepository = new UserRepository();

    public VBox createEmailChangeBox(VBox vBoxToAddTo, UserProfileController controller) {
        // Check if the VBox is already present
        for (Node node : vBoxToAddTo.getChildren()) {
            if (node instanceof VBox && "emailChangeBox".equals(node.getId())) {
                return null; // Return null or the existing VBox
            }
        }

        // Create a new VBox to hold the email change form
        VBox emailChangeBox = new VBox();
        emailChangeBox.setSpacing(10);
        emailChangeBox.setId("emailChangeBox");

        // Create a label for the email change form
        Label emailChangeLabel = new Label("New Email Address:");
        emailChangeLabel.setStyle("-fx-font-weight: bold;");

        // Create a TextField for the new email
        TextField newEmailField = new TextField();
        newEmailField.setPromptText("Enter new email");

        // Create a "Cancel" button and it's action
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            newEmailField.clear();
            vBoxToAddTo.getChildren().remove(emailChangeBox);
        });

        // Create a "Proceed" button and it's action
        Button proceedButton = new Button("Proceed");
        proceedButton.setOnAction(e -> {
            if (ValidationUtils.validateEmail(newEmailField.getText()) && !userRepository.isEmailInUse(newEmailField.getText())) {
                // Update the email in users.txt
                String oldEmail = User.getLoggedInUserEmail();
                String newEmail = newEmailField.getText();
                userRepository.updateUserEmail(oldEmail, newEmail);

                // Update the email in User
                User.setLoggedInUserEmail(newEmail);
                controller.updateEmailPrompt();

                // Create a success label and add it to vBoxToAddTo
                createSuccessLabel(vBoxToAddTo);

                // Remove the emailChangeBox from vBoxToAddTo
                if (vBoxToAddTo.getChildren().contains(emailChangeBox)) {
                    vBoxToAddTo.getChildren().remove(emailChangeBox);
                }
            } else {
                // Remove existing warningBox if present
                emailChangeBox.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof Label && ((Label) child).getText().equals("Invalid email or email already in use. Please try again.")));

                // Create a warning VBox and insert it at the second position (index 1)
                VBox warningBox = createWarningVBox("Invalid email or email already in use. Please try again.");
                emailChangeBox.getChildren().add(2, warningBox);
            }
        });

        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(cancelButton, proceedButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setSpacing(10);

        // Add the elements to the emailChangeBox
        emailChangeBox.getChildren().addAll(emailChangeLabel, newEmailField, buttonBox);

        // Pass the emailChangeBox to function
        vBoxToAddTo.getChildren().add(emailChangeBox);

        return emailChangeBox;
    }

    public VBox createPasswordChangeBox(VBox vBoxToAddTo, UserProfileController controller) {
        // Check if the VBox is already present
        for (Node node : vBoxToAddTo.getChildren()) {
            if (node instanceof VBox && "passwordChangeBox".equals(node.getId())) {
                return null; // Return null or the existing VBox
            }
        }

        // Create a new VBox to hold the password change form
        VBox passwordChangeBox = new VBox();
        passwordChangeBox.setSpacing(10);
        passwordChangeBox.setId("passwordChangeBox");

        // Create a label for the old password change form
        Label currentPasswordLabel = new Label("Enter current password:");
        currentPasswordLabel.setStyle("-fx-font-weight: bold;");

        // Create a TextField for the current password
        TextField currentPasswordField = new TextField();
        currentPasswordField.setPromptText("Enter current password");

        // Create a label for the password change form
        Label passwordChangeLabel = new Label("New password:");
        passwordChangeLabel.setStyle("-fx-font-weight: bold;");

        // Create a TextField for the new password
        TextField newPasswordField = new TextField();
        newPasswordField.setPromptText("Enter new password");

        // Create a "Cancel" button and it's action
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            currentPasswordField.clear();
            newPasswordField.clear();
            vBoxToAddTo.getChildren().remove(passwordChangeBox);
        });

        // Create a "Proceed" button and it's action
        Button proceedButton = new Button("Proceed");
        proceedButton.setOnAction(e -> {
            // Hash the current password
            Register register = new Register();
            String currentHashedPassword = register.hashPassword(currentPasswordField.getText());

            UserReader userReader = new UserReader();
            if (Objects.equals(userReader.getPasswordByEmail(User.getLoggedInUserEmail()), currentHashedPassword)) {
                // Remove existing warningBox if present
                passwordChangeBox.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof Label && ((Label) child).getText().equals("Invalid current password. Please try again.")));

                if (ValidationUtils.validatePassword(newPasswordField.getText())) {
                    // Update the password in users.txt
                    String currentEmail = User.getLoggedInUserEmail();
                    String newPassword = newPasswordField.getText();
                    userRepository.updateUserPassword(currentEmail, newPassword);

                    // Create a success label and add it to vBoxToAddTo
                    createSuccessLabel(vBoxToAddTo);

                    // Remove the emailChangeBox from vBoxToAddTo
                    if (vBoxToAddTo.getChildren().contains(passwordChangeBox)) {
                        vBoxToAddTo.getChildren().remove(passwordChangeBox);
                    }
                } else {
                    // Remove existing warningBox if present
                    passwordChangeBox.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof Label && ((Label) child).getText().equals("Weak password. Please enter a password with at least 8 characters, including a number and a special character.")));

                    // Create a warning VBox and insert it at the second position (index 4)
                    VBox warningBox = createWarningVBox("Weak password. Please enter a password with at least 8 characters, including a number and a special character.");
                    passwordChangeBox.getChildren().add(4, warningBox);
                }
            } else {
                // Remove existing warningBox if present
                passwordChangeBox.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof Label && ((Label) child).getText().equals("Invalid current password. Please try again.")));

                // Create a warning VBox and insert it at the second position (index 2)
                VBox warningBox = createWarningVBox("Invalid current password. Please try again.");
                passwordChangeBox.getChildren().add(2, warningBox);
            }

        });

        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(cancelButton, proceedButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setSpacing(10);

        // Add the elements to the emailChangeBox
        passwordChangeBox.getChildren().addAll(currentPasswordLabel, currentPasswordField, passwordChangeLabel, newPasswordField, buttonBox);

        // Pass the emailChangeBox to function
        vBoxToAddTo.getChildren().add(passwordChangeBox);

        return passwordChangeBox;
    }

    public VBox createUsernameChangeBox(VBox vBoxToAddTo, UserProfileController controller) {
        // Check if the VBox is already present
        for (Node node : vBoxToAddTo.getChildren()) {
            if (node instanceof VBox && "usernameChangeBox".equals(node.getId())) {
                return null; // Return null or the existing VBox
            }
        }

        // Create a new VBox to hold the email change form
        VBox usernameChangeBox = new VBox();
        usernameChangeBox.setSpacing(10);
        usernameChangeBox.setId("usernameChangeBox");

        // Create a label for the username change form
        Label usernameChangeLabel = new Label("New Username:");
        usernameChangeLabel.setStyle("-fx-font-weight: bold;");

        // Create a TextField for the new username
        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("Enter username");

        // Create a "Cancel" button and it's action
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            newUsernameField.clear();
            vBoxToAddTo.getChildren().remove(usernameChangeBox);
        });

        // Create a "Proceed" button and it's action
        Button proceedButton = new Button("Proceed");
        proceedButton.setOnAction(e -> {
            if (ValidationUtils.validateName(newUsernameField.getText())) {
                // Update the username in users.txt
                String oldUsername = User.getLoggedInUserName();
                String newUsername = newUsernameField.getText();
                userRepository.updateUserName(oldUsername, newUsername);

                // Update the username in User
                User.setLoggedInUserName(newUsername);
                controller.updateUsernamePrompt();

                // Create a success label and add it to vBoxToAddTo
                createSuccessLabel(vBoxToAddTo);

                // Remove the usernameChangeBox from vBoxToAddTo
                if (vBoxToAddTo.getChildren().contains(usernameChangeBox)) {
                    vBoxToAddTo.getChildren().remove(usernameChangeBox);
                }


            } else {
                // Remove existing warningBox if present
                usernameChangeBox.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof Label && ((Label) child).getText().equals("Invalid username. Please try again.")));

                // Create a warning VBox and insert it at the second position (index 2)
                VBox warningBox = createWarningVBox("Invalid username. Please try again.");
                usernameChangeBox.getChildren().add(2, warningBox);
            }
        });

        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(cancelButton, proceedButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setSpacing(10);

        // Add the elements to the emailChangeBox
        usernameChangeBox.getChildren().addAll(usernameChangeLabel, newUsernameField, buttonBox);

        // Pass the emailChangeBox to function
        vBoxToAddTo.getChildren().add(usernameChangeBox);

        return usernameChangeBox;
    }

    public VBox createWarningVBox(String message) {
        // Create a new VBox to hold the warning message
        VBox warningBox = new VBox();
        warningBox.setPadding(new Insets(5, 10, 5, 10));
        warningBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(1))));

        // Create a label for the warning message
        Label warningLabel = new Label();
        warningLabel.setText(message);
        warningLabel.setStyle("-fx-text-fill: red;");

        // Add the warningLabel to the warningBox
        warningBox.getChildren().add(warningLabel);

        return warningBox;
    }

    public void createSuccessLabel(VBox vBoxToAddTo) {
        // Create a label for the success message
        Label successLabel = new Label("Changes saved successfully!");
        successLabel.setStyle("-fx-text-fill: green;");

        // Create a PauseTransition with a duration of 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        // Set the action to be performed after the PauseTransition is over
        pause.setOnFinished(event -> vBoxToAddTo.getChildren().remove(successLabel));

        // Start the PauseTransition
        pause.play();

        vBoxToAddTo.getChildren().add(successLabel);
    }
}
