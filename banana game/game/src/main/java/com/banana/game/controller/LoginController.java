package com.banana.game.controller;

import com.banana.game.model.Player;
import com.banana.game.service.UserService;
import com.banana.game.view.GameView;
import com.banana.game.view.LoginView;

import javax.swing.*;
public class LoginController {

    // Login screen
    private LoginView view = new LoginView();

    // Service for authentication
    private UserService service = new UserService();

    /*
     * Constructor
     */
    public LoginController() {

        // Login button event
        view.loginButton.addActionListener(e -> login());

        // Register button event -> open register screen
        view.registerButton.addActionListener(e -> new RegisterController());
    }

    /*
     * Handles login logic
     */
    private void login() {

        // Get user input
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        // Check credentials
        if (service.login(username, password)) {

            // Get user score
            int score = service.getScore(username);

            // Create player object
            Player player = new Player(username, score);

            // Close login window
            view.dispose();

            // Open game screen
            GameView gameView = new GameView();

            // Start game controller
            new GameController(gameView, player.getUsername());

        } else {

            // Show error if login fails
            JOptionPane.showMessageDialog(view, "Invalid credentials");
        }
    }
}