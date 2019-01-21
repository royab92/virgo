package com.example.rb.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //ShowNotificationIntentService.startActionShow(getApplicationContext());

        Intent intent1 = new Intent(context, ShowNotificationIntentService.class);
        context.startService(intent1);
    }
}
