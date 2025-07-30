package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.Contrato;

public class PuchoDia {
    private String fecha, estado;
    private int cantidad, expectativa;
    private long _id;

    public PuchoDia(long _id, String fecha, String estado, int cantidad, int expectativa) {
        this._id = _id;
        this.fecha = fecha;
        this.estado = estado;
        this.cantidad = cantidad;
        this.expectativa = expectativa;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(Contrato.ENTRADAS.COLUMNA_FECHA, fecha);
        values.put(Contrato.ENTRADAS.COLUMNA_CANTIDAD, cantidad);
        values.put(Contrato.ENTRADAS.COLUMNA_EXPECTATIVA, expectativa);
        values.put(Contrato.ENTRADAS.COLUMNA_ESTADO, estado);

        return values;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExpectativa() {
        return expectativa;
    }

    public void setExpectativa(int expectativa) {
        this.expectativa = expectativa;
    }
}
