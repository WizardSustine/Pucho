package com.example.pucho.ENTIDADES;

import static com.example.pucho.ENTIDADES.AlarmEvent.TRIGGER_NOTIFICATION;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pucho.MainActivity;
import com.example.pucho.R;
import com.example.pucho.RECEPTORES.TimeChangeReceiver;

public class NotificationApp {
    private static NotificationManager notificationManager;

    private NotificationManagerCompat notificationManagerCompat;
    private Context context;
    public NotificationApp(Context context){
        this.context = context;
        notificationManagerCompat = NotificationManagerCompat.from(context);


    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name= "puchochannel";
            String desc = "Channel for Pucho App Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("puchochannelandroid", name, imp);
            channel.setDescription(desc);

            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public boolean notificationPermission(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            int REQUEST_POST_NOTIFICATIONS = 1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_POST_NOTIFICATIONS);

            }

            return false;
        } else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public void notiBuild(){
        //this is the intent that is supposed to be called when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("cancelar", 3);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        //this is the intent that is supposed to be called when the button is clicked
        Intent addPuchoIntent = new Intent(context, TimeChangeReceiver.class);
        addPuchoIntent.putExtra(TRIGGER_NOTIFICATION, 1);
        PendingIntent pendingAddPuchoIntent = PendingIntent.getBroadcast(context, 0,
                addPuchoIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "puchochannelandroid")
                .setSmallIcon(R.drawable.ic_smoke_notification)
                .setContentTitle("Puchos App")
                .setContentText("Hora de fumar")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingNotificationIntent)
                .addAction(R.drawable.ic_smoke_notification,"agregar pucho", pendingAddPuchoIntent)
                .setAutoCancel(true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(789, builder.build());
    }
    public void closeNotification(){
        notificationManagerCompat.cancel(789);
    }


}
