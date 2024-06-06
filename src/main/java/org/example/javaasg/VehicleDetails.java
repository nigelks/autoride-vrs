package org.example.javaasg;

// JavaFX imports
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.image.Image;

public class VehicleDetails {
    public VBox createVBox(VBox parentVbox, String detail, String imageResource, String styleString) {
        VBox vbox = new VBox();
        ImageView imageView = new ImageView();
        Label label = new Label();

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setSmooth(true);

        try {
            // Load the image as a resource
            Image image = new Image(getClass().getResourceAsStream(imageResource));
            imageView.setImage(image); // Set the image on the imageView
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(detail + " static image not found for car: " + "Image directory: " + imageResource);
        }

        // Label
        label.setText(detail);
        label.setMaxWidth(Double.MAX_VALUE); // Set the maximum width of the label to fill the HBox
        label.setAlignment(Pos.CENTER);
        label.setPadding(new javafx.geometry.Insets(5, 0, 0, 0));
        label.setStyle("-fx-font-size: 12px; -fx-font-family: Arial");

        vbox.getChildren().addAll(imageView, label);
        vbox.setAlignment(Pos.CENTER);

        // Style VBox from Style class
        Style style = new Style();
        style.vehicleDetailsVboxStyle(vbox);

        // Set a fixed size for the VBox
        double fixedWidth = 80.0;
        vbox.setMinWidth(fixedWidth);
        vbox.setMaxWidth(fixedWidth);
        vbox.setPrefWidth(fixedWidth);

        // Bind the maximum size of the VBox to the size of its parent
        vbox.maxWidthProperty().bind(parentVbox.widthProperty());
        vbox.maxHeightProperty().bind(parentVbox.heightProperty());

        return vbox;
    }
}