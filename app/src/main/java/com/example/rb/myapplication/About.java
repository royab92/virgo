package com.example.rb.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        TextView txt=(TextView)findViewById(R.id.matn);
        Typeface tf=Typeface.createFromAsset(getAssets(),"IRANSansMobile(FaNum).ttf");
        txt.setTypeface(tf);
        TextView title=(TextView)findViewById(R.id.textView2);
        title.setTypeface(tf);
       // ImageView tel=(ImageView)findViewById(R.id.telegram);
       // ImageView ins=(ImageView)findViewById(R.id.instagram);
       // ImageView web=(ImageView)findViewById(R.id.web);

    }
    public void Clickweb(View v){
    /*  String url=(String)v.getTag();
        Intent inte=new Intent();
        inte.setAction(Intent.ACTION_VIEW);
        inte.addCategory(Intent.CATEGORY_BROWSABLE);
        inte.setData(Uri.parse(url));
        startActivity(inte);*/
    }
    public void Clickinsta(View v){
        String url=(String)v.getTag();
        Intent inte=new Intent();
        inte.setAction(Intent.ACTION_VIEW);
        inte.addCategory(Intent.CATEGORY_BROWSABLE);
        inte.setData(Uri.parse(url));
        startActivity(inte);
    }
    public void Clicktele(View v){
        String url=(String)v.getTag();
        Intent inte=new Intent();
        inte.setAction(Intent.ACTION_VIEW);
        inte.addCategory(Intent.CATEGORY_BROWSABLE);
        inte.setData(Uri.parse(url));
        startActivity(inte);
    }
}
