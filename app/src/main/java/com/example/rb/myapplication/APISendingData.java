package com.example.rb.myapplication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bahrampashootan on 5/31/2018 AD.
 */

public class APISendingData {
    private Context context;
    public APISendingData(Context context){
     this.context=context;
    }
    public interface onSignUpComplate{
        void onSignUp(String Code, int StatusCode, String Message);
    }
    public void signUp(JSONObject requestja, final onSignUpComplate osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/registerUser",requestja,new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    JSONObject nresponse;
                  //  Log.i("Get_Response: ", response.toString());
                   try {
                      nresponse= response.getJSONObject("Data");
                       String code = nresponse.getString("Code");
                         int status=nresponse.getInt("StatusCode");
                       String message=nresponse.getString("Message");
                    osc.onSignUp(code,status,message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new ErrorListener(){
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
