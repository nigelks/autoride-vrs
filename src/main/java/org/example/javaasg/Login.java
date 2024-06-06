package org.example.javaasg;

public class Login {
    public String validateLogin(String email, String password) {
        // Hash the input password
        Register register = new Register();
        String hashedPassword = register.hashPassword(password);

        // Validate the login credentials
        UserReader userReader = new UserReader();
        String role = userReader.validateLogin(email, hashedPassword);

        if (!role.equals("invalid")) {
            // Set the logged-in user details in the User class
            User.setLoggedInUserEmail(email);
            UserRepository userRepository = new UserRepository();
            User.setLoggedInUserName(userRepository.getNamebyEmail(email));
        }

        return role;
    }
}
