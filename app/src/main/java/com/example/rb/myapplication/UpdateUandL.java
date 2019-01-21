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
 * Created by bahrampashootan on 9/19/2018 AD.
 */

public class UpdateUandL {
    private Context context;

    public UpdateUandL(Context context) {
        this.context = context;
    }

    public interface mapProcessResponse {
        void mapgetProcess(String code, int questionId, String question, String answer1, String answer2, String answer3, String answer4, int correct, int StatusCode, String Message);
    }

    public void maponProcess(JSONObject requestja, final UpdateUandL.mapProcessResponse osc) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "Http://baziigram.com/api/webAPI/UpdateUserAndLocation", requestja, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject nresponse;
                //  Log.i("Get_Response: ", response.toString());
                try {
                    nresponse = response.getJSONObject("Data");
                    String code=nresponse.getString("code");
                        int questionId = nresponse.getInt("questionId");
                        String questions = nresponse.getString("question");
                       String answer1 = nresponse.getString("answer1");
                      String answer2 = nresponse.getString("answer2");
                       String answer3 = nresponse.getString("answer3");
                       String answer4 = nresponse.getString("answer4");
                       int correct= nresponse.getInt("correctAnswer");
                    int status = nresponse.getInt("StatusCode");
                    String message = nresponse.getString("Message");
                    osc.mapgetProcess(code,questionId, questions, answer1, answer2, answer3, answer4, correct, status, message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
