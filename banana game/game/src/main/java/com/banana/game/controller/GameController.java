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

    private BananaApiService api = new BananaApiService();
    private FactService factService = new FactService();
    private UserService userService = new UserService();

    private BananaQuestion currentQuestion;

    private int score = 0;
    private int challenge = 0;

    private int time = 40;
    private Timer timer;
    private Timer shuffleTimer;

    private int[] currentAnswers;

    public GameController(GameView view, String username){

        this.view = view;

        SessionManager.login(username);
        view.setUsername(username);

        nextChallenge();

        view.getLogoutButton().addActionListener(e->logout());
    }

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

    private void applyDifficultyEffects(){

        if(challenge == 2) hideTemporarily(3000);
        else if(challenge == 3) hideTemporarily(6000);
        else if(challenge == 4) hideTemporarily(9000);
        else if(challenge == 5){
            hideTemporarily(9000);
            startShuffle();
        }
    }

    private void hideTemporarily(int millis){

        view.hideAnswers();

        new Timer(millis, e -> {
            ((Timer)e.getSource()).stop();
            view.showAnswers();
        }).start();
    }

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

    private void checkAnswer(int selected){

        timer.stop();
        if(shuffleTimer!=null) shuffleTimer.stop();

        if(selected == currentQuestion.getAnswer()){
            score += 10;
            view.setScore(score);
            view.playCorrectSound(); // ✅ correct sound
        }else{
            view.playWrongSound(); // ✅ wrong sound
        }

        showFact();
    }

    private void showFact(){

        String fact = factService.getFact();
        view.showFact(fact);

        new Timer(5000,e->{

            ((Timer)e.getSource()).stop();
            view.showFact("");
            nextChallenge();

        }).start();
    }

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

    private void logout(){

        if(timer!=null) timer.stop();
        if(shuffleTimer!=null) shuffleTimer.stop();

        SessionManager.logout();
        view.dispose();

        new LoginController();
    }
}