package com.example.util;

public class Validator {

    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return email.contains("@") && email.contains(".");
    }

    public static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}