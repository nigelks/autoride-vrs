package org.example.javaasg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.TableView;

public class EditVehicleDialog extends Application {

    private Vehicles vehicle;
    private TableView<Vehicles> tableView;

    public EditVehicleDialog(Vehicles vehicle, TableView<Vehicles> tableView) {
        this.vehicle = vehicle;
        this.tableView = tableView;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javaasg/edit-vehicle-dialog.fxml"));
            EditVehicleDialogController controller = new EditVehicleDialogController();
            controller.setVehicle(vehicle);
            controller.setTableView(tableView);
            loader.setController(controller);
            VBox root = loader.load();

            // Create a new Stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root, 800, 600));
            dialogStage.setTitle("Edit Vehicles");

            // Set the opacity of the primary stage
            primaryStage.setOpacity(0.7);

            // Restore the opacity when the dialog is closed
            dialogStage.setOnHidden(e -> primaryStage.setOpacity(1.0));

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}