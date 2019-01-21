package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
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

import java.util.concurrent.TimeUnit;

public class Question extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean responseFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        this.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "IRANSansMobile(FaNum).ttf");
        pref =getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
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
        final String phone = pref.getString("phone", null);
        editor.putInt("qnum",(pref.getInt("qnum",0)-1));
        editor.commit();


        TextView q = (TextView) findViewById(R.id.question);
        q.setText(pref.getString("question0",null));
        q.setTypeface(tf);
        final Button a1 = (Button) findViewById(R.id.answer1);
        a1.setText(pref.getString("answer10",null));
        a1.setTypeface(tf);
        final Button a2 = (Button) findViewById(R.id.answer2);
        a2.setText(pref.getString("answer20",null));
        a2.setTypeface(tf);
        final Button a3 = (Button) findViewById(R.id.answer3);
        a3.setText(pref.getString("answer30",null));
        a3.setTypeface(tf);
        final Button a4 = (Button) findViewById(R.id.answer4);
        a4.setText(pref.getString("answer40",null));
        a4.setTypeface(tf);
final TextView t3=(TextView)findViewById(R.id.result);
t3.setTypeface(tf);
       final TextView t4=(TextView)findViewById(R.id.next);
        t4.setTypeface(tf);
       final TextView t5=(TextView)findViewById(R.id.home);
        t5.setTypeface(tf);
        TextView t6=(TextView)findViewById(R.id.textView17);
        t6.setTypeface(tf);
        TextView t7=(TextView)findViewById(R.id.qnumber);
        t7.setTypeface(tf);
        t7.setText(String.valueOf(pref.getInt("qnum",0)));
    final  ImageView res=(ImageView)findViewById(R.id.imageView4);
      final  ProgressBar time=(ProgressBar)findViewById(R.id.progressBar5);
        time.setMax(pref.getInt("qtime0",0));
        final TextView pro=(TextView)findViewById(R.id.textprogress1);
        pro.setTypeface(tf);
        final CountDownTimer myc=   new CountDownTimer(pref.getInt("qtime0",0)*1000, 1000) {
            int in=1;
            public void onTick(long millisUntilFinished) {
                responseFlag = true;
                time.setProgress(in);
                in=in+1;
                pro.setText("" + String.format("%d ثانیه",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                time.setVisibility(View.INVISIBLE);
                pro.setText(" زمان شما به پایان رسید!");
                //Toast.makeText(getContext(),"hhhhh",Toast.LENGTH_SHORT).show();
                responseFlag=false;
                for(int i=0;i<pref.getInt("qnum",0);i++ ){
                    editor.putInt("questionId"+String.valueOf(i),pref.getInt("questionId"+String.valueOf(i+1),0));
                    editor.putString("question"+String.valueOf(i),pref.getString("question"+String.valueOf(i+1),null));
                    editor.putString("answer1"+String.valueOf(i),pref.getString("answer1"+String.valueOf(i+1),null));
                    editor.putString("answer2"+String.valueOf(i),pref.getString("answer2"+String.valueOf(i+1),null));
                    editor.putString("answer3"+String.valueOf(i),pref.getString("answer3"+String.valueOf(i+1),null));
                    editor.putString("answer4"+String.valueOf(i),pref.getString("answer4"+String.valueOf(i+1),null));
                    editor.putInt("correct"+String.valueOf(i),pref.getInt("correct"+String.valueOf(i+1),0));
                    editor.putInt("qtime"+String.valueOf(i),pref.getInt("qtime"+String.valueOf(i+1),0));
                    editor.commit();
                }
            }
        }.start();

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseFlag) {
                    if ( pref.getInt("correct0",0)== 1) {
                        a1.setBackgroundColor(getResources().getColor(R.color.green));
                        a2.setEnabled(false);
                        a3.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    } else {

                        a1.setBackgroundColor(getResources().getColor(R.color.red));
                        a2.setEnabled(false);
                        a3.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    }
                    final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                    load.setVisibility(View.VISIBLE);
                    updateUser();
                    AnswerDaily asd = new AnswerDaily(Question.this);
                    JSONObject requestjo = new JSONObject();
                    try {

                        requestjo.put("id",String.valueOf(pref.getInt("questionId0",0)));
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
                                    if(pref.getInt("correct0",0)==1){
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
                                    for(int i=0;i<pref.getInt("qnum",0);i++ ){
                                        editor.putInt("questionId"+String.valueOf(i),pref.getInt("questionId"+String.valueOf(i+1),0));
                                        editor.putString("question"+String.valueOf(i),pref.getString("question"+String.valueOf(i+1),null));
                                        editor.putString("answer1"+String.valueOf(i),pref.getString("answer1"+String.valueOf(i+1),null));
                                        editor.putString("answer2"+String.valueOf(i),pref.getString("answer2"+String.valueOf(i+1),null));
                                        editor.putString("answer3"+String.valueOf(i),pref.getString("answer3"+String.valueOf(i+1),null));
                                        editor.putString("answer4"+String.valueOf(i),pref.getString("answer4"+String.valueOf(i+1),null));
                                        editor.putInt("correct"+String.valueOf(i),pref.getInt("correct"+String.valueOf(i+1),0));
                                        editor.putInt("qtime"+String.valueOf(i),pref.getInt("qtime"+String.valueOf(i+1),0));
                                        editor.commit();
                                    }
                                }
                            }
                        });
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Question.this);
                    builder.setTitle("خطا")
                            .setMessage("متاسفانه زمان به پایان رسیده است!")
                            .setCancelable(false)
                            .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //  Intent i = new Intent(QrResult.this, MapsActivity.class);
                                    //  startActivity(i);
                                    // finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseFlag) {
                    if (pref.getInt("correct0",0) == 2) {
                        a2.setBackgroundColor(getResources().getColor(R.color.green));
                        a1.setEnabled(false);
                        a3.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    } else {

                        a2.setBackgroundColor(getResources().getColor(R.color.red));
                        a1.setEnabled(false);
                        a3.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    }
                    final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                    load.setVisibility(View.VISIBLE);
                    updateUser();
                    AnswerDaily asd = new AnswerDaily(Question.this);
                    JSONObject requestjo = new JSONObject();
                    try {

                        requestjo.put("id",String.valueOf(pref.getInt("questionId0",0)));
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
                                    if(pref.getInt("correct0",0)==2){
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
                                    for(int i=0;i<pref.getInt("qnum",0);i++ ){
                                        editor.putInt("questionId"+String.valueOf(i),pref.getInt("questionId"+String.valueOf(i+1),0));
                                        editor.putString("question"+String.valueOf(i),pref.getString("question"+String.valueOf(i+1),null));
                                        editor.putString("answer1"+String.valueOf(i),pref.getString("answer1"+String.valueOf(i+1),null));
                                        editor.putString("answer2"+String.valueOf(i),pref.getString("answer2"+String.valueOf(i+1),null));
                                        editor.putString("answer3"+String.valueOf(i),pref.getString("answer3"+String.valueOf(i+1),null));
                                        editor.putString("answer4"+String.valueOf(i),pref.getString("answer4"+String.valueOf(i+1),null));
                                        editor.putInt("correct"+String.valueOf(i),pref.getInt("correct"+String.valueOf(i+1),0));
                                        editor.putInt("qtime"+String.valueOf(i),pref.getInt("qtime"+String.valueOf(i+1),0));
                                        editor.commit();
                                    }
                                }
                            }
                        });
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Question.this);
                    builder.setTitle("خطا")
                            .setMessage("متاسفانه زمان به پایان رسیده است!")
                            .setCancelable(false)
                            .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //  Intent i = new Intent(QrResult.this, MapsActivity.class);
                                    //  startActivity(i);
                                    // finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseFlag) {
                    if (pref.getInt("correct0",0) == 3) {
                        a3.setBackgroundColor(getResources().getColor(R.color.green));
                        a2.setEnabled(false);
                        a1.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    } else {

                        a3.setBackgroundColor(getResources().getColor(R.color.red));
                        a2.setEnabled(false);
                        a1.setEnabled(false);
                        a4.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    }
                    final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                    load.setVisibility(View.VISIBLE);
                    updateUser();
                    AnswerDaily asd = new AnswerDaily(Question.this);
                    JSONObject requestjo = new JSONObject();
                    try {

                        requestjo.put("id",String.valueOf(pref.getInt("questionId0",0)));
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
                                    if(pref.getInt("correct0",0)==3){
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
                                    for(int i=0;i<pref.getInt("qnum",0);i++ ){
                                        editor.putInt("questionId"+String.valueOf(i),pref.getInt("questionId"+String.valueOf(i+1),0));
                                        editor.putString("question"+String.valueOf(i),pref.getString("question"+String.valueOf(i+1),null));
                                        editor.putString("answer1"+String.valueOf(i),pref.getString("answer1"+String.valueOf(i+1),null));
                                        editor.putString("answer2"+String.valueOf(i),pref.getString("answer2"+String.valueOf(i+1),null));
                                        editor.putString("answer3"+String.valueOf(i),pref.getString("answer3"+String.valueOf(i+1),null));
                                        editor.putString("answer4"+String.valueOf(i),pref.getString("answer4"+String.valueOf(i+1),null));
                                        editor.putInt("correct"+String.valueOf(i),pref.getInt("correct"+String.valueOf(i+1),0));
                                        editor.putInt("qtime"+String.valueOf(i),pref.getInt("qtime"+String.valueOf(i+1),0));
                                        editor.commit();
                                    }
                                }
                            }
                        });
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Question.this);
                    builder.setTitle("خطا")
                            .setMessage("متاسفانه زمان به پایان رسیده است!")
                            .setCancelable(false)
                            .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //  Intent i = new Intent(QrResult.this, MapsActivity.class);
                                    //  startActivity(i);
                                    // finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseFlag) {
                    if (pref.getInt("correct0",0)== 4) {
                        a4.setBackgroundColor(getResources().getColor(R.color.green));
                        a2.setEnabled(false);
                        a3.setEnabled(false);
                        a1.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    } else {

                        a4.setBackgroundColor(getResources().getColor(R.color.red));
                        a2.setEnabled(false);
                        a3.setEnabled(false);
                        a1.setEnabled(false);
                        pro.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.VISIBLE);
                        t5.setVisibility(View.VISIBLE);
                        time.setVisibility(View.INVISIBLE);
                        myc.cancel();
                    }
                    final ProgressBar load=(ProgressBar)findViewById(R.id.loading);
                    load.setVisibility(View.VISIBLE);
                    updateUser();
                    AnswerDaily asd = new AnswerDaily(Question.this);
                    JSONObject requestjo = new JSONObject();
                    try {

                        requestjo.put("id",String.valueOf(pref.getInt("questionId0",0)));
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
                                    if(pref.getInt("correct0",0)==4){
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
                                    for(int i=0;i<pref.getInt("qnum",0);i++ ){
                                        editor.putInt("questionId"+String.valueOf(i),pref.getInt("questionId"+String.valueOf(i+1),0));
                                        editor.putString("question"+String.valueOf(i),pref.getString("question"+String.valueOf(i+1),null));
                                        editor.putString("answer1"+String.valueOf(i),pref.getString("answer1"+String.valueOf(i+1),null));
                                        editor.putString("answer2"+String.valueOf(i),pref.getString("answer2"+String.valueOf(i+1),null));
                                        editor.putString("answer3"+String.valueOf(i),pref.getString("answer3"+String.valueOf(i+1),null));
                                        editor.putString("answer4"+String.valueOf(i),pref.getString("answer4"+String.valueOf(i+1),null));
                                        editor.putInt("correct"+String.valueOf(i),pref.getInt("correct"+String.valueOf(i+1),0));
                                        editor.putInt("qtime"+String.valueOf(i),pref.getInt("qtime"+String.valueOf(i+1),0));
                                        editor.commit();
                                    }

                                }
                            }
                        });
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Question.this);
                    builder.setTitle("خطا")
                            .setMessage("متاسفانه زمان به پایان رسیده است!")
                            .setCancelable(false)
                            .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //  Intent i = new Intent(QrResult.this, MapsActivity.class);
                                    //  startActivity(i);
                                    // finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
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
        UpdateUser upd=new UpdateUser(Question.this);
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
                        Toast.makeText(Question.this,Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void Clickmain(View v){
        Intent i=new Intent(Question.this,Main2Activity.class);
        startActivity(i);
        finish();
    }
    public void Clicknext(View v){
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        if(pref.getInt("qnum",0)>0){
        Intent j=new Intent(Question.this,Question.class);
        startActivity(j);
        finish();}else {
            Toast.makeText(Question.this, "سوالات امروز شما به پایان رسیده است!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
