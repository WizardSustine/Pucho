package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.Contrato;

public class Expectativas {
    private int diasRestantes, cantidad;
    private String fechaInicio, estado;

    public Expectativas(String fecha){
        this.fechaInicio = fecha;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(Contrato.EXPECTATIVAS.COLUMNA_INICIO, fechaInicio);
        values.put(Contrato.EXPECTATIVAS.COLUMNA_CANTIDAD, cantidad);
        values.put(Contrato.EXPECTATIVAS.COLUMNA_DIAS, diasRestantes);
        values.put(Contrato.EXPECTATIVAS.COLUMNA_ESTADO, estado);
        return values;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
