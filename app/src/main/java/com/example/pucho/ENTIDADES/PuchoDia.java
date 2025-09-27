package com.example.pucho.ENTIDADES;

import android.content.ContentValues;

import com.example.pucho.SQLite.ContratoSQL;

public class PuchoDia {
    private String fecha, time_last;
    private int consumo, expectativa;
    private long _id;

    public PuchoDia(String fecha) {
        this._id = 0;
        this.fecha = fecha;
        this.consumo = 0;
        this.expectativa = 0;
        this.time_last = "";
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(ContratoSQL.ENTRADAS.COLUMNA_FECHA, fecha);
        values.put(ContratoSQL.ENTRADAS.COLUMNA_CANTIDAD, consumo);
        values.put(ContratoSQL.ENTRADAS.COLUMNA_EXPECTATIVA, expectativa);
        values.put(ContratoSQL.ENTRADAS.COLUMNA_TIME_LAST, time_last);

        return values;
    }

    public String getTime_last() {
        return time_last;
    }

    public void setTime_last(String time_last) {
        this.time_last = time_last;
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
