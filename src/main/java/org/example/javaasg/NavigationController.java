package org.example.javaasg;

// Java imports
import java.util.Arrays;
import java.util.List;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class NavigationController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private HBox navMenuHBox;

    @FXML
    private Button navMenuFindCars;

    @FXML
    private Button navMenuRentalHistory;

    @FXML
    private Button navMenuSalesReport;

    @FXML
    private Button navMenuAdminNotification;

    @FXML
    private Button navMenuCustNotification;

    @FXML
    private Button navMenuManagement;

    @FXML
    private Button navMenuProfile;

    private List<Button> buttons;

    // Getters
    public HBox getNavMenuHBox() {
        return navMenuHBox;
    }

    public Button getNavMenuRentalHistory() {
        return navMenuRentalHistory;
    }

    public Button getNavMenuSalesReport() {
        return navMenuSalesReport;
    }

    public Button getNavMenuAdminNotification() {
        return navMenuAdminNotification;
    }

    public Button getNavMenuCustNotification() {
        return navMenuCustNotification;
    }

    public Button getNavMenuManagement() {
        return navMenuManagement;
    }

    // Static variable to store the ID of the active button, set default to Find Cars
    private static String activeButtonId = "navMenuFindCars";

    // Method to set the ID of the active button
    public static void setActiveButtonId(String buttonId) {
        activeButtonId = buttonId;
    }

    public void initialize() {
        // Set welcome label
        String loggedInEmail = User.getLoggedInUserEmail();
        welcomeLabel.setText("User: " + (loggedInEmail != null ? loggedInEmail : ""));

        // Nav menu visibility
        NavigationButton navigationButton = new NavigationButton();
        boolean isAdmin = User.getIsAdmin();
        navigationButton.showButtons(this, isAdmin);

        // Initialize the list of buttons
        buttons = Arrays.asList(navMenuFindCars,
                navMenuRentalHistory,
                navMenuSalesReport,
                navMenuAdminNotification,
                navMenuCustNotification,
                navMenuManagement,
                navMenuProfile);

        // Set the active button
        if (activeButtonId != null) {
            for (Button button : buttons) {
                if (button.getId().equals(activeButtonId)) {
                    setActiveButton(button);
                    break;
                }
            }
        }
    }

    @FXML
    protected void onFindCarsClick(ActionEvent event) {
        setActiveButtonId("navMenuFindCars");
        if(User.getIsAdmin()) {
            SceneSwitcher.switchScene(event, "admin-vehicles-view.fxml");
        } else {
            SceneSwitcher.switchScene(event, "vehicle-view.fxml");
        }
    }

    @FXML
    protected void onRentalHistoryClick(ActionEvent event) {
        setActiveButtonId("navMenuRentalHistory");
        SceneSwitcher.switchScene(event, "rental-history-view.fxml");
    }

    @FXML
    protected void onProfileClick(ActionEvent event) {
        setActiveButtonId("navMenuProfile");
        SceneSwitcher.switchScene(event, "user-profile.fxml");
    }

    @FXML
    protected void onLogoutClick(ActionEvent event) {
        setActiveButtonId("navMenuFindCars"); // Reset the active button
        SceneSwitcher.switchScene(event, "hello-view.fxml");
    }

    @FXML
    protected void onSalesReportClick(ActionEvent event) {
        setActiveButtonId("navMenuSalesReport");
        SceneSwitcher.switchScene(event, "admin-report.fxml");
    }

    @FXML
    protected void onCustomerNotificationClick(ActionEvent event) {
        setActiveButtonId("navMenuCustNotification");
        SceneSwitcher.switchScene(event, "customer-notification.fxml");
    }

    @FXML
    protected void onAdminNotificationClick(ActionEvent event) {
        setActiveButtonId("navMenuAdminNotification");
        SceneSwitcher.switchScene(event, "customer-notification.fxml");
    }

    @FXML
    protected void onManagementClick(ActionEvent event) {
        setActiveButtonId("navMenuManagement");
        SceneSwitcher.switchScene(event, "admin-management.fxml");
    }

    public void setActiveButton(Button ActiveButton) {
        Border thickBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2)));
        Border resetBorder = Border.EMPTY;

        for (Button button : buttons) {
            if (button == ActiveButton) {
                button.setBorder(thickBorder);
                button.setStyle("-fx-background-radius: 5;");
            } else {
                button.setBorder(resetBorder);
            }
        }
    }
}
