package com.example.pucho.RECEPTORES;

import static com.example.pucho.ENTIDADES.AlarmEvent.TRIGGER_NOTIFICATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.pucho.controladores.AlarmAndBDController;

public class TimeChangeReceiver extends BroadcastReceiver {

    private int claveValor;
    private AlarmAndBDController alarmAndBDController;


    @Override
    public void onReceive(Context context, Intent intent) {
        alarmAndBDController = new AlarmAndBDController(context);


        Bundle extras = intent.getExtras();
        if(extras == null) {
            claveValor = 0;
        } else {
            claveValor = extras.getInt(TRIGGER_NOTIFICATION);
        }

        System.out.println("Recibe el Receiver" );
        System.out.println("Este es el valor de claveValor " + String.valueOf(claveValor) );

        if(claveValor  /*intent.getIntExtra(TRIGGER_NOTIFICATION, 0)*/ == 2){
            alarmAndBDController.startNotification();
        }else if(claveValor/*intent.getIntExtra(TRIGGER_NOTIFICATION, 0)*/ == 1) {
            System.out.println("está recibiendo TimeChangeReceiver como si estuvieses apretando el boton de la notificacion");
            alarmAndBDController.closeNotification();
            alarmAndBDController.addPucho();
        }
    }


}
