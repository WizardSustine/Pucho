package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.ContratoSQL;

public class Expectativas {
    private int diasRestantes, cantidad;
    private String fechaInicio, fechaUltima;

    public Expectativas(String fecha){
        this.fechaInicio = fecha;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(ContratoSQL.EXPECTATIVAS.COLUMNA_INICIO, fechaInicio);
        values.put(ContratoSQL.EXPECTATIVAS.COLUMNA_ULTIMA, fechaUltima);
        values.put(ContratoSQL.EXPECTATIVAS.COLUMNA_CANTIDAD, cantidad);
        values.put(ContratoSQL.EXPECTATIVAS.COLUMNA_DIAS, diasRestantes);
        return values;
    }
    public int getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(int diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaUltima() {
        return fechaUltima;
    }

    public void setFechaUltima(String fechaUltima) {
        this.fechaUltima = fechaUltima;
    }
}
