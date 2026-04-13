package com.banana.game.controller;

import com.banana.game.service.UserService;
import com.banana.game.view.RegisterView;

import javax.swing.*;
public class RegisterController {

    // View (UI for registration)
    private RegisterView view = new RegisterView();

    // Service (handles data storage)
    private UserService service = new UserService();

    /*
     * Constructor - runs when controller is created
     */
    public RegisterController() {

        // Add event listener to create button
        // When button is clicked -> register() method runs
        view.createButton.addActionListener(e -> register());
    }

    /*
     * Handles registration process
     */
    private void register() {

        // Get user input from text fields
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        // Call service to register user
        if (service.register(username, password)) {

            // If success -> show message
            JOptionPane.showMessageDialog(view, "Account created!");

            // Close register window
            view.dispose();

        } else {

            // If username exists -> show error
            JOptionPane.showMessageDialog(view, "Username already exists!");
        }
    }
}