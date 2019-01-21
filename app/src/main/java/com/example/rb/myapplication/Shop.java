package com.example.rb.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Shop extends AppCompatActivity {
SharedPreferences pref;
    MediaPlayer mp;
    int price=0;
    int pcount=0;
    int acount=0;
    int scount=0;
    TextView sn;
    TextView pr;
    TextView pn;
    TextView an;
    TextView st;
    TextView tg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
    }
    public void ClickBack(View v){
        Intent inte=new Intent(Shop.this,MapsActivity.class);
        startActivity(inte);
        finish();
    }
    public void ClickPotion(View v){

        price=price+300;
        pcount=pcount+1;
    }
    public void ClickArmor(View v){

        price=price+1000;
        acount=acount+1;
    }
    public void ClickStar(View v){
        price=price+50;
        scount=scount+1;
    }
    public void ClickTake(View v){

        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        int gold;
        gold=pref.getInt("gold",0);
        if(gold>=price) {
            //dar sorate kharid
            gold=gold-price;
            editor.putInt("gold",gold);
            editor.putInt("potion",pref.getInt("potion",0)+pcount);
            editor.putInt("armor",pref.getInt("armor",0)+acount);
            editor.putInt("stars",pref.getInt("stars",0)+scount);
            editor.putInt("awardcount", 0);
            editor.putInt("enemycount", 0);
            editor.apply();
            price=0;
            acount=0;
            pcount=0;
            scount=0;
        }
        else {

        }

    }
    public void ClickReset(View v){
        price=0;
        acount=0;
        pcount=0;
        scount=0;

    }
    protected void onPause() {
        super.onPause();

    }
    protected void onResume(){
        super.onResume();
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        int gold;
        gold=pref.getInt("gold",0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //update user
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        String authkey=pref.getString("AuthKey",null);
        String phone=pref.getString("phone",null);
        int gold=pref.getInt("gold",0);
        int life=pref.getInt("life",0);
        int potion=pref.getInt("potion",0);
        int score=pref.getInt("score",0);
        int level=pref.getInt("level",0);
        int fire=pref.getInt("stars",0);
        int armor=pref.getInt("armor",0);
        UpdateUser upd=new UpdateUser(Shop.this);
        JSONObject requestjo = new JSONObject();
        try {
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            requestjo.put("AuthKey",authkey);
            requestjo.put("Gold",gold);
            requestjo.put("Life",life);
            requestjo.put("Potion",potion);
            requestjo.put("score",score);
            requestjo.put("level",level);
            requestjo.put("fireballs",fire);
            requestjo.put("armor",armor);
            upd.UpdateRecieve(requestjo,new UpdateUser.Update(){
                @Override
                public void onUpdate(int StatusCode, String Message){
                    if(StatusCode!=0){
                        Toast.makeText(Shop.this,Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
