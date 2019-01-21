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

public class Friends {
    private Context context;
    public Friends(Context context){
        this.context=context;
    }
    public interface intro{
        void onintro(int StatusCode, String Message);
    }
    public void introRecieve(JSONObject requestja, final Friends.intro osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/IntroToFriends",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.onintro(status,message);
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
