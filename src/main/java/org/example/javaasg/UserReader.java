package org.example.javaasg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserReader {
    // Validate the user login credentials
    public String validateLogin(String inputUsername, String inputPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";;;");
                String username = parts[1];
                String password = parts[2];
                String role = parts[3];

                if (username.equals(inputUsername) && password.equals(inputPassword)) {
                    return role.equals("1") ? "admin" : "customer";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "invalid";
    }

    public String getPasswordByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Directories.getUsersDB()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";;;");
                String userEmail = parts[1];
                String userPassword = parts[2];

                if (userEmail.equals(email)) {
                    return userPassword;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        String filePath = Directories.getUsersDB();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }

        // Log the users for debugging
//        for (User user : users) {
//            System.out.println(user.toString());
//        }

        return users;
    }

    public User parseUser(String line) {
        try {
            String[] parts = line.split(";;;");
            if (parts.length >= 4) { // Ensure that the line has enough parts
                String username = parts[0];
                String email = parts[1];
                String password = parts[2];
                int roles = Integer.parseInt(parts[3]);

                return new User(email, username, password, roles);
            } else {
                System.err.println("Invalid user format: " + line);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing user: " + e.getMessage());
        }
        // Return null if parsing fails
        return null;
    }
}
