package com.example.microsoft.getstartednh;

/**
 * Created by Wesley on 7/1/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.RemoteMessage;
//import com.microsoft.windowsazure.notifications.NotificationsBroadcastReceiver;
import com.microsoft.windowsazure.notifications.NotificationsHandler;


public class MyFirebaseMessagingServiceHandler extends NotificationsHandler {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    Context ctx;

    @Override
    public void onNewToken (String token){
        final String TAG = "FCM registration";

        Log.d(TAG, "New FCM registration token: " + token);

    }

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");
        showNotification(nhMessage);
        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(nhMessage);
        }
    }

    @Override
    public void onUnregistered(final Context context, String fcmRegistrationId) {
        ctx = context;

        final String msg  =  "<Unregistered>";

        showNotification(msg);
        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(msg);
        }
    }

    @Override
    public void onRegistered(final Context context, String fcmRegistrationId) {
        ctx = context;

        final String msg  =  "<Registered: "+fcmRegistrationId+">";

        showNotification(msg);
        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(msg);
        }
    }

    private void showNotification(String msg) {

        //Context ctx = getApplicationContext();

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx, "FCM channel")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notification Hub Demo")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setSound(defaultSoundUri)
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
