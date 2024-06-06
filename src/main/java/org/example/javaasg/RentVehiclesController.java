package org.example.javaasg;

// Java imports
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// JavaFX imports
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;


public class RentVehiclesController {
    @FXML
    private VBox rentalVBox;

    @FXML
    private VBox checkoutVBox;

    @FXML
    private ImageView carImageView;

    @FXML
    private Label carNameLabel;

    @FXML
    private Label carSegmentLabel;

    @FXML
    private Label carPriceLabel;

    @FXML
    private DatePicker pickupDatePicker;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private ComboBox<String> dropoffLocationMenu;

    @FXML
    private Label totalPrice;

    @FXML
    private Button BackButton;

    @FXML
    private Button checkoutButton;

    private static Vehicles car;

    public static void setCar(Vehicles car) {
        RentVehiclesController.car = car;
    }

    @FXML
    public void initialize() {
    totalPrice.setManaged(false);

    // Add menu items to the dropoff location menu
    dropoffLocationMenu.getItems().addAll("Asia Pacific University");
    carNameLabel.setText(car.getVehicleName());
    carSegmentLabel.setText(car.getSegment());
    carPriceLabel.setText("RM" +car.getPrice() + "/day");
        
    // Validate pickup date
    pickupDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            if (newValue.isBefore(LocalDate.now())) {
                AlertHelper.showAlert("Invalid Date", "Pickup date cannot be in the past.");
                pickupDatePicker.setValue(oldValue);
            } else if (returnDatePicker.getValue() != null && !newValue.isBefore(returnDatePicker.getValue())) {
                AlertHelper.showAlert("Invalid Date", "Pickup date must be before return date.");
                pickupDatePicker.setValue(oldValue);
            }
        }
    });
    
    // Validate return date
    returnDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            if (pickupDatePicker.getValue() == null) {
                AlertHelper.showAlert("Invalid Date", "Please select a pickup date first.");
                returnDatePicker.setValue(null);
            } else if (newValue.isBefore(pickupDatePicker.getValue().plusDays(1))) {
                AlertHelper.showAlert("Invalid Date", "Return date must be at least one day after the pickup date.");
                returnDatePicker.setValue(oldValue);
            } else {
                totalPrice.setManaged(true);
                long daysBetween = ChronoUnit.DAYS.between(pickupDatePicker.getValue(), newValue);
                try {
                    double pricePerDay = Double.parseDouble(car.getPrice());
                    double totalPriceValue = pricePerDay * daysBetween;
                    totalPrice.setText("Total Price: RM" + totalPriceValue + " for " + daysBetween + " days");
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price: " + e.getMessage());
                }
            }
        }
    });

        try {
            // Load the image as a resource
            Image image = new Image(getClass().getResourceAsStream(car.getImageName()));
            carImageView.setImage(image);
        } catch (IllegalArgumentException | NullPointerException e) {
             System.out.println("Image not found for car: " +car.getVehicleName()+ "||"+ "Image directory: " +car.getImageName());
        }
    }

    public void goBack(ActionEvent event){
        SceneSwitcher.switchScene(event, "vehicle-view.fxml");
    }

    public void checkOut(ActionEvent event){
        if (dropoffLocationMenu.getSelectionModel().getSelectedItem() == null) {
            AlertHelper.showAlert("Form Error", "Please select a drop-off location.");
            return;
        }    

        if (pickupDatePicker.getValue() == null) {
            AlertHelper.showAlert("Invalid Date", "Please select a pickup date first.");
            return;
        }

        if (returnDatePicker.getValue() == null) {
            AlertHelper.showAlert("Invalid Date", "Please select a return date first.");
            return;
        }

        double totalPriceValue = Double.parseDouble(totalPrice.getText().replace("Total Price: RM", "").split(" ")[0]);
        CheckoutController.setParameters(car, pickupDatePicker.getValue(), returnDatePicker.getValue(), totalPriceValue);

        try {
            // Load the checkout view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout-view.fxml"));
            VBox checkoutView = loader.load();

            // Disable the rental details VBox
            pickupDatePicker.setDisable(true);
            returnDatePicker.setDisable(true);
            dropoffLocationMenu.setDisable(true);
            BackButton.setDisable(true);
            checkoutButton.setDisable(true);

            // Update styling of rental details VBox and set visibility of the checkout vbox
            rentalVBox.setStyle("-fx-border-width: 1; -fx-border-radius: 6 0 0 6; -fx-background-color: #f9f9f9; -fx-border-color: #808080");
            checkoutVBox.setVisible(true);

            // Set the checkout view to checkoutVBox
            checkoutVBox.getChildren().setAll(checkoutView.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMenuButtonText(MenuButton menuButton, MenuItem menuItem) {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuButton.setText(menuItem.getText());
            }
        });
    }
}