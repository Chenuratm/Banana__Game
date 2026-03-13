package com.banana.game.controller;

import com.banana.game.model.BananaQuestion;
import com.banana.game.service.BananaApiService;
import com.banana.game.service.FactService;
import com.banana.game.service.UserService;
import com.banana.game.session.SessionManager;
import com.banana.game.view.GameView;

import javax.swing.*;
import java.util.Random;

public class GameController {

    private GameView view;

    private BananaApiService api = new BananaApiService();
    private FactService factService = new FactService();
    private UserService userService = new UserService();

    private BananaQuestion currentQuestion;

    private int score = 0;
    private int challenge = 0;

    private int time = 60;

    private Timer timer;

    public GameController(GameView view,String username){

        this.view = view;

        SessionManager.login(username);

        view.setUsername(username);

        startTimer();

        nextChallenge();

        view.getLogoutButton().addActionListener(e->logout());
    }

    private void startTimer(){

        timer = new Timer(1000,e->{

            time--;
            view.setTimer(time);

            if(time<=0){
                endGame();
            }

        });

        timer.start();
    }

    private void nextChallenge(){

        if(challenge>=6){
            endGame();
            return;
        }

        challenge++;

        view.setChallenge(challenge);

        currentQuestion = api.getQuestion(challenge);

        view.displayQuestion(currentQuestion);

        generateAnswers();
    }

    private void generateAnswers(){

        Random r = new Random();

        int correct = currentQuestion.getAnswer();

        int[] answers = new int[5];

        int correctIndex = r.nextInt(5);

        for(int i=0;i<5;i++){

            if(i==correctIndex){
                answers[i]=correct;
            }else{
                answers[i]=r.nextInt(10);
            }
        }

        view.setAnswers(answers,this::checkAnswer);
    }

    private void checkAnswer(int selected){

        if(selected == currentQuestion.getAnswer()){

            score += 10;
            view.setScore(score);
        }

        showFact();
    }

    private void showFact(){

        String fact = factService.getFact();

        view.showFact(fact);

        new Timer(10000,e->{

            ((Timer)e.getSource()).stop();
            view.showFact("");
            nextChallenge();

        }).start();
    }

    private void endGame(){

        timer.stop();

        userService.updateScore(SessionManager.getCurrentUser(),score);

        JOptionPane.showMessageDialog(view,
                "Game Over\nScore: "+score);

        SessionManager.logout();

        view.dispose();

        new LoginController();
    }

    private void logout(){

        timer.stop();

        SessionManager.logout();

        view.dispose();

        new LoginController();
    }
}