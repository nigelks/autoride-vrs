package org.example.javaasg;

// Java imports
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class CustomerRentalHistoryController {
    @FXML
    private TableView<CustomerRentalHistory> RentalHistoryTable;
    @FXML
    private TableColumn<CustomerRentalHistory, String> numberColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, String> vehicleColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, String> durationColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, String> priceColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, String> startDateColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, String> endDateColumn;
    @FXML
    private TableColumn<CustomerRentalHistory, Double> totalPriceColumn;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button rentalHistoryFilterReset;
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();

    public void initialize() {
        numberColumn.setCellFactory(new Callback<>() {
            
            @Override
            public TableCell<CustomerRentalHistory, String> call(TableColumn<CustomerRentalHistory, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : Integer.toString(getIndex() + 1));
                        setStyle("-fx-alignment: CENTER;");
                    }
                };
            }
        });

        vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("rentalDuration"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rentalPrice"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentalStartDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentalEndDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalRentalPrice"));

        // Bind the width property of each TableColumn to the width property of the TableView
        numberColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.1));
        vehicleColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));
        durationColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));
        priceColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));
        startDateColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));
        endDateColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));
        totalPriceColumn.prefWidthProperty().bind(RentalHistoryTable.widthProperty().multiply(0.15));

        totalPriceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CustomerRentalHistory, Double> call(TableColumn<CustomerRentalHistory, Double> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.format("%.2f", item));
                            setStyle("-fx-alignment: CENTER;");
                        }
                    }
                };
            }
        });

        // Read the rental histories and populate the TableView
        resetFilters();

        // Center the content of all columns
        centerColumn(durationColumn);
        centerColumn(priceColumn);
        centerColumn(startDateColumn);
        centerColumn(endDateColumn);
//        centerColumn(totalPriceColumn);

        // Bind the DatePicker values to the ObjectProperties
        startDate.bind(startDatePicker.valueProperty());
        endDate.bind(endDatePicker.valueProperty());

        // Add change listeners to the DatePicker controls
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterRentalHistories());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterRentalHistories());

        // Add reset filter action to the reset button
        rentalHistoryFilterReset.setOnAction(event -> {
            resetFilters();
        });
    }

    private void centerColumn(TableColumn<CustomerRentalHistory, String> column) {
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<CustomerRentalHistory, String> call(TableColumn<CustomerRentalHistory, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                            setStyle("-fx-alignment: CENTER;");
                        }
                    }
                };
            }
        });
    }

    // Add a new field to store the original data
    private ObservableList<CustomerRentalHistory> originalData;

    private void resetFilters() {
        // Reset the DatePicker values
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        // Read the rental histories from the file
        CustomerRentalHistoryReader reader = new CustomerRentalHistoryReader();
        List<CustomerRentalHistory> rentalHistories = reader.readRentalHistoriesFromFile();

        // Update the TableView with the rental histories
        originalData = FXCollections.observableArrayList(rentalHistories);
        RentalHistoryTable.setItems(originalData);
    }

    private void filterRentalHistories() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ObservableList<CustomerRentalHistory> filteredList = FXCollections.observableArrayList();
    
        LocalDate effectiveEndDate = endDate.get() != null ? endDate.get() : LocalDate.MAX;
    
        if (startDatePicker.getValue() != null && endDate.get() != null && endDate.get().isBefore(startDatePicker.getValue().plusDays(1))) {
            // Show an error message and return
            AlertHelper.showAlert("Date Error", "The start date cannot be empty and return date must be at least a day after the pickup date.");
            return;
        }
    
        for (CustomerRentalHistory history : originalData) {
            LocalDate rentalStartDate = LocalDate.parse(history.getRentalStartDate(), formatter);
            LocalDate rentalEndDate = LocalDate.parse(history.getRentalEndDate(), formatter);    
    
            if ((startDatePicker.getValue() == null || !rentalStartDate.isBefore(startDatePicker.getValue())) && !rentalEndDate.isAfter(effectiveEndDate)) {
                filteredList.add(history);
            }
        }
    
        RentalHistoryTable.setItems(filteredList);
    }
}