package com.moch.javaquiz;

public class Question {

    private String question;
    private String category;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private boolean answer1;
    private boolean answer2;
    private boolean answer3;
    private boolean answer4;

    public Question() {}

    public Question(String question, String category, String option1, String option2, String option3, String option4, boolean answer1, boolean answer2, boolean answer3, boolean answer4) {
        this.question = question;
        this.category = category;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        this.category = Category;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public boolean isAnswer1() {
        return answer1;
    }

    public void setAnswer1(boolean answer1) {
        this.answer1 = answer1;
    }

    public boolean isAnswer2() {
        return answer2;
    }

    public void setAnswer2(boolean answer2) {
        this.answer2 = answer2;
    }

    public boolean isAnswer3() {
        return answer3;
    }

    public void setAnswer3(boolean answer3) {
        this.answer3 = answer3;
    }

    public boolean isAnswer4() {
        return answer4;
    }

    public void setAnswer4(boolean answer4) {
        this.answer4 = answer4;
    }

}