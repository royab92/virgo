package com.example.rb.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bahrampashootan on 8/22/2018 AD.
 */

public class SettingFragment extends Fragment   {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Switch mySwitch = null;
    MediaPlayer mp;
    ImageView wtik;
    ImageView mtik;
    boolean play=false;
    BottomNavigationView navigation;
    boolean man=true;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        SharedPreferences.Editor editor=this.getActivity().getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        mySwitch = (Switch) view.findViewById(R.id.switch1);
        SharedPreferences pref=this.getActivity().getSharedPreferences("VorujakPref",MODE_PRIVATE);
       // editor.putBoolean("man",true);
       // editor.commit();
setHasOptionsMenu(true);
        Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        TextView l1=(TextView)view.findViewById(R.id.layer1);
        l1.setTypeface(tf);
        TextView l2=(TextView)view.findViewById(R.id.layer2);
        l2.setTypeface(tf);
        TextView l3=(TextView)view.findViewById(R.id.layer3);
        l3.setTypeface(tf);
        TextView l4=(TextView)view.findViewById(R.id.layer4);
        l4.setTypeface(tf);
        TextView l5=(TextView)view.findViewById(R.id.layer5);
        l5.setTypeface(tf);
        TextView l6=(TextView)view.findViewById(R.id.layer7);
        l6.setTypeface(tf);
        TextView l7=(TextView)view.findViewById(R.id.version);
        l7.setTypeface(tf);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        l7.setText(versionName);
        TextView l8=(TextView)view.findViewById(R.id.story);
        l8.setTypeface(tf);
        TextView l9=(TextView)view.findViewById(R.id.about);
        l9.setTypeface(tf);
        Button l10=(Button)view.findViewById(R.id.button2);
        l10.setTypeface(tf);
        TextView l11=(TextView)view.findViewById(R.id.usern);
        l11.setTypeface(tf);
        TextView l12=(TextView)view.findViewById(R.id.textView19);
        l12.setTypeface(tf);
        EditText l13=(EditText)view.findViewById(R.id.editText2);
        l13.setTypeface(tf);
        TextView l16=(TextView)view.findViewById(R.id.textView11);
        l16.setTypeface(tf);
        Button l18=(Button)view.findViewById(R.id.button3);
        l18.setTypeface(tf);
        if(pref.contains("username")&&!pref.getString("username",null).equals("")){
            l13.setHint(pref.getString("username",null));
        }
        EditText l14=(EditText)view.findViewById(R.id.editText3);
        l14.setTypeface(tf);
        if(pref.contains("age")&&!pref.getString("age",null).equals("")){
            l14.setHint(pref.getString("age",null));
        }
        TextView l15=(TextView)view.findViewById(R.id.topbar);
        l15.setTypeface(tf);
        mp=MediaPlayer.create(this.getActivity(),R.raw.drones);
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
        wtik=(ImageView)view.findViewById(R.id.wtik);
        wtik.setVisibility(View.INVISIBLE);}else{
                 mtik=(ImageView)view.findViewById(R.id.mtik);
                mtik.setVisibility(View.INVISIBLE);
            }}else{
            wtik=(ImageView)view.findViewById(R.id.wtik);
            wtik.setVisibility(View.INVISIBLE);
        }

      if(pref.contains("play")){
          play=pref.getBoolean("play",false);
      }
        if(play){
            mySwitch.setChecked(true);
        }
        else {
            mySwitch.setChecked(false);
        }
    }

    public void Clickhelp(View v){
        Intent ih=new Intent(this.getActivity(),Help.class);
        startActivity(ih);
    }
    public void Clickabout(View v){
        Intent ina=new Intent(this.getActivity(),About.class);
        startActivity(ina);
    }
    public void Clickwoman(View v){
        ImageView wtik=(ImageView)v.findViewById(R.id.wtik);
        wtik.setVisibility(View.VISIBLE);
        ImageView mtik=(ImageView)v.findViewById(R.id.mtik);
        mtik.setVisibility(View.INVISIBLE);
        man=false;

    }
    public void Clickman(View v){
        ImageView wtik=(ImageView)v.findViewById(R.id.wtik);
        wtik.setVisibility(View.INVISIBLE);
        ImageView mtik=(ImageView)v.findViewById(R.id.mtik);
        mtik.setVisibility(View.VISIBLE);
        man=true;
    }
    public void Clicksave(View v){
        EditText name=(EditText)v.findViewById(R.id.editText2);
        EditText age=(EditText)v.findViewById(R.id.editText3);
        if(man){
            editor.putBoolean("man",true);
            editor.commit();
        }else {
            editor.putBoolean("man",false);
            editor.commit();
        }
        if(!name.getText().toString().matches("")){
            editor.putString("username",name.getText().toString());
            editor.commit();
           // name.setText("");
        }
        if(!age.getText().toString().matches("")){
            editor.putString("age",age.getText().toString());
            editor.commit();
            //age.setText("");
        }
        Toast.makeText(this.getActivity(),"ذخیره سازی موفق",Toast.LENGTH_SHORT).show();
        SharedPreferences pref=this.getActivity().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        navigation = (BottomNavigationView) v.findViewById(R.id.navigationView);
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.manprofile);}else{
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.womanprofile);
            }
        }
       /* BottomNavigationMenuView menuView = (BottomNavigationMenuView)
                navigation.getChildAt(0);
        final View iconView = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
        // iconView.setBackgroundColor(getResources().getColor(R.color.White));
        iconView.setBackground(this.getActivity().getDrawable(R.drawable.profile));
        iconView.setScaleY(1.6f);
        iconView.setScaleX(1.6f);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref=this.getActivity().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
               // ImageView wtik=(ImageView)view.findViewById(R.id.wtik);
                wtik.setVisibility(View.INVISIBLE);}else{
               // ImageView mtik=(ImageView)view.findViewById(R.id.mtik);
                mtik.setVisibility(View.INVISIBLE);
            }}

        if(pref.contains("play")){
            play=pref.getBoolean("play",false);
        }
        if(play){
            mySwitch.setChecked(true);
        }
        else {
            mySwitch.setChecked(false);
        }

    }
    public void Clickswitch(View v){
        SharedPreferences.Editor editor=this.getActivity().getSharedPreferences("VorujakPref",MODE_PRIVATE).edit();
        mySwitch = (Switch) v.findViewById(R.id.switch1);
        if(mySwitch.isChecked()){
            mp.start();
            mySwitch.setChecked(true);
            play=true;
            editor.putBoolean("play",true);
            editor.commit();
        }else {
            mp.pause();
            mySwitch.setChecked(false);
            play=false;
            editor.putBoolean("play",false);
            editor.commit();
        }
    }
    public void Clickintro(View v){

    }
}
