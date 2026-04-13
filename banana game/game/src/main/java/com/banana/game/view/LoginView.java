package com.banana.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    public JTextField usernameField = new JTextField();
    public JPasswordField passwordField = new JPasswordField();
    public JButton loginButton = new JButton("Login");
    public JButton registerButton = new JButton("Register");

    public LoginView() {

        setTitle("🍌 Banana Game - Login");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🔥 Main background
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(30,30,30));

        // 🔥 Card panel
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(320,260));
        card.setBackground(new Color(45,45,45));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,80),2),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        // Title
        JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // 🔥 Style inputs
        styleField(usernameField);
        styleField(passwordField);

        // 🔥 Style buttons
        styleButton(loginButton, new Color(70,130,180));
        styleButton(registerButton, new Color(100,100,100));

        // Add components
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0,15)));

        card.add(createLabel("Username"));
        card.add(usernameField);
        card.add(Box.createRigidArea(new Dimension(0,10)));

        card.add(createLabel("Password"));
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0,20)));

        card.add(loginButton);
        card.add(Box.createRigidArea(new Dimension(0,10)));
        card.add(registerButton);

        background.add(card);
        add(background);

        setVisible(true);
    }

    // 🔥 Label style
    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // 🔥 Input styling
    private void styleField(JTextField field){
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        field.setBackground(new Color(60,60,60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    }

    // 🔥 Button styling
    private void styleButton(JButton btn, Color baseColor){

        btn.setFocusPainted(false);
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,35));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                btn.setBackground(baseColor.brighter());
            }
            public void mouseExited(MouseEvent e){
                btn.setBackground(baseColor);
            }
        });
    }
}