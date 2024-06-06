package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Register {

    @FXML
    private TextField enterFullName;

    @FXML
    private TextField enterEmail;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private PasswordField enterConfirmPassword;

    @FXML
    private Label FieldErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Label confirmPasswordErrorLabel;

    private UserRepository userRepository = new UserRepository();

    public boolean validateName() {
        String fullName = enterFullName.getText();
        boolean isValid = ValidationUtils.validateName(fullName);
        FieldErrorLabel.setVisible(!isValid);

        enterFullName.setOnKeyTyped(event -> {
            String newText = enterFullName.getText();
            boolean newIsValid = !newText.isEmpty();
            FieldErrorLabel.setVisible(!newIsValid);
        });

        return isValid;
    }

    public boolean validateEmail() {
        String email = enterEmail.getText();
        boolean isValid = ValidationUtils.validateEmail(email) && ValidationUtils.isEmailUnique(email, userRepository);
        emailErrorLabel.setVisible(!isValid);
        if (userRepository.isEmailInUse(email)) {
            emailErrorLabel.setText("This email is already in use, please use another email");
        }
        return isValid;
    }


    public boolean validatePassword() {
        String password = enterPassword.getText();
        boolean isStrongPassword = ValidationUtils.validatePassword(password);
        passwordErrorLabel.setVisible(!isStrongPassword);
        return isStrongPassword;
    }

    public boolean validateConfirmPassword() {
        String password = enterPassword.getText();
        String confirmPassword = enterConfirmPassword.getText();
        boolean isValid = ValidationUtils.validatePassword(password, confirmPassword);
        confirmPasswordErrorLabel.setVisible(!isValid);
        return isValid;
    }

    public void registerAccount(ActionEvent event) {
        if (validateName() && validateEmail() && validatePassword() && validateConfirmPassword()) {
            User newUser = new User(
                enterEmail.getText(),
                enterFullName.getText(),
                hashPassword(enterPassword.getText()),
                0
            );
            userRepository.saveUser(newUser);
            SceneSwitcher.switchScene(event, "login-view.fxml");
            showSuccessAlert();
        } else {
            showValidationError();
        }
    }

    protected String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void linkToLogin(ActionEvent event) {
        SceneSwitcher.switchScene(event, "login-view.fxml");
    }

    public void onRegisterReturnBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "hello-view.fxml");
    }

    private void showSuccessAlert() {
        AlertHelper.showAlert("Registration Successful", "Congratulations! Your account has been successfully registered.");
    }

    private void showValidationError() {
        AlertHelper.showAlert("Registration failed", "Please ensure that your data is filled and error-free.");
    }
}