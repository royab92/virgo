package com.example.rb.myapplication;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bahrampashootan on 9/21/2018 AD.
 */

public class Tabt2Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout swipeLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { return inflater.inflate(R.layout.fragment_tabt2, null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        final Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");

        TextView t1=(TextView)view.findViewById(R.id.rank);
        t1.setTypeface(tf);
        TextView t2=(TextView)view.findViewById(R.id.avatar);
        t2.setTypeface(tf);
        TextView t3=(TextView)view.findViewById(R.id.name);
        t3.setTypeface(tf);
        TextView t4=(TextView)view.findViewById(R.id.score);
        t4.setTypeface(tf);
        final SharedPreferences pref = getContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        final String phone=pref.getString("phone",null);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        BestPlayer bp=new BestPlayer(this.getActivity());
        JSONObject requestjo = new JSONObject();
        try {
            requestjo.put("level",pref.getInt("level",1));
            requestjo.put("type","score");
            requestjo.put("AppName","addigit");
            requestjo.put("AppVersion",BuildConfig.VERSION_NAME);
            requestjo.put("UserId","0");
            requestjo.put("PhoneNo",phone);
            bp.TopPlayerRecieve(requestjo,new BestPlayer.RecieveTop(){
                @Override
                public void onTopRecieve(int status, String message, int[]levels, int[]scores, String[]names, int corrects[], int golds[], int[]avatars, String phones[]){
                    if (status==0){
                        //namayesh liste bartarinha
                        PercentRelativeLayout pl=(PercentRelativeLayout)view.findViewById(R.id.tabid);
                        int ncount;
                        int indexn=0;
                        float tmar=0.055f;
                        float amar=0.025f;
                        float nmar=0.055f;
                        float smar=0.055f;
                        //Toast.makeText(getContext(),pref.getString("phone",null),Toast.LENGTH_SHORT).show();
                        for(int i=0;i<levels.length;i++){
                            if(names[i].matches(pref.getString("username",null))&&phones[i].matches(pref.getString("phone",null))){
                                indexn=i;
                            }
                        }
                        if(levels.length>25){
                            if (indexn>12){
                                for(int i=(indexn-12);i<(indexn+12);i++){
                                    TextView rnk=new TextView(getActivity());
                                    rnk.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params=(PercentRelativeLayout.LayoutParams)rnk.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info=params.getPercentLayoutInfo();
                                    info.topMarginPercent=tmar;
                                    info.widthPercent=0.10f;
                                    tmar=tmar+0.15f;
                                    info.startMarginPercent=0.87f;
                                    rnk.setTypeface(tf);
                                    rnk.setText(String.valueOf(i+1));
                                    rnk.setTextColor(getResources().getColor(R.color.White));
                                    rnk.requestLayout();
                                    rnk.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    rnk.setGravity(Gravity.CENTER_HORIZONTAL);
                                    rnk.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        View v=new View(getActivity());
                                        v.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.MATCH_PARENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                        PercentRelativeLayout.LayoutParams vparams=(PercentRelativeLayout.LayoutParams)v.getLayoutParams();
                                        PercentLayoutHelper.PercentLayoutInfo vinfo=vparams.getPercentLayoutInfo();
                                        vinfo.topMarginPercent=tmar-0.18f;
                                        vinfo.heightPercent=0.13f;
                                        v.requestLayout();
                                        v.setBackgroundColor(getResources().getColor(R.color.tabi));
                                        pl.addView(v);
                                        rnk.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(rnk);

                                    ImageView test=new ImageView(getActivity());
                                    test.setLayoutParams(new PercentRelativeLayout.LayoutParams(80,60));
                                    PercentRelativeLayout.LayoutParams params1=(PercentRelativeLayout.LayoutParams)test.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info1=params1.getPercentLayoutInfo();
                                    info1.widthPercent=0.17f;
                                    info1.heightPercent=0.13f;
                                    info1.topMarginPercent=amar;
                                    amar=amar+0.15f;
                                    info1.startMarginPercent=0.64f;
                                    if(avatars[i]==1){
                                        test.setImageResource(R.drawable.woman_avatar_rnk);
                                    }else{
                                        test.setImageResource(R.drawable.man_avatar_rnk);}
                                    test.requestLayout();
                                    pl.addView(test);

                                    TextView name=new TextView(getActivity());
                                    name.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params2=(PercentRelativeLayout.LayoutParams)name.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info2=params2.getPercentLayoutInfo();
                                    info2.widthPercent=0.25f;
                                    info2.topMarginPercent=nmar;
                                    nmar=nmar+0.15f;
                                    info2.startMarginPercent=0.35f;
                                    name.setTypeface(tf);
                                    name.setText(names[i]);
                                    name.setTextColor(getResources().getColor(R.color.White));
                                    name.requestLayout();
                                    name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    name.setGravity(Gravity.CENTER_HORIZONTAL);
                                    name.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        name.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(name);

                                    TextView score=new TextView(getActivity());
                                    score.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params3=(PercentRelativeLayout.LayoutParams)score.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info3=params3.getPercentLayoutInfo();
                                    info3.topMarginPercent=smar;
                                    info3.widthPercent=0.15f;
                                    smar=smar+0.15f;
                                    info3.startMarginPercent=0.11f;
                                    score.setTypeface(tf);
                                    score.setText(String.valueOf(scores[i]));
                                    score.setTextColor(getResources().getColor(R.color.White));
                                    score.requestLayout();
                                    score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    score.setGravity(Gravity.CENTER_HORIZONTAL);
                                    score.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        score.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(score);
                                }
                            }else{
                                for(int i=0;i<25;i++){
                                    TextView rnk=new TextView(getActivity());
                                    rnk.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params=(PercentRelativeLayout.LayoutParams)rnk.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info=params.getPercentLayoutInfo();
                                    info.topMarginPercent=tmar;
                                    info.widthPercent=0.10f;
                                    tmar=tmar+0.15f;
                                    info.startMarginPercent=0.87f;
                                    rnk.setTypeface(tf);
                                    rnk.setText(String.valueOf(i+1));
                                    rnk.setTextColor(getResources().getColor(R.color.White));
                                    rnk.requestLayout();
                                    rnk.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    rnk.setGravity(Gravity.CENTER_HORIZONTAL);
                                    rnk.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        View v=new View(getActivity());
                                        v.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.MATCH_PARENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                        PercentRelativeLayout.LayoutParams vparams=(PercentRelativeLayout.LayoutParams)v.getLayoutParams();
                                        PercentLayoutHelper.PercentLayoutInfo vinfo=vparams.getPercentLayoutInfo();
                                        vinfo.topMarginPercent=tmar-0.18f;
                                        vinfo.heightPercent=0.13f;
                                        v.requestLayout();
                                        v.setBackgroundColor(getResources().getColor(R.color.tabi));
                                        pl.addView(v);
                                        rnk.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(rnk);

                                    ImageView test=new ImageView(getActivity());
                                    test.setLayoutParams(new PercentRelativeLayout.LayoutParams(80,60));
                                    PercentRelativeLayout.LayoutParams params1=(PercentRelativeLayout.LayoutParams)test.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info1=params1.getPercentLayoutInfo();
                                    info1.widthPercent=0.17f;
                                    info1.heightPercent=0.13f;
                                    info1.topMarginPercent=amar;
                                    amar=amar+0.15f;
                                    info1.startMarginPercent=0.64f;
                                    if(avatars[i]==1){
                                        test.setImageResource(R.drawable.woman_avatar_rnk);
                                    }else{
                                        test.setImageResource(R.drawable.man_avatar_rnk);}
                                    test.requestLayout();
                                    pl.addView(test);

                                    TextView name=new TextView(getActivity());
                                    name.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params2=(PercentRelativeLayout.LayoutParams)name.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info2=params2.getPercentLayoutInfo();
                                    info2.widthPercent=0.25f;
                                    info2.topMarginPercent=nmar;
                                    nmar=nmar+0.15f;
                                    info2.startMarginPercent=0.35f;
                                    name.setTypeface(tf);
                                    name.setText(names[i]);
                                    name.setTextColor(getResources().getColor(R.color.White));
                                    name.requestLayout();
                                    name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    name.setGravity(Gravity.CENTER_HORIZONTAL);
                                    name.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        name.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(name);

                                    TextView score=new TextView(getActivity());
                                    score.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams params3=(PercentRelativeLayout.LayoutParams)score.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo info3=params3.getPercentLayoutInfo();
                                    info3.topMarginPercent=smar;
                                    info3.widthPercent=0.15f;
                                    smar=smar+0.15f;
                                    info3.startMarginPercent=0.11f;
                                    score.setTypeface(tf);
                                    score.setText(String.valueOf(scores[i]));
                                    score.setTextColor(getResources().getColor(R.color.White));
                                    score.requestLayout();
                                    score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    score.setGravity(Gravity.CENTER_HORIZONTAL);
                                    score.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                    if(i==indexn){
                                        score.setTextColor(getResources().getColor(R.color.green));
                                    }
                                    pl.addView(score);
                                }
                            }
                        }else{
                            for(int i=0;i<levels.length;i++){
                                TextView rnk=new TextView(getActivity());
                                rnk.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                PercentRelativeLayout.LayoutParams params=(PercentRelativeLayout.LayoutParams)rnk.getLayoutParams();
                                PercentLayoutHelper.PercentLayoutInfo info=params.getPercentLayoutInfo();
                                info.topMarginPercent=tmar;
                                info.widthPercent=0.10f;
                                tmar=tmar+0.15f;
                                info.startMarginPercent=0.87f;
                                rnk.setTypeface(tf);
                                rnk.setText(String.valueOf(i+1));
                                rnk.setTextColor(getResources().getColor(R.color.White));
                                rnk.requestLayout();
                                rnk.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                rnk.setGravity(Gravity.CENTER_HORIZONTAL);
                                rnk.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                if(i==indexn){
                                    View v=new View(getActivity());
                                    v.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.MATCH_PARENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                    PercentRelativeLayout.LayoutParams vparams=(PercentRelativeLayout.LayoutParams)v.getLayoutParams();
                                    PercentLayoutHelper.PercentLayoutInfo vinfo=vparams.getPercentLayoutInfo();
                                    vinfo.topMarginPercent=tmar-0.18f;
                                    vinfo.heightPercent=0.13f;
                                    v.requestLayout();
                                    v.setBackgroundColor(getResources().getColor(R.color.tabi));
                                    pl.addView(v);
                                    rnk.setTextColor(getResources().getColor(R.color.green));
                                }
                                pl.addView(rnk);

                                ImageView test=new ImageView(getActivity());
                                test.setLayoutParams(new PercentRelativeLayout.LayoutParams(80,60));
                                PercentRelativeLayout.LayoutParams params1=(PercentRelativeLayout.LayoutParams)test.getLayoutParams();
                                PercentLayoutHelper.PercentLayoutInfo info1=params1.getPercentLayoutInfo();
                                info1.widthPercent=0.17f;
                                info1.heightPercent=0.13f;
                                info1.topMarginPercent=amar;
                                amar=amar+0.15f;
                                info1.startMarginPercent=0.64f;
                                if(avatars[i]==1){
                                    test.setImageResource(R.drawable.woman_avatar_rnk);
                                }else{
                                    test.setImageResource(R.drawable.man_avatar_rnk);}
                                test.requestLayout();
                                pl.addView(test);

                                TextView name=new TextView(getActivity());
                                name.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                PercentRelativeLayout.LayoutParams params2=(PercentRelativeLayout.LayoutParams)name.getLayoutParams();
                                PercentLayoutHelper.PercentLayoutInfo info2=params2.getPercentLayoutInfo();
                                info2.widthPercent=0.25f;
                                info2.topMarginPercent=nmar;
                                nmar=nmar+0.15f;
                                info2.startMarginPercent=0.35f;
                                name.setTypeface(tf);
                                name.setText(names[i]);
                                name.setTextColor(getResources().getColor(R.color.White));
                                name.requestLayout();
                                name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                name.setGravity(Gravity.CENTER_HORIZONTAL);
                                name.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                if(i==indexn){
                                    name.setTextColor(getResources().getColor(R.color.green));
                                }
                                pl.addView(name);

                                TextView score=new TextView(getActivity());
                                score.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                                PercentRelativeLayout.LayoutParams params3=(PercentRelativeLayout.LayoutParams)score.getLayoutParams();
                                PercentLayoutHelper.PercentLayoutInfo info3=params3.getPercentLayoutInfo();
                                info3.topMarginPercent=smar;
                                info3.widthPercent=0.15f;
                                smar=smar+0.15f;
                                info3.startMarginPercent=0.11f;
                                score.setTypeface(tf);
                                score.setText(String.valueOf(scores[i]));
                                score.setTextColor(getResources().getColor(R.color.White));
                                score.requestLayout();
                                score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                score.setGravity(Gravity.CENTER_HORIZONTAL);
                                score.setTextSize(TypedValue.COMPLEX_UNIT_PT,9f);
                                if(i==indexn){
                                    score.setTextColor(getResources().getColor(R.color.green));
                                }
                                pl.addView(score);
                            }

                        }

                    }else{
                        Toast.makeText(getActivity(),"بروز خطا در برقراری ارتباط با سرور",Toast.LENGTH_SHORT).show();
                    }
                }
            });}catch (JSONException e) {
            e.printStackTrace();}

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
            getFragmentManager().beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();

      }
    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                swipeLayout.setRefreshing(false);

            }

        }, 1000);
        getFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
