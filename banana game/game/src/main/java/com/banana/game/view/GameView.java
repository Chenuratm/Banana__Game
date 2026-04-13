package com.banana.game.view;

import com.banana.game.model.BananaQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.function.Consumer;
import javax.sound.sampled.*;


public class GameView extends JFrame {

    // Top panel labels
    private JLabel userLabel = new JLabel();          // shows username
    private JLabel scoreLabel = new JLabel("Score: 0"); // shows score
    private JLabel challengeLabel = new JLabel("Challenge: 0/6"); // shows level

    // Timer bar (countdown)
    private JProgressBar timerBar = new JProgressBar(0,40);

    // Image for question
    private JLabel imageLabel = new JLabel();

    // Answer buttons (max 5 options)
    private JButton[] answerButtons = new JButton[5];

    // Logout button
    private JButton logoutButton = new JButton("Logout");

    // Fact panel (shows after answer)
    private JPanel factPanel = new JPanel();
    private JLabel factLabel = new JLabel("", SwingConstants.CENTER);

    /*
     * Constructor - builds the UI
     */
    public GameView() {

        setTitle("🍌 Banana Game"); // window title
        setSize(900,650);
        setLocationRelativeTo(null); // center screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10,10));
        getContentPane().setBackground(new Color(30,30,30));

        // Add panels
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // Setup fact overlay
        setupFactPanel();

        // Resize listener (updates fact panel size)
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                factPanel.setBounds(0,0,getWidth(),getHeight());
            }
        });

        setVisible(true); // show window
    }

    /*
     * Creates top panel (user info + timer)
     */
    private JPanel createTopPanel(){

        JPanel panel = new JPanel(new GridLayout(1,5,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBackground(new Color(45,45,45));

        // Style labels
        styleLabel(userLabel);
        styleLabel(scoreLabel);
        styleLabel(challengeLabel);

        // Timer settings
        timerBar.setValue(40);
        timerBar.setStringPainted(true);
        timerBar.setString("Time: 40s");
        timerBar.setForeground(new Color(0,200,0));

        // Logout button style
        logoutButton.setBackground(new Color(200,60,60));
        logoutButton.setForeground(Color.WHITE);

        // Add components
        panel.add(userLabel);
        panel.add(scoreLabel);
        panel.add(timerBar);
        panel.add(challengeLabel);
        panel.add(logoutButton);

        return panel;
    }

    /*
     * Center panel (shows question image)
     */
    private JPanel createCenterPanel(){

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(30,30,30));

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(720,520));
        card.setBackground(new Color(45,45,45));

        // Border styling
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,80),2),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        card.add(imageLabel, BorderLayout.CENTER);

        wrapper.add(card);

        return wrapper;
    }

    /*
     * Bottom panel (answer buttons)
     */
    private JPanel createBottomPanel(){

        JPanel panel = new JPanel(new GridLayout(1,5,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBackground(new Color(30,30,30));

        // Create 5 buttons
        for(int i=0;i<5;i++){

            JButton btn = new JButton();
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(70,130,180));
            btn.setForeground(Color.WHITE);

            // Mouse hover effect (EVENT)
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    btn.setBackground(new Color(100,160,220));
                }
                public void mouseExited(MouseEvent e){
                    btn.setBackground(new Color(70,130,180));
                }
            });

            answerButtons[i] = btn;
            panel.add(btn);
        }

        return panel;
    }

    /*
     * Style labels (helper method)
     */
    private void styleLabel(JLabel label){
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /*
     * Fact overlay panel (shown after answer)
     */
    private void setupFactPanel(){

        factPanel.setLayout(new BorderLayout());
        factPanel.setBackground(new Color(20,20,20));

        factLabel.setForeground(Color.WHITE);
        factLabel.setFont(new Font("Arial", Font.BOLD, 28));

        factPanel.add(factLabel, BorderLayout.CENTER);
        factPanel.setVisible(false);

        getLayeredPane().add(factPanel, JLayeredPane.POPUP_LAYER);
        factPanel.setBounds(0,0,getWidth(),getHeight());
    }

    /*
     * Show fact message
     */
    public void showFact(String fact){

        if(fact == null || fact.isEmpty()){
            factPanel.setVisible(false);
            return;
        }

        factLabel.setText(
                "<html><div style='text-align:center; padding:40px;'>"
                        + fact +
                        "</div></html>"
        );

        factPanel.setVisible(true);
    }

    /*
     * Play sound from resources folder
     */
    private void playSound(String fileName){
        try{
            URL soundURL = getClass().getResource("/sounds/" + fileName);

            if(soundURL == null){
                System.out.println("Sound not found: " + fileName);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        }catch(Exception e){
            System.out.println("Sound error: " + e.getMessage());
        }
    }

    // Sound methods
    public void playCorrectSound(){ playSound("correct.wav"); }
    public void playWrongSound(){ playSound("wrong.wav"); }
    public void playTickSound(){ playSound("tick.wav"); }

    /*
     * Update timer bar
     */
    public void setTimer(int time){

        timerBar.setValue(time);
        timerBar.setString("Time: " + time + "s");

        // If time low → red + tick sound
        if(time <= 10){
            timerBar.setForeground(Color.RED);
            playTickSound();
        }else{
            timerBar.setForeground(new Color(0,200,0));
        }
    }

    /*
     * Set answers to buttons
     * Consumer = function that handles click event
     */
    public void setAnswers(int[] answers, Consumer<Integer> listener){

        for(int i=0;i<answerButtons.length;i++){

            if(i < answers.length){

                int value = answers[i];
                JButton btn = answerButtons[i];

                btn.setVisible(true);
                btn.setText(""+value);

                // Remove old listeners
                for(ActionListener al : btn.getActionListeners()){
                    btn.removeActionListener(al);
                }

                // Add new click event
                btn.addActionListener(e -> {
                    listener.accept(value);
                });

            }else{
                answerButtons[i].setVisible(false);
            }
        }
    }

    /*
     * Display question image
     */
    public void displayQuestion(BananaQuestion q){
        try{
            ImageIcon icon = new ImageIcon(new URL(q.getImageUrl()));

            Image img = icon.getImage().getScaledInstance(
                    650, 450, Image.SCALE_SMOOTH
            );

            imageLabel.setIcon(new ImageIcon(img));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Update UI values
    public void setUsername(String name){ userLabel.setText("Player: " + name); }
    public void setScore(int s){ scoreLabel.setText("Score: " + s); }
    public void setChallenge(int c){ challengeLabel.setText("Challenge: " + c + "/6"); }

    public JButton getLogoutButton(){ return logoutButton; }

    public void hideAnswers(){
        for(JButton b : answerButtons){ b.setVisible(false); }
    }

    public void showAnswers(){
        for(JButton b : answerButtons){ b.setVisible(true); }
    }
}