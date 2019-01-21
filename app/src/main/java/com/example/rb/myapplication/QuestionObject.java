package com.example.rb.myapplication;

/**
 * Created by bahrampashootan on 9/6/2018 AD.
 */

public class QuestionObject {
int id;
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int correct;
    int time;
    protected void init(int qid, String q, String a1, String a2, String a3, String a4, int ca, int ti){
id=qid;
        question=q;
        answer1=a1;
        answer2=a2;
        answer3=a3;
        answer4=a4;
        correct=ca;
        time=ti;
    }
}
