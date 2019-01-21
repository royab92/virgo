package com.example.rb.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class StoredQuestion extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_question);

        this.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "IRANSansMobile(FaNum).ttf");
        pref = this.getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor = pref.edit();
        final SharedPreferences.Editor editor = pref.edit();
        TextView t1 = (TextView) findViewById(R.id.textstar);
        t1.setTypeface(tf);
        t1.setText("" + pref.getInt("score", 0));
        final TextView t2 = (TextView) findViewById(R.id.textgold);
        t2.setTypeface(tf);
        t2.setText("" + pref.getInt("gold", 0));
        TextView un = (TextView) findViewById(R.id.username);
        un.setTypeface(tf);
        if (pref.contains("username")) {
            String name = pref.getString("username", null);
            un.setText(name);

        } else {
            editor.putString("username", "کاربر مهمان");
            editor.commit();
            un.setText("کاربر مهمان");
        }
        TextView q = (TextView) findViewById(R.id.question);
        q.setText(pref.getString("squestion0",null));
        q.setTypeface(tf);
        final Button a1 = (Button) findViewById(R.id.answer1);
        a1.setText(pref.getString("sanswer10",null));
        a1.setTypeface(tf);
        final Button a2 = (Button) findViewById(R.id.answer2);
        a2.setText(pref.getString("sanswer20",null));
        a2.setTypeface(tf);
        final Button a3 = (Button) findViewById(R.id.answer3);
        a3.setText(pref.getString("sanswer30",null));
        a3.setTypeface(tf);
        final Button a4 = (Button) findViewById(R.id.answer4);
        a4.setText(pref.getString("sanswer40",null));
        a4.setTypeface(tf);
        final TextView t3=(TextView)findViewById(R.id.result);
        t3.setTypeface(tf);
        final TextView t5=(TextView)findViewById(R.id.home);
        t5.setTypeface(tf);
        final  ImageView res=(ImageView)findViewById(R.id.imageView4);
        final String phone = pref.getString("phone", null);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( pref.getInt("scorrect0",0)== 1) {
                    a1.setBackgroundColor(getResources().getColor(R.color.green));
                    a2.setEnabled(false);
                    a3.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);

                } else {

                    a1.setBackgroundColor(getResources().getColor(R.color.red));
                    a2.setEnabled(false);
                    a3.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);
                }
                editor.putInt("stornum",(pref.getInt("stornum",0)-1));
                editor.apply();
                for(int i=0;i<pref.getInt("stornum",0);i++ ){
                    editor.putInt("squestionId"+String.valueOf(i),pref.getInt("squestionId"+String.valueOf(i+1),0));
                    editor.putString("squestion"+String.valueOf(i),pref.getString("squestion"+String.valueOf(i+1),null));
                    editor.putString("sanswer1"+String.valueOf(i),pref.getString("sanswer1"+String.valueOf(i+1),null));
                    editor.putString("sanswer2"+String.valueOf(i),pref.getString("sanswer2"+String.valueOf(i+1),null));
                    editor.putString("sanswer3"+String.valueOf(i),pref.getString("sanswer3"+String.valueOf(i+1),null));
                    editor.putString("sanswer4"+String.valueOf(i),pref.getString("sanswer4"+String.valueOf(i+1),null));
                    editor.putInt("scorrect"+String.valueOf(i),pref.getInt("scorrect"+String.valueOf(i+1),0));
                    editor.apply();
                }

                final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                load.setVisibility(View.VISIBLE);
                updateUser();
                AnswerDaily asd = new AnswerDaily(StoredQuestion.this);
                JSONObject requestjo = new JSONObject();
                try {

                    requestjo.put("id",pref.getInt("squestionId0",0));
                    requestjo.put("answer", 1);
                    requestjo.put("AppName", "addigit");
                    requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                    requestjo.put("Userid", "0");
                    requestjo.put("PhoneNo", phone);
                    asd.onAnswer(requestjo, new AnswerDaily.ProcessAnswer() {
                        @Override
                        public void getAnswer(int userGold, int StatusCode, String Message) {
                            if (StatusCode == 0) {
                                // file benevisad
                                load.setVisibility(View.INVISIBLE);
                                if(pref.getInt("scorrect0",0)==1){
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("هورااا پاسخ درست دادی ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.trueanswer);
                                    editor.putInt("gold",userGold);
                                    editor.commit();
                                    t2.setText("" + pref.getInt("gold", 0));
                                 }else{
                                t3.setVisibility(View.VISIBLE);
                                t3.setText("حیف شد! پاسخ اشتباه بود ");
                                res.setVisibility(View.VISIBLE);
                                res.setImageResource(R.drawable.falseanswer);
                            }
                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pref.getInt("scorrect0",0) == 2) {
                    a2.setBackgroundColor(getResources().getColor(R.color.green));
                    a1.setEnabled(false);
                    a3.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);

                } else {

                    a2.setBackgroundColor(getResources().getColor(R.color.red));
                    a1.setEnabled(false);
                    a3.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);

                }
                editor.putInt("stornum",(pref.getInt("stornum",0)-1));
                editor.apply();
                for(int i=0;i<pref.getInt("stornum",0);i++ ){
                    editor.putInt("squestionId"+String.valueOf(i),pref.getInt("squestionId"+String.valueOf(i+1),0));
                    editor.putString("squestion"+String.valueOf(i),pref.getString("squestion"+String.valueOf(i+1),null));
                    editor.putString("sanswer1"+String.valueOf(i),pref.getString("sanswer1"+String.valueOf(i+1),null));
                    editor.putString("sanswer2"+String.valueOf(i),pref.getString("sanswer2"+String.valueOf(i+1),null));
                    editor.putString("sanswer3"+String.valueOf(i),pref.getString("sanswer3"+String.valueOf(i+1),null));
                    editor.putString("sanswer4"+String.valueOf(i),pref.getString("sanswer4"+String.valueOf(i+1),null));
                    editor.putInt("scorrect"+String.valueOf(i),pref.getInt("scorrect"+String.valueOf(i+1),0));
                    editor.apply();
                }
                final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                load.setVisibility(View.VISIBLE);
                updateUser();
                AnswerDaily asd = new AnswerDaily(StoredQuestion.this);
                JSONObject requestjo = new JSONObject();
                try {

                    requestjo.put("id",pref.getInt("squestionId0",0));
                    requestjo.put("answer", 2);
                    requestjo.put("AppName", "addigit");
                    requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                    requestjo.put("Userid", "0");
                    requestjo.put("PhoneNo", phone);
                    asd.onAnswer(requestjo, new AnswerDaily.ProcessAnswer() {
                        @Override
                        public void getAnswer(int userGold, int StatusCode, String Message) {

                            if (StatusCode == 0) {
                                load.setVisibility(View.INVISIBLE);
                                // file benevisad
                                if(pref.getInt("scorrect0",0)==2){
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("هورااا پاسخ درست دادی ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.trueanswer);
                                    editor.putInt("gold",userGold);
                                    editor.commit();
                                    t2.setText("" + pref.getInt("gold", 0));
                                }else{
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("حیف شد! پاسخ اشتباه بود ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.falseanswer);
                                }
                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pref.getInt("scorrect0",0) == 3) {
                    a3.setBackgroundColor(getResources().getColor(R.color.green));
                    a2.setEnabled(false);
                    a1.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);

                } else {

                    a3.setBackgroundColor(getResources().getColor(R.color.red));
                    a2.setEnabled(false);
                    a1.setEnabled(false);
                    a4.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);
                }
                editor.putInt("stornum",(pref.getInt("stornum",0)-1));
                editor.apply();
                for(int i=0;i<pref.getInt("stornum",0);i++ ){
                    editor.putInt("squestionId"+String.valueOf(i),pref.getInt("squestionId"+String.valueOf(i+1),0));
                    editor.putString("squestion"+String.valueOf(i),pref.getString("squestion"+String.valueOf(i+1),null));
                    editor.putString("sanswer1"+String.valueOf(i),pref.getString("sanswer1"+String.valueOf(i+1),null));
                    editor.putString("sanswer2"+String.valueOf(i),pref.getString("sanswer2"+String.valueOf(i+1),null));
                    editor.putString("sanswer3"+String.valueOf(i),pref.getString("sanswer3"+String.valueOf(i+1),null));
                    editor.putString("sanswer4"+String.valueOf(i),pref.getString("sanswer4"+String.valueOf(i+1),null));
                    editor.putInt("scorrect"+String.valueOf(i),pref.getInt("scorrect"+String.valueOf(i+1),0));
                    editor.apply();
                }
                final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                load.setVisibility(View.VISIBLE);
                updateUser();
                AnswerDaily asd = new AnswerDaily(StoredQuestion.this);
                JSONObject requestjo = new JSONObject();
                try {

                    requestjo.put("id",pref.getInt("squestionId0",0));
                    requestjo.put("answer", 3);
                    requestjo.put("AppName", "addigit");
                    requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                    requestjo.put("Userid", "0");
                    requestjo.put("PhoneNo", phone);
                    asd.onAnswer(requestjo, new AnswerDaily.ProcessAnswer() {
                        @Override
                        public void getAnswer(int userGold, int StatusCode, String Message) {

                            if (StatusCode == 0) {
                                load.setVisibility(View.INVISIBLE);
                                //file benevisad
                                if(pref.getInt("scorrect0",0)==3){
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("هورااا پاسخ درست دادی ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.trueanswer);
                                    editor.putInt("gold",userGold);
                                    editor.commit();
                                    t2.setText("" + pref.getInt("gold", 0));
                                }else{
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("حیف شد! پاسخ اشتباه بود ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.falseanswer);
                                }
                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pref.getInt("scorrect0",0)== 4) {
                    a4.setBackgroundColor(getResources().getColor(R.color.green));
                    a2.setEnabled(false);
                    a3.setEnabled(false);
                    a1.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);
                } else {

                    a4.setBackgroundColor(getResources().getColor(R.color.red));
                    a2.setEnabled(false);
                    a3.setEnabled(false);
                    a1.setEnabled(false);
                    t5.setVisibility(View.VISIBLE);

                }
                editor.putInt("stornum",(pref.getInt("stornum",0)-1));
                editor.apply();
                for(int i=0;i<pref.getInt("stornum",0);i++ ){
                    editor.putInt("squestionId"+String.valueOf(i),pref.getInt("squestionId"+String.valueOf(i+1),0));
                    editor.putString("squestion"+String.valueOf(i),pref.getString("squestion"+String.valueOf(i+1),null));
                    editor.putString("sanswer1"+String.valueOf(i),pref.getString("sanswer1"+String.valueOf(i+1),null));
                    editor.putString("sanswer2"+String.valueOf(i),pref.getString("sanswer2"+String.valueOf(i+1),null));
                    editor.putString("sanswer3"+String.valueOf(i),pref.getString("sanswer3"+String.valueOf(i+1),null));
                    editor.putString("sanswer4"+String.valueOf(i),pref.getString("sanswer4"+String.valueOf(i+1),null));
                    editor.putInt("scorrect"+String.valueOf(i),pref.getInt("scorrect"+String.valueOf(i+1),0));
                    editor.apply();
                }
                final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                load.setVisibility(View.VISIBLE);
                updateUser();
                AnswerDaily asd = new AnswerDaily(StoredQuestion.this);
                JSONObject requestjo = new JSONObject();
                try {

                    requestjo.put("id",pref.getInt("squestionId0",0));
                    requestjo.put("answer", 4);
                    requestjo.put("AppName", "addigit");
                    requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                    requestjo.put("Userid", "0");
                    requestjo.put("PhoneNo", phone);
                    asd.onAnswer(requestjo, new AnswerDaily.ProcessAnswer() {
                        @Override
                        public void getAnswer(int userGold, int StatusCode, String Message) {
                            if (StatusCode == 0) {
                                // file benevisad
                                load.setVisibility(View.INVISIBLE);
                                if(pref.getInt("scorrect0",0)==4){
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("هورااا پاسخ درست دادی ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.trueanswer);
                                    editor.putInt("gold",userGold);
                                    editor.commit();
                                    t2.setText("" + pref.getInt("gold", 0));
                                }
                                else{
                                    t3.setVisibility(View.VISIBLE);
                                    t3.setText("حیف شد! پاسخ اشتباه بود ");
                                    res.setVisibility(View.VISIBLE);
                                    res.setImageResource(R.drawable.falseanswer);
                                }

                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void updateUser(){
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
        int time=pref.getInt("time",0);
        String gender;
        int avatar;
        if(pref.getBoolean("man",false)){
            gender="male";
            avatar=2;
        }else{
            gender="female";
            avatar=1;
        }

        String namea=pref.getString("username",null);
        String agea=pref.getString("age",null);
        UpdateUser upd=new UpdateUser(this);
        JSONObject requestjo = new JSONObject();
        try {

            requestjo.put("AuthKey",authkey);
            requestjo.put("Gold",gold);
            requestjo.put("Life",life);
            requestjo.put("Potion",potion);
            requestjo.put("score",score);
            requestjo.put("level",level);
            requestjo.put("fireballs",fire);
            requestjo.put("armor",armor);
            requestjo.put("time",time);
            requestjo.put("gender",gender);
            requestjo.put("name",namea);
            requestjo.put("birthdate",agea);
            requestjo.put("avatar",avatar);
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            upd.UpdateRecieve(requestjo,new UpdateUser.Update(){
                @Override
                public void onUpdate(int StatusCode, String Message){

                    if(StatusCode!=0){
                        Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void Clickmain(View v){
        Intent i=new Intent(StoredQuestion.this,Main2Activity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    }

