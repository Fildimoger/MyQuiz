package com.example.myquiz;

// Class representing a single quiz question
public class Questions {

    // Fields to store question, options, and correct answer number
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private int answerNr;

    // Default constructor
    public Questions(){}

    // Constructor with parameters to initialize the question object
    public Questions(String question, String option1, String option2, String option3, String option4, int answerNr) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
    }

    // Getter method for retrieving the question
    public String getQuestion() {
        return question;
    }

    // Getter methods for retrieving each option
    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    // Getter method for retrieving the correct answer number
    public int getAnswerNr() {
        return answerNr;
    }

    // Setter method for updating the question
    public void setQuestion(String question) {
        this.question = question;
    }

    // Setter methods for updating each option
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    // Setter method for updating the correct answer number
    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }
}
