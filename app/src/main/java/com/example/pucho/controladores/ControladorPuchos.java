package com.example.pucho.controladores;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.MainActivity;
import com.example.pucho.SQLite.BDManager;

public class ControladorPuchos {
    private BDManager bdManager;
    private PuchoDia puchoDia;
    public Context context;
    private Expectativas expectativa;
    public ControladorPuchos(Context context, Expectativas expectativas){
        this.context = context;
        this.expectativa = expectativas;
        bdManager = new BDManager(context);
    }

    public PuchoDia verifyNonExistence(String date){
        bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        if(!date.equals(cursor.getString(1))){
            puchoDia = new PuchoDia(0, date,"PENDIENTE", 0, expectativa.getCantidad());
            bdManager.insert_puchos(puchoDia);
            puchoDia.set_id((long)cursor.getInt(0));
            cursor.close();
            bdManager.close();
        }else {
            puchoDia = new PuchoDia((long)cursor.getInt(0), cursor.getString(1), cursor.getString(2) ,cursor.getInt(3), cursor.getInt(4));
            cursor.close();
            bdManager.close();
        }
        return puchoDia;
    }

    public PuchoDia addPucho(){
        bdManager.open();
        int x;
        Cursor cursor = bdManager.fetch_puchos();
        x = 1 + Integer.parseInt(cursor.getString(3));
        puchoDia = new PuchoDia((long) cursor.getInt(0), cursor.getString(1),"PENDIENTE", x, cursor.getInt(4));
        bdManager.update_puchos(puchoDia.get_id(), puchoDia);
        cursor.close();
        bdManager.close();
        return puchoDia;
    }

}
