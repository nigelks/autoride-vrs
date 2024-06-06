package org.example.javaasg;

// Java imports
import java.util.function.Consumer;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class VehicleFilterCheckbox {
    private String label;
    private VBox container;
    private Consumer<ActionEvent> action;

    public VehicleFilterCheckbox(String label, VBox container, Consumer<ActionEvent> action) {
        this.label = label;
        this.container = container;
        this.action = action;
    }

    public CheckBox create() {
        CheckBox checkBox = new CheckBox(label);
        checkBox.setFocusTraversable(false);
        checkBox.setPadding(new Insets(0,0,7.5,20)); // Padding
        checkBox.setOnAction(action::accept);
        container.getChildren().add(checkBox);

        container.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #808080; -fx-padding: 0 0 7.5 0;");

        return checkBox;
    }
}