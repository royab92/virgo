package com.example.rb.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Waiting extends AppCompatActivity {
    private String code;
    SharedPreferences pref;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
       // BlurKit.init(this);
       // com.wonderkiln.blurkit.BlurLayout blurry=(com.wonderkiln.blurkit.BlurLayout)findViewById(R.id.blurLayout);
      //  blurry.setBlurRadius(2);
        pref=getSharedPreferences("VorujakPref",MODE_PRIVATE);
        phone=pref.getString("phone",null);
        code=pref.getString("code",null);
        String gender;
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
                gender="male";}else {gender="female";}
        }else {
            gender="male";
        }
        LastSend asd=new LastSend(Waiting.this);
        JSONObject requestjo=new JSONObject();
        try{
            requestjo.put("Code",code);
            requestjo.put("AppName","addigit");
            requestjo.put("AppVersion",BuildConfig.VERSION_NAME);
            requestjo.put("Userid","0");
            requestjo.put("PhoneNo",phone);
            requestjo.put("Gender",gender);
            asd.Response(requestjo,new LastSend.Responses(){
                @Override
                public void getResponse(int status,String message ) {
                    if(status==0){
                        //   Toast.makeText(Activation.this,"success",Toast.LENGTH_SHORT).show();
                        //msg.setText(message);
                        RecieveUser apg = new RecieveUser(Waiting.this);
                        JSONObject requestje = new JSONObject();
                        try {
                            requestje.put("AppName", "addigit");
                            requestje.put("AppVersion", BuildConfig.VERSION_NAME);
                            requestje.put("UserId", "0");
                            requestje.put("PhoneNo", phone);
                            apg.DataRecieve(requestje, new RecieveUser.Recieve() {
                                @Override
                                public void onDataRecieve(String Authkey, int gold, int life, int potion, int score, int level, int fire, int armor, int time, String gender, String name, String age, int avatar, int StatusCode, String Message) {
                                    if (StatusCode == 0) {
                                         Toast.makeText(Waiting.this, "فعال سازی موفق", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
                                        editor.putString("AuthKey", Authkey);
                                        editor.putInt("gold", gold);
                                        editor.putInt("life", life);
                                        editor.putInt("potion", potion);
                                        editor.putInt("level",level);
                                        editor.putInt("score",score);
                                        editor.putInt("stars",fire);
                                        editor.putInt("armor",armor);
                                        editor.putInt("time",time);
                                        if(gender.equals("male")) {
                                            editor.putBoolean("man", true);
                                        }else {
                                            editor.putBoolean("man", false);
                                        }
                                        editor.putString("username",name);
                                        editor.putString("age",age);
                                        editor.putInt("avatar",avatar);
                                        editor.putBoolean("activate",true);
                                        editor.putString("localVersion","1");
                                        editor.putString("userid","0");
                                        editor.apply();
                                        Intent i=new Intent(Waiting.this,
                                                Main2Activity.class);
                                        startActivity(i);
                                        finish();

                                    }
                                    else{
                                        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
                                        editor.remove("phone");
                                        editor.putBoolean("activate",false);
                                        editor.apply();
                                        Toast.makeText(Waiting.this,"بروز خطا",Toast.LENGTH_SHORT).show();
                                        Intent ret=new Intent(Waiting.this,Register.class);
                                        startActivity(ret);
                                        finish();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else {
                        //msg.setText(message);
                        SharedPreferences.Editor editor=getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
                        editor.remove("phone");
                        editor.putBoolean("activate",false);
                        editor.apply();
                        //Toast.makeText(Waiting.this,String.valueOf(status),Toast.LENGTH_SHORT).show();
                        Toast.makeText(Waiting.this,"بروز خطا در فعالسازی",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Waiting.this,message,Toast.LENGTH_SHORT).show();
                        Intent ret2=new Intent(Waiting.this,Register.class);
                        startActivity(ret2);
                        finish();
                    }
                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
