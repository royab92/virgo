package com.example.rb.myapplication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by bahrampashootan on 6/20/2018 AD.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG ="MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
//Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//Displaying token on logcat
        Log.d(TAG,"Refreshed token:" + refreshedToken);
    }
}
