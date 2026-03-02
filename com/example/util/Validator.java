package com.example.util;

public class Validator {

    public static boolean validateEmail(String email) {
        return email.contains("@");
    }
}