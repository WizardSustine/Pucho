package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.Contrato;

public class PuchoDia {
    private String fecha, estado;
    private int consumo, expectativa, time_foreach;
    private long _id;

    public PuchoDia(String fecha) {
        this._id = 0;
        this.fecha = fecha;
        this.estado = "PENDIENTE";
        this.consumo = 0;
        this.expectativa = 0;
        this.time_foreach = 0;
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(Contrato.ENTRADAS.COLUMNA_FECHA, fecha);
        values.put(Contrato.ENTRADAS.COLUMNA_CANTIDAD, consumo);
        values.put(Contrato.ENTRADAS.COLUMNA_EXPECTATIVA, expectativa);
        values.put(Contrato.ENTRADAS.COLUMNA_TIME_FOREACH, time_foreach);
        values.put(Contrato.ENTRADAS.COLUMNA_ESTADO, estado);

        return values;
    }

    public int getTime_foreach() {
        return time_foreach;
    }

    public void setTime_foreach(int time_foreach) {
        this.time_foreach = time_foreach;
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

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public int getExpectativa() {
        return expectativa;
    }

    public void setExpectativa(int expectativa) {
        this.expectativa = expectativa;
    }
}
