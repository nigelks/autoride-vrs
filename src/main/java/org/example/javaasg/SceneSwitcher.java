// SceneSwitcher.java
package org.example.javaasg;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SceneSwitcher {
    public static void switchScene(ActionEvent event, String fxmlFileName) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Get the current stage properties
            double x = currentStage.getX();
            double y = currentStage.getY();
            double width = currentStage.getWidth();
            double height = currentStage.getHeight();

            // Load the new scene
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxmlFileName));
            Scene scene = new Scene(root, width, height);

            // Set the new scene
            currentStage.setScene(scene);

            // Apply the stage properties to the new scene
            currentStage.setX(x);
            currentStage.setY(y);
            currentStage.setWidth(width);
            currentStage.setHeight(height);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}