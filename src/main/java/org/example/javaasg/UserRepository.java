package org.example.javaasg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UserRepository {
    public void saveUser(User user) {
        String userData = user.getFullName() + ";;;" + user.getEmail() + ";;;" + user.getPassword() + ";;;" + user.getRoles() + "\n";
        try (FileWriter writer = new FileWriter(Directories.getUsersDB(), true)) {
            writer.write(userData);
            System.out.println("User data saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing user data to file: " + e.getMessage());
        }
    }

    public boolean isEmailInUse(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";;;");
                if (userData[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
        return false;
    }

    public String getNamebyEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";;;");
                if (parts[1].equals(email)) {
                    return parts[0];
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
        return null;
    }

    public void updateUserEmail(String oldEmail, String newEmail) {
        Path path = Paths.get(Directories.getUsersDB());
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(oldEmail)) {
                    String[] splitLine = lines.get(i).split(";");
                    splitLine[1] = newEmail;
                    lines.set(i, String.join(";", splitLine));
                    break;
                }
            }

            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserPassword(String currentEmail, String newPassword) {
        Path path = Paths.get(Directories.getUsersDB());
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            Register register = new Register();
            String newHashedPassword = register.hashPassword(newPassword);

            for (int i = 0; i < lines.size(); i++) {
                String[] splitLine = lines.get(i).split(";;;");
                if (splitLine[1].equals(currentEmail)) {
                    splitLine[2] = newHashedPassword;
                    lines.set(i, String.join(";;;", splitLine));
                    break;
                }
            }

            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserName(String oldUserName, String newUserName) {
        Path path = Paths.get(Directories.getUsersDB());
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(oldUserName)) {
                    String[] splitLine = lines.get(i).split(";");
                    splitLine[0] = newUserName;
                    lines.set(i, String.join(";", splitLine));
                    break;
                }
            }

            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}