package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bahrampashootan on 9/19/2018 AD.
 */

public class Question2Fragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question2, null);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        final Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        pref = this.getActivity().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor = pref.edit();
        final SharedPreferences.Editor editor = pref.edit();
        TextView t1 = (TextView) view.findViewById(R.id.textstar);
        t1.setTypeface(tf);
        t1.setText("" + pref.getInt("score", 0));
        final TextView t2 = (TextView) view.findViewById(R.id.textgold);
        t2.setTypeface(tf);
        t2.setText("" + pref.getInt("gold", 0));
        TextView un = (TextView) view.findViewById(R.id.username);
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
        final double lat = Double.longBitsToDouble(pref.getLong("lat", 0));
        final double longt = Double.longBitsToDouble(pref.getLong("long", 0));
        String shopid=pref.getString("shopid",null);
        UpdateUandL asd = new UpdateUandL(this.getActivity());
        JSONObject requestjo = new JSONObject();
        try {

            requestjo.put("latitude", lat);
            requestjo.put("longtitude", longt);
            requestjo.put("shopId", shopid);
            requestjo.put("award", true);
            requestjo.put("type", "question");
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("Userid", "0");
            requestjo.put("PhoneNo", phone);
            asd.maponProcess(requestjo, new UpdateUandL.mapProcessResponse() {
                @Override
                public void mapgetProcess(final String code, final int questionId, String question, String answer1, String answer2, String answer3, String answer4, final int correct, int StatusCode, String Message) {
                  // Toast.makeText(getContext(),String.valueOf(StatusCode),Toast.LENGTH_SHORT).show();
                    if(StatusCode==1||StatusCode==2||StatusCode==3||StatusCode==5){
                        Toast.makeText(getActivity(),"بروز خطا",Toast.LENGTH_SHORT).show();
                    }
                    if(StatusCode==4){
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("خطا")
                                .setMessage("متاسفانه سوالی در این مکان نیست.")
                                .setCancelable(true)
                                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if(StatusCode==6){
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("خطا")
                                .setMessage("شما قبلا در این مکان بوده اید..")
                                .setCancelable(true)
                                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (StatusCode == 0) {

                        editor.putInt("tquestionId",questionId);
                        editor.putString("tquestion",question);
                        editor.putString("tanswer1",answer1);
                        editor.putString("tanswer2",answer2);
                        editor.putString("tanswer3",answer3);
                        editor.putString("tanswer4",answer4);
                        editor.putInt("tcorrect",correct);
                        editor.apply();
                      TextView q = (TextView) view.findViewById(R.id.question);
                        q.setText(question);
                        q.setTypeface(tf);
                        final Button a1 = (Button) view.findViewById(R.id.answer1);
                        a1.setText(answer1);
                        a1.setTypeface(tf);
                        final Button a2 = (Button) view.findViewById(R.id.answer2);
                        a2.setText(answer2);
                        a2.setTypeface(tf);
                        final Button a3 = (Button) view.findViewById(R.id.answer3);
                        a3.setText(answer3);
                        a3.setTypeface(tf);
                        final Button a4 = (Button) view.findViewById(R.id.answer4);
                        a4.setText(answer4);
                        a4.setTypeface(tf);
                        final TextView tv1 = (TextView) view.findViewById(R.id.texttime);
                        tv1.setTypeface(tf);
                        TextView tv2 = (TextView) view.findViewById(R.id.textView10);
                        tv2.setTypeface(tf);
                        a1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    if ( correct== 1) {
                                        a1.setBackgroundColor(getResources().getColor(R.color.green));
                                        a2.setEnabled(false);
                                        a3.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("هورا پاسخ درست است! ");

                                    } else {

                                        a1.setBackgroundColor(getResources().getColor(R.color.red));
                                        a2.setEnabled(false);
                                        a3.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("متاسفانه پاسخ اشتباه است! ");
                                    }
                                    final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                    load.setVisibility(View.VISIBLE);
                                updateUser();
                                    AnswerDaily asd = new AnswerDaily(getActivity());
                                    JSONObject requestjo = new JSONObject();
                                    try {

                                        requestjo.put("id",questionId);
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
                                                    if(correct==1){
                                                        ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                        tr.setVisibility(View.VISIBLE);
                                                        editor.putInt("gold",userGold);
                                                        editor.commit();
                                                        t2.setText("" + pref.getInt("gold", 0));
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

                                    if (correct == 2) {
                                        a2.setBackgroundColor(getResources().getColor(R.color.green));
                                        a1.setEnabled(false);
                                        a3.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("هورا پاسخ درست است! ");

                                    } else {

                                        a2.setBackgroundColor(getResources().getColor(R.color.red));
                                        a1.setEnabled(false);
                                        a3.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                    }
                                    final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                    load.setVisibility(View.VISIBLE);
                                updateUser();
                                    AnswerDaily asd = new AnswerDaily(getActivity());
                                    JSONObject requestjo = new JSONObject();
                                    try {

                                        requestjo.put("id",questionId);
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
                                                    if(correct==2){
                                                        ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                        tr.setVisibility(View.VISIBLE);
                                                        editor.putInt("gold",userGold);
                                                        editor.commit();
                                                        t2.setText("" + pref.getInt("gold", 0));
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

                                    if (correct == 3) {
                                        a3.setBackgroundColor(getResources().getColor(R.color.green));
                                        a2.setEnabled(false);
                                        a1.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("هورا پاسخ درست است! ");

                                    } else {

                                        a3.setBackgroundColor(getResources().getColor(R.color.red));
                                        a2.setEnabled(false);
                                        a1.setEnabled(false);
                                        a4.setEnabled(false);
                                        tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                    }
                                    final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                    load.setVisibility(View.VISIBLE);
                                updateUser();
                                    AnswerDaily asd = new AnswerDaily(getActivity());
                                    JSONObject requestjo = new JSONObject();
                                    try {

                                        requestjo.put("id",questionId);
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
                                                    if(correct==3){
                                                        ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                        tr.setVisibility(View.VISIBLE);
                                                        editor.putInt("gold",userGold);
                                                        editor.commit();
                                                        t2.setText("" + pref.getInt("gold", 0));
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

                                    if (correct== 4) {
                                        a4.setBackgroundColor(getResources().getColor(R.color.green));
                                        a2.setEnabled(false);
                                        a3.setEnabled(false);
                                        a1.setEnabled(false);
                                        tv1.setText("هورا پاسخ درست است! ");

                                    } else {

                                        a4.setBackgroundColor(getResources().getColor(R.color.red));
                                        a2.setEnabled(false);
                                        a3.setEnabled(false);
                                        a1.setEnabled(false);
                                        tv1.setText("متاسفانه پاسخ اشتباه است! ");


                                    }
                                    final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                    load.setVisibility(View.VISIBLE);
                                updateUser();
                                    AnswerDaily asd = new AnswerDaily(getActivity());
                                    JSONObject requestjo = new JSONObject();
                                    try {

                                        requestjo.put("id",questionId);
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
                                                    if(correct==4){
                                                        ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                        tr.setVisibility(View.VISIBLE);
                                                        editor.putInt("gold",userGold);
                                                        editor.commit();
                                                        t2.setText("" + pref.getInt("gold", 0));
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
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void Clickplus(View v){
        pref = this.getActivity().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.contains("stornum")){
            int size=pref.getInt("stornum",0);
         editor.putInt("stornum",size+1);
         editor.putInt("squestionId"+String.valueOf(size),pref.getInt("tquestionId",0));
            editor.putString("squestion"+String.valueOf(size),pref.getString("tquestion",null));
            editor.putString("sanswer1"+String.valueOf(size),pref.getString("tanswer1",null));
            editor.putString("sanswer2"+String.valueOf(size),pref.getString("tanswer2",null));
            editor.putString("sanswer3"+String.valueOf(size),pref.getString("tanswer3",null));
            editor.putString("sanswer4"+String.valueOf(size),pref.getString("tanswer4",null));
            editor.putInt("scorrect"+String.valueOf(size),pref.getInt("tcorrect",0));
            editor.apply();
        }
        else{
            int size1=0;
            editor.putInt("stornum",size1+1);
            editor.putInt("squestionId"+String.valueOf(size1),pref.getInt("tquestionId",0));
            editor.putString("squestion"+String.valueOf(size1),pref.getString("tquestion",null));
            editor.putString("sanswer1"+String.valueOf(size1),pref.getString("tanswer1",null));
            editor.putString("sanswer2"+String.valueOf(size1),pref.getString("tanswer2",null));
            editor.putString("sanswer3"+String.valueOf(size1),pref.getString("tanswer3",null));
            editor.putString("sanswer4"+String.valueOf(size1),pref.getString("tanswer4",null));
            editor.putInt("scorrect"+String.valueOf(size1),pref.getInt("tcorrect",0));
            editor.apply();
        }
    }
    private void updateUser(){
        pref=getContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
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
        int agea=Integer.parseInt(pref.getString("age",null));
        UpdateUser upd=new UpdateUser(this.getActivity());
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
            requestjo.put("age",agea);
            requestjo.put("avatar",avatar);
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            upd.UpdateRecieve(requestjo,new UpdateUser.Update(){
                @Override
                public void onUpdate(int StatusCode, String Message){

                    if(StatusCode!=0){
                        Toast.makeText(getContext(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
