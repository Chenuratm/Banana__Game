package com.banana.game.model;

public class Player {

    private String username;
    private int score;
    private int difficulty;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
        this.difficulty = 1;
    }

}