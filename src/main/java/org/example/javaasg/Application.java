package org.example.javaasg;

// JavaFX imports
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the root layout from the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("AutoRide Rentals");

        // Set the minimum width and height of the stage
        primaryStage.setMinWidth(1500);
        primaryStage.setMinHeight(800);

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}