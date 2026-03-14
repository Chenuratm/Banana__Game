package com.banana.game.controller;

import com.banana.game.model.BananaQuestion;
import com.banana.game.service.BananaApiService;
import com.banana.game.service.FactService;
import com.banana.game.service.UserService;
import com.banana.game.session.SessionManager;
import com.banana.game.view.GameView;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameController {

    private GameView view;

    private BananaApiService api = new BananaApiService();
    private FactService factService = new FactService();
    private UserService userService = new UserService();

    private BananaQuestion currentQuestion;

    private int score = 0;
    private int challenge = 0;

    private int time = 40;     // timer for each challenge

    private Timer timer;

    private int difficulty = 10;   // controls how hard answers are

    public GameController(GameView view,String username){

        this.view = view;

        SessionManager.login(username);

        view.setUsername(username);

        nextChallenge();

        view.getLogoutButton().addActionListener(e->logout());
    }

    private void startTimer(){

        time = 40;   // reset time every challenge
        view.setTimer(time);

        if(timer!=null){
            timer.stop();
        }

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

        if(challenge>=6){
            endGame();
            return;
        }

        challenge++;

        view.setChallenge(challenge);

        currentQuestion = api.getQuestion(challenge);

        view.displayQuestion(currentQuestion);

        generateAnswers();

        startTimer();   // start new 40 second timer
    }

    private void generateAnswers(){

        Random r = new Random();

        int correct = currentQuestion.getAnswer();

        int[] answers = new int[5];

        int correctIndex = r.nextInt(5);

        Set<Integer> used = new HashSet<>();

        for(int i=0;i<5;i++){

            if(i==correctIndex){
                answers[i]=correct;
                used.add(correct);
            }else{

                int wrong;

                do{
                    wrong = correct + r.nextInt(difficulty*2) - difficulty;
                }while(used.contains(wrong) || wrong<0);

                answers[i]=wrong;
                used.add(wrong);
            }
        }

        view.setAnswers(answers,this::checkAnswer);
    }

    private void checkAnswer(int selected){

        timer.stop();

        if(selected == currentQuestion.getAnswer()){

            score += 10;

            difficulty += 5;   // increase difficulty

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

        if(timer!=null){
            timer.stop();
        }

        userService.updateScore(SessionManager.getCurrentUser(),score);

        JOptionPane.showMessageDialog(view,
                "Game Over\nScore: "+score);

        SessionManager.logout();

        view.dispose();

        new LoginController();
    }

    private void logout(){

        if(timer!=null){
            timer.stop();
        }

        SessionManager.logout();

        view.dispose();

        new LoginController();
    }
}