package com.example.rb.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zsoft.signala.hubs.HubConnection;
import com.zsoft.signala.hubs.HubOnDataCallback;
import com.zsoft.signala.hubs.IHubProxy;
import com.zsoft.signala.transport.StateBase;
import com.zsoft.signala.transport.longpolling.LongPollingTransport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Bitmap.Config.ARGB_8888;
import static com.example.rb.myapplication.Award.awardType.none;

public class QuestionMapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 2000;
    boolean canGetLocation=false;
    public Location llocation=new Location("");
    double latitude;
    double longitude;
    boolean isGpsEnabled=false;
    boolean isNetworkEnabled=false;
    protected LocationManager lm;
    private GoogleMap mMap;
    private double l1;
    private double l2;
    public int number;
    static boolean flag=true;
    private Location location=new Location("ll");
    int awardCount = 1;
    protected int enemyCount = 1;
    private LocationManager mService;
    private GpsStatus mStatus;
    public ArrayList<Location> al=new ArrayList<Location>();
    User player = new User();
    private double mindist = 30;
    MediaPlayer mp;
    boolean play;
    SharedPreferences pref;
    int size;
    boolean isFABOpen=false;
    private ArrayList<Award> awards = new ArrayList<Award>();
    float zoomLevel;
    boolean canzoom;
    GoogleApiClient mGoogleApi;
    // private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    int pow;
    private BroadcastReceiver gpsLocationReciever=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
                LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    onMapReady(mMap);
                }
                else {
                    new Handler().postDelayed(sendUpdatesToUI,10);
                }
            }
        }
    };
    HubConnection con;
    protected IHubProxy hub = null;
    boolean firstime;
    String smessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maps);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        initGoogleAPIClient();
        showSettingDialog();
        mp= MediaPlayer.create(QuestionMapsActivity.this, R.raw.drones);
        pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        player.init(pref.getInt("gold", 0), pref.getInt("score", 0), pref.getInt("armor", 0), pref.getInt("potion", 0), pref.getInt("stars", 0), pref.getInt("life", 0));
        isFABOpen=false;
        Typeface tf=Typeface.createFromAsset(getAssets(),"IRANSansMobile(FaNum).ttf");
        TextView t1 = (TextView) findViewById(R.id.textstar);
        t1.setTypeface(tf);
        t1.setText("" + pref.getInt("score", 0));
        TextView t2 = (TextView) findViewById(R.id.textgold);
        t2.setTypeface(tf);
        t2.setText("" + pref.getInt("gold", 0));
        TextView un = (TextView) findViewById(R.id.username);
        un.setTypeface(tf);
editor.putBoolean("mapUpdate",true);
editor.apply();
        firstime=true;
        String url = "http://chat.baziigram.com";
        try {

            //SignalA Hub Connection
            con = new HubConnection(url, this, new LongPollingTransport()) {

                @Override
                public void OnError(Exception exception) {
                    // Log.e("SignalR State Error", exception.getMessage());
                    //Toast.makeText(this,"SignalR State Error"+ exception.getMessage(),Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }

                @Override
                public void OnMessage(String message) {
                    // Log.i("SignalR Message", message);
                    // Toast.makeText(MapsActivity.this,"SignalR Message"+ message,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnStateChanged(StateBase oldState, StateBase newState) {
                    //  Log.i("SignalR State Changed", oldState.getState() + " to " + newState.getState());
                    //Toast.makeText(MapsActivity.this,"SignalR State Changed"+oldState.getState() + " to " + newState.getState() ,Toast.LENGTH_SHORT).show();
                    switch (newState.getState()) {
                        case Disconnected:
                            break;
                        case Connected:
                            //registerHome(); //Register the home ID once signalR is connected
                            // Toast.makeText(MapsActivity.this,"connected",Toast.LENGTH_SHORT).show();
                            break;
                        case Connecting:
                            break;
                        case Reconnecting:
                            break;
                        case Disconnecting:
                            break;
                    }
                }
            };

            //Listen only for "devices" hub messages
            hub = con.CreateHubProxy("ChatHub");

            //Listen for device updates
            hub.On("broadcastMessage", new HubOnDataCallback() {
                @Override
                public void OnReceived(JSONArray args) {
                    // Log.i("SignalR Received", args.toString());
                   // Toast.makeText(QuestionMapsActivity.this, "SignalR Received" + args.toString(), Toast.LENGTH_SHORT).show();
                    smessage = args.toString();
                }
            });

            //Start SignalA
            if (con != null)
                con.Start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pref.contains("username")) {
            String name = pref.getString("username", null);
            un.setText(name);

        } else {
            editor.putString("username", "کاربر مهمان");
            editor.commit();
            un.setText("کاربر مهمان");
        }
      //  play=false;
        zoomLevel = 14.0f;
canzoom=true;
        SharedPreferences pref=getSharedPreferences("VorujakPref", MODE_PRIVATE);
        FloatingActionButton im=(FloatingActionButton) findViewById(R.id.fab2);
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
                im.setImageResource(R.drawable.manprofile);
            }else {
                im.setImageResource(R.drawable.womanprofile);
            }}
      /*  Button sound=(Button)findViewById(R.id.button3);
        sound.setOnClickListener(new View.OnClickListener(){
                                     @Override
                                     public void onClick(View v){
                                         if(play){
                                             mp.pause();
                                             Button b=(Button)findViewById(R.id.button3);
                                             b.setAlpha(0.5f);
                                             play=false;
                                         }else {
                                             mp.start();
                                             Button b=(Button)findViewById(R.id.button3);
                                             b.setAlpha(1f);
                                             play=true;
                                         }
                                     }
                                 }

        );*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        location = getLocation();
        pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        final String phone = pref.getString("phone", null);
      // l1=35.733157;
      //   l2=51.318088;
      //  location.setLatitude(l1);
        // location.setLongitude(l2);
       if( pref.getBoolean("mapUpdate",false)){
           editor.putBoolean("mapUpdate",false);
           editor.apply();
        new CountDownTimer(5000,100){
            public void onTick(long millisUntilFinished){
                location=getLocation();
            }
            public void onFinish(){
               MapProccess();
            }
        }.start();}else{
           MapProccess();
       }
    }




    public Bitmap makeBitmap(Context context, String text)
    {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.marker);
        bitmap = bitmap.copy(ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK); // Text color
        paint.setTextSize(24 * scale); // Text size
        paint.setFakeBoldText(true);
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE); // Text shadow
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        //  int x = bitmap.getWidth() - bounds.width() - 10; // 10 for padding from right
        int y = bounds.height()+90;
        int x=bounds.width()+70;

        canvas.drawText(text, x, y, paint);

        return  bitmap;
    }
    protected  double Dist(Location l1, Location l2){
        // double latd;
        //  double longd;

        double dist;
        dist=l1.distanceTo(l2);
        // latd=(l.getLatitude()-te.getLatitude())*111.699;
        //longd=(l.getLongitude()-te.getLongitude())*110.567;
        // dist=Math.sqrt(Math.pow((latd),2)+Math.pow((longd),2));


        return dist;
    }
    public int NormalPD(User user){
        double badkonaki=2.0;
        double gavazn=3.0;
        double gavi=4.0;
        double merikhi=5.0;
        double nakhoonak=6.0;
        double kermmarmoolaki=7.0;
        double min=Math.round((double)user.score/2000);
        min=min<2?0:min;
        min=min>5?5:min;
        double sigma;
        sigma=Math.pow((2-min),2.0)+Math.pow((3-min),2.0)+Math.pow((4-min),2.0)+Math.pow((5-min),2.0);
        sigma=sigma/4;
        double []probArr={0.0,0.0,0.0,0.0,0.0,0.0};
        probArr[4]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((nakhoonak-min),2.0)*(-1))/2));
        probArr[0]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((badkonaki-min),2.0)*(-1))/2));
        probArr[2]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((gavi-min),2.0)*(-1))/2));
        probArr[1]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((gavazn-min),2.0)*(-1))/2));
        probArr[3]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((merikhi-min),2.0)*(-1))/2));
        probArr[5]=(1/Math.sqrt(2*3.14*sigma))*(Math.exp((Math.pow((kermmarmoolaki-min),2.0)*(-1))/2));
        double max=probArr[0];
        int index=0;
        for(int i=0;i<6;i++){
            if(probArr[i]>=max){
                max=probArr[i];
                index=i;
            }
        }
        return index;
    }
    Award.awardType awardAppear(User user,double strn){
        double potionProb=(1000.0/(double)(user.score*user.potion*user.gold+10000))*100.0;
        double armorProb=(1000.0/(double)(user.score*user.armor*user.gold+10000))*100.0;
        double goldProb=(1000.0/(double)(user.score*user.gold+10000))*100.0;
        double starProb=0.0;
        starProb =(double)(strn/user.life+user.stars+100)*100.0;
        double [] values={potionProb,armorProb,goldProb,starProb};
        double max=values[0];
        int index=0;
        for(int i=0;i<values.length;i++){
            if(values[i]>=max){
                max=values[i];
                index=i;
            }
        }
        if(index==0) return Award.awardType.potionType;
        if(index==1) return Award.awardType.armorType;
        if(index==2) return Award.awardType.goldType;
        if(index==3) return Award.awardType.starType;
        return none;
    }
    public void Clickzoom(View v){
        if(location!=null){
            LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, zoomLevel));}else {
            Toast.makeText(QuestionMapsActivity.this, "خطا در موقعیت یابی", Toast.LENGTH_SHORT).show();}
    }
    // public void Sound(View v){
     /*   if(play){ mp.start();}
        else{mp.pause();}*/
    //  }



    @Override
    public void onResume(){
        super.onResume();
        pref=getApplicationContext().getSharedPreferences("VorujakPref",MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();
      /*  play=sw.isChecked();
        if(play){ mp.start();}
        else{mp.pause();}*/
        player.armor=pref.getInt("armor",0);
        player.score=pref.getInt("score",0);
        player.gold=pref.getInt("gold",0);
        player.potion=pref.getInt("potion",0);
        player.stars=pref.getInt("stars",0);
        player.life=pref.getInt("life",0);
        SharedPreferences pref=getSharedPreferences("VorujakPref", MODE_PRIVATE);
        FloatingActionButton im=(FloatingActionButton) findViewById(R.id.fab2);
        if(pref.contains("man")){
            if(pref.getBoolean("man",false)){
                im.setImageResource(R.drawable.manprofile);
            }else {
                im.setImageResource(R.drawable.womanprofile);
            }}
        //ReadEA();
        if(player.score>=10000){
            player.gold=player.gold+50;
            player.score=player.score-10000;
            editor.putInt("gold",player.gold);
            editor.putInt("score",player.score);
            editor.commit();
        }
        TextView t1 = (TextView) findViewById(R.id.textstar);
        t1.setText("" + player.score);
        TextView t2 = (TextView) findViewById(R.id.textgold);
        t2.setText("" + player.gold);

        registerReceiver(gpsLocationReciever,new IntentFilter("android.location.PROVIDERS_CHANGED"));

    }

    Award.awardType InttoType(int index) {
        if (index == 0) return Award.awardType.potionType;
        if (index == 1) return Award.awardType.armorType;
        if (index == 2) return Award.awardType.goldType;
        if (index == 3) return Award.awardType.starType;
        return none;
    }
    int TypetoInt(Award.awardType at){
        if(at== Award.awardType.potionType) return 0;
        if(at== Award.awardType.armorType) return 1;
        if(at== Award.awardType.goldType) return 2;
        if(at== Award.awardType.starType) return 3;
        return 4;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gpsLocationReciever!=null){
            unregisterReceiver(gpsLocationReciever);
        }

    }
    public Location getLocation() {
        try {
            lm = (LocationManager) QuestionMapsActivity.this.getSystemService(QuestionMapsActivity.this.LOCATION_SERVICE);
            isGpsEnabled = lm.isProviderEnabled(lm.GPS_PROVIDER);
            Log.v("isGPSEnabled", "=" + isGpsEnabled);
            isNetworkEnabled = lm.isProviderEnabled(lm.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);
            if (!isGpsEnabled && !isNetworkEnabled) {
                Toast.makeText(QuestionMapsActivity.this, "موقعیت یاب خود را روشن نمایید!", Toast.LENGTH_LONG).show();
            }
            else {
                this.canGetLocation = true;
                if (isNetworkEnabled && !isGpsEnabled) {
                    llocation = null;
                    if(Build.VERSION.SDK_INT >= 23)
                        if (QuestionMapsActivity.this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && QuestionMapsActivity.this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }

                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (lm != null) {
                        llocation = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
                        if (llocation != null) {
                            latitude = llocation.getLatitude();
                            longitude = llocation.getLongitude();
                        }
                    }
                }
                if (isGpsEnabled && !isNetworkEnabled) {
                    llocation = null;
                    if (llocation == null) {
                        lm.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (lm != null) {
                            llocation = lm
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (llocation != null) {
                                latitude = llocation.getLatitude();
                                longitude = llocation.getLongitude();
                            }
                        }

                    }
                }
                if(isNetworkEnabled && isGpsEnabled)
                {
                    llocation=null;
                    Criteria c=new Criteria();
                    String p=lm.getBestProvider(c,true);
                    lm.requestLocationUpdates(p,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                    if(lm!=null) {
                        llocation = lm.getLastKnownLocation(p);
                        if(llocation!=null){
                            latitude = llocation.getLatitude();
                            longitude = llocation.getLongitude();
                        }
                    }

                }
            }
        }catch (Exception e){e.printStackTrace();}
        return llocation;}
    public void stopUsingGPS(){
        if (lm!=null)
            lm.removeUpdates(QuestionMapsActivity.this);
    }
    @Override
    public void onLocationChanged(Location locationn) {
        location = getLocation();
        onMapReady(mMap);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(QuestionMapsActivity.this, "موقعیت یاب خود را روشن نمایید!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        onMapReady(mMap);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   if(play)
       //     mp.pause();
    }
    private void initGoogleAPIClient(){
        mGoogleApi=new GoogleApiClient.Builder(QuestionMapsActivity.this).addApi(LocationServices.API).build();
        mGoogleApi.connect();

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 0:
                switch (resultCode){
                    case RESULT_OK:
                        onMapReady(mMap);
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }
    private Runnable sendUpdatesToUI=new Runnable() {
        @Override
        public void run() {
            showSettingDialog();
        }
    };
    private void showSettingDialog(){
        LocationRequest lr=LocationRequest.create();
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lr.setInterval(6000);
        lr.setFastestInterval(1000);
        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(lr);
        builder.setAlwaysShow(true);
        final PendingResult<LocationSettingsResult> result=LocationServices.SettingsApi.checkLocationSettings(mGoogleApi,builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status=result.getStatus();
                final LocationSettingsStates state=result.getLocationSettingsStates();
                switch (status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:
                        location = getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            status.startResolutionForResult(QuestionMapsActivity.this,0);
                        }catch (IntentSender.SendIntentException e){
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }
    public boolean isOnline(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable()){
            return false;
        }
        return true;
    }

public void Clickhome(View v){
    Intent i=new Intent(QuestionMapsActivity.this,Main2Activity.class);
    startActivity(i);
    finish();
}

private void MapProccess(){
    pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
    final SharedPreferences.Editor editor = pref.edit();
    final String phone = pref.getString("phone", null);
    location=getLocation();
    if (location != null) {
        if (location.getLatitude() != 0 && location.getLongitude() != 0) {
            mMap.clear();
            l1 = location.getLatitude();
            l2 = location.getLongitude();
            editor.putLong("lat", Double.doubleToRawLongBits(l1));
            editor.putLong("long", Double.doubleToRawLongBits(l2));
            editor.commit();
            LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(start).title("موقعیت من"));
            Circle circle = mMap.addCircle(new CircleOptions().center(start).radius(30).fillColor(R.color.lpurple).strokeWidth(0.2f).strokeColor(R.color.dpurple));
            if (canzoom) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 6.0f));
                canzoom = false;
            }
            if((smessage!=null&&smessage.matches("Update"))||firstime){
                if (isOnline()) {
                    GetLocation gl = new GetLocation(QuestionMapsActivity.this);
                    JSONObject requestjo = new JSONObject();
                    try {
                        requestjo.put("latitude", l1);
                        requestjo.put("longtitude", l2);
                        requestjo.put("type", "question");
                        requestjo.put("AppName", "addigit");
                        requestjo.put("AppVersion", BuildConfig.VERSION_NAME);
                        requestjo.put("UserId", "0");
                        requestjo.put("PhoneNo", phone);
                        editor.putInt("flag", 1);
                        editor.commit();
                        gl.LocationRecieve(requestjo, new GetLocation.Recieve() {

                            @Override
                            public void onLocationRecieve(int status, String message, ArrayList<Award> shopAward, final String[] etypes) {
                                // Toast.makeText(MapsActivity.this,"hello",Toast.LENGTH_SHORT).show();

                                if (status == 0) {
                                    // flag = true;
                                    //  Toast.makeText(MapsActivity.this,String.valueOf(etypes.length),Toast.LENGTH_SHORT).show();
                                    if(shopAward.size()!=0) {
                                        int pow = NormalPD(player) + 2;
                                        editor.putInt("mapcount", etypes.length);
                                        editor.apply();
                                        for (int i = 0; i < etypes.length; i++) {
                                            //  if (etypes[i].matches("enemy")) {
                                            Award tempa = new Award();
                                            tempa = shopAward.get(i);
                                            awards.add(i, tempa);
                                            //if(i<20){
                                        //    editor.remove("maplat" + String.valueOf(i));
                                          //  editor.remove("maplong" + String.valueOf(i));
                                          //  editor.remove("maptype" + String.valueOf(i));
                                          //  editor.remove("mapid" + String.valueOf(i));

                                            editor.putLong("maplat" + String.valueOf(i), Double.doubleToRawLongBits(awards.get(i).alocation.getLatitude()));
                                            editor.putLong("maplong" + String.valueOf(i), Double.doubleToRawLongBits(awards.get(i).alocation.getLongitude()));
                                            editor.putString("maptype" + String.valueOf(i), etypes[i]);
                                            editor.putString("mapid" + String.valueOf(i), awards.get(i).id);
                                            editor.apply();
                                            //  }
                                            //   } else {
                                            //enemy
                                            // }
                                        }
                                        ArrayList<Marker> markers=new ArrayList<Marker>();
                                        for (int i = 0; i < etypes.length; i++) {
                                            double tlat = awards.get(i).alocation.getLatitude();
                                            double tlong = awards.get(i).alocation.getLongitude();
                                            LatLng temp = new LatLng(tlat, tlong);
                                            if(etypes[i].matches("question;1")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.easyquestionpin);  Bitmap b = d.getBitmap();
                                                Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                                                Marker marker=  mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                                                markers.add(marker);
                                            }
                                            if(etypes[i].matches("question;2")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.midquestionpin);  Bitmap b = d.getBitmap();
                                                Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                                                Marker marker=  mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                                                markers.add(marker);
                                            }
                                            if(etypes[i].matches("question;3")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.hardquestionpin);  Bitmap b = d.getBitmap();
                                                Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                                                Marker marker=   mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                                                markers.add(marker);
                                            }
                                         //   BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.questionpin);

                                            //  mMap.addMarker(new MarkerOptions().position(temp).title("award point").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        }
                                        for (int h = 0; h < etypes.length; h++) {
                                            awards.get(h).dista = Dist(location, awards.get(h).alocation);
                                        }
                                        double minda = awards.get(0).dista;
                                        int indexda = 0;
                                        for (int j = 0; j < etypes.length; j++) {
                                            if (awards.get(j).dista <= minda) {
                                                minda = awards.get(j).dista;
                                                indexda = j;
                                            }
                                        }
                                        boolean exist = false;
                                        if (pref.contains("templat")) {
                                            for (int j = 0; j < etypes.length; j++) {
                                                if (awards.get(j).alocation.getLatitude() == Double.longBitsToDouble(pref.getLong("templat", 0)) && awards.get(j).alocation.getLongitude() == Double.longBitsToDouble(pref.getLong("templong", 0))) {
                                                    exist = true;
                                                }
                                            }

                                        }
                                        boolean timeflag = true;
                                        Date stime = Calendar.getInstance().getTime();
                                        long t1 = (stime.getTime()) / 1000;
                                        if (pref.contains("temptime")) {
                                            Long t3 = pref.getLong("temptime", 0);
                                            if (Math.abs(t3 - t1) >= 30) {
                                                timeflag = true;
                                            } else {
                                                timeflag = false;
                                            }
                                        }

                                        //  editor.putString("awardtype",awards.get(indexda).atype);
                                        if ((minda < mindist && !exist) || (minda < mindist && exist && timeflag)){
                                            editor.putLong("templat", Double.doubleToRawLongBits(awards.get(indexda).alocation.getLatitude()));
                                            editor.putLong("templong", Double.doubleToRawLongBits(awards.get(indexda).alocation.getLongitude()));
                                            LatLng arrivep = new LatLng(awards.get(indexda).alocation.getLatitude(),awards.get(indexda).alocation.getLongitude());
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrivep,22.0f ));
                                            animateMarker(markers.get(indexda));
                                            Date ntime = Calendar.getInstance().getTime();
                                            long t2 = (ntime.getTime()) / 1000;
                                            editor.putLong("temptime", t2);
                                            editor.putString("shopid", awards.get(indexda).id);
                                            editor.putInt("score", pref.getInt("score", 0) + 20);
                                            editor.commit();
                                            updateUser();
                                            final int abc=indexda;
                                            PercentRelativeLayout pl = (PercentRelativeLayout) findViewById(R.id.mlayout);
                                            if (etypes[indexda].matches("question;1")||etypes[indexda].matches("question;2")||etypes[indexda].matches("question;3")) {
                                                new CountDownTimer(1500, 100) {
                                                    public void onTick(long millisUntilFinished) {

                                                    }

                                                    public void onFinish() {
                                                        AlertDialog.Builder builder;
                                                        builder = new AlertDialog.Builder(QuestionMapsActivity.this, R.style.AlertDialogTheme);
                                                        // AlertDialog dlg=builder.create();
                                                        //  dlg.getWindow().setGravity(Gravity.BOTTOM);
                                                        builder.setTitle("اطلاعیه")
                                                                .setMessage("پرسش را پاسخ دهید و غافلگیر شوید!")
                                                                .setCancelable(true)
                                                                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Intent q = new Intent(QuestionMapsActivity.this, MapQuestion.class);
                                                                        editor.putString("qtype", etypes[abc]);
                                                                        editor.apply();
                                                                        q.putExtra("question", true);
                                                                        startActivity(q);
                                                                        finish();
                                                                    }
                                                                })
                                                                .setNegativeButton("ذخیره بشه", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Intent m = new Intent(QuestionMapsActivity.this, SavingQuestion.class);
                                                                        m.putExtra("question", true);
                                                                        editor.putString("qtype", etypes[abc]);
                                                                        editor.apply();
                                                                        startActivity(m);
                                                                        finish();
                                                                    }
                                                                })
                                                                .setIcon(R.drawable.questions)
                                                                .show();
                                                    }
                                                }.start();
                                            }
                                        }
                                    }

                                } else {
                                    Toast.makeText(QuestionMapsActivity.this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
                                }
                                //WriteEA();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    firstime=false;
                } else {
                    new CountDownTimer(10000, 100) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(QuestionMapsActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("خطا")
                                    .setMessage("لطفا اینترنت خود را متصل نمایید!")
                                    .setCancelable(false)
                                    .setPositiveButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            onMapReady(mMap);
                                        }
                                    })
                                    .setIcon(R.drawable.wifi)
                                    .show();}
                    }.start();
                }
            }else {
                // ArrayList<Award> shopAward=new ArrayList<Award>();
                int mapCount = pref.getInt("mapcount", 0);
                if (mapCount != 0) {
                    for (int i = 0; i < mapCount; i++) {
                        Award tempaward = new Award();
                        Location templocation = new Location("");
                        templocation.setLatitude(Double.longBitsToDouble(pref.getLong("maplat" + String.valueOf(i), 0)));
                        templocation.setLongitude(Double.longBitsToDouble(pref.getLong("maplong" + String.valueOf(i), 0)));
                        tempaward.Award(templocation, none, 1, pref.getString("mapid" + String.valueOf(i), null), 1000.0);
                        awards.add(i, tempaward);

                    }
                    ArrayList<Marker> markers=new ArrayList<Marker>();
                    for (int i = 0; i < mapCount; i++) {
                        double tlat = awards.get(i).alocation.getLatitude();
                        double tlong = awards.get(i).alocation.getLongitude();
                        LatLng temp = new LatLng(tlat, tlong);
                        if(pref.getString("maptype" + String.valueOf(i),null).matches("question;1")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.easyquestionpin);  Bitmap b = d.getBitmap();
                            Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                            Marker marker= mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                            markers.add(marker);
                        }
                        if(pref.getString("maptype" + String.valueOf(i),null).matches("question;2")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.midquestionpin);  Bitmap b = d.getBitmap();
                            Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                            Marker marker=  mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                            markers.add(marker);
                        }
                        if(pref.getString("maptype" + String.valueOf(i),null).matches("question;3")){ BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.hardquestionpin);  Bitmap b = d.getBitmap();
                            Bitmap bhalfsize = Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);
                            Marker marker=  mMap.addMarker(new MarkerOptions().position(temp).title("question point").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
                            markers.add(marker);
                        }
                        //  mMap.addMarker(new MarkerOptions().position(temp).title("award point").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                    }
                    if(mapCount>20){
                        mapCount=20;
                    }
                    for (int h = 0; h < mapCount; h++) {
                        awards.get(h).dista = Dist(location, awards.get(h).alocation);
                    }
                    double minda = awards.get(0).dista;
                    int indexda = 0;
                    for (int j = 0; j < mapCount; j++) {
                        if (awards.get(j).dista <= minda) {
                            minda = awards.get(j).dista;
                            indexda = j;
                        }
                    }
                    boolean exist = false;
                    if (pref.contains("templat")) {
                        for (int j = 0; j < mapCount; j++) {
                            if (awards.get(j).alocation.getLatitude() == Double.longBitsToDouble(pref.getLong("templat", 0)) && awards.get(j).alocation.getLongitude() == Double.longBitsToDouble(pref.getLong("templong", 0))) {
                                exist = true;
                            }
                        }

                    }
                    boolean timeflag = true;
                    Date stime = Calendar.getInstance().getTime();
                    long t1 = (stime.getTime()) / 1000;
                    if (pref.contains("temptime")) {
                        Long t3 = pref.getLong("temptime", 0);
                        if (Math.abs(t3 - t1) >= 30) {
                            timeflag = true;
                        } else {
                            timeflag = false;
                        }
                    }

                    //  editor.putString("awardtype",awards.get(indexda).atype);
                    if ((minda < mindist && !exist) || (minda < mindist && exist && timeflag)) {

                        editor.putLong("templat", Double.doubleToRawLongBits(awards.get(indexda).alocation.getLatitude()));
                        editor.putLong("templong", Double.doubleToRawLongBits(awards.get(indexda).alocation.getLongitude()));
                        LatLng arrivep = new LatLng(awards.get(indexda).alocation.getLatitude(),awards.get(indexda).alocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrivep,22.0f ));
                        animateMarker(markers.get(indexda));
                        Date ntime = Calendar.getInstance().getTime();
                        long t2 = (ntime.getTime()) / 1000;
                        editor.putLong("temptime", t2);
                        editor.putString("shopid", awards.get(indexda).id);
                        editor.putInt("score", pref.getInt("score", 0) + 20);
                        editor.commit();
                        updateUser();
                        final int abc=indexda;
                        PercentRelativeLayout pl = (PercentRelativeLayout) findViewById(R.id.mlayout);
                        if (pref.getString("maptype" + String.valueOf(indexda), null).matches("question;1")||pref.getString("maptype" + String.valueOf(indexda), null).matches("question;2")||pref.getString("maptype" + String.valueOf(indexda), null).matches("question;3")){
                            new CountDownTimer(1500,100){
                                public void onTick(long millisUntilFinished){

                                }
                                public void onFinish(){

                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(QuestionMapsActivity.this,R.style.AlertDialogTheme);
                           // AlertDialog dlg=builder.create();
                            //dlg.getWindow().setGravity(Gravity.BOTTOM);
                            builder.setTitle("اطلاعیه")
                                    .setMessage("پرسش را پاسخ دهید و غافلگیر شوید!")
                                    .setCancelable(true)
                                    .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent q = new Intent(QuestionMapsActivity.this, MapQuestion.class);
                                            editor.putString("qtype",pref.getString("maptype" + String.valueOf(abc),null));
                                            editor.apply();
                                            q.putExtra("question", true);
                                            startActivity(q);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("ذخیره بشه", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent m=new Intent(QuestionMapsActivity.this,SavingQuestion.class);
                                            m.putExtra("question", true);
                                            editor.putString("qtype",pref.getString("maptype" + String.valueOf(abc),null));
                                            editor.apply();
                                            startActivity(m);
                                            finish();
                                        }})
                                    .setIcon(R.drawable.questions)
                                    .show();
                                }
                            }.start();
                        }
                    }

                }
            }

        }
    } else

    {

        Toast.makeText(QuestionMapsActivity.this, "خطا در موقعیت یابی", Toast.LENGTH_SHORT).show();
        // LatLng tehran = new LatLng(35.707, 51.366);
        // mMap.addMarker(new MarkerOptions().position(tehran).title("موقعیت").icon(BitmapDescriptorFactory.fromResource(R.drawable.pingp)));
        // float zoomLevel = 16.0f;
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tehran, zoomLevel));

    }
}
private void updateUser(){
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
    UpdateUser upd=new UpdateUser(QuestionMapsActivity.this);
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
                    Toast.makeText(QuestionMapsActivity.this,Message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }catch (JSONException e) {
        e.printStackTrace();
    }
}
private void deletePoint(int ind){
    pref = getApplicationContext().getSharedPreferences("VorujakPref", MODE_PRIVATE);
    final SharedPreferences.Editor editor = pref.edit();
   editor.putInt("mapcount",pref.getInt("mapcount", 0)-1);
   editor.apply();
   for (int i=ind;i<pref.getInt("mapcount", 0);i++){
      // editor.remove("maplat" + String.valueOf(i));
      // editor.remove("maplong" + String.valueOf(i));
      // editor.remove("maptype" + String.valueOf(i));
     //  editor.remove("mapid" + String.valueOf(i));

       editor.putLong("maplat" + String.valueOf(i),pref.getLong("maplat"+ String.valueOf(i+1),0));
       editor.putLong("maplong" + String.valueOf(i),pref.getLong("maplong"+ String.valueOf(i+1),0));
       editor.putString("maptype" + String.valueOf(i),pref.getString("maptype"+ String.valueOf(i+1),null));
       editor.putString("mapid" + String.valueOf(i),pref.getString("mapid"+ String.valueOf(i+1),null));
       editor.apply();
   }
}
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
    private void animateMarker(final Marker marker){
        final Handler handler=new Handler();
        final long start=SystemClock.uptimeMillis();
        final long duration=50000;//ms
        Projection pro=mMap.getProjection();
        final LatLng markerLatLng=marker.getPosition();
        Point startp=pro.toScreenLocation(markerLatLng);
        startp.offset(0,-10);
        final LatLng startLatLng=pro.fromScreenLocation(startp);
        final  android.view.animation.Interpolator interpolator=new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed=SystemClock.uptimeMillis()-start;
                float t=interpolator.getInterpolation((float)elapsed/duration);
                double lng=t*markerLatLng.longitude+(1-t)*startLatLng.longitude;
                double lat=t*markerLatLng.latitude+(1-t)*startLatLng.latitude;
                marker.setPosition(new LatLng(lat,lng));
                if(t<1.0){
                    handler.postDelayed(this,16);
                }
            }
        });

    }
}
