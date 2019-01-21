package com.example.rb.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.RectangleShape;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bahrampashootan on 8/21/2018 AD.
 */

public class HomeFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final int ID_DAILY     = 1;
    private static final int ID_STORED   = 2;
    String SHOWCASE_ID="s3";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        pref = this.getActivity().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        TextView txt0=(TextView)view.findViewById(R.id.textView16);
        ImageView txt1=(ImageView)view.findViewById(R.id.btnm);
        TextView txt2=(TextView)view.findViewById(R.id.mapsearch);
        TextView txt3=(TextView)view.findViewById(R.id.questions);
        TextView txt4=(TextView)view.findViewById(R.id.aliens);
        TextView txt5=(TextView)view.findViewById(R.id.features);
       // new MaterialShowcaseView.Builder(this.getActivity()).;
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300); // half second between each showcase view
        config.setMaskColor(Color.argb(200,153,0,153));
        RectangleShape shape=new RectangleShape(10,30);
        config.setShape(shape);
       // config.setTitleTextGravity(Gravity.CENTER);
        // config.setContentTextColor(Color.argb(255,255,255,255));
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this.getActivity(), SHOWCASE_ID);

        sequence.setConfig(config);
        sequence.addSequenceItem(txt0,
                " به ویرگو خوش اومدی                                   " +
                        "      در اینجا اتفاقات عجیبی خواهد افتاد!" +
                        "از طریق ویرگو می تونی با فرازمینی ها ارتباط برقرار کنی، اونا رو ببینی و باهاشون دوست بشی! شاید هم مجبور بشی که با اونا بجنگی! برای این کار نیاز به توپ آتشین و معجون و کلی فیچر دیگه داری که کم کم پیداش می کنی! یادت باشه که اینجا می تونه خیلی عجیب باشه! پس خودتو آماده کن", "باشه");
        sequence.addSequenceItem(txt1,
                "فرازمینی ها پرسش و پاسخ رو دوست دارن!                                            اینجا یه صندوق سوالات داری که هر ۲۴ ساعت برات با تعدادی پرسش پُر میشه، اگه جواب درست بدی تعدادی سکه فضایی بهت تعلق می گیره! با این سکه ها می تونی کلی کار انجام بدی، پس سعی کن به دنبال پاسخ درست باشی...", "باشه");
        sequence.addSequenceItem(txt2,
                "با استفاده از این گزینه می تونی روی نقشه جستجو کنی، هر علامتی که روی نقشه ظاهر میشه نشونه یه چیزی هست که به مرور خودت کشفش می کنی، فقط اینو بهت بگم که هم فرازمینی هست، هم فیچر و پرسش! این رو هم بگم که شاید بعضی چیزا به مرور روی نقشه ات ظاهر بشه، پس حواست به همه چیز باشه", "باشه");

        sequence.addSequenceItem(txt3,
                "این گزینه کمکت می کنه فقط پرسش های روی نقشه رو ببینی! یعنی یه تعداد پرسش روی نقشه هست که حتما باید بری نزدیکشون تا برات نمایان بشه. اگه همون لحظه امکان پاسخگویی نداشتی هیچ مشکلی نیست، اونو برای خودت ذخیره کن تا به دست کسی نیوفته! بعد از ذخیره کردن، می تونی اونو تو صندوق سوالاتت ببینیش!", "باشه");

        sequence.addSequenceItem(txt4,
                "با این گزینه هم می تونی فقط جای فرازمینی ها رو روی نقشه پیدا کنی، آروم بهشون نزدیک بشی و وقتی دوربین باز شد بگردی دنبالشون! شاید یه گوشه ای مخفی شده باشه!! همیشه باید حواست باشه که اگه بهت حمله کرد تو هم با توپ آتشین بهش حمله کنی...", "باشه");
        sequence.addSequenceItem(txt5,"فیچرها زیادن! تعدادیشون رو باید بخری، اگه خوش شانس باشی تعدادی رو هم می تونی روی نقشه پیدا کنی! بعضی از فرازمینی ها شلخته هستند و فیچرهای خودشون رو این طرف و اون طرف جا میذارن! برو ببینم که چیکار می کنی...                                            راستی به زودی می تونی با دوستات یه گروه تشکیل بدید و با هم به اکتشافات جدید دست بزنید","باشه");
        sequence.start();
        TextView t1 = (TextView) view.findViewById(R.id.textstar);
        t1.setTypeface(tf);
        t1.setText("" + pref.getInt("score", 0));
        TextView t2 = (TextView) view.findViewById(R.id.textgold);
        t2.setTypeface(tf);
        t2.setText("" + pref.getInt("gold", 0));
        TextView un = (TextView) view.findViewById(R.id.username);
        un.setTypeface(tf);
        ImageView avatar = (ImageView) view.findViewById(R.id.propic);
        if (pref.contains("username")) {
            String name = pref.getString("username", null);
            un.setText(name);

        } else {
            editor.putString("username", "کاربر مهمان");
            editor.commit();
            un.setText("کاربر مهمان");
        }

        if(pref.contains("man")){
        Boolean man=pref.getBoolean("man",false);
        if(man){
        avatar.setImageResource(R.drawable.man);
        }else{
        avatar.setImageResource(R.drawable.woman);
        }
         }
        TextView lvl = (TextView) view.findViewById(R.id.level);
        int level = pref.getInt("level", 0);
        lvl.setTypeface(tf);
        lvl.setText("" + level);


        ProgressBar myp = (ProgressBar) view.findViewById(R.id.progressBar4);
        /*
        az file maghadir exp ra bekhanad

        */
        myp.setMax(15);
        myp.setProgress(1);

        TextView guard = (TextView) view.findViewById(R.id.guard);
        guard.setTypeface(tf);
        if(pref.getInt("armore", 0)>=0){
        guard.setText("" + pref.getInt("armore", 0));}
        else {
            guard.setText("");
            ImageView imm=(ImageView)view.findViewById(R.id.imageView23);
            imm.setAlpha(0.5f);
        }
        TextView bomb = (TextView) view.findViewById(R.id.bomb);
        bomb.setTypeface(tf);
        if(pref.getInt("stars", 0)>=0){
        bomb.setText("" + pref.getInt("stars", 0));}else {
            bomb.setText("");
            ImageView imm=(ImageView)view.findViewById(R.id.imageView24);
            imm.setAlpha(0.5f);
        }
        TextView life = (TextView) view.findViewById(R.id.life);
        life.setTypeface(tf);
        if(pref.getInt("life", 0)>=0){
        life.setText("" + pref.getInt("life", 0));}else {
            life.setText("");
            ImageView imm=(ImageView)view.findViewById(R.id.imageView25);
            imm.setAlpha(0.5f);
        }
        TextView potion = (TextView) view.findViewById(R.id.amordad);
        potion.setTypeface(tf);
        if(pref.getInt("potion", 0)>=0){
        potion.setText("" + pref.getInt("potion", 0));}else {
            potion.setText("");
            ImageView imm=(ImageView)view.findViewById(R.id.imageView26);
            imm.setAlpha(0.5f);}
        TextView raad = (TextView) view.findViewById(R.id.x);
        raad.setTypeface(tf);
        raad.setText("");
            ImageView immx=(ImageView)view.findViewById(R.id.imageView27);
            immx.setAlpha(0.5f);
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setTypeface(tf);
        if(pref.getInt("time",0)>=0){
        time.setText(""+pref.getInt("time",0));}else {
            time.setText("");
            ImageView imm=(ImageView)view.findViewById(R.id.imageView28);
            imm.setAlpha(0.5f);}
        TextView qu = (TextView) view.findViewById(R.id.questions);
        qu.setTypeface(tf);
        TextView maps = (TextView) view.findViewById(R.id.mapsearch);
        maps.setTypeface(tf);
        TextView fea = (TextView) view.findViewById(R.id.features);
        fea.setTypeface(tf);
        TextView al = (TextView) view.findViewById(R.id.aliens);
        al.setTypeface(tf);
        TextView tprogress = (TextView) view.findViewById(R.id.textprogress);
        tprogress.setTypeface(tf);
        //az file bekhanad
        tprogress.setText("1/15");
      final TextView qt=(TextView)view.findViewById(R.id.qnum);
        qt.setTypeface(tf);
        if(pref.contains("qnum")||pref.contains("stornum")){
            qt.setText(String.valueOf(pref.getInt("qnum",0)+pref.getInt("stornum",0)));
        }
        Date stime = Calendar.getInstance().getTime();
        long ti1 = (stime.getTime()) / (1000 * 60 * 60);
        long ti2;
        if (pref.contains("qserverTime")) {
            ti2 = pref.getLong("qserverTime", 0) / (60 * 60);
        } else {
            ti2 = ti1 + 24;
        }

        final String phone = pref.getString("phone", null);
updateUser();
        if (((!(pref.contains("qnum")))||(pref.getInt("qnum",0) <= 0 && Math.abs(ti2 - ti1) >= 24))||(Math.abs(ti2 - ti1) >= 24)) {
            Intent notifyIntent = new Intent(this.getActivity(),MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (getContext(), 10, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                    1000 * 60 * 60 * 24, pendingIntent);

            //  Toast.makeText(getContext(),"check",Toast.LENGTH_SHORT).show();
            Process asd = new Process(this.getActivity());
            JSONObject requestjo = new JSONObject();
            try {
                requestjo.put("AppName", "addigit");
                requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                requestjo.put("Userid", "0");
                requestjo.put("PhoneNo", phone);
                requestjo.put("category", "general");
                requestjo.put("hardness", 1);

                asd.onProcess(requestjo, new Process.ProcessResponse() {
                            @Override
                            public void getProcess(int questionId[], String question[], String answer1[], String answer2[], String answer3[], String answer4[], int correct[], int expireTime[], int goldAward[], int hardLevel[], String category[], Long serverTime, int StatusCode, String Message) {
                                //  Toast.makeText(Main2Activity.this, Message, Toast.LENGTH_SHORT).show();
                                if (StatusCode == 0) {
                                    editor.putInt("qnum",questionId.length);
                                    editor.putLong("qserverTime",serverTime);
                                    editor.commit();
                                   if(pref.contains("qnum")){
                                        qt.setText(String.valueOf(pref.getInt("qnum",0)));
                                    }
                                    for (int i = 0; i < questionId.length; i++) {
                                        editor.putInt("questionId"+String.valueOf(i),questionId[i]) ;
                                        editor.putString("question"+String.valueOf(i),question[i]);
                                        editor.putString("answer1"+String.valueOf(i),answer1[i]);
                                        editor.putString("answer2"+String.valueOf(i),answer2[i]);
                                        editor.putString("answer3"+String.valueOf(i),answer3[i]);
                                        editor.putString("answer4"+String.valueOf(i),answer4[i]);
                                        editor.putInt("correct"+String.valueOf(i),correct[i]);
                                        editor.putInt("qtime"+String.valueOf(i),expireTime[i]);
                                        editor.commit();
                                    }
                                }
                            }
                        }
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       // else if(pref.getInt("qnum",0) == 0){
          //  Toast.makeText(getContext(),"سوالات امروز شما به پایان رسیده است!",Toast.LENGTH_SHORT).show();
       // }

    }

    public void Clickmap(View v){
        Intent i=new Intent(this.getActivity(),MapsActivity.class);
        startActivity(i);
    }
    public void Clickalien(View v){
        Intent i=new Intent(this.getActivity(),Aliens.class);
        startActivity(i);
    }
    public void Clickquestion(View v){
        Intent i=new Intent(this.getActivity(),QuestionMapsActivity.class);
        startActivity(i);
    }

    public void Clickmenu(View v){

    }
    private void updateUser(){
        pref=getContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        String authkey=pref.getString("AuthKey",null);
        String phone=pref.getString("phone",null);
        int gold=pref.getInt("gold",0);
        int life=pref.getInt("life",0);
        int potion=pref.getInt("potion",0);
        int score=pref.getInt("score",0);
        int level=pref.getInt("level",0);
        int fire=pref.getInt("stars",0);
        int armor=pref.getInt("armor",0);
        int time=pref.getInt("time",0);
        String gender;
        int avatar;
        if(pref.getBoolean("man",false)){
            gender="male";
            avatar=2;
        }else{
            gender="female";
            avatar=1;
        }

        String namea=pref.getString("username",null);
        String agea=pref.getString("age",null);
        UpdateUser upd=new UpdateUser(this.getActivity());
        JSONObject requestjo = new JSONObject();
        try {

            requestjo.put("AuthKey",authkey);
            requestjo.put("Gold",gold);
            requestjo.put("Life",life);
            requestjo.put("Potion",potion);
            requestjo.put("score",score);
            requestjo.put("level",level);
            requestjo.put("fireballs",fire);
            requestjo.put("armor",armor);
            requestjo.put("time",time);
            requestjo.put("gender",gender);
            requestjo.put("name",namea);
            requestjo.put("birthdate",agea);
            requestjo.put("avatar",avatar);
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            upd.UpdateRecieve(requestjo,new UpdateUser.Update(){
                @Override
                public void onUpdate(int StatusCode, String Message){

                    if(StatusCode!=0){
                        Toast.makeText(getContext(),Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
/*

 */