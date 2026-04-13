package com.banana.game.controller;

import com.banana.game.model.BananaQuestion;
import com.banana.game.service.BananaApiService;
import com.banana.game.service.FactService;
import com.banana.game.service.UserService;
import com.banana.game.session.SessionManager;
import com.banana.game.view.GameView;

import javax.swing.*;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class GameController {

    private GameView view;

    private BananaApiService api = new BananaApiService(); // API
    private FactService factService = new FactService();   // facts
    private UserService userService = new UserService();   // user data

    private BananaQuestion currentQuestion;

    private int score = 0;
    private int challenge = 0;

    private int time = 40;
    private Timer timer;
    private Timer shuffleTimer;

    private int[] currentAnswers;

    /*
     * Constructor
     */
    public GameController(GameView view, String username){

        this.view = view;

        // Create session (virtual identity)
        SessionManager.login(username);

        view.setUsername(username);

        nextChallenge();

        // Logout button event
        view.getLogoutButton().addActionListener(e->logout());
    }

    /*
     * Start countdown timer
     */
    private void startTimer(){

        time = 40;
        view.setTimer(time);

        if(timer!=null) timer.stop();

        timer = new Timer(1000,e->{

            time--;
            view.setTimer(time);

            if(time<=0){
                timer.stop();
                showFact();
            }

        });

        timer.start();
    }

    /*
     * Load next question
     */
    private void nextChallenge(){

        if(challenge >= 6){
            endGame();
            return;
        }

        challenge++;
        view.setChallenge(challenge);

        currentQuestion = api.getQuestion(challenge);
        view.displayQuestion(currentQuestion);

        generateAnswers();
        applyDifficultyEffects();

        startTimer();
    }

    /*
     * Generate answer options
     */
    private void generateAnswers(){

        Random r = new Random();
        int correct = currentQuestion.getAnswer();

        int optionCount = (challenge == 1) ? 4 : 5;

        currentAnswers = new int[optionCount];
        Set<Integer> used = new HashSet<>();

        int correctIndex = r.nextInt(optionCount);

        for(int i=0;i<optionCount;i++){

            if(i == correctIndex){
                currentAnswers[i] = correct;
                used.add(correct);
            }else{
                int wrong;
                do{
                    wrong = r.nextInt(10);
                }while(used.contains(wrong));

                currentAnswers[i] = wrong;
                used.add(wrong);
            }
        }

        view.setAnswers(currentAnswers, this::checkAnswer);
    }

    /*
     * Difficulty effects
     */
    private void applyDifficultyEffects(){

        if(challenge == 2) hideTemporarily(3000);
        else if(challenge == 3) hideTemporarily(6000);
        else if(challenge == 4) hideTemporarily(9000);
        else if(challenge == 5){
            hideTemporarily(9000);
            startShuffle();
        }
    }

    /*
     * Hide answers temporarily
     */
    private void hideTemporarily(int millis){

        view.hideAnswers();

        new Timer(millis, e -> {
            ((Timer)e.getSource()).stop();
            view.showAnswers();
        }).start();
    }

    /*
     * Shuffle answers
     */
    private void startShuffle(){

        if(shuffleTimer!=null) shuffleTimer.stop();

        shuffleTimer = new Timer(4000, e -> shuffleAnswers());
        shuffleTimer.start();
    }

    private void shuffleAnswers(){

        List<Integer> list = new ArrayList<>();
        for(int a : currentAnswers) list.add(a);

        Collections.shuffle(list);

        for(int i=0;i<list.size();i++){
            currentAnswers[i] = list.get(i);
        }

        view.setAnswers(currentAnswers, this::checkAnswer);
    }

    /*
     * Check answer (EVENT HANDLER)
     */
    private void checkAnswer(int selected){

        timer.stop();
        if(shuffleTimer!=null) shuffleTimer.stop();

        if(selected == currentQuestion.getAnswer()){
            score += 10;
            view.setScore(score);
            view.playCorrectSound();
        }else{
            view.playWrongSound();
        }

        showFact();
    }

    /*
     * Show fact after answer
     */
    private void showFact(){

        String fact = factService.getFact();
        view.showFact(fact);

        new Timer(5000,e->{

            ((Timer)e.getSource()).stop();
            view.showFact("");
            nextChallenge();

        }).start();
    }

    /*
     * End game
     */
    private void endGame(){

        if(timer!=null) timer.stop();
        if(shuffleTimer!=null) shuffleTimer.stop();

        userService.updateScore(SessionManager.getCurrentUser(),score);

        JOptionPane.showMessageDialog(view,
                "Game Over\nScore: "+score);

        SessionManager.logout();
        view.dispose();

        new LoginController();
    }

    /*
     * Logout
     */
    private void logout(){

        if(timer!=null) timer.stop();
        if(shuffleTimer!=null) shuffleTimer.stop();

        SessionManager.logout();
        view.dispose();

        new LoginController();
    }
}