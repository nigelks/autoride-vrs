package org.example.javaasg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;


public class AddVehicles implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private TextField carModelField;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> segmentComboBox;

    @FXML
    private ComboBox<String> seatComboBox;

    @FXML
    private TextField fuelConsumptionField;

    @FXML
    private ComboBox<String> gearboxComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> brands = FXCollections.observableArrayList("Proton", "Perodua", "Honda", "Toyota");
        brandComboBox.setItems(brands);

        ObservableList<String> segments = FXCollections.observableArrayList("Sedan", "SUV", "MPV", "Hatchback");
        segmentComboBox.setItems(segments);

        ObservableList<String> seats = FXCollections.observableArrayList("2", "4", "5", "7");
        seatComboBox.setItems(seats);

        ObservableList<String> gearboxes = FXCollections.observableArrayList("Manual", "Automatic");
        gearboxComboBox.setItems(gearboxes);
    }

    @FXML
    private void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    @FXML
    private void addVehicle(ActionEvent event) {
        String carModel = carModelField.getText();
        String brand = brandComboBox.getValue();
        String price = priceField.getText();
        String segment = segmentComboBox.getValue();
        String seat = seatComboBox.getValue();
        String fuelConsumption = fuelConsumptionField.getText();
        String gearbox = gearboxComboBox.getValue();
        Image image = imageView.getImage();
    
        if (carModel.isEmpty() || brand == null || price.isEmpty() || segment == null || seat == null || fuelConsumption.isEmpty() || gearbox == null || image == null) {
            showEmptyFieldAlert();
            return;
        }
    
        if (!price.matches("\\d+") || !fuelConsumption.matches("\\d+(\\.\\d+)?")) {
            invalidPriceOrFuelAlert();
            return;
        }
    
        Vehicles vehicles = new Vehicles();
        vehicles.setVehicleName(carModel);
        vehicles.setBrand(brand);
        vehicles.setPrice(price);
        vehicles.setSegment(segment);
        vehicles.setSeats(seat);
        vehicles.setFuelEconomy(fuelConsumption);
        vehicles.setGearbox(gearbox);

        String imageName = carModel.toLowerCase().replace(' ', '_');
        Path outputPath = Paths.get("src/main/resources/assets/vehicles/" + imageName + ".png");
        
        try {
            Files.createDirectories(outputPath.getParent());
            
            // Load the image into an ImageView
            ImageView tempImageView = new ImageView(image);
            tempImageView.setPreserveRatio(true);
            tempImageView.setFitWidth(300);
            tempImageView.setFitHeight(300);
            
            // Create a new Image with the resized dimensions
            Image resizedImage = tempImageView.snapshot(null, null);
            
            // Write the resized image to file
            ImageIO.write(SwingFXUtils.fromFXImage(resizedImage, null), "png", outputPath.toFile());
            
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the data to the text file
        String data = carModel + ";" + brand + ";" + imageName + ";" + price + ";" + segment + ";" + seat + " Seats;" + fuelConsumption + "km/L;" + gearbox + "\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Directories.getCarList(), true))) {
            writer.write(data);
            writer.flush();
            AlertHelper.showAlert("Vehicles Added Successfully", "Redirecting back to admin home page.");
            
            SceneSwitcher.switchScene(event, "admin-vehicles-view.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    @FXML
    private void goBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "admin-vehicles-view.fxml");
    }

    private void showEmptyFieldAlert() {
        AlertHelper.showAlert("Adding Vehicles Failed", "All fields must be filled!");
    }

    private void invalidPriceOrFuelAlert () {
        AlertHelper.showAlert("Adding Vehicles Failed", "Price and Fuel Consumption must be a number.");
    }
    
}