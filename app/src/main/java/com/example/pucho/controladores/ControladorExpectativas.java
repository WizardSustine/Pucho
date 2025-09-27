package com.example.pucho.controladores;

import android.content.Context;
import android.database.Cursor;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.SQLite.BDManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ControladorExpectativas {
    private BDManager bdManager;
    private Expectativas expectativas;
    private Context context;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    public ControladorExpectativas(Context context){
        this.context = context;
        bdManager = new BDManager(context);
        expectativas = null;
    }

    public Expectativas verifyExistence() {
        bdManager.open();
        Cursor cursor = bdManager.fetch_expectativas();
        System.out.println("corre Expectativas verify Existence");
        System.out.println(cursor.getColumnName(1) + " - " + cursor.getColumnName(2) + " - " + cursor.getColumnName(3));
        if (cursor.getCount() > 0) {
            if(cursor.getInt(3) > 0){
                System.out.println("Expectativas Verify Existence, si hay expectativas");
                cursor.close();
                bdManager.close();
                expectativas = new Expectativas("0000");
                setExpectativasData();
                restDay();

                return expectativas;
            }else {
                System.out.println("Expectativas Verify Existence, si NO hay expectativas");
                cursor.close();
                bdManager.close();
                return null;
            }
        }else{
            System.out.println("Expectativas Verify Existence, si NO 2 hay expectativas");
            cursor.close();
            bdManager.close();
            return null;
        }
    }
    public boolean restDay(){
        Date now = new Date();
        String currentDate = dateFormat.format(now);
        System.out.println(currentDate + " esto es hoy");
        bdManager.open();
        Cursor cursor = bdManager.fetch_expectativas();
        if (cursor.getCount() > 0){
            if(cursor.getInt(4) > 0 && !cursor.getString(2).equals(currentDate)){
                int x = cursor.getInt(4) - 1;
                expectativas.setFechaUltima(currentDate);
                expectativas.setDiasRestantes(x);
                bdManager.update_expectativas(cursor.getInt(0), expectativas);
                cursor.close();
                bdManager.close();
                setExpectativasData();
                System.out.println("RestDay working");

                return true;
            }else{
                cursor.close();
                bdManager.close();
                return false;
            }
        }
        cursor.close();
        bdManager.close();
        return false;
    }
    private void setExpectativasData(){
        bdManager.open();
        Cursor cursor = bdManager.fetch_expectativas();
        System.out.println("Set Expectativas Data on controlladorExpectativas");
        System.out.println();
        expectativas.setFechaInicio(cursor.getString(1));
        expectativas.setFechaUltima(cursor.getString(2));
        expectativas.setCantidad(cursor.getInt(3));
        expectativas.setDiasRestantes(cursor.getInt(4));
        cursor.close();
        bdManager.close();
    }
}
