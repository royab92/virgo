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
 * Created by bahrampashootan on 6/4/2018 AD.
 */

public class AppConfig {
    private Context context;
    public AppConfig(Context context){
        this.context=context;
    }
    public interface Configuration{
        void onSignUp(String Appname, String IosVersion, String AndroidVersion, boolean IosUpdate, boolean AndroidUpdate, String ServerTime, String IosUrl, String AndroidUrl, String[] AdTexts, String BaseAPI, String BasePay, int StatusCode, String Message);
    }
    public void signUp(JSONObject requestja, final AppConfig.Configuration osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/AppConfig",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    String Appname=nresponse.getString("ApplicationName");
                    String IosVersion = nresponse.getString("IosVersion");
                    String AndroidVersion=nresponse.getString("AndroidVersion");
                    boolean IosUpdate =nresponse.getBoolean("IosShouldUpdate");
                    boolean AndroidUpdate =nresponse.getBoolean("AndroidShouldUpdate");
                    String ServerTime=nresponse.getString("ServerTime");
                    String IosUrl=nresponse.getString("IosDownloadUrl");
                    String AndroidUrl=nresponse.getString("AndroidDownloadUrl");
                    JSONArray JAdTexts=nresponse.getJSONArray("AdTexts");
                    String[] AdTexts=new String[JAdTexts.length()];
                    for(int i=0;i<JAdTexts.length();i++){
                        AdTexts[i]=JAdTexts.getString(i);
                    }
                    String BaseAPI=nresponse.getString("BaseAPIUrl");
                    String BasePay=nresponse.getString("BasePayUrl");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.onSignUp(Appname,IosVersion,AndroidVersion,IosUpdate,AndroidUpdate,ServerTime,IosUrl,AndroidUrl,AdTexts,BaseAPI,BasePay,status,message);
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
