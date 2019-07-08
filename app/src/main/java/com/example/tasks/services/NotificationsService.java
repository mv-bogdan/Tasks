package com.example.tasks.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.tasks.R;
import com.example.tasks.notifications.TasksNotifications;
import com.example.tasks.ui.MainActivity;

import static java.lang.Thread.sleep;


public class NotificationsService extends JobIntentService {

//    public NotificationsService() {
//        super("NotificationsService");
//    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("popa", "1");
        int notificationId = intent.getIntExtra("notification_id", 1);
        Log.d("popa", "2");
        switch (notificationId) {
            case 1 :
                try {
                    sleep(3000);
                    createNotificationChannel();
                    TasksNotifications.createNotification(getBaseContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("TasksChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
