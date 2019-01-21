package com.example.rb.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bahrampashootan on 9/8/2018 AD.
 */

public class RankingFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking,null);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        TextView w1=(TextView)view.findViewById(R.id.topbar);
        w1.setTypeface(tf);
        Button b1=(Button)view.findViewById(R.id.dana);
        b1.setTypeface(tf);

        Button b2=(Button)view.findViewById(R.id.tajrobe);
        b2.setTypeface(tf);

        Button b3=(Button)view.findViewById(R.id.dara);
        b3.setTypeface(tf);


    }
    public void Clickdana(View v){

    }
    public void Clicktajrobe(View v){

    }
    public void Clickdara(View v){

    }
}
