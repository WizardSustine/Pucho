package com.example.pucho.controladores;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.R;
import com.example.pucho.SQLite.BDManager;
import com.example.pucho.SQLite.Contrato;

public class BDController {

    private ControladorExpectativas controladorExpectativas;
    private ControladorPuchos controladorPuchos;
    private PuchoDia puchoDia;
    private Expectativas expectativas;

    public BDController(Context context, String date){
        controladorPuchos = new ControladorPuchos(context);
        controladorExpectativas = new ControladorExpectativas(context);

        controladorPuchos.verifyExistence(date);
        puchoDia= controladorPuchos.getPuchoDia();
        expectativas = controladorExpectativas.verifyExistence();
        if(expectativas != null && expectativas.getDiasRestantes() != 0){
            puchoDia.setExpectativa(expectativas.getCantidad());
            controladorPuchos.setExpectativa(expectativas.getCantidad());
        }
    }

    public PuchoDia addPuchos(String date){
        puchoDia = controladorPuchos.addPucho(date);
        return puchoDia;
    }

    public PuchoDia setExpectativa(){
        expectativas = controladorExpectativas.verifyExistence();
        if(expectativas != null && expectativas.getDiasRestantes() != 0){
            puchoDia.setExpectativa(expectativas.getCantidad());
            controladorPuchos.setExpectativa(expectativas.getCantidad());
        }
        return puchoDia;
    }

    public SimpleCursorAdapter getPuchosAdapter() {
        SimpleCursorAdapter adapter = controladorPuchos.getPuchosAdapter();
        return adapter;
    }
}
