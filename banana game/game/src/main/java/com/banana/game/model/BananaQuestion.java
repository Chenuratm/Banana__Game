package com.banana.game.model;

public class BananaQuestion {

    private String imageUrl;
    private int answer;

    public BananaQuestion(String imageUrl, int answer) {
        this.imageUrl = imageUrl;
        this.answer = answer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getAnswer() {
        return answer;
    }
}