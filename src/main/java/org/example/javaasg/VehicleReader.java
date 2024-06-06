package org.example.javaasg;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class VehicleReader {
    private File file;
    private List<Vehicles> vehicles;

    public VehicleReader() {
        this.file = new File(Directories.getCarList());
        this.vehicles = readVehiclesFromFile();
    }

    public List<Vehicles> readVehiclesFromFile() {
        List<Vehicles> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Vehicles car = new Vehicles();
                car.setVehicleName(parts[0]);
                car.setBrand(parts[1]);
                car.setDirectoryToImage(parts[2]);
                car.setPrice(parts[3]);
                car.setSegment(parts[4]);
                car.setSeats(parts[5]);
                car.setFuelEconomy(parts[6]);
                car.setGearbox(parts[7]);
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void deleteVehicle(Vehicles vehicle) {
        vehicles.remove(vehicle);
        writeVehiclesToFile();

        try {
            Path imagePath = Paths.get(vehicle.getImageName());
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeVehiclesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Vehicles vehicle : vehicles) {
                writer.println(vehicleToFileFormat(vehicle));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String vehicleToFileFormat(Vehicles vehicle) {
        return String.join(";",
            vehicle.getVehicleName(),
            vehicle.getBrand(),
            vehicle.getImageName().replace("/assets/vehicles/", "").replace(".png", ""),
            vehicle.getPrice(),
            vehicle.getSegment(),
            vehicle.getSeats(),
            vehicle.getFuelEconomy(),
            vehicle.getTranmission()
        );
    }

    public List<Vehicles> getVehicles() {
        return vehicles;
    }

    public Set<String> getUniqueBrands() {
        return vehicles.stream()
                .map(Vehicles::getBrand)
                .collect(Collectors.toSet());
    }

    public Set<String> getUniqueSegments() {
        return vehicles.stream()
                .map(Vehicles::getSegment)
                .collect(Collectors.toSet());
    }
}
