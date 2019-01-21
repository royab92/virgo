package com.example.rb.myapplication;


import android.location.Location;

import static com.example.rb.myapplication.Award.awardType.none;

public class Award {
    public enum awardType{
        potionType,
        armorType,
        goldType,
        starType,
        none

    }
    Location alocation;
    int number;
    String id;
    awardType atype=none;
    double dista;
    public void Award(Location location, awardType type, int num, String idd, double distaa){
        alocation=location;
        atype=type;
        number=num;
        id=idd;
        dista=distaa;
    }
}
