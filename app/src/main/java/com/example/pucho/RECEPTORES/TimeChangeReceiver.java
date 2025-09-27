package com.example.pucho.RECEPTORES;

import static android.content.Intent.getIntent;
import static com.example.pucho.ENTIDADES.AlarmEvent.TRIGGER_NOTIFICATION;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pucho.MainActivity;
import com.example.pucho.R;
import com.example.pucho.controladores.AlarmAndBDController;

public class TimeChangeReceiver extends BroadcastReceiver {

    private int claveValor;
    private NotificationManagerCompat notificationManagerCompat;

    private AlarmAndBDController alarmAndBDController;


    @Override
    public void onReceive(Context context, Intent intent) {
        alarmAndBDController = new AlarmAndBDController(context);
        notificationManagerCompat = NotificationManagerCompat.from(context);


        Bundle extras = intent.getExtras();
        if(extras == null) {
            claveValor = 0;
        } else {
            claveValor = extras.getInt(TRIGGER_NOTIFICATION);
        }

        System.out.println("Recibe el Receiver" );
        System.out.println("Este es el valor de claveValor " + String.valueOf(claveValor) );

        if(claveValor/*intent.getIntExtra(TRIGGER_NOTIFICATION, 0)*/ == 2){
            startNotification(context);
        }else if(claveValor/*intent.getIntExtra(TRIGGER_NOTIFICATION, 0)*/ == 1) {
            System.out.println("está recibiendo TimeChangeReceiver como si estuvieses apretando el boton de la notificacion");
            closeNotification();
            alarmAndBDController.addPucho();
        }


    }
    private void startNotification(Context context){
        //the intent that is started when the notification is clicked (works)
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
    private void closeNotification(){
        notificationManagerCompat.cancel(789);
    }

}
