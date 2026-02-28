package com.banana.game.view;

import com.banana.game.model.BananaQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameView extends JFrame {

    private JLabel usernameLabel;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JLabel challengeLabel;

    private JLabel questionImageLabel;

    private JTextField answerField;
    private JButton submitButton;
    private JButton logoutButton;

    public GameView() {

        setTitle("Banana Challenge Game");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(new Color(255, 223, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usernameLabel = new JLabel("Player: ");
        scoreLabel = new JLabel("Score: 0");
        timerLabel = new JLabel("Time: 60");
        challengeLabel = new JLabel("Challenge: 0/6");

        headerPanel.add(usernameLabel);
        headerPanel.add(scoreLabel);
        headerPanel.add(timerLabel);
        headerPanel.add(challengeLabel);

        add(headerPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        questionImageLabel = new JLabel("", SwingConstants.CENTER);
        centerPanel.add(questionImageLabel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        answerField = new JTextField(10);
        answerField.setFont(new Font("Arial", Font.PLAIN, 18));

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);

        bottomPanel.add(new JLabel("Your Answer: "));
        bottomPanel.add(answerField);
        bottomPanel.add(submitButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}