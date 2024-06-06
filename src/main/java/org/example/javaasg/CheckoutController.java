package org.example.javaasg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import com.almasb.fxgl.entity.action.Action;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CheckoutController {
    @FXML
    private Label carName;

    @FXML
    private Label rentalDuration;

    @FXML
    private Label pricePerDay;

    @FXML
    private Label checkoutPrice;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField CVV;

    @FXML
    private TextField expiryDate;

    @FXML
    private TextField cardName;

    @FXML
    private ImageView cardLogo;

    private static Vehicles car;
    private static LocalDate pickupDate;
    private static LocalDate returnDate;
    private static double totalPrice;
    private OrderIdGenerator orderIdGenerator;

    public CheckoutController() {
        orderIdGenerator = new OrderIdGenerator();
    }


    
    public static void setParameters(Vehicles car, LocalDate pickupDate, LocalDate returnDate, double totalPrice) {
        CheckoutController.car = car;
        CheckoutController.pickupDate = pickupDate;
        CheckoutController.returnDate = returnDate;
        CheckoutController.totalPrice = totalPrice;
    }    

    @FXML
    public void initialize() {
        if (car != null) {
            carName.setText(car.getVehicleName());
            pricePerDay.setText("RM" + car.getPrice() + " per day");
            long daysBetween = ChronoUnit.DAYS.between(pickupDate, returnDate);
            rentalDuration.setText("Rental Duration: " + daysBetween + " days");
            checkoutPrice.setText("Total Price: RM" + String.valueOf(totalPrice));
        }

        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            String cardType = getCardType(newValue);
            if (cardType != null) {
                cardNumber.setPromptText(cardType);
            } else {
                cardNumber.setPromptText("Card Number");
            }
        });

        // Limit card number text field to 16 digits
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 16) {
                cardNumber.setText(oldValue);
            }
        });

        expiryDate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 2 && oldValue.length() != 3) {
                expiryDate.setText(newValue + "/");
            }
        });
    }

    private String getCardType(String cardNumber) {
        // Regex for Mastercard and Visa
        String mastercardRegex = "^5[1-5][0-9]{14}$|^2[2-7][0-9]{14}$";
        String visaRegex = "^4[0-9]{12}(?:[0-9]{3})?$";

        if (cardNumber.matches(mastercardRegex)) {
            cardLogo.setImage(new Image(Directories.getMastercardLogo()));
            return "Mastercard";
        } else if (cardNumber.matches(visaRegex)) {
            cardLogo.setImage(new Image(Directories.getVisaLogo()));
            return "Visa";
        } else {
            cardLogo.setImage(null);
            return null;
        }
    }

    private boolean validatePayment() {
        String cardNumberText = cardNumber.getText();
        String cvvText = CVV.getText();
        String expiryDateText = expiryDate.getText();
        String cardNameText = cardName.getText();

        // Validate card number
        if (cardNumberText == null || cardNumberText.length() != 16) {
            AlertHelper.showAlert("Invalid Card Number", "Card number must be 16 digits.");
            return false;
        }

        // Validate CVV
        if (cvvText == null || cvvText.length() != 3) {
            AlertHelper.showAlert("Invalid CVV", "CVV must be 3 digits.");
            return false;
        }

        // Validate expiry date
        if (expiryDateText == null || !expiryDateText.matches("\\d{2}/\\d{2}")) {
            AlertHelper.showAlert("Invalid Expiry Date", "Expiry date must be in the format MM/YY.");
            return false;
        } else {
            int month = Integer.parseInt(expiryDateText.substring(0, 2));
            int year = Integer.parseInt(expiryDateText.substring(3, 5)) + 2000; // add 2000 to get the full year
            int currentYear = LocalDate.now().getYear();

            if (month < 1 || month > 12 || year < currentYear) {
                AlertHelper.showAlert("Invalid Expiry Date", "Please enter a valid month and year.");
                return false;
            }
        }

        // Validate card name
        if (cardNameText == null || cardNameText.isEmpty()) {
            AlertHelper.showAlert("Invalid Card Name", "Card name cannot be empty.");
            return false;
        }

        return true;
    }

    public void cancelOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Order");
        alert.setHeaderText("Are you sure you want to cancel the order?");
    
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AlertHelper.showAlert("Order Cancelled", "Your order has been cancelled.");
            SceneSwitcher.switchScene(event, "vehicle-view.fxml");
        }
    }

    public void placeOrder(ActionEvent event) {
        try {
            System.out.println("Place Order button pressed");
            if (validatePayment()) {

                String email = User.getLoggedInUserEmail();
                String user = User.getLoggedInUserName();
                
                String userOrderDetails = String.format("%s;%s;%s;%d;%d;%s;%s",
                    user,
                    email,
                    car.getVehicleName(),
                    ChronoUnit.DAYS.between(pickupDate, returnDate),
                    (int)totalPrice,
                    pickupDate.toString(),
                    returnDate.toString());  

                String orderId = orderIdGenerator.getNextOrderId();
                long currentTimeMillis = System.currentTimeMillis();
                String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(currentTimeMillis));


                String orderDetails = String.format("%s;%s;%s;%s;%d;%d;%s;%s;%s;%d;%d",
                    orderId,
                    user,
                    email,
                    car.getVehicleName(),
                    ChronoUnit.DAYS.between(pickupDate, returnDate),
                    (int)totalPrice,
                    pickupDate.toString(),
                    returnDate.toString(),
                    timestamp,
                    0,
                    0);
                    

                // Save to user's file
                String userFileName = "./db/userorder/" + email + "_order.txt";
                saveOrderDetails(userFileName, userOrderDetails);
        
                // Save to admin's file
                String adminFileName = "./db/all_orders.txt";
                saveOrderDetails(adminFileName, orderDetails);
        
                AlertHelper.showAlert("Order Placed", "Your order has been placed.");
                SceneSwitcher.switchScene(event, "vehicle-view.fxml");
            } else {
                AlertHelper.showAlert("Payment Failed", "Payment validation failed. Please check your payment details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    private void saveOrderDetails(String fileName, String orderDetails) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
    
            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.println(orderDetails);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
