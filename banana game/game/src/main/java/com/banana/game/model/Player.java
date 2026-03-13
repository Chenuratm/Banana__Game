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

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void increaseDifficulty() {
        this.difficulty++;
    }
}