package com.example.a19_5_9_testoooooo2;

public class QuestionBean {
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_IMAGE = "IMG";

    private int id;
    private long time;
    private String question;
    private int score;
    private int answer;
    private String ex1;
    private String ex2;
    private String ex3;
    private String ex4;
    private String type;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public int getScore() {
        return score;
    }

    public int getAnswer() {
        return answer;
    }

    public String getEx1() {
        return ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public String getType() {
        return type;
    }
}
