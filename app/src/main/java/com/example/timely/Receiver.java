package com.example.timely;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Receiver extends BroadcastReceiver {

    final static String MY_NOTIFICATION_CHANNEL_ID = "com.example.timely.channelid";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent in = new Intent(context,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,in,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,MY_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Timely")
                .setContentText("Time's Up")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.alarm_beep));


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(001, builder.build());

    }
}
