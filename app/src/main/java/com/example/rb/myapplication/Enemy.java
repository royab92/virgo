package com.example.rb.myapplication;


import android.location.Location;

public class Enemy {
    boolean islocal=true;
    Location elocation;
    double strength=2.0;
    String ename="badkonaki";
    double power=2.0;
    protected void init(boolean local, Location location, String name)
    {
        islocal=local;
        elocation=location;
        ename=name;
        switch (ename){
            case "badkonaki":
                power=2.0;
                strength=2.0;
                break;
            case  "gavi":
                power=3.0;
                strength=3.0;
                break;
            case "gavazn":
                power=4.0;
                strength=4.0;
                break;
            case "merikhi":
                power=5.0;
                strength=5.0;
                break;
            case "nakhoonak":
                power=6.0;
                strength=6.0;
                break;
            case "kermmarmoolaki":
                power=7.0;
                strength=7.0;
                break;
            default:power=2.0;
                strength=2.0;
        }
    }
    public double getPower(){
        return power;
    }

}
