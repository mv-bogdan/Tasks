package com.example.tasks.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.example.tasks.R;
import com.example.tasks.db.RealmController;
import com.example.tasks.db.TasksModel;
import com.example.tasks.notifications.SimpleTaskNotification;

import java.util.Objects;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.tasks.ui.MainActivity.ID;
import static com.example.tasks.ui.MainActivity.TITLE;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getExtras().getInt(ID);
        String taskName = intent.getExtras().getString(TITLE);

        String message = "Your task time is out!";
        int number = 1;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence channelName = "c_name";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("3260", channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        SimpleTaskNotification.notify(context, message, taskName, number);

    }
}