package org.example.javaasg;

public class ValidationUtils {

    public static boolean validateName(String fullName) {
        return !fullName.isEmpty() && fullName.matches(".*[a-zA-Z]+.*");
    }

    public static boolean validateEmail(String email) {
        return !email.isEmpty() && email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
    }

    public static boolean isEmailUnique(String email, UserRepository userRepository) {
        return !userRepository.isEmailInUse(email);
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()].*") &&
               !password.isEmpty();
    }

    public static boolean validatePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword) && !confirmPassword.isEmpty();
    }
}