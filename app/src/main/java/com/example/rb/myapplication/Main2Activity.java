package com.example.rb.myapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;

public class Main2Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    SharedPreferences pref;
    private ActionBar toolbar;
    SharedPreferences.Editor editor;
    BottomNavigationView navigation;
    Switch mySwitch = null;
    MediaPlayer mp;
    boolean play;
    boolean man = true;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<QuestionObject> questions = new ArrayList<QuestionObject>();
    private static final int ID_DAILY     = 1;
    private static final int ID_STORED   = 2;
    QuickAction quickAction;
    boolean firstime;
    final static String TAG = Main2Activity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        String line = null;
        File file=new File("siavashFile.txt");
        if(file.exists()){
            Toast.makeText(Main2Activity.this,"exists",Toast.LENGTH_SHORT).show();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream (new File("siavashFile.txt"));

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }

if(line!=null){
            Toast.makeText(Main2Activity.this,line,Toast.LENGTH_SHORT).show();
}


      /*  String classname="com.example.rb.myapplication.Aliens";
        Object obj=null;
        try {
            obj = Class.forName(classname).newInstance();
        }catch (InstantiationException we){
            we.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

if(obj!=null){
            Toast.makeText(Main2Activity.this,"obj not null",Toast.LENGTH_SHORT).show();
}
          // obj.toString();*/

     /*   TextView txt=(TextView)theInflatedView.findViewById(R.id.questions);
        ViewTarget target=new ViewTarget(txt);
        new ShowcaseView.Builder(this)
                .setTarget(target)
                .setContentTitle("Results")
                .setContentText("This is highlighting the final results")
                .hideOnTouchOutside()
                .build();*/

        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), 10, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
         pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
       if(!pref.contains("changes")) {
           editor=pref.edit();
           editor.putBoolean("changes",true);
           editor.apply();
           AlertDialog.Builder builder;
           builder = new AlertDialog.Builder(Main2Activity.this, R.style.AlertDialogTheme);
           builder.setTitle("تغییرات نسخه ۱.۶.۷")
                   .setMessage("- اضافه شدن راهنمای بازی" +"\n"
                           )
                   .setCancelable(false)
                   .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   })
                   .setIcon(android.R.drawable.ic_dialog_info)
                   .show();
       }
        if(isOnline()){

        }
else{
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
            builder.setTitle("خطا")
                    .setMessage("لطفا اینترنت خود را متصل نمایید!")
                    .setCancelable(true)
                    .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.drawable.wifi)
                    .show();
        }

        toolbar = getSupportActionBar();
        loadFragment(new HomeFragment());
        ActionBar a = getSupportActionBar();
        if (a != null) {
            if (a.isShowing()) {
                a.hide();
            }
        }
        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setItemIconTintList(null);
        navigation.getMenu().findItem(R.id.navigation_home).getIcon().setTint(getResources().getColor(R.color.violet));
        navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
        final SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
        if (pref.contains("man")) {
            if (pref.getBoolean("man", false)) {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.manp);
            } else {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.womanp);
            }
        }

        navigation.setOnNavigationItemSelectedListener(this);
   /*     TextView rnk=new TextView(this);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "IRANSansMobile.ttf");
        PercentRelativeLayout pl=(PercentRelativeLayout)findViewById(R.id.main);
        rnk.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT,PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
        PercentRelativeLayout.LayoutParams params=(PercentRelativeLayout.LayoutParams)rnk.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info=params.getPercentLayoutInfo();
        info.topMarginPercent=0.22f;
        info.widthPercent=0.05f;
        info.heightPercent=0.03f;
        info.startMarginPercent=0.80f;
        rnk.setTypeface(tf);
        if(pref.contains("qnum")){
            rnk.setText(String.valueOf(pref.getInt("qnum",0)));
        }
        rnk.setTextColor(getResources().getColor(R.color.White));
        rnk.requestLayout();
        rnk.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rnk.setGravity(Gravity.CENTER_HORIZONTAL);
        rnk.setTextSize(TypedValue.COMPLEX_UNIT_PT,6f);
        rnk.setId(R.id.qnumid);
        rnk.setBackground(getResources().getDrawable(R.drawable.number));
        pl.addView(rnk);
        rnk.setVisibility(View.INVISIBLE);*/

        ActionItem nextItem = new ActionItem(ID_DAILY, "", (R.drawable.questions));
        ActionItem prevItem = new ActionItem(ID_STORED, "", R.drawable.box);
         quickAction = new QuickAction(this, QuickAction.VERTICAL);
        quickAction.addActionItem(nextItem);
        quickAction.addActionItem(prevItem);
        quickAction.setColorRes(R.color.lpurple);
      /*  View view = View.inflate(this, R.layout.fragment_home, null);
        Button cmenu=(Button) view.findViewById(R.id.btnm);
        cmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAction.show(v);
            }
        });*/
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionItem item) {
                if(item.getActionId()==ID_DAILY){
                    SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
                    if (!pref.contains("qnum") || pref.getInt("qnum", 0) > 0) {
                       // Fragment fragment;
                       // fragment = new QuestionFragment();
                      //  loadFragment(fragment);
                        Intent i=new Intent(Main2Activity.this,Question.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Main2Activity.this, "سوالات امروز شما به پایان رسیده است!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(item.getActionId()==ID_STORED){
                    SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
                    if (pref.contains("stornum") && pref.getInt("stornum", 0) > 0) {
                        Intent i=new Intent(Main2Activity.this,StoredQuestion.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Main2Activity.this, "سوالی در صندوق موجود نمی باشد!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
               // Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
               // TextView rnk=(TextView)findViewById(R.id.qnumid);
                //if(rnk!=null){
                   // rnk.setVisibility(View.INVISIBLE);
               // }
            }
        });




    /*    Intent i = getIntent();
        boolean q = i.getBooleanExtra("question", false);
        if (q) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            // Toast.makeText(Main2Activity.this,String.valueOf(fm.getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
            Fragment fragment;
            fragment = new Question2Fragment();
            loadFragment(fragment);
        }*/




/*
      MenuItem item=navigation.getMenu().findItem(R.id.navigation_home);
        Animation animation=new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(navigation.getMenu().findItem(R.id.navigation_home).getIcon());
        imageView.startAnimation(animation);
        item.setActionView(imageView);
*/
        BottomNavigationViewHelper.disableShiftMode(navigation,getApplicationContext());
        toolbar.setTitle("");
        navigation.getMenu().findItem(R.id.navigation_setting).setTitle("");
        navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("");
        navigation.getMenu().findItem(R.id.navigation_shop).setTitle("");
        navigation.getMenu().findItem(R.id.navigation_sale).setTitle("");
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)
                navigation.getChildAt(0);
        final View iconView = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
        // iconView.setBackgroundColor(getResources().getColor(R.color.White));
        iconView.setBackground(getDrawable(R.drawable.profile));
        iconView.setScaleY(1.5f);
        iconView.setScaleX(1.5f);
        //iconView.setTranslationY(3);
        final View iconView1 = menuView.getChildAt(3).findViewById(android.support.design.R.id.icon);
        iconView1.setScaleX(1.2f);
        iconView1.setScaleY(1.2f);
        final View iconView2 = menuView.getChildAt(1).findViewById(android.support.design.R.id.icon);
        iconView2.setScaleX(1.2f);
        iconView2.setScaleY(1.2f);
        final View iconView3 = menuView.getChildAt(0).findViewById(android.support.design.R.id.icon);
        iconView3.setScaleX(1.2f);
        iconView3.setScaleY(1.2f);
        //  final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
        // final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        mp = MediaPlayer.create(this, R.raw.drones);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.navigation_shop:
                fragment = new ShopFragment();
                loadFragment(fragment);
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_shop).setTitle("فروشگاه");
                navigation.getMenu().findItem(R.id.navigation_sale).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_shop).setChecked(true);
                ActionBar a = getSupportActionBar();
                if (a != null) {
                    if (a.isShowing()) {
                        a.hide();
                    }
                }
                return true;
            case R.id.navigation_ranking:
                fragment = new RankingFragment();
                loadFragment(fragment);
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("برترین ها");
                navigation.getMenu().findItem(R.id.navigation_shop).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_sale).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_ranking).setChecked(true);
                a = getSupportActionBar();
                if (a != null) {
                    if (a.isShowing()) {
                        a.hide();
                    }
                }
                return true;
            case R.id.navigation_home:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0 ){
                    fm.popBackStack();
                }
                fragment = new HomeFragment();
                loadFragment(fragment);
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_shop).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_sale).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
                a = getSupportActionBar();
                if (a != null) {
                    if (a.isShowing()) {
                        a.hide();
                    }
                }
                return true;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                loadFragment(fragment);
                //final ActionBar abar = getSupportActionBar();
                //  abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line under the action bar
                // View viewActionBar = getLayoutInflater().inflate(R.layout.abs, null);
                //  ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                //   ActionBar.LayoutParams.WRAP_CONTENT,
                //   ActionBar.LayoutParams.MATCH_PARENT,
                //  Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                //  TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.tvTitle);
                //  textviewTitle.setText("پیکربندی");
                //  abar.setCustomView(viewActionBar, params);
                //  abar.setDisplayShowCustomEnabled(true);
                // abar.setDisplayShowTitleEnabled(false);
                //  abar.setDisplayHomeAsUpEnabled(true);
                // abar.setIcon(R.color.transparent);
                //abar.setHomeButtonEnabled(true);
                //toolbar.setTitle("پیکربندی");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("پیکربندی");
                navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_shop).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_sale).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_setting).setChecked(true);
               // Toast.makeText(Main2Activity.this,navigation.getMenu().findItem(R.id.navigation_setting).getTitle(),Toast.LENGTH_SHORT).show();
                a = getSupportActionBar();
                if (a != null) {
                    if (a.isShowing()) {
                        a.hide();
                    }
                }
                return true;
            case R.id.navigation_sale:
                fragment = new TradeFragment();
                loadFragment(fragment);
                navigation.getMenu().findItem(R.id.navigation_sale).setTitle("مبادله");
                navigation.getMenu().findItem(R.id.navigation_setting).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_ranking).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_shop).setTitle("");
                navigation.getMenu().findItem(R.id.navigation_sale).setChecked(true);
                return true;
        }
        return false;
    }

    public void Clickmap(View v) {
        Intent i = new Intent(Main2Activity.this, MapsActivity.class);
        startActivity(i);
        finish();
    }

    public void Clickalien(View v) {
        Intent i = new Intent(Main2Activity.this, AlienMapsActivity.class);
        startActivity(i);
    }

    public void Clickquestion(View v) {
        Intent i = new Intent(Main2Activity.this, QuestionMapsActivity.class);
        startActivity(i);
        finish();
    }

    public void ClickFeature(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(Main2Activity.this, R.style.AlertDialogTheme);
        builder.setTitle("بزودی....")
                .setMessage("در نسخه های آتی این بخش فعال می شود!"
                )
                .setCancelable(false)
                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    private boolean loadFragment(Fragment fragment) {
        String tag = fragment.getClass().getName();
        //switching fragment
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment,tag)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    public void Clickhelp(View v) {
        Intent ih = new Intent(Main2Activity.this, Help.class);
        startActivity(ih);
    }

    public void Clickabout(View v) {
        Intent ina = new Intent(Main2Activity.this, About.class);
        startActivity(ina);
    }
    public void Clickintro(View v){
      final String phone=pref.getString("phone",null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main2Activity.this,R.style.AlertDialogTheme);
        alertDialog.setTitle("معرفی برنامه به دوستان");
        alertDialog.setMessage("شماره موبایل دوستت را وارد کن:");
        final EditText input = new EditText(Main2Activity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setTextColor(getResources().getColor(R.color.dpurple));
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
alertDialog.setCancelable(true);
        alertDialog.setView(input);
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setPositiveButton("تایید",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().matches("")){
                            Toast.makeText(Main2Activity.this,"شماره ای وارد نشده است!",Toast.LENGTH_SHORT).show();
                        }else {
                            Friends upd = new Friends(Main2Activity.this);
                            JSONObject requestjo = new JSONObject();
                            try {

                                requestjo.put("friendNo", input.getText().toString());
                                requestjo.put("AppName", "addigit");
                                requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                                requestjo.put("UserId", "0");
                                requestjo.put("PhoneNo", phone);
                                upd.introRecieve(requestjo, new Friends.intro() {
                                    @Override
                                    public void onintro(int StatusCode, String Message) {

                                        if (StatusCode != 0) {

                                            if(StatusCode==4){
                                                AlertDialog.Builder builder;
                                                builder = new AlertDialog.Builder(Main2Activity.this, R.style.AlertDialogTheme);
                                                builder.setTitle("اخطار")
                                                        .setMessage(" شما نمی توانید معرف خودتان باشید!")
                                                        .setCancelable(true)
                                                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }else {
                                                Toast.makeText(Main2Activity.this, Message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).setNegativeButton("اشتراک گذاری در شبکه های اجتماعی",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try{
                   // File photo=new File(getFilesDir(),"launch.png");
                    Uri imageu=Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),BitmapFactory.decodeResource(getResources(),R.drawable.launch),null,null));
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_SUBJECT,"Virgo");
                    String sa = "\n"+ "من در حال بازی کردن بازی جذاب و هیجان انگیز ویرگو هستم, دوست دارم که تو هم در کنار من به این بازی بپیوندی!"+"\n";
                    sa=sa+"http://baziigram.com/DownloadApp/virgo.apk";
                    i.putExtra(Intent.EXTRA_TEXT,sa);
                    i.putExtra(Intent.EXTRA_STREAM,imageu);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(i,"انتخاب کنید:"));
                }catch (Exception e){

                }
            }
        });
        alertDialog.show();
    }

    public void Clickwoman(View v) {
        ImageView wtik = (ImageView) findViewById(R.id.wtik);
        wtik.setVisibility(View.VISIBLE);
        ImageView mtik = (ImageView) findViewById(R.id.mtik);
        mtik.setVisibility(View.INVISIBLE);
        man = false;

    }

    public void Clickman(View v) {
        ImageView wtik = (ImageView) findViewById(R.id.wtik);
        wtik.setVisibility(View.INVISIBLE);
        ImageView mtik = (ImageView) findViewById(R.id.mtik);
        mtik.setVisibility(View.VISIBLE);
        man = true;

    }

    public void Clicksave(View v) {
        EditText name = (EditText) findViewById(R.id.editText2);
        EditText age = (EditText) findViewById(R.id.editText3);
        SharedPreferences.Editor editor = getSharedPreferences("VorujakPref", MODE_PRIVATE).edit();
        if (man) {
            editor.putBoolean("man", true);
            editor.commit();
        } else {
            editor.putBoolean("man", false);
            editor.commit();
        }
        if (!name.getText().toString().matches("")) {
            editor.putString("username", name.getText().toString());
            editor.commit();
            // name.setText("");
        }
        if (!age.getText().toString().matches("")) {
            String cage=age.getText().toString();
            cage=cage.replace("۰","0").replace("۱","1").replace("۲","2").replace("۳","3").replace("۴","4").replace("۵","5").replace("۶","6").replace("۷","7").replace("۸","8").replace("۹","9");
           // Toast.makeText(Main2Activity.this,cage,Toast.LENGTH_SHORT).show();
            editor.putString("age",cage );
            editor.commit();
            //age.setText("");
        }
        Toast.makeText(Main2Activity.this, "ذخیره سازی موفق", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        if (pref.contains("man")) {
            if (pref.getBoolean("man", false)) {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.manp);
            } else {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.womanp);
            }
        }
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)
                navigation.getChildAt(0);
        final View iconView = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
        // iconView.setBackgroundColor(getResources().getColor(R.color.White));
        iconView.setBackground(getDrawable(R.drawable.profile));
        iconView.setScaleY(1.5f);
        iconView.setScaleX(1.5f);


        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
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
        UpdateUser upd=new UpdateUser(Main2Activity.this);
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
                        Toast.makeText(Main2Activity.this,Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        String line = null;
        File file=new File("siavashFile.txt");
        if(file.exists()){
            Toast.makeText(Main2Activity.this,"exists",Toast.LENGTH_SHORT).show();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream (new File("siavashFile.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }

        if(line!=null){
            Toast.makeText(Main2Activity.this,line,Toast.LENGTH_SHORT).show();
        }

        SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
        if (pref.contains("man")) {
            if (pref.getBoolean("man", false)) {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.manp);
            } else {
                navigation.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.womanp);
            }
        }
        SettingFragment sf = new SettingFragment();
        View v = sf.getView();
        if (v != null) {
            mySwitch = (Switch) v.findViewById(R.id.switch1);
            if (play) {
                mySwitch.setChecked(true);
            } else {
                mySwitch.setChecked(false);
            }
        }
     /*   View view = View.inflate(this, R.layout.fragment_ranking, null);
        Button b2=(Button)view.findViewById(R.id.tajrobe);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new TajrobFragment();
                loadFragment(fragment);
            }
        });*/
    }

    public void Clickswitch(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("VorujakPref", MODE_PRIVATE).edit();
        mySwitch = (Switch) findViewById(R.id.switch1);
        if (mySwitch.isChecked()) {
            mp.start();
            play = true;
            mySwitch.setChecked(true);
            editor.putBoolean("play", true);
            editor.commit();
        } else {
            mp.pause();
            mySwitch.setChecked(false);
            play = false;
            editor.putBoolean("play", false);
            editor.commit();
        }
    }

  /*  public void Clickquestions(View v) {
        SharedPreferences pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
        if (!pref.contains("qnum") || pref.getInt("qnum", 0) != 0) {
            Fragment fragment;
            fragment = new QuestionFragment();
            loadFragment(fragment);
        } else {
            Toast.makeText(Main2Activity.this, "سوالات امروز شما به پایان رسیده است!", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
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

        String name=pref.getString("username",null);
        String age=pref.getString("age",null);
        UpdateUser upd=new UpdateUser(Main2Activity.this);
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
            requestjo.put("name",name);
            requestjo.put("birthdate",age);
            requestjo.put("avatar",avatar);
            requestjo.put("AppName", "addigit");
            requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
            requestjo.put("UserId", "0");
            requestjo.put("PhoneNo", phone);
            upd.UpdateRecieve(requestjo,new UpdateUser.Update(){
                @Override
                public void onUpdate(int StatusCode, String Message){

                    if(StatusCode!=0){
                        Toast.makeText(Main2Activity.this,Message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean isOnline(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable()){
            return false;
        }
        return true;
    }
    public void Clickdana(View v){
        Fragment fragment;
        fragment = new DanaFragment();
        loadFragment(fragment);
    }
    public void Clicktajrobe(View v){
       /* Fragment fragment = getSupportFragmentManager().findFragmentByTag(TajrobFragment.class.getName());
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();}*/
        Fragment fragment1;
        fragment1 = new TajrobFragment();
        loadFragment(fragment1);

    }
    public void Clickdara(View v){
        Fragment fragment;
        fragment = new DaraFragment();
        loadFragment(fragment);

    }
    public void Clickmenu(View v){
        quickAction.show(v);
       // TextView rnk=(TextView)findViewById(R.id.qnumid);
       // if(rnk!=null){
        //    rnk.setVisibility(View.VISIBLE);
       // }

    }
    public void Clickplus(View v){
        Toast.makeText(Main2Activity.this,"سوال به صندوق سوالات اضافه شد!",Toast.LENGTH_SHORT).show();
        pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.contains("stornum")){
            int size=pref.getInt("stornum",0);
            editor.putInt("stornum",size+1);
            editor.putInt("squestionId"+String.valueOf(size),pref.getInt("tquestionId",0));
            editor.putString("squestion"+String.valueOf(size),pref.getString("tquestion",null));
            editor.putString("sanswer1"+String.valueOf(size),pref.getString("tanswer1",null));
            editor.putString("sanswer2"+String.valueOf(size),pref.getString("tanswer2",null));
            editor.putString("sanswer3"+String.valueOf(size),pref.getString("tanswer3",null));
            editor.putString("sanswer4"+String.valueOf(size),pref.getString("tanswer4",null));
            editor.putInt("scorrect"+String.valueOf(size),pref.getInt("tcorrect",0));
            editor.apply();
        }
        else{
            int size1=0;
            editor.putInt("stornum",size1+1);
            editor.putInt("squestionId"+String.valueOf(size1),pref.getInt("tquestionId",0));
            editor.putString("squestion"+String.valueOf(size1),pref.getString("tquestion",null));
            editor.putString("sanswer1"+String.valueOf(size1),pref.getString("tanswer1",null));
            editor.putString("sanswer2"+String.valueOf(size1),pref.getString("tanswer2",null));
            editor.putString("sanswer3"+String.valueOf(size1),pref.getString("tanswer3",null));
            editor.putString("sanswer4"+String.valueOf(size1),pref.getString("tanswer4",null));
            editor.putInt("scorrect"+String.valueOf(size1),pref.getInt("tcorrect",0));
            editor.apply();
        }
        Fragment fragment;
        fragment = new HomeFragment();
        loadFragment(fragment);
    }
    public void Clickdate(View v){
        PersianCalendar now= new PersianCalendar();
        com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog datePickerDialog= com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.newInstance(new com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String birthdate=year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
                EditText age = (EditText) findViewById(R.id.editText3);
                age.setText(birthdate);
               // Toast.makeText(getApplicationContext(),birthdate,Toast.LENGTH_SHORT).show();
            }
        },now.getPersianYear(),now.getPersianMonth(),now.getPersianDay());
      datePickerDialog.setThemeDark(true);
       datePickerDialog.show(getFragmentManager(),"tpd");
    }

    @Override
    public void onBackPressed() {
        pref = getSharedPreferences("VorujakPref", MODE_PRIVATE);
        editor=pref.edit();
       // if(pref.getBoolean("back",false)){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
       // Toast.makeText(Main2Activity.this,String.valueOf(fm.getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
        Fragment fragment=new HomeFragment();
    Fragment my=fm.findFragmentByTag(fragment.getClass().getName());
        Fragment fragment1=new ShopFragment();
        Fragment my1=fm.findFragmentByTag(fragment1.getClass().getName());
        Fragment fragment2=new SettingFragment();
        Fragment my2=fm.findFragmentByTag(fragment2.getClass().getName());
        Fragment fragment3=new RankingFragment();
        Fragment my3=fm.findFragmentByTag(fragment3.getClass().getName());
        if ((fm.getBackStackEntryCount() <= 1 )||(my!= null && my.isVisible()) ||(my1!= null && my1.isVisible())||(my2!= null && my2.isVisible())||(my3!= null && my3.isVisible())) {
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(Main2Activity.this,R.style.AlertDialogTheme);
            builder.setTitle("خروج")
                    .setMessage("آیا مایل به خروج از برنامه هستید؟")
                    .setCancelable(true)
                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // editor.putBoolean("back",false);
                            // editor.apply();
                            // Main2Activity.super.onBackPressed();
                            finish();
                        }
                    })
                    .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // editor.putBoolean("back",false);
                            //editor.apply();
                        }
                    })
                    .setIcon(R.drawable.exit)
                    .show();

        } else {
            fm.popBackStack();
 }

    }


    }

