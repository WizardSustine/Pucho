package com.example.pucho.controladores;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.MainActivity;
import com.example.pucho.R;
import com.example.pucho.SQLite.BDManager;
import com.example.pucho.SQLite.Contrato;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ControladorPuchos {
    private BDManager bdManager;
    private PuchoDia puchoDia;
    public Context context;
    private Expectativas expectativa;
    public ControladorPuchos(Context context, Expectativas expectativas, String date){
        this.context = context;
        this.expectativa = expectativas;
        bdManager = new BDManager(context);
        puchoDia = new PuchoDia(date);

    }

    public PuchoDia verifyNonExistence(String date){
        bdManager.open();
        System.out.println("el date es " + date);

        Cursor cursor = bdManager.fetch_puchos();
        System.out.println(String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            if (date.equals(cursor.getString(1))) {
                System.out.println("Ejecuta el cursor 1 Controlador Pucho verify non existence");
                puchoDia.set_id((long) cursor.getInt(0));
                puchoDia.setFecha(cursor.getString(1));
                puchoDia.setConsumo(cursor.getInt(2));
                puchoDia.setExpectativa(cursor.getInt(3));
                puchoDia.setTime_foreach(cursor.getInt(4));
                puchoDia.setEstado("PENDIENTE");

                cursor.close();
                bdManager.close();
            } else {
                System.out.println("Ejecuta el cursor 2 Controlador Pucho verify non existence");
                puchoDia.set_id(0);
                puchoDia.setFecha(date);
                puchoDia.setConsumo(0);
                if (expectativa != null){
                    puchoDia.setExpectativa(expectativa.getCantidad());
                }else{
                    puchoDia.setExpectativa(0);}
                puchoDia.setTime_foreach(0);
                puchoDia.setEstado("PENDIENTE");

                bdManager.insert_puchos(puchoDia);
                puchoDia.set_id((long) cursor.getInt(0));
                cursor.close();
                bdManager.close();
            }
        }else{
            System.out.println("Ejecuta el cursor 3 Controlador Pucho verify non existence");

            puchoDia = new PuchoDia(date);
            bdManager.insert_puchos(puchoDia);
            cursor = bdManager.fetch_puchos();
            System.out.println("before getInt 0");
            puchoDia.set_id((long) cursor.getInt(0));
            cursor.close();
            bdManager.close();
        }
        return puchoDia;
    }


    public PuchoDia addPucho(){
        System.out.println("Operando en Controlador addPucho()");
        bdManager.open();
        int x;
        Cursor cursor = bdManager.fetch_puchos();
        System.out.println(" 1 " + cursor.getColumnName(1) + " - 2 " + cursor.getColumnName(2));
        System.out.println(" 2 " + cursor.getColumnName(3) + " - 4 " + cursor.getColumnName(4));
        System.out.println(" 0 " + cursor.getColumnName(0) );
        System.out.println(" 0 " + String.valueOf(cursor.getInt(0)));
        x = 1 + cursor.getInt(3);

        System.out.println(String.valueOf(puchoDia.getConsumo()) + " - " + String.valueOf(cursor.getInt(2)));
        puchoDia.set_id((long) cursor.getInt(0));
        puchoDia.setFecha(cursor.getString(1));
        puchoDia.setEstado("PENDIENTE");
        puchoDia.setConsumo(x);
        puchoDia.setExpectativa(cursor.getInt(4));
        System.out.println(String.valueOf(puchoDia.getConsumo())  + " -  este es el último");
        bdManager.update_puchos(puchoDia.get_id(), puchoDia);
        cursor.close();
        bdManager.close();
        return puchoDia;
    }

    public SimpleCursorAdapter getAdapter() {
        System.out.println("falla en Controlador getAdapter()");
        bdManager.open();
        final String[] from = new String[]{
                Contrato.ENTRADAS._ID, Contrato.ENTRADAS.COLUMNA_FECHA, Contrato.ENTRADAS.COLUMNA_CANTIDAD, Contrato.ENTRADAS.COLUMNA_EXPECTATIVA, Contrato.ENTRADAS.COLUMNA_TIME_FOREACH, Contrato.ENTRADAS.COLUMNA_ESTADO
        };
        final int[] to = new int[]{R.id.id_ListViewPuchos, R.id.dateListViewPuchos, R.id.cantidadListViewPuchos, R.id.expectativaListViewPuchos,R.id.timeforeachListViewPuchos, R.id.stateListViewPuchos};                bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.view_consumo, cursor, from, to);
        adapter.notifyDataSetChanged();
        cursor.close();
        bdManager.close();
        return adapter;
    }

}
