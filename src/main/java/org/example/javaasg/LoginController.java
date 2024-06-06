package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.javaasg.User;
import org.example.javaasg.Register;
import org.example.javaasg.UserReader;
import org.example.javaasg.UserRepository;
import javafx.scene.input.MouseEvent;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField loginPasswordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String email = loginTextField.getText();
        String password = loginPasswordField.getText();

        Login login = new Login();
        String role = login.validateLogin(email, password);

        if (!role.equals("invalid")) {
            // Login successful, switch to the appropriate scene
            User.setIsAdmin(role.equals("admin"));
            String sceneName = User.getIsAdmin() ? "admin-vehicles-view.fxml" : "vehicle-view.fxml";
            SceneSwitcher.switchScene(event, sceneName);
        } else {
            // Login failed, show an error message
            welcomeText.setText("Invalid username or password.");
        }
    }

    @FXML
    protected void onLoginCancelButtonClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "hello-view.fxml");
    }

    @FXML
    protected void onUnfocusClick(MouseEvent event) {
        if (!loginTextField.getBoundsInParent().contains(event.getX(), event.getY())) {
            loginTextField.getParent().requestFocus();
        }
    }
}
