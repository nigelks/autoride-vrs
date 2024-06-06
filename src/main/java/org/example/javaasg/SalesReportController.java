package org.example.javaasg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class SalesReportController {
    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private TableView<GenerateSalesReport> ViewReport;

    @FXML
    private TableColumn<GenerateSalesReport, String> numberColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> vehicleColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> customerColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> emailColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> pickupColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> returnColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> daysColumn;

    @FXML
    private TableColumn<GenerateSalesReport, String> priceColumn;

    public void initialize() {

    yearComboBox.getItems().addAll("2021", "2022", "2023", "2024");
    monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    // Add listeners to the ComboBoxes
    yearComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> filterSalesReports());
    monthComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> filterSalesReports());
    

    numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
    vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
    customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    pickupColumn.setCellValueFactory(new PropertyValueFactory<>("pickup"));
    returnColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    ViewReport.setItems(GenerateSalesReport.getSalesReports());

    Callback<TableColumn<GenerateSalesReport, String>, TableCell<GenerateSalesReport, String>> cellFactory
        = p -> new TableCell<>() {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? "" : item);
        setAlignment(Pos.CENTER);
    }
    };


    numberColumn.setCellFactory(cellFactory);
    customerColumn.setCellFactory(cellFactory);
    emailColumn.setCellFactory(cellFactory);
    pickupColumn.setCellFactory(cellFactory);
    returnColumn.setCellFactory(cellFactory);
    daysColumn.setCellFactory(cellFactory);
    priceColumn.setCellFactory(cellFactory);

    calculateTotalSales();
    }
     
    private void filterSalesReports() {
        String selectedYear = yearComboBox.getSelectionModel().getSelectedItem();
        String selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();
    
        ObservableList<GenerateSalesReport> filteredReports = FXCollections.observableArrayList();
    
        if (selectedYear != null && selectedMonth != null) {
            int monthIndex = monthComboBox.getItems().indexOf(selectedMonth) + 1; // Convert month name to month number
            String monthString = monthIndex < 10 ? "0" + monthIndex : String.valueOf(monthIndex); // Ensure the month is two digits
    
            for (GenerateSalesReport report : GenerateSalesReport.getSalesReports()) {
                if (report.getPickup().startsWith(selectedYear + "-" + monthString)) {
                    filteredReports.add(report);
                }
            }
        } else if (selectedYear != null) {
            for (GenerateSalesReport report : GenerateSalesReport.getSalesReports()) {
                if (report.getPickup().startsWith(selectedYear)) {
                    filteredReports.add(report);
                }
            }
        } else if (selectedMonth != null) {
            int monthIndex = monthComboBox.getItems().indexOf(selectedMonth) + 1; // Convert month name to month number
            String monthString = monthIndex < 10 ? "0" + monthIndex : String.valueOf(monthIndex); // Ensure the month is two digits
    
            for (GenerateSalesReport report : GenerateSalesReport.getSalesReports()) {
                if (report.getPickup().contains("-" + monthString + "-")) {
                    filteredReports.add(report);
                }
            }
        }
    
        ViewReport.setItems(filteredReports);
        calculateTotalSales();
    }

    private void calculateTotalSales() {
        double totalSales = 0;
        for (GenerateSalesReport sale : ViewReport.getItems()) {
            totalSales += Double.parseDouble(sale.getPrice());
        }
        totalSalesLabel.setText("Total Sales: RM" + totalSales);
    }
}
