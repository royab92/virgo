package com.example.rb.myapplication;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class ShowNotificationIntentService extends IntentService {
    private static final String ACTION_SHOW_NOTIFICATION = "my.app.service.action.show";
    private static final String ACTION_HIDE_NOTIFICATION = "my.app.service.action.hide";


    public ShowNotificationIntentService() {
        super("ShowNotificationIntentService");
    }

    public static void startActionShow(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_SHOW_NOTIFICATION);
        context.startService(intent);
    }

    public static void startActionHide(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_HIDE_NOTIFICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_NOTIFICATION.equals(action)) {
                handleActionShow();
            } else if (ACTION_HIDE_NOTIFICATION.equals(action)) {
                handleActionHide();
            }
        }
    }

    private void handleActionShow() {
        showStatusBarIcon(ShowNotificationIntentService.this);
    }

    private void handleActionHide() {
       // hideStatusBarIcon(ShowNotificationIntentService.this);
    }

    public static void showStatusBarIcon(Context ctx) {
        Context context = ctx;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"notify_001")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("سوالات روزانه")
                        .setContentText("صندوق سوالات روزانه پر شده است!");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), notificationIntent,
                0);
        builder.setContentIntent(contentIntent).setAutoCancel(true);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }
}
