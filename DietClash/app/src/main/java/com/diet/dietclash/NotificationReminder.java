package com.diet.dietclash;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//adapted from https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
public class NotificationReminder extends BroadcastReceiver {
    public static String ID = "id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = MainActivity.nm;
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(ID, 0);
        nm.notify(id, notification);
    }
}
