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
 * Created by bahrampashootan on 7/14/2018 AD.
 */

public class BestPlayer {
    private Context context;
    public BestPlayer(Context context){
        this.context=context;
    }
    public interface RecieveTop{
        void onTopRecieve(int status, String message, int[] levels, int[] scores, String[] names, int corrects[], int golds[], int[] avatars, String phoneNo[]);
    }
    public void TopPlayerRecieve(JSONObject requestja, final BestPlayer.RecieveTop osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/TopPlayers",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    JSONArray top=nresponse.getJSONArray("TopPlayers");
                    int[]levels=new int[top.length()];
                    int[]scores=new int[top.length()];
                    String[] names=new String[top.length()];
                    String[] phones=new String[top.length()];
                    int[] avatars=new int[top.length()];
                    int[] corrects=new int[top.length()];
                    int[] golds=new int[top.length()];
                    for(int i=0;i<top.length();i++){
                        JSONObject temp=top.getJSONObject(i);
                        levels[i]=temp.getInt("level");
                        scores[i]=temp.getInt("score");
                        names[i]=temp.getString("name");
                        corrects[i]=temp.getInt("correctAnswers");
                        golds[i]=temp.getInt("gold");
                        avatars[i]=temp.getInt("avatar");
                        phones[i]=temp.getString("phoneNo");
                    }

                    osc.onTopRecieve(status,message,levels,scores,names,corrects,golds,avatars,phones);
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
