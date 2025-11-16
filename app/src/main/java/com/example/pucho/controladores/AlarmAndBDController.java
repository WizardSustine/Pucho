package com.example.pucho.controladores;

import static com.example.pucho.MainActivity.savedState;

import android.content.Context;
import android.widget.SimpleCursorAdapter;

import com.example.pucho.ENTIDADES.AlarmEvent;
import com.example.pucho.ENTIDADES.PuchoDia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            alarmEvent.setNotificationTrigger(0, savedState);
        } else if (x == 1) {
            alarmEvent.setNotificationTrigger(alarmEvent.setTimeNextPucho(hoy, formattedDate), savedState);
        }
    }
    public void addPucho(){
        bdController.addPuchos(formattedDate);
        hoy = bdController.getPuchoDia();
        if(bdController.getPuchoDia().getExpectativa() > bdController.getPuchoDia().getConsumo() && bdController.getPuchoDia().getConsumo() > 0){
            setAlarmEvent(1);
        }
    }

    public void delete_pucho(long id){
        bdController.delete_pucho(id);
    }
    public static ArrayList<PuchoDia> getAdapter(){
        return bdController.getPuchosAdapter();
    }

    public PuchoDia getPucho(){
        return hoy;
    }

    public void setExpectativas(){
        hoy = bdController.setExpectativa();
    }
    public boolean setNotificationPermission(){
        return alarmEvent.notificationApp.notificationPermission();
    }

    public void startNotification(){
        alarmEvent.notificationApp.notiBuild();
    }

    public void closeNotification(){
        alarmEvent.notificationApp.closeNotification();
    }

    public static ArrayList<PuchoDia> get30Dias(){
        return bdController.get30Dias();
    }
}
