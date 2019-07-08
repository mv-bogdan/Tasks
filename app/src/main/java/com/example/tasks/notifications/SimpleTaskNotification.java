package com.example.tasks.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.tasks.R;
import com.example.tasks.ui.MainActivity;

public class SimpleTaskNotification {

    private static final String NOTIFICATION_TAG = "SimpleTask";

    public static void notify(final Context context,
                              final String exampleString, final String taskName, final int number) {
        final Resources res = context.getResources();
        //final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.example_picture);
        final String ticker = exampleString;
        final String title = taskName + ": " + exampleString;
        final String text = res.getString(
                R.string.simple_task_notification_placeholder_text_template, exampleString);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_simple_task)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(ticker)
                .setNumber(number)
                .addAction(
                        R.drawable.ic_action_stat_share,
                        res.getString(R.string.action_complete),
                        PendingIntent.getActivity(
                                context,
                                0,
                                Intent.createChooser(new Intent(context, MainActivity.class)
                                        .setType("text/plain")
                                        .putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);
        builder.setChannelId("3260");
        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
