package com.example.rb.myapplication;

import android.content.Context;
import android.location.Location;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.rb.myapplication.Award.awardType.none;

/**
 * Created by bahrampashootan on 7/3/2018 AD.
 */

public class GetLocation {
    private Context context;

    public GetLocation(Context context){
        this.context=context;
    }
    public interface Recieve{
        void onLocationRecieve(int status, String message, ArrayList<Award> shopa, String[] etypes);
    }
    public void LocationRecieve(JSONObject requestja, final GetLocation.Recieve osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/ReceiveNearLocations",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    int status=nresponse.getInt("status");
                    String message=nresponse.getString("message");
                    JSONArray shop=nresponse.getJSONArray("Shops");
                   // boolean[]award=new boolean[shop.length()];
                  //  String[] atypes=new String[shop.length()];
                   // boolean[]enemy=new boolean[shop.length()];
                    String[] etypes=new String[shop.length()];
                  //  double[]lats=new double[shop.length()];
                  //  double[]longs=new double[shop.length()];
                  //  String[]ids=new String[shop.length()];
                  //  int[]remains=new int[shop.length()];
                    ArrayList<Award> shopAward=new ArrayList<Award>();

                    for(int i=0;i<shop.length();i++){
                        Award tempaward=new Award();
                        Location templocation=new Location("");
                        JSONObject temp=shop.getJSONObject(i);
                        templocation.setLatitude(temp.getDouble("latitude"));
                        templocation.setLongitude(temp.getDouble("longtitude"));
                        tempaward.Award(templocation,none,temp.getInt("remainCount"),temp.getString("shopId"),1000.0);
                        shopAward.add(i,tempaward);
                      //  award[i]=temp.getBoolean("award");
                      //  atypes[i]=temp.getString("awardType");
                       // enemy[i]=temp.getBoolean("enemy");
                        etypes[i]=temp.getString("type");
                    }

                    osc.onLocationRecieve(status,message,shopAward,etypes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //  osc.onSignUp(false);
                //Log.i("Get_Error: ", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }
}
