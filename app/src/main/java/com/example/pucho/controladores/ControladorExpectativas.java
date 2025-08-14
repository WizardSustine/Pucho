package com.example.pucho.controladores;

import android.content.Context;
import android.database.Cursor;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.BDManager;

public class ControladorExpectativas {
    private BDManager bdManager;
    private Expectativas expectativas;
    private Context context;
    public ControladorExpectativas(Context context){
        this.context = context;
        bdManager = new BDManager(context);
        expectativas = null;
    }

    public Expectativas verifyExistence() {
        bdManager.open();
        Cursor cursor = bdManager.fetch_expectativas();
        System.out.println("acá en Expectativas verify Existence algo pasa");
        System.out.println(cursor.getColumnName(1) + " - " + cursor.getColumnName(2) + " - " + cursor.getColumnName(3));
        if (cursor.getCount() > 0) {
            if(cursor.getInt(2) > 0){
                System.out.println("Expectativas Verify Existence, si hay expectativas");
                cursor.close();
                bdManager.close();
                expectativas = new Expectativas("0000");
                setExpectativasData();
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
        bdManager.open();
        Cursor cursor = bdManager.fetch_expectativas();
        if (cursor.getCount() > 0){
            if(cursor.getInt(2) > 0){
                int x = cursor.getInt(2) - 1;

                expectativas.setDiasRestantes(x);
                bdManager.update_expectativas(cursor.getInt(0), expectativas);
                cursor.close();
                setExpectativasData();
                bdManager.close();
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
        expectativas.setCantidad(cursor.getInt(2));
        expectativas.setDiasRestantes(cursor.getInt(3));
        expectativas.setEstado(cursor.getString(4));
        cursor.close();
        bdManager.close();
    }
}
