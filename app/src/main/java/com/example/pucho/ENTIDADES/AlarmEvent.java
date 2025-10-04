package com.example.pucho.ENTIDADES;


import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.example.pucho.RECEPTORES.TimeChangeReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmEvent {
    private PendingIntent pendingIntent;
    private static AlarmManager alarmManager;
    public static NotificationApp notificationApp;
    public static final String TRIGGER_NOTIFICATION = "trigger_notification";
    private final int NOTIFICATION_ID = 2;

    public AlarmEvent(Context context) {
        notificationApp = new NotificationApp(context);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimeChangeReceiver.class);
        intent.putExtra(TRIGGER_NOTIFICATION,NOTIFICATION_ID);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        notificationApp.createNotificationChannel();
    }
    public long setTimeNextPucho(PuchoDia hoy, String formattedDate) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        long millis = 0;
        try {
            if(hoy.getExpectativa() > hoy.getConsumo()) {
                int cantidadRestante = hoy.getExpectativa() - hoy.getConsumo();

                Date timeFinish = timeFormat.parse(formattedDate + " 23:59:59");

                Date timeLast = timeFormat.parse(formattedDate + " " + hoy.getTime_last());

                long diferencia = timeFinish.getTime() - timeLast.getTime();

                long timeLapse = diferencia / cantidadRestante;

                millis = timeLapse + timeLast.getTime();
            }
            String pruebasss = timeFormat.format(millis);
            System.out.println(pruebasss + " se supone que suene a esta hora");
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return millis;

    }

    public void setNotificationTrigger(long millis, boolean notificationSwitch) {
        if (notificationSwitch && notificationApp.notificationPermission()) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            //alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.cancel(pendingIntent);
            alarmManager.set(AlarmManager.RTC, millis, pendingIntent);
            System.out.println("Alarm is set");

            System.out.println(timeFormat.format(millis));
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }
    }
