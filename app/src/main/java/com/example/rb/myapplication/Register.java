package com.example.rb.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    //SharedPreferences pref;

    EditText tel;
    EditText telm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            tel=(EditText)findViewById(R.id.editText);
        telm=(EditText)findViewById(R.id.moaref);
            // tel.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
          //  pref=getApplicationContext().getSharedPreferences("MyPref",0);
        TextView txt=(TextView)findViewById(R.id.textView13);
        Typeface tf=Typeface.createFromAsset(getAssets(),"IRANSansMobile(FaNum).ttf");
        txt.setTypeface(tf);
        TextView txt2=(TextView)findViewById(R.id.textView14);
        txt2.setTypeface(tf);
        tel.setTypeface(tf);
        telm.setTypeface(tf);
        TextView t1=(TextView)findViewById(R.id.textView4);
        t1.setTypeface(tf);
        TextView t2=(TextView)findViewById(R.id.textView7);
        t2.setTypeface(tf);
        TextView t3=(TextView)findViewById(R.id.textView8);
        t3.setTypeface(tf);
        ImageView wtik=(ImageView)findViewById(R.id.wtik);
        wtik.setVisibility(View.INVISIBLE);
        Button txt4=(Button) findViewById(R.id.button);
        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        editor.putBoolean("man",true);
        editor.commit();
        txt.setTypeface(tf);
        tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(s.length()==11){
                 tel.setCursorVisible(false);
                 tel.setFocusable(false);
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        telm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==11){
                    tel.setCursorVisible(false);
                    tel.setFocusable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        }
    public void Confirm(View v){
        final EditText phone=(EditText)findViewById(R.id.editText);
       // final TextView res=(TextView)findViewById(R.id.resp);

        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        String mob="";
        String mobm="";
        mob=phone.getText().toString();
        mobm=telm.getText().toString();
        if(mob!=null&&mob.length()==11) {
            if (mob.contains(" ") || mob.contains(",") || mob.contains(".") || mob.contains(";") || mob.contains("-") || mob.contains("/") || mob.contains("N") || mob.contains("*") || mob.contains("#") || mob.contains("(") || mob.contains(")") || mob.contains("+")) {
                Toast.makeText(Register.this, "شماره موبایل به درستی وارد نشده است!", Toast.LENGTH_SHORT).show();
            } else {
                editor.putString("phone", mob);
                editor.putString("moaref",mobm);
                editor.apply();
                Intent i = new Intent(Register.this, Activation.class);
                // i.putExtra("code", code);
                startActivity(i);
                finish();
            }
        }
    else{
            Toast.makeText(Register.this,"شماره موبایل به درستی وارد نشده است!",Toast.LENGTH_LONG).show();
    }
    }
    public void Clickwoman(View v){
        ImageView wtik=(ImageView)findViewById(R.id.wtik);
        wtik.setVisibility(View.VISIBLE);
        ImageView mtik=(ImageView)findViewById(R.id.mtik);
        mtik.setVisibility(View.INVISIBLE);
        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        editor.putBoolean("man",false);
        editor.commit();
    }
    public void Clickman(View v){
        ImageView wtik=(ImageView)findViewById(R.id.wtik);
        wtik.setVisibility(View.INVISIBLE);
        ImageView mtik=(ImageView)findViewById(R.id.mtik);
        mtik.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        editor.putBoolean("man",true);
        editor.commit();
    }
    }

