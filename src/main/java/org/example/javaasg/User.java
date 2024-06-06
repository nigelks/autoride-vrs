package org.example.javaasg;

public class User {
    private String email;
    private String fullName;
    private String password;
    private int roles;
    private static String loggedInUserEmail;
    private static String loggedInUserName;
    private static boolean isAdmin;

    public User(String email, String fullName, String password, int roles) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String name) {
        fullName = name;
    }

    public String getPassword() {
        return password;
    }

    public int getRoles() {
        return roles;
    }

    public static void setLoggedInUserEmail(String email) {
        loggedInUserEmail = email;
    }

    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public static void setLoggedInUserName(String fullName) {
        loggedInUserName = fullName;
    }

    public static String getLoggedInUserName() {
        return loggedInUserName;
    }

    public static void setIsAdmin(boolean isAdmin) {
        User.isAdmin = isAdmin;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }
}
