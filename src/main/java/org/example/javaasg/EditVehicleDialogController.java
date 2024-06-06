package org.example.javaasg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditVehicleDialogController implements Initializable {

    private Vehicles vehicle;
    private TableView<Vehicles> tableView;

    @FXML
    private TextField vehicleNameField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField seatField;
    @FXML
    private ComboBox<String> segmentField;
    @FXML
    private TextField fuelField;
    @FXML
    private ComboBox<String> gearboxField;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;

    public EditVehicleDialogController() {
    }

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    }

    public void setTableView(TableView<Vehicles> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleNameField.setText(vehicle.getVehicleName());
        brandField.setText(vehicle.getBrand());
        priceField.setText(vehicle.getPrice());
        seatField.setText(vehicle.getSeats());
        segmentField.setValue(vehicle.getSegment());
        fuelField.setText(vehicle.getFuelEconomy());
        gearboxField.setValue(vehicle.getTranmission());

        vehicleNameField.setEditable(false);
        brandField.setEditable(false);    

        segmentField.getItems().addAll("Sedan", "SUV", "MPV", "Hatchback");
        gearboxField.getItems().addAll("Manual", "Automatic");
    }

    public void saveVehicle() {
        if (vehicleNameField.getText().isEmpty() || brandField.getText().isEmpty() || priceField.getText().isEmpty() || seatField.getText().isEmpty() || segmentField.getValue().isEmpty() || gearboxField.getValue().isEmpty() || fuelField.getText().isEmpty()) {
            AlertHelper.showAlert("Input Error", "All fields are required!");
        } else if (!seatField.getText().endsWith(" Seats")) {
            AlertHelper.showAlert("Input Error", "Seats field must end with ' Seats'!");
        } else if (!fuelField.getText().endsWith("km/L")) {
            AlertHelper.showAlert("Input Error", "Fuel Economy field must end with 'km/L'!");
        } else if (!priceField.getText().matches("\\d+(\\.\\d+)?")) {
            AlertHelper.showAlert("Input Error", "Price must be a number!");
        } else if (!fuelField.getText().replace("km/L", "").trim().matches("\\d+(\\.\\d+)?")) {
            AlertHelper.showAlert("Input Error", "Fuel Economy before 'km/L' must be a number!");
        } else {
            try {    
    
                vehicle.setVehicleName(vehicleNameField.getText());
                vehicle.setBrand(brandField.getText());
                vehicle.setPrice(priceField.getText());
                vehicle.setSeats(seatField.getText());
                vehicle.setSegment(segmentField.getValue());
                vehicle.setGearbox(gearboxField.getValue());
                vehicle.setFuelEconomy(fuelField.getText());
    
                // Update the TableView
                tableView.refresh();
    
                // Update the text file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(Directories.getCarList(), false));
                    for (Vehicles v : tableView.getItems()) {
                        writer.write(v.getVehicleName() + ";" + v.getBrand() + ";" + v.getVehicleName().toLowerCase().replace(" ", "_") + ";" + v.getPrice() + ";" + v.getSegment() + ";" + v.getSeats() + ";" + v.getFuelEconomy() + ";" + v.getTranmission());
                        writer.newLine();
                    }
                    writer.close();
                    AlertHelper.showAlert("Edit Successfully", "" + vehicle.getVehicleName() + " has been updated successfully!");
                    closeDialog();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                AlertHelper.showAlert("Input Error", "Price must be a number!");
            }
        }
    }        
    public void closeDialog() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}