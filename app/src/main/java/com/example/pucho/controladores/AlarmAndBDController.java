package com.example.pucho.controladores;

import static com.example.pucho.MainActivity.notificationSwitch;

import android.content.Context;
import android.widget.SimpleCursorAdapter;

import com.example.pucho.ENTIDADES.AlarmEvent;
import com.example.pucho.ENTIDADES.PuchoDia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmAndBDController {
    private static AlarmEvent alarmEvent;
    private static BDController bdController;
    private static PuchoDia hoy;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String formattedDate;

    public AlarmAndBDController (Context context) {
        Date now = new Date();
        formattedDate = dateFormat.format(now);
        alarmEvent = new AlarmEvent(context);
        bdController = new BDController(context,formattedDate);
        hoy = bdController.getPuchoDia();
    }

    public void setAlarmEvent(int x){
        if(x == 0) {
            alarmEvent.setNotificationTrigger(0, notificationSwitch);
        } else if (x == 1) {
            alarmEvent.setNotificationTrigger(alarmEvent.setTimeNextPucho(hoy, formattedDate), notificationSwitch);
        }
    }
    public void addPucho(){
        hoy = bdController.addPuchos(formattedDate);
        if(hoy.getExpectativa() > hoy.getConsumo() && hoy.getConsumo() > 0){
            setAlarmEvent(1);
        }
    }

    public static SimpleCursorAdapter getAdapter(){
        return bdController.getPuchosAdapter();
    }

    public PuchoDia getPucho(){
        return hoy;
    }

    public PuchoDia setExpectativas(){
        hoy = bdController.setExpectativa();
        return hoy;
    }
    public boolean setNotificationPermission(){
        return alarmEvent.notificationApp.notificationPermission();
    }
}
