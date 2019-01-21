package com.example.rb.myapplication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bahrampashootan on 6/4/2018 AD.
 */

public class RecieveUser {
    private Context context;
    public RecieveUser(Context context){
        this.context=context;
    }
    public interface Recieve{
        void onDataRecieve(String Authkey, int gold, int life, int potion, int score, int level, int fire, int armor, int time, String gender, String name, String age, int avatar, int StatusCode, String Message);
    }
    public void DataRecieve(JSONObject requestja, final RecieveUser.Recieve osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/ReceiveUser",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    String Authkey=nresponse.getString("AuthKey");
                    int gold = nresponse.getInt("Gold");
                    int life=nresponse.getInt("Life");
                    int potion=nresponse.getInt("Potion");
                    int score=nresponse.getInt("score");
                    int level=nresponse.getInt("level");
                    int fire=nresponse.getInt("fireballs");
                    int armor=nresponse.getInt("armor");
                    int time=nresponse.getInt("time");
                    String gender=nresponse.getString("gender");
                    String name=nresponse.getString("name");
                    String age=nresponse.getString("birthdate");
                    int avatar=nresponse.getInt("avatar");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.onDataRecieve(Authkey,gold,life,potion,score,level,fire,armor,time,gender,name,age,avatar,status,message);
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
