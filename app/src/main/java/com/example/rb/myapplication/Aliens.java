package com.example.rb.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


public class Aliens extends UnityPlayerActivity {
    public static final String PREFS_NAME = "VorujakPref";
    static private String _myInt;

    static public String getMyInt()
    {
        return _myInt;
    }

    static public void callbackToUnityMethod(String num, String gameObject, String methodName){
        _myInt = num;
        UnityPlayer.UnitySendMessage(gameObject, methodName, "READY");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliens);

        Intent i=new Intent(Aliens.this,UnityPlayerActivity.class);
        startActivity(i);


    }
    public void setPreferenceString (String prefKey, String prefValue) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(prefKey, prefValue);

        editor.commit();

    }

    public String getPreferenceString (String prefKey) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String playerName = String.valueOf(settings.getInt(prefKey, 0));
        //Toast.makeText(Aliens.this,"getpref",Toast.LENGTH_SHORT).show();
        return playerName;
    }
   // private void getParams(String msg){
    //    Toast.makeText(getApplicationContext(),"success in recieve"+msg,Toast.LENGTH_SHORT).show();
   // }
}
