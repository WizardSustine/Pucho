package com.example.pucho.ENTIDADES;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.example.pucho.RECEPTORES.TimeChangeReceiver;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Notificacion {
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Context context;

    public Notificacion(Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimeChangeReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    public void setNotification(long millis, boolean notificationSwitch) {
        if (notificationSwitch) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("This is happening because package manager isn't equal to checkselfPermission");
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                //int REQUEST_POST_NOTIFICATIONS = 1;
                System.out.println(Build.VERSION.SDK_INT + " this is build version sdk");

                System.out.println(Build.VERSION_CODES.TIRAMISU + " this is build version tiramisu");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    //try {
                    Intent intent2 = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent2.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                    startActivity(context, intent2,null);
                    //ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_POST_NOTIFICATIONS);
                /*} catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                   throw new RuntimeException(e);
                }*/
                }
                // here to request the missing permissions, and then overriding
                //public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                     int[] grantResults);
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            /*if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                    Intent intentNoti = new Intent();
                    intentNoti.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    intentNoti.setData(Uri.parse("package:" + context.getPackageName()));
                    startActivity(context, intentNoti,null);
            }*/
            //alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.cancel(pendingIntent);
            alarmManager.set(AlarmManager.RTC, millis, pendingIntent);
            System.out.println("Alarm is set");


            System.out.println(timeFormat.format(millis));
            createNotificationChannel();
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name= "puchochannel";
            String desc = "Channel for Pucho App Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("puchochannelandroid", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
