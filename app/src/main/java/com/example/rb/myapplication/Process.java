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

public class Process {
    private Context context;
    public Process(Context context){
        this.context=context;
    }
    public interface ProcessResponse{
        void getProcess(int questionId[], String question[], String answer1[], String answer2[], String answer3[], String answer4[], int correct[], int expireTime[], int goldAward[], int hardLevel[], String category[], Long serverTime, int StatusCode, String Message);
    }
    public void onProcess(JSONObject requestja, final Process.ProcessResponse osc ){
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,"Http://baziigram.com/api/webAPI/ReceiveDailyQuestions",requestja,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse= response.getJSONObject("Data");
                    JSONArray slist=nresponse.getJSONArray("list");
                    int questionId[]=new int[slist.length()];
                    String questions[]=new String[slist.length()];
                    String answer1[]=new String[slist.length()];
                    String answer2[]=new String[slist.length()];
                    String answer3[]=new String[slist.length()];
                    String answer4[]=new String[slist.length()];
                    String category[]=new String[slist.length()];
                    int correct[]=new int[slist.length()];
                    int expire[]=new int[slist.length()];
                    int gold[]=new int[slist.length()];
                    int hard[]=new int[slist.length()];

                    for(int i=0;i<slist.length();i++) {
                        JSONObject temp = slist.getJSONObject(i);
                        questionId[i]=temp.getInt("QuestionsId");
                        questions[i]=temp.getString("question");
                        answer1[i]=temp.getString("answer1");
                        answer2[i]=temp.getString("answer2");
                        answer3[i]=temp.getString("answer3");
                        answer4[i]=temp.getString("answer4");
                        correct[i]=temp.getInt("correctAnswer");
                        expire[i]=temp.getInt("expireTime");
                        gold[i]=temp.getInt("goldAward");
                        hard[i]=temp.getInt("hardnessLevel");
                        category[i]=temp.getString("category");
                    }
                    Long serverTime=nresponse.getLong("serverTime");
                    int status=nresponse.getInt("StatusCode");
                    String message=nresponse.getString("Message");
                    osc.getProcess(questionId,questions,answer1,answer2,answer3,answer4,correct,expire,gold,hard,category,serverTime,status,message);
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
