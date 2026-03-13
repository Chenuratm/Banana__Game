package com.banana.game.controller;

import com.banana.game.model.Player;
import com.banana.game.service.UserService;
import com.banana.game.view.GameView;
import com.banana.game.view.LoginView;

import javax.swing.*;

public class LoginController {

    private LoginView view = new LoginView();
    private UserService service = new UserService();

    public LoginController() {

        view.loginButton.addActionListener(e -> login());
        view.registerButton.addActionListener(e -> new RegisterController());
    }

    private void login() {

        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        if (service.login(username, password)) {

            int score = service.getScore(username);
            Player player = new Player(username, score);

            view.dispose(); // close login window

            GameView gameView = new GameView();

            new GameController(gameView, player.getUsername());

        } else {
            JOptionPane.showMessageDialog(view, "Invalid credentials");
        }
    }
}