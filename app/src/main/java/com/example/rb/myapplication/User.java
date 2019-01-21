package com.example.rb.myapplication;


import java.io.Serializable;

public class User implements Serializable {
    int gold=0;
    int score=0;
    int armor=0;
    int potion=0;
    int stars=0;
    int life=0;
    protected void init(int bygold,int byscore,int byarmor,int bypotion,int bystars,int bylife){
        gold=bygold;
        potion=bypotion;
        armor=byarmor;
        score=byscore;
        stars=bystars;
        life=bylife;
    };
}
