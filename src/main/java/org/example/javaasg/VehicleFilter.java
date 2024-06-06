package org.example.javaasg;

// Java imports
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// JavaFX imports
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class VehicleFilter {
    List<Vehicles> filter(List<Vehicles> cars, VBox filterVBox, Slider seatsSlider, Function<Vehicles, String> criteriaFunction) {
        // Get the list of checked criteria
        List<String> checkedCriteria = filterVBox.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .map(node -> (CheckBox) node)
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .collect(Collectors.toList());

        // Filter the list of cars based on the checked criteria
        List<Vehicles> filteredCars = cars.stream()
                .filter(car -> checkedCriteria.isEmpty() || checkedCriteria.contains(criteriaFunction.apply(car)))
                .collect(Collectors.toList());

        // Apply the slider filter to the filteredCars list only if the slider is not at its minimum value
        List<Vehicles> finalFilteredCars;
        if (seatsSlider.getValue() > seatsSlider.getMin()) {
            finalFilteredCars = filteredCars.stream()
                    .filter(car -> {
                        String seats = car.getSeats().replaceAll("\\D+", "");
                        return seats.isEmpty() || Integer.parseInt(seats) == (int) seatsSlider.getValue();
                    })
                    .collect(Collectors.toList());
        } else {
            finalFilteredCars = filteredCars;
        }

        return finalFilteredCars;
    }

    private void uncheck(VBox vbox) {
        // Uncheck all checkboxes in the VBox
        vbox.getChildren().forEach(node -> {
            if (node instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) node;
                checkbox.setSelected(false);
            }
        });
    }

    public void reset(VBox brandVBox, VBox segmentVBox, Slider vehicleSeatsFilterSlider) {
        // Uncheck all checkboxes in brandVBox
        uncheck(brandVBox);

        // Uncheck all checkboxes in segmentVBox
        uncheck(segmentVBox);

        // Reset the seats slider
        vehicleSeatsFilterSlider.setValue(vehicleSeatsFilterSlider.getMin());
    }
}
