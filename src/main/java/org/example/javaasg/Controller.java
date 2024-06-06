// Controller.java
package org.example.javaasg;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class Controller {
    @FXML
    protected void redirectToRegister(ActionEvent event) {
        SceneSwitcher.switchScene(event, "register-view.fxml");
    }

    @FXML
    protected void redirectToLogin(ActionEvent event) {
        SceneSwitcher.switchScene(event, "login-view.fxml");
    }
}