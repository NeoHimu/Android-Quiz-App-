package com.example.himanshu.onlinequizapp.Model;

/**
 * Created by himanshu on 2/3/18.
 */

public class Question {
    private String Question;
    private String Choice1;
    private String Choice2;
    private String Choice3;
    private String Choice4;
    private String CorrectChoice;


    public Question() {
    }

    public Question(String question, String choice1, String choice2, String choice3, String choice4, String correctChoice) {
        Question = question;
        Choice1 = choice1;
        Choice2 = choice2;
        Choice3 = choice3;
        Choice4 = choice4;
        CorrectChoice = correctChoice;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getChoice1() {
        return Choice1;
    }

    public void setChoice1(String choice1) {
        Choice1 = choice1;
    }

    public String getChoice2() {
        return Choice2;
    }

    public void setChoice2(String choice2) {
        Choice2 = choice2;
    }

    public String getChoice3() {
        return Choice3;
    }

    public void setChoice3(String choice3) {
        Choice3 = choice3;
    }

    public String getChoice4() {
        return Choice4;
    }

    public void setChoice4(String choice4) {
        Choice4 = choice4;
    }

    public String getCorrectChoice() {
        return CorrectChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        CorrectChoice = correctChoice;
    }
}
