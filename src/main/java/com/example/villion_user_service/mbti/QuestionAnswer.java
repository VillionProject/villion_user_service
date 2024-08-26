package com.example.villion_user_service.mbti;

public class QuestionAnswer {
    private String question;
    private int choice1Count;
    private int choice2Count;
    public QuestionAnswer(String question) {
        this.question = question;
        this.choice1Count = 0;
        this.choice2Count = 0;
    }
    public void incrementChoice1Count(int choice1Count) {
        this.choice1Count = choice1Count;
    }
    public void incrementChoice2Count(int choice2Count) {
        this.choice2Count = choice2Count;
    }
    public String getQuestion() {
        return question;
    }
    public int getChoice1Count() {
        return choice1Count;
    }
    public int getChoice2Count() {
        return choice2Count;
    }
}