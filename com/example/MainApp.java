package com.example;
import com.example.controller.ContactController;
import com.example.controller.UserController;


public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=== BuggyWebApp iniciado ===");

        UserController userController = new UserController();
        userController.createUser("Juan", "juan@mail.com");

        ContactController contactController = new ContactController();

        contactController.submitContactForm("", "");

        System.out.println("=== BuggyWebApp terminado ===");
		

	}

}
