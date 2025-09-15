package com.example.pucho.RECEPTORES;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.pucho.controladores.DayCheckActivity;
import com.example.pucho.controladores.NotificacionActivity;

public class DayChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dayChangerActivity = new Intent(context, DayCheckActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, dayChangerActivity, PendingIntent.FLAG_IMMUTABLE);

    }
}
