package com.example.rb.myapplication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bahrampashootan on 8/31/2018 AD.
 */

public class Store {
    private Context context;
    public Store(Context context){
        this.context=context;
    }
    public interface StoreItems{
        void onStore(int Required[], String Name[], boolean IsEnable[], String Icon[], String Type[], int coinAmount[],int rialAmount[], int level[], int StatusCode, String Message);
    }

    public void inStore(JSONObject requestja, final Store.StoreItems osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/StoreItems",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());

                try {
                    nresponse= response.getJSONObject("Data");
                    JSONArray slist=nresponse.getJSONArray("list");
                    int requires[]=new int[slist.length()];
                    String names[]=new String[slist.length()];
                    boolean enables[]=new boolean[slist.length()];
                    String icons[]=new String[slist.length()];
                    String types[]=new String[slist.length()];
                    int camounts[]=new int[slist.length()];
                    int ramounts[]=new int[slist.length()];
                    int levels[]=new int[slist.length()];
                    for(int i=0;i<slist.length();i++) {
                        JSONObject temp = slist.getJSONObject(i);
                        requires[i]=temp.getInt("requiredGold");
                        names[i]=temp.getString("name");
                        enables[i]=temp.getBoolean("isEnabel");
                        icons[i]=temp.getString("icon");
                        types[i]=temp.getString("type");
                        camounts[i]=temp.getInt("coinAmount");
                        ramounts[i]=temp.getInt("rialAmount");
                        levels[i]=temp.getInt("level");
                    }
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.onStore(requires,names,enables,icons,types,camounts,ramounts,levels,status,message);
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
