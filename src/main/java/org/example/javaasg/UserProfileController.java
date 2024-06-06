package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class UserProfileController {
    @FXML
    private VBox profileMainVbox;

    @FXML
    private TextField userProfileEmail;

    @FXML
    private TextField userProfileUsername;

    @FXML
    private VBox userProfileUsernameVBox;

    @FXML
    private VBox userProfileEmailVBox;

    @FXML
    private VBox userProfilePasswordVBox;

    @FXML
    private HBox profileMenuAccountDetails;

    @FXML
    private HBox profileMenuSecurity;

    public void initialize() {
        userProfileUsername.setPromptText(User.getLoggedInUserName());
        userProfileEmail.setPromptText(User.getLoggedInUserEmail());

        displayOnlyThisVBox(userProfileUsernameVBox, userProfileEmailVBox);
    }

    @FXML
    protected void onChangeUsernameButtonClick(javafx.scene.input.MouseEvent event) {
        UserProfileModel model = new UserProfileModel();
        model.createUsernameChangeBox(userProfileUsernameVBox, this);
    }

//    @FXML
//    protected void onChangeEmailButtonClick(javafx.scene.input.MouseEvent event) {
//        UserProfileModel model = new UserProfileModel();
//        model.createEmailChangeBox(userProfileEmailVBox, this);
//    }

    @FXML
    protected void onChangePasswordButtonClick(javafx.scene.input.MouseEvent event) {
        UserProfileModel model = new UserProfileModel();
        model.createPasswordChangeBox(userProfilePasswordVBox, this);
    }

    public void updateEmailPrompt() {
        userProfileEmail.setPromptText(User.getLoggedInUserEmail());
    }

    public void updateUsernamePrompt() {
        userProfileUsername.setPromptText(User.getLoggedInUserName());
    }

    // Styling methods
    @FXML
    protected void onLabelHover(javafx.scene.input.MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-opacity: 0.5;");
    }

    @FXML
    protected void onLabelHoverExit(javafx.scene.input.MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-opacity: 1;");
    }

    @FXML
    protected void onMenuHover(javafx.scene.input.MouseEvent event) {
        HBox hbox = (HBox) event.getSource();
        hbox.setStyle("-fx-background-color: #D7D7D7; -fx-border-width: 0 0 1 0; -fx-border-color: #808080; -fx-background-radius: 6 6 0 0");
    }

    @FXML
    protected void onMenuHoverExit(javafx.scene.input.MouseEvent event) {
        HBox hbox = (HBox) event.getSource();
        hbox.setStyle("-fx-background-color: #F4F4F4; -fx-border-width: 0 0 1 0; -fx-border-color: #808080; -fx-background-radius: 6 6 0 0");
    }

    private void displayOnlyThisVBox(VBox... vboxes) {
        // Hide all VBoxes
        userProfileUsernameVBox.setVisible(false);
        userProfileUsernameVBox.setManaged(false);
        userProfileEmailVBox.setVisible(false);
        userProfileEmailVBox.setManaged(false);
        userProfilePasswordVBox.setVisible(false);
        userProfilePasswordVBox.setManaged(false);

        // Show only the specified VBoxes
        int visibleCount = 0;
        for (VBox vbox : vboxes) {
            vbox.setVisible(true);
            vbox.setManaged(true);
            visibleCount++;
        }

        // Adjust the spacing of the parent VBox (Fix gap if only one VBox is visible)
        if (visibleCount > 1) {
            // Set the spacing to 10 if more than one VBox is visible
            profileMainVbox.setSpacing(10);
        } else {
            // Set the spacing to 0 if only one VBox is visible
            profileMainVbox.setSpacing(0);
        }
    }

    @FXML
    protected void onAccountDetailsMenuClick(javafx.scene.input.MouseEvent event) {
        displayOnlyThisVBox(userProfileUsernameVBox, userProfileEmailVBox);
    }

    @FXML
    protected void onSecurityMenuClick(javafx.scene.input.MouseEvent event) {
        displayOnlyThisVBox(userProfilePasswordVBox);
    }
}
