package com.example.vulnerable;

public class Utils {

    // Simple logic to generate a random password
    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*";
        StringBuilder password = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt((int) (Math.random() * characters.length())));
        }
        return password.toString();
    }
}
