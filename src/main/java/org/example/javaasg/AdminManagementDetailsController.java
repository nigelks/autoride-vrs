package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AdminManagementDetailsController {
    @FXML
    private VBox managementUserUsernameVBox;

    @FXML
    private VBox managementUserPasswordVBox;

    @FXML
    private TextField managementUserUsernameField;

    @FXML
    private TextField managementUserPasswordField;

    private AdminManagement adminManagement;

    public void setAdminManagement(AdminManagement adminManagement) {
        this.adminManagement = adminManagement;
    }

    public void initialize() {
        loadUserDetails();
    }

    public void loadUserDetails() {
        if (adminManagement != null && adminManagement.getSelectedUser() != null) {
            User selectedUser = adminManagement.getSelectedUser();
            managementUserUsernameField.setPromptText(selectedUser.getFullName());
        }
    }

    @FXML
    protected void onManageUsernameButtonClick(javafx.scene.input.MouseEvent event) {
        AdminManagementModel model = new AdminManagementModel();
        model.createUsernameManagementBox(managementUserUsernameVBox, adminManagement.getSelectedUser(),this);
    }

    @FXML
    protected void onManagePasswordButtonClick(javafx.scene.input.MouseEvent event) {
        AdminManagementModel model = new AdminManagementModel();
        model.createPasswordManagementBox(managementUserPasswordVBox, adminManagement.getSelectedUser(), this);
    }

    public void updateUsernamePrompt() {
        User selectedUser = adminManagement.getSelectedUser();
        managementUserUsernameField.setPromptText(selectedUser.getFullName());
    }

    public void updatePasswordPrompt() {
        User selectedUser = adminManagement.getSelectedUser();
        managementUserPasswordField.setPromptText(selectedUser.getPassword());
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
}