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

public class UpdateUser {
    private Context context;
    public UpdateUser(Context context){
        this.context=context;
    }
    public interface Update{
        void onUpdate(int StatusCode, String Message);
    }
    public void UpdateRecieve(JSONObject requestja, final UpdateUser.Update osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/UpdateUser",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.onUpdate(status,message);
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
/*
UpdateUser apg = new UpdateUser(Activation.this);
                        JSONObject requestjo = new JSONObject();
                        try {
                            requestjo.put("AuthKey", "");
                            requestjo.put("Gold", "100");
                            requestjo.put("Life", "4");
                            requestjo.put("Potion","5");
                            requestjo.put("AppName", "addigit");
                            requestjo.put("AppVersion", "1");
                            requestjo.put("Userid", "0");
                            requestjo.put("PhoneNo", phone);
                             apg.UpdateRecieve(requestjo, new UpdateUser.Update() {
                                @Override
                                public void onUpdate(int StatusCode, String Message) {
                                    if (StatusCode == 0) {
                                        Toast.makeText(Activation.this, "success", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
*/
