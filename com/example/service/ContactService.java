package com.example.service;

import com.example.model.ContactForm;
import com.example.util.Logger;
import com.example.util.Validator;

public class ContactService {

    public void processForm(ContactForm form) {
        Logger.log("Validando formulario...");

        if (Validator.validateEmail(form.getEmail())) {
            Logger.log("Email es válido!");
        }

        Logger.log("Procesando formulario para: " + form.getName());
        Logger.log("Mensaje enviado a: " + form.getName().toUpperCase());
        Logger.log("Formulario procesado");
    }
}