package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.Contrato;

public class Expectativas {
    private int dias, cantidad;
    private String inicio;

    public Expectativas(String inicio){
        this.inicio = inicio;
        dias = 31;
        cantidad = 0;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(Contrato.EXPECTATIVAS.COLUMNA_INICIO, inicio);
        values.put(Contrato.EXPECTATIVAS.COLUMNA_DIAS, dias);
        values.put(Contrato.EXPECTATIVAS.COLUMNA_CANTIDAD, cantidad);
        return values;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
