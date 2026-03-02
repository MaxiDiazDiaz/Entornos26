package com.example.service;

import com.example.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public void addUser(User u) {
        users.add(u);
        System.out.println("Usuario agregado: " + u.getName());
    }
}