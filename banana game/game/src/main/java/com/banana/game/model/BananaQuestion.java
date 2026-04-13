package com.banana.game.model;
public class BananaQuestion {

    private String imageUrl; // URL of banana question image
    private int answer;      // correct answer

    // Constructor
    public BananaQuestion(String imageUrl, int answer) {
        this.imageUrl = imageUrl;
        this.answer = answer;
    }

    // Get image URL
    public String getImageUrl() {
        return imageUrl;
    }

    // Get correct answer
    public int getAnswer() {
        return answer;
    }
}