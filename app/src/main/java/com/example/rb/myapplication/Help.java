package com.example.rb.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        TextView txt=(TextView)findViewById(R.id.matn);
        Typeface tf=Typeface.createFromAsset(getAssets(),"IRANSansMobile(FaNum).ttf");
        txt.setTypeface(tf);
        TextView title=(TextView)findViewById(R.id.textView2);
        title.setTypeface(tf);

    }

}
