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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ControladorPuchos {
    private BDManager bdManager;
    private PuchoDia puchoDia;
    private Context context;
    public ControladorPuchos(Context context){
        this.context = context;
        bdManager = new BDManager(context);
        puchoDia = new PuchoDia("00-00-0000");
    }
    public void verifyExistence(String date){
        bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        System.out.println(cursor.getColumnName(1) + " - " + cursor.getColumnName(2) + " - " + cursor.getColumnName(3));
        if (cursor.getCount() > 0) {
            if (date.equals(cursor.getString(1))) {
                System.out.println("la fecha es igual a la fecha del cursor");
                cursor.close();
                bdManager.close();
                setPuchoBDData();
            }else {
                System.out.println("la fecha NO ES igual a la fecha del cursor");
                puchoDia = new PuchoDia(date);
                bdManager.insert_puchos(puchoDia);
                cursor.close();
                bdManager.close();
                setPuchoBDData();
            }
        }else{
            System.out.println("La base de datos está vacia");
            puchoDia = new PuchoDia(date);
            bdManager.insert_puchos(puchoDia);
            cursor.close();
            bdManager.close();
            setPuchoBDData();
        }
    }

    public void setExpectativa(int k){
        bdManager.open();
        puchoDia.setExpectativa(k);
        bdManager.update_puchos(puchoDia.get_id(), puchoDia);
        bdManager.close();
    }
    private void setPuchoBDData(){
        bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        puchoDia.set_id(cursor.getInt(0));
        puchoDia.setFecha(cursor.getString(1));
        puchoDia.setConsumo(cursor.getInt(2));
        puchoDia.setExpectativa(cursor.getInt(3));
        puchoDia.setTime_foreach(cursor.getInt(4));
        puchoDia.setEstado("PENDIENTE");
        cursor.close();
        bdManager.close();
    }

    public PuchoDia addPucho(String date){
        verifyExistence(date);
        bdManager.open();
        int x = puchoDia.getConsumo() + 1;
        puchoDia.setConsumo(x);
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(date);
        bdManager.update_puchos(puchoDia.get_id(), puchoDia);
        bdManager.close();
        return puchoDia;
    }

    public void setEndingDay(String date){
        verifyExistence(date);
    }
    public PuchoDia getPuchoDia() {
        return puchoDia;
    }

    public SimpleCursorAdapter getPuchosAdapter() {
        bdManager.open();
        System.out.println("falla en Controlador getAdapter()");
        final String[] from = new String[]{
                Contrato.ENTRADAS._ID, Contrato.ENTRADAS.COLUMNA_FECHA, Contrato.ENTRADAS.COLUMNA_CANTIDAD, Contrato.ENTRADAS.COLUMNA_EXPECTATIVA, Contrato.ENTRADAS.COLUMNA_TIME_LAST, Contrato.ENTRADAS.COLUMNA_ESTADO
        };
        final int[] to = new int[]{R.id.id_ListViewPuchos, R.id.dateListViewPuchos, R.id.cantidadListViewPuchos, R.id.expectativaListViewPuchos,R.id.timeforeachListViewPuchos, R.id.stateListViewPuchos};                bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.view_consumo, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        cursor.close();
        bdManager.close();
        return adapter;
    }
}
