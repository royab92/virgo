package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class Activation extends AppCompatActivity {
    private String code;
    SharedPreferences pref;
    private String phone;
    private String moaref;
    int pstatus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Typeface tf=Typeface.createFromAsset(getAssets(),"IRANSansMobile(FaNum).ttf");
        final EditText actcode=(EditText)findViewById(R.id.activation);
        //actcode.setBackgroundColor(Color.WHITE);
        actcode.setTypeface(tf);
        TextView tv=(TextView)findViewById(R.id.textView);
        tv.setTypeface(tf);
        final TextView tv1=(TextView)findViewById(R.id.timer);
        tv1.setTypeface(tf);
        TextView tv2=(TextView)findViewById(R.id.textView15);
        tv2.setTypeface(tf);
       final Button bt1=(Button)findViewById(R.id.retry);
        bt1.setTypeface(tf);
        pref=getSharedPreferences("VorujakPref",MODE_PRIVATE);
        phone=pref.getString("phone",null);


moaref=pref.getString("moaref",null);
        APISendingData asd = new APISendingData(Activation.this);
        JSONObject requestjo = new JSONObject();
        try {
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("Userid", "0");
            requestjo.put("PhoneNo", phone);
            requestjo.put("ReagentNo",moaref);

            asd.signUp(requestjo, new APISendingData.onSignUpComplate() {
                @Override
                public void onSignUp(String code, int status, String message) {
if(status==2){
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(Activation.this, R.style.AlertDialogTheme);
    builder.setTitle("خطا")
            .setMessage("شما از طرف هیچ کس معرفی نشده اید!")
            .setCancelable(false)
            .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        actcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==5){
                    actcode.setCursorVisible(false);
                    actcode.setFocusable(false);
                    SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
                    // final TextView msg=(TextView)findViewById(R.id.message);
                    EditText act=(EditText)findViewById(R.id.activation);
                    code=act.getText().toString();
                    editor.putString("code",code);
                    editor.apply();
                    Intent wait=new Intent(Activation.this,Waiting.class);
                    startActivity(wait);
                    finish();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final ProgressBar load=(android.widget.ProgressBar)findViewById(R.id.load);
        load.setProgress(0);
        load.setMax(60);

        new CountDownTimer(60000,1000){
            public void onTick(long millisUntilFinished){
                pstatus+=1;
                load.setProgress(pstatus);
                tv1.setText(""+String.format("%d ثانیه",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish(){
                tv1.setText(" ");
                load.setVisibility(View.GONE);
                bt1.setEnabled(true);
                bt1.setAlpha(1);
            }
        }.start();
    }

    public void Retry(View v){

        Intent h=new Intent(Activation.this,
                Register.class);
        startActivity(h);
        finish();
        //bad az 1 min emkan darad
    }


}
