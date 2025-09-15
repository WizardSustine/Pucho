package com.example.pucho;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.SQLite.BDManager;

public class NewExpectativaActivity extends Activity {
    private EditText cantidadPuchos, cantidadDias, fechaInicio;
    private String fecha;
    private ImageButton addPuchos, deletePuchos, addDias, deleteDias;
    private Button agregar, cancelar;
    private int puchos, dias;
    private BDManager bdManager;
    private Expectativas expectativa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        puchos = 0;
        dias = 0;

        setTitle("Establecer expectativas");
        setContentView(R.layout.activity_expectativas);

        bdManager = new BDManager(this);
        bdManager.open();

        cantidadDias = findViewById(R.id.editDiasCantidad);
        cantidadPuchos = findViewById(R.id.editPuchosCantidad);
        fechaInicio = findViewById(R.id.editTextDate);

        cantidadDias.setText("0");
        cantidadPuchos.setText("0");

        addDias = findViewById(R.id.imageButton3);
        deleteDias = findViewById(R.id.imageButton4);
        addPuchos = findViewById(R.id.imageButton);
        deletePuchos = findViewById(R.id.imageButton2);

        agregar = findViewById(R.id.button_add);
        cancelar = findViewById(R.id.button_cancel);


        Intent intent = getIntent();
        fecha = intent.getStringExtra("date");

        fechaInicio.setText(fecha);

        expectativa = new Expectativas(fecha);
        expectativa.setEstado("PENDIENTE");
        expectativa.setCantidad(puchos);
        expectativa.setDiasRestantes(dias);

        addDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddDias();
            }
        });

        deleteDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeleteDias();
            }
        });

        addPuchos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddPuchos();
            }
        });

        deletePuchos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeletePuchos();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              expectativa.setCantidad(Integer.parseInt(cantidadPuchos.getText().toString()));
              expectativa.setDiasRestantes(Integer.parseInt(cantidadDias.getText().toString()));
              bdManager.insert_expectativas(expectativa);
              bdManager.close();
              returnHome();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bdManager.close();
                returnHome();
            }
        });
    }

    public void setAddPuchos(){
        puchos++;
        cantidadPuchos.setText(String.valueOf(puchos));
        expectativa.setCantidad(puchos);
    }

    public void setDeletePuchos(){
        puchos--;
        cantidadPuchos.setText(String.valueOf(puchos));
        expectativa.setCantidad(puchos);
    }

    public void setAddDias(){
        dias++;
        cantidadDias.setText(String.valueOf(dias));
        expectativa.setDiasRestantes(dias);
    }
    public void setDeleteDias(){
        dias--;
        cantidadDias.setText(String.valueOf(dias));
        expectativa.setDiasRestantes(dias);
    }
    public void returnHome() {
        Intent home_intent = new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
