package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

public class UserController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void createUser(String name, String email) {
        userService.addUser(new User(name, email));
    }
}