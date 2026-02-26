package com.banana.game.view;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

    public JTextField usernameField = new JTextField();
    public JPasswordField passwordField = new JPasswordField();
    public JButton createButton = new JButton("Create Account");

    public RegisterView() {

        setTitle("Register");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        panel.add(new JLabel("Username"));
        panel.add(usernameField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(createButton);

        add(panel);
        setVisible(true);
    }
}