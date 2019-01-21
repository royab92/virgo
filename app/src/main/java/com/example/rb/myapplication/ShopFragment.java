package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class ShopFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop,null);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
      final  Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        pref = this.getActivity().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor = pref.edit();
        final SharedPreferences.Editor editor = pref.edit();
        TextView t1 = (TextView) view.findViewById(R.id.textstar);
        t1.setTypeface(tf);
        t1.setText("" + pref.getInt("score", 0));
        TextView t2 = (TextView) view.findViewById(R.id.textgold);
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
        TextView t3 = (TextView) view.findViewById(R.id.textView5);
        t3.setTypeface(tf);
        TextView t4 = (TextView) view.findViewById(R.id.textView6);
        t4.setTypeface(tf);

     String phone=pref.getString("phone", null);
        int level=pref.getInt("level",0);
        Store str=new Store(this.getActivity());
        JSONObject requestjo = new JSONObject();
        try {
            requestjo.put("level",level);
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            str.inStore(requestjo, new Store.StoreItems(){
                @Override
                public void onStore(final int Required[], String Name[], boolean IsEnable[], String Icon[], String Type[],final int cAmount[], final int rAmount[], int level[], int StatusCode, String Message) {
                    //Toast.makeText(MainActivity.this,"Appconfig",Toast.LENGTH_SHORT).show();
                   // int gcount=1;
                   // int tcount=1;
                    float tmar = 0.03f;
                    float tmar1 = 0.03f;
                    float amar = 0.03f;
                    float amar1 = 0.03f;
                    float pmar=0.07f;
                    float pmar1=0.07f;
                    if (StatusCode == 0) {
                        PercentRelativeLayout co = (PercentRelativeLayout) view.findViewById(R.id.coins);
                        PercentRelativeLayout fe = (PercentRelativeLayout) view.findViewById(R.id.featurs);
                    for (int i=0;i<Required.length;i++){
                        if(Type[i].equals("goldPack")){
                            TextView gold = new TextView(getActivity());
                            gold.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                            PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) gold.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
                            info.topMarginPercent = 0.02f;
                            info.widthPercent = 0.22f;
                            info.heightPercent=0.73f;
                            info.startMarginPercent = tmar;
                            tmar=tmar+0.24f;
                            gold.setTypeface(tf);
                            gold.setText(Name[i]);
                            gold.setTextColor(getResources().getColor(R.color.White));
                            gold.requestLayout();
                            gold.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            gold.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
                            gold.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f);
                            gold.setClickable(true);
                            gold.setBackground(getResources().getDrawable(R.drawable.alien));
                            co.addView(gold);
                            final int index=i;
                            gold.setClickable(true);
                            gold.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(getActivity());
                                   // builder.setTitle("خطا")

                                    builder.setMessage("آیا مایل به پرداخت و خرید هستید؟!")
                                            .setCancelable(true)
                                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if(pref.getInt("gold",0)>=rAmount[index]){
                                                        int  newgold=pref.getInt("gold",0)-rAmount[index];
                                                        newgold=newgold+Required[index];
                                                        editor.putInt("gold",newgold);
                                                        editor.apply();
                                                        TextView t2 = (TextView) view.findViewById(R.id.textgold);
                                                        t2.setText("" + pref.getInt("gold", 0));
                                                    }else{
                                                        AlertDialog.Builder builder;
                                                        builder = new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("خطا")
                                                                .setMessage("سکه شما کافی نیست!")
                                                                .setCancelable(true)
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
                                            })
                                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                           // .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();

                                }
                            });
                            ImageView test = new ImageView(getActivity());
                            test.setLayoutParams(new PercentRelativeLayout.LayoutParams(80, 60));
                            PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) test.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                            info1.widthPercent = 0.22f;
                            info1.heightPercent = 0.20f;
                            info1.topMarginPercent = 0.27f;
                            info1.startMarginPercent = amar;
                            if(i>0){amar = amar + 0.23f+(0.01f*i);}else{
                            amar = amar + 0.24f;}
                                test.setImageResource(R.drawable.bitcoin);
                            test.requestLayout();
                            co.addView(test);
                            TextView pgold = new TextView(getActivity());
                            pgold.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                            PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) pgold.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                            info2.topMarginPercent = 0.58f;
                            info2.widthPercent = 0.20f;
                            info2.startMarginPercent = pmar;
                            pmar=pmar+0.25f;
                            pgold.setTypeface(tf);
                            pgold.setText(String.valueOf(rAmount[i])+"سکه");
                            pgold.setTextColor(getResources().getColor(R.color.White));
                            pgold.requestLayout();
                            pgold.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            pgold.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
                            pgold.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f);
                            pgold.setClickable(true);
                            //gold.setClickable(true);
                           pgold.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(getActivity());
                                    // builder.setTitle("خطا")

                                    builder.setMessage("آیا مایل به پرداخت و خرید هستید؟!")
                                            .setCancelable(true)
                                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if(pref.getInt("gold",0)>=rAmount[index]){
                                                        int  newgold=pref.getInt("gold",0)-rAmount[index];
                                                        newgold=newgold+Required[index];
                                                        editor.putInt("gold",newgold);
                                                        editor.apply();
                                                        TextView t2 = (TextView) view.findViewById(R.id.textgold);
                                                        t2.setText("" + pref.getInt("gold", 0));
                                                    }else{
                                                        AlertDialog.Builder builder;
                                                        builder = new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("خطا")
                                                                .setMessage("سکه شما کافی نیست!")
                                                                .setCancelable(true)
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
                                            })
                                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            // .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            });
                            co.addView(pgold);
                            if(!IsEnable[i]){
                                gold.setAlpha(0.5f);
                                test.setAlpha(0.5f);
                                pgold.setAlpha(0.5f);
                            }
                        }
                        if(Type[i].equals("time")){
                            TextView time = new TextView(getActivity());
                            time.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                            PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) time.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
                            info.topMarginPercent = 0.02f;
                            info.widthPercent = 0.22f;
                            info.heightPercent=0.73f;
                            info.startMarginPercent = tmar1;
                            tmar1=tmar1+0.24f;
                            final int index=i;
                            time.setTypeface(tf);
                            time.setText(Name[i]);
                            time.setTextColor(getResources().getColor(R.color.White));
                            time.requestLayout();
                            time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            time.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
                            time.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f);
                            time.setClickable(true);
                           // gold.setClickable(true);
                           time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(getActivity());
                                    // builder.setTitle("خطا")

                                    builder.setMessage("آیا مایل به پرداخت و خرید هستید؟!")
                                            .setCancelable(true)
                                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if(pref.getInt("gold",0)>=cAmount[index]){
                                                        int  newgold=pref.getInt("gold",0)-cAmount[index];
                                                     int  newtime=pref.getInt("time",0)+Required[index];
                                                        editor.putInt("gold",newgold);
                                                        editor.putInt("time",newtime);
                                                        editor.apply();
                                                        TextView t2 = (TextView) view.findViewById(R.id.textgold);
                                                        t2.setText("" + pref.getInt("gold", 0));
                                                    }else{
                                                        AlertDialog.Builder builder;
                                                        builder = new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("خطا")
                                                                .setMessage("سکه شما کافی نیست!")
                                                                .setCancelable(true)
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
                                            })
                                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            // .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            });
                            time.setBackground(getResources().getDrawable(R.drawable.question));
                            fe.addView(time);
                            ImageView testa = new ImageView(getActivity());
                            testa.setLayoutParams(new PercentRelativeLayout.LayoutParams(80, 60));
                            PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) testa.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                            info1.widthPercent = 0.22f;
                            info1.heightPercent = 0.20f;
                            info1.topMarginPercent = 0.27f;
                            info1.startMarginPercent = amar1;
                            if(i>0){amar1 = amar1 + 0.23f+(0.01f*i);}else{
                                amar1 = amar1 + 0.24f;}
                            testa.setImageResource(R.drawable.time);
                            testa.requestLayout();
                            fe.addView(testa);
                            TextView ptime = new TextView(getActivity());
                            ptime.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
                            PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) ptime.getLayoutParams();
                            PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                            info2.topMarginPercent = 0.58f;
                            info2.widthPercent = 0.20f;
                            info2.startMarginPercent = pmar1;
                            pmar1=pmar1+0.25f;
                            ptime.setTypeface(tf);
                            ptime.setText(String.valueOf(cAmount[i])+"سکه");
                            ptime.setTextColor(getResources().getColor(R.color.White));
                            ptime.requestLayout();
                            ptime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            ptime.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
                            ptime.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f);
                            ptime.setClickable(true);
                            //gold.setClickable(true);
                            ptime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(getActivity());
                                    // builder.setTitle("خطا")

                                    builder.setMessage("آیا مایل به پرداخت و خرید هستید؟!")
                                            .setCancelable(true)
                                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if(pref.getInt("gold",0)>=cAmount[index]){
                                                        int  newgold=pref.getInt("gold",0)-cAmount[index];
                                                        int  newtime=pref.getInt("time",0)+Required[index];
                                                        editor.putInt("gold",newgold);
                                                        editor.putInt("time",newtime);
                                                        editor.apply();
                                                        TextView t2 = (TextView) view.findViewById(R.id.textgold);
                                                        t2.setText("" + pref.getInt("gold", 0));
                                                    }else{
                                                        AlertDialog.Builder builder;
                                                        builder = new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("خطا")
                                                                .setMessage("سکه شما کافی نیست!")
                                                                .setCancelable(true)
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
                                            })
                                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            // .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            });
                            fe.addView(ptime);
                            if(!IsEnable[i]){
                                time.setAlpha(0.5f);
                                ptime.setAlpha(0.5f);
                                testa.setAlpha(0.5f);
                            }
                        }
                    }
                    }
                          else{

                        }

                }
            });
        } catch (JSONException e) {
                e.printStackTrace();
            }

    }
    /*
     String id="gold"+String.valueOf(gcount);
                            int resID = getResources().getIdentifier(id, "id","com.example.rb.myapplication" );
                            TextView tg=(TextView)view.findViewById(resID);
                            tg.setVisibility(View.VISIBLE);
                            tg.setText(Name[i]);
                            String idp="imageView"+String.valueOf(gcount-1);
                            int resIDD = getResources().getIdentifier(idp, "id","com.example.rb.myapplication" );
                            ImageView ig=(ImageView)view.findViewById(resIDD);
                            ig.setVisibility(View.VISIBLE);
                            String idd="pgold"+String.valueOf(gcount);
                            int resIDDD = getResources().getIdentifier(idd, "id","com.example.rb.myapplication" );
                            TextView tgp=(TextView)view.findViewById(resIDDD);
                            tgp.setVisibility(View.VISIBLE);
                            tgp.setText(Amount[i]+"ریال");
                            if(!IsEnable[i]){
                                tg.setAlpha(0.5f);
                                ig.setAlpha(0.5f);
                                tgp.setAlpha(0.5f);
                            }
                            gcount=gcount+1;

                             String id="time"+String.valueOf(tcount);
                            int resID = getResources().getIdentifier(id, "id","com.example.rb.myapplication" );
                            TextView tg=(TextView)view.findViewById(resID);
                            tg.setVisibility(View.VISIBLE);
                            tg.setText(Name[i]);
                            String idp="imageView"+String.valueOf(tcount+6);
                            int resIDD = getResources().getIdentifier(idp, "id","com.example.rb.myapplication" );
                            ImageView ig=(ImageView)view.findViewById(resIDD);
                            ig.setVisibility(View.VISIBLE);
                            String idd="ptime"+String.valueOf(tcount);
                            int resIDDD = getResources().getIdentifier(idd, "id","com.example.rb.myapplication" );
                            TextView tgp=(TextView)view.findViewById(resIDDD);
                            tgp.setVisibility(View.VISIBLE);
                            if(!IsEnable[i]){
                                tg.setAlpha(0.5f);
                                ig.setAlpha(0.5f);
                                tgp.setAlpha(0.5f);
                            }
                            tgp.setText(Required[i]+"طلا");
                            tcount=tcount+1;
     */


}
