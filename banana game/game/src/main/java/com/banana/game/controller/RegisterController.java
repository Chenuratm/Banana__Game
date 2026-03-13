package com.banana.game.controller;

import com.banana.game.service.UserService;
import com.banana.game.view.RegisterView;

import javax.swing.*;

public class RegisterController {

    private RegisterView view = new RegisterView();
    private UserService service = new UserService();

    public RegisterController() {

        view.createButton.addActionListener(e -> register());
    }

    private void register() {

        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        if (service.register(username, password)) {
            JOptionPane.showMessageDialog(view, "Account created!");
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Username already exists!");
        }
    }
}