package com.banana.game.view;

import com.banana.game.model.BananaQuestion;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.function.Consumer;

public class GameView extends JFrame {

    private JLabel userLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JLabel timerLabel = new JLabel("Time: 60");
    private JLabel challengeLabel = new JLabel("Challenge: 0/6");

    private JLabel imageLabel = new JLabel();

    private JButton[] answerButtons = new JButton[5];

    private JLabel factLabel = new JLabel("", SwingConstants.CENTER);

    private JButton logoutButton = new JButton("Logout");

    public GameView() {

        setTitle("Banana Game");
        setSize(800,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(userLabel);
        top.add(scoreLabel);
        top.add(timerLabel);
        top.add(challengeLabel);
        top.add(logoutButton);

        add(top,BorderLayout.NORTH);

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel,BorderLayout.CENTER);

        JPanel answers = new JPanel(new GridLayout(1,5));

        for(int i=0;i<5;i++){

            answerButtons[i] = new JButton();
            answers.add(answerButtons[i]);
        }

        add(answers,BorderLayout.SOUTH);

        add(factLabel,BorderLayout.WEST);

        setVisible(true);
    }

    public void setUsername(String name){
        userLabel.setText("Player: "+name);
    }

    public void setScore(int score){
        scoreLabel.setText("Score: "+score);
    }

    public void setTimer(int time){
        timerLabel.setText("Time: "+time);
    }

    public void setChallenge(int c){
        challengeLabel.setText("Challenge: "+c+"/6");
    }

    public void displayQuestion(BananaQuestion q){

        try{

            ImageIcon icon = new ImageIcon(new URL(q.getImageUrl()));
            imageLabel.setIcon(icon);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setAnswers(int[] answers, Consumer<Integer> listener){

        for(int i=0;i<5;i++){

            int value = answers[i];

            answerButtons[i].setText(""+value);

            answerButtons[i].addActionListener(e -> listener.accept(value));
        }
    }

    public void showFact(String fact){

        factLabel.setText("<html>"+fact+"</html>");
    }

    public JButton getLogoutButton(){
        return logoutButton;
    }
}