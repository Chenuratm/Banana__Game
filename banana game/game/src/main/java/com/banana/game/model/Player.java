package com.banana.game.model;

public class Player {

    private String username;
    private int score;
    private int difficulty;

    // Constructor
    public Player(String username, int score) {
        this.username = username;
        this.score = score;

        // Default difficulty level
        this.difficulty = 1;
    }

    // Get username
    public String getUsername() {
        return username;
    }

    // Get score
    public int getScore() {
        return score;
    }

    // Get difficulty level
    public int getDifficulty() {
        return difficulty;
    }

    /*
     * Add points to score
     */
    public void addScore(int points) {
        this.score += points;
    }

    /*
     * Increase difficulty level
     */
    public void increaseDifficulty() {
        this.difficulty++;
    }
}