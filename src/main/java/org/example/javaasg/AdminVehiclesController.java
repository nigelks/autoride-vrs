package org.example.javaasg;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminVehiclesController {

    @FXML
    private Button addVehicleButton;

    @FXML
    private TableView<Vehicles> ViewCarList;

    @FXML
    private TableColumn<Vehicles, String> numberColumn;

    @FXML
    private TableColumn<Vehicles, String> vehicleColumn;

    @FXML
    private TableColumn<Vehicles, String> brandColumn;

    @FXML
    private TableColumn<Vehicles, String> segmentColumn;

    @FXML
    private TableColumn<Vehicles, String> seatColumn;

    @FXML
    private TableColumn<Vehicles, String> fuelColumn;

    @FXML
    private TableColumn<Vehicles, String> priceColumn;

    @FXML
    private TableColumn<Vehicles, Void> actionColumn;

    private VehicleReader vehicleReader;

    public void initialize() {
        vehicleReader = new VehicleReader();

        numberColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null) {
                    setText(empty ? null : Integer.toString(this.getTableRow().getIndex() + 1));
                }
                setAlignment(Pos.CENTER);
            }
        });
    

        Callback<TableColumn<Vehicles, String>, TableCell<Vehicles, String>> cellFactory
                = p -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item);
                setAlignment(Pos.CENTER);
            }
        };

        brandColumn.setCellFactory(cellFactory);
        segmentColumn.setCellFactory(cellFactory);
        seatColumn.setCellFactory(cellFactory);
        fuelColumn.setCellFactory(cellFactory);
        priceColumn.setCellFactory(cellFactory);

        vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        segmentColumn.setCellValueFactory(new PropertyValueFactory<>("segment"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ViewCarList.getItems().setAll(vehicleReader.getVehicles());

        actionColumn.setCellFactory(param -> new TableCell<Vehicles, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    editButton.setOnAction(event -> {
                        Vehicles vehicle = getTableView().getItems().get(getIndex());
                        EditVehicleDialog editVehicleDialog = new EditVehicleDialog(vehicle, ViewCarList);
                        Stage stage = new Stage();
                        editVehicleDialog.start(stage);
                    });
                    deleteButton.setOnAction(event -> {
                        Vehicles vehicle = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Delete Vehicle");
                        alert.setContentText("Are you sure you want to delete this vehicle?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK){
                            vehicleReader.deleteVehicle(vehicle);
                            ViewCarList.getItems().remove(vehicle);
                        }
                    });
                    HBox actionButtons = new HBox(editButton, deleteButton);
                    actionButtons.setSpacing(10);
                    actionButtons.setAlignment(Pos.CENTER);
                    setGraphic(actionButtons);
                }
            }
        });
    }

    @FXML
    private void addVehicleButton(ActionEvent event){
        SceneSwitcher.switchScene(event, "admin-add-vehicles.fxml");
    }
}