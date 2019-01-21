package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SavingQuestion extends AppCompatActivity {
    SharedPreferences pref;
    Boolean qmap=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_question);
        pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        final String phone = pref.getString("phone", null);
        final double lat = Double.longBitsToDouble(pref.getLong("lat", 0));
        final double longt = Double.longBitsToDouble(pref.getLong("long", 0));
        Intent i = getIntent();
        boolean q = i.getBooleanExtra("question", false);
        if (q) {
            qmap=true;
        }
        else {
            qmap=false;
        }
        String shopid=pref.getString("shopid",null);
        UpdateUandL asd = new UpdateUandL(SavingQuestion.this);
        JSONObject requestjo = new JSONObject();
        try {

            requestjo.put("latitude", lat);
            requestjo.put("longtitude", longt);
            requestjo.put("shopId", shopid);
            requestjo.put("award", true);
            requestjo.put("type", pref.getString("qtype",null));
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("Userid", "0");
            requestjo.put("PhoneNo", phone);
            asd.maponProcess(requestjo, new UpdateUandL.mapProcessResponse() {
                @Override
                public void mapgetProcess(final String code, final int questionId, String question, String answer1, String answer2, String answer3, String answer4, final int correct, int StatusCode, String Message) {
                    // Toast.makeText(getContext(),String.valueOf(StatusCode),Toast.LENGTH_SHORT).show();
                    if (StatusCode == 1 || StatusCode == 2 || StatusCode == 3 || StatusCode == 5) {
                        Toast.makeText(SavingQuestion.this, "بروز خطا", Toast.LENGTH_SHORT).show();
                        //setContentView(R.layout.activity_maps);
                        Intent h;
                        if(qmap){
                         h=new Intent(SavingQuestion.this,QuestionMapsActivity.class);}else{
                            h=new Intent(SavingQuestion.this,MapsActivity.class);
                        }
                        startActivity(h);
                        finish();
                    }
                    if (StatusCode == 4) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(SavingQuestion.this);
                        builder.setTitle("خطا")
                                .setMessage("متاسفانه سوالی در این مکان نیست.")
                                .setCancelable(true)
                                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  setContentView(R.layout.activity_maps);
                                        Intent h;
                                        if(qmap){
                                            h=new Intent(SavingQuestion.this,QuestionMapsActivity.class);}else{
                                            h=new Intent(SavingQuestion.this,MapsActivity.class);
                                        }
                                        startActivity(h);
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (StatusCode == 6) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(SavingQuestion.this);
                        builder.setTitle("خطا")
                                .setMessage("شما قبلا در این مکان بوده اید..")
                                .setCancelable(true)
                                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  setContentView(R.layout.activity_maps);
                                        Intent h;
                                        if(qmap){
                                            h=new Intent(SavingQuestion.this,QuestionMapsActivity.class);}else{
                                            h=new Intent(SavingQuestion.this,MapsActivity.class);
                                        }
                                        startActivity(h);
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (StatusCode == 0) {
                        if (pref.contains("stornum")) {
                            int size = pref.getInt("stornum", 0);
                            editor.putInt("stornum", size + 1);
                            editor.putInt("squestionId"+String.valueOf(size), questionId);
                            editor.putString("squestion"+String.valueOf(size), question);
                            editor.putString("sanswer1"+String.valueOf(size), answer1);
                            editor.putString("sanswer2"+String.valueOf(size), answer2);
                            editor.putString("sanswer3"+String.valueOf(size), answer3);
                            editor.putString("sanswer4"+String.valueOf(size), answer4);
                            editor.putInt("scorrect"+String.valueOf(size), correct);
                            editor.apply();
                        } else {
                            int size1 = 0;
                            editor.putInt("stornum", size1 + 1);
                            editor.putInt("squestionId"+String.valueOf(size1), questionId);
                            editor.putString("squestion"+String.valueOf(size1), question);
                            editor.putString("sanswer1"+String.valueOf(size1), answer1);
                            editor.putString("sanswer2"+String.valueOf(size1), answer2);
                            editor.putString("sanswer3"+String.valueOf(size1), answer3);
                            editor.putString("sanswer4"+String.valueOf(size1), answer4);
                            editor.putInt("scorrect"+String.valueOf(size1), correct);
                            editor.apply();}
                        Toast.makeText(SavingQuestion.this,"سوال به صندوق سوالات اضافه شد!",Toast.LENGTH_SHORT).show();
                        Intent h;
                        if(qmap){
                            h=new Intent(SavingQuestion.this,QuestionMapsActivity.class);}else{
                            h=new Intent(SavingQuestion.this,MapsActivity.class);
                        }
                        startActivity(h);
                        finish();
                    }
                }});
        }catch (JSONException e) {
            e.printStackTrace();
            // setContentView(R.layout.activity_maps);
        }



    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}