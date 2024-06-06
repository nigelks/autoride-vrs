package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminManagementController {
    @FXML
    private VBox managementAccountVBox;

    @FXML
    private VBox managementDetailsVBox;

    public void initialize() {
        List<User> users = new AdminManagement().loadUsers();

        if (users == null) {
            return;
        }

        managementAccountVBox.getChildren().clear();

        for (User user : users) {
            // Skip the logged-in user
            if (user.getEmail().equals(User.getLoggedInUserEmail())) {
                continue;
            }
            
            AdminManagement adminManagement = new AdminManagement();
            VBox usersBox = adminManagement.createUsersBox(user, managementDetailsVBox);
            managementAccountVBox.getChildren().add(usersBox);
        }
    }
}
