package com.example.model;

public class ContactForm {
    private String name;
    private String email;

    public ContactForm(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}