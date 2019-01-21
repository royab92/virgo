package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bahrampashootan on 8/29/2018 AD.
 */

public class QuestionFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean responseFlag;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, null);
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
        editor.putInt("qnum",(pref.getInt("qnum",0)-1));
        editor.commit();


                        TextView q = (TextView) view.findViewById(R.id.question);
                        q.setText(pref.getString("question0",null));
                        q.setTypeface(tf);
                        final Button a1 = (Button) view.findViewById(R.id.answer1);
                        a1.setText(pref.getString("answer10",null));
                        a1.setTypeface(tf);
                        final Button a2 = (Button) view.findViewById(R.id.answer2);
                        a2.setText(pref.getString("answer20",null));
                        a2.setTypeface(tf);
                        final Button a3 = (Button) view.findViewById(R.id.answer3);
                        a3.setText(pref.getString("answer30",null));
                        a3.setTypeface(tf);
                        final Button a4 = (Button) view.findViewById(R.id.answer4);
                        a4.setText(pref.getString("answer40",null));
                        a4.setTypeface(tf);
                        final TextView tv1 = (TextView) view.findViewById(R.id.texttime);
                     final CountDownTimer myc=   new CountDownTimer(pref.getInt("qtime0",0)*1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                responseFlag = true;
                                tv1.setText("" + String.format("%d ثانیه",
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                tv1.setText(" زمان شما به پایان رسید!");
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
                                            tv1.setText("هورا پاسخ درست است! ");

                                            myc.cancel();
                                        } else {

                                            a1.setBackgroundColor(getResources().getColor(R.color.red));
                                            a2.setEnabled(false);
                                            a3.setEnabled(false);
                                            a4.setEnabled(false);
                                           tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                            myc.cancel();
                                        }
                                       final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                        load.setVisibility(View.VISIBLE);
                                        updateUser();
                                        AnswerDaily asd = new AnswerDaily(getActivity());
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
                                                            ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                            tr.setVisibility(View.VISIBLE);
                                                            editor.putInt("gold",userGold);
                                                            editor.commit();
                                                            t2.setText("" + pref.getInt("gold", 0));
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
                                        builder = new AlertDialog.Builder(getActivity());
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
                                            tv1.setText("هورا پاسخ درست است! ");

                                            myc.cancel();
                                        } else {

                                            a2.setBackgroundColor(getResources().getColor(R.color.red));
                                            a1.setEnabled(false);
                                            a3.setEnabled(false);
                                            a4.setEnabled(false);
                                            tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                            myc.cancel();
                                        }
                                        final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                        load.setVisibility(View.VISIBLE);
                                        updateUser();
                                        AnswerDaily asd = new AnswerDaily(getActivity());
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
                                                            ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                            tr.setVisibility(View.VISIBLE);
                                                            editor.putInt("gold",userGold);
                                                            editor.commit();
                                                            t2.setText("" + pref.getInt("gold", 0));
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
                                        builder = new AlertDialog.Builder(getActivity());
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
                                            tv1.setText("هورا پاسخ درست است! ");

                                            myc.cancel();
                                        } else {

                                            a3.setBackgroundColor(getResources().getColor(R.color.red));
                                            a2.setEnabled(false);
                                            a1.setEnabled(false);
                                            a4.setEnabled(false);
                                            tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                            myc.cancel();
                                        }
                                        final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                        load.setVisibility(View.VISIBLE);
                                        updateUser();
                                        AnswerDaily asd = new AnswerDaily(getActivity());
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
                                                            ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                            tr.setVisibility(View.VISIBLE);
                                                            editor.putInt("gold",userGold);
                                                            editor.commit();
                                                            t2.setText("" + pref.getInt("gold", 0));
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
                                        builder = new AlertDialog.Builder(getActivity());
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
                                            tv1.setText("هورا پاسخ درست است! ");

                                            myc.cancel();
                                        } else {

                                            a4.setBackgroundColor(getResources().getColor(R.color.red));
                                            a2.setEnabled(false);
                                            a3.setEnabled(false);
                                            a1.setEnabled(false);
                                            tv1.setText("متاسفانه پاسخ اشتباه است! ");

                                            myc.cancel();
                                        }
                                        final ProgressBar load=(ProgressBar)view.findViewById(R.id.loading);
                                        load.setVisibility(View.VISIBLE);
                                        updateUser();
                                        AnswerDaily asd = new AnswerDaily(getActivity());
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
                                                          ImageView tr=(ImageView)view.findViewById(R.id.treasure);
                                                          tr.setVisibility(View.VISIBLE);
                                                          editor.putInt("gold",userGold);
                                                          editor.commit();
                                                          t2.setText("" + pref.getInt("gold", 0));
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
                                        builder = new AlertDialog.Builder(getActivity());
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