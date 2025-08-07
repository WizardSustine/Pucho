package com.example.pucho.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.Contrato.ENTRADAS;

public class BDManager {
    private BDHelper bdHELPER;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public BDManager(Context context){
        this.context = context;
    }

    public BDManager open() throws SQLException {
        bdHELPER = new BDHelper(context);
        sqLiteDatabase = bdHELPER.getWritableDatabase();
        return this;
    }
    public void close(){
        bdHELPER.close();
    }

    public void insert_puchos(PuchoDia pucho){
        sqLiteDatabase.insert(ENTRADAS.NOMBRE_TABLA, null, pucho.toContentValues());
    }

    public int update_puchos(long _id, PuchoDia pucho){
        int x =sqLiteDatabase.update(ENTRADAS.NOMBRE_TABLA, pucho.toContentValues(), ENTRADAS._ID + " = " + _id, null);
        return x;
    }

    public void delete_puchos(long _id){
        sqLiteDatabase.delete(ENTRADAS.NOMBRE_TABLA, ENTRADAS._ID + " = " + _id, null);
    }

    public Cursor fetch_puchos(){
        String[] columns = new String[]{
                ENTRADAS._ID, ENTRADAS.COLUMNA_FECHA, ENTRADAS.COLUMNA_CANTIDAD, ENTRADAS.COLUMNA_EXPECTATIVA, ENTRADAS.COLUMNA_TIME_FOREACH, ENTRADAS.COLUMNA_ESTADO
        };
        Cursor cursor = sqLiteDatabase.query(ENTRADAS.NOMBRE_TABLA, columns, null, null, null, null, null);//ENTRADAS.COLUMNA_FECHA + " ASC"
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void insert_expectativas(Expectativas expectativas){
        sqLiteDatabase.insert(Contrato.EXPECTATIVAS.NOMBRE_TABLA, null, expectativas.toContentValues());
    }

    public int update_expectativas(long _id, Expectativas expectativas){
        int i = sqLiteDatabase.update(Contrato.EXPECTATIVAS.NOMBRE_TABLA, expectativas.toContentValues(), Contrato.EXPECTATIVAS._ID + " = " + _id, null);
        return i;
    }

    public void delete_expectativas(long _id){
        sqLiteDatabase.delete(Contrato.EXPECTATIVAS.NOMBRE_TABLA, Contrato.EXPECTATIVAS._ID + " = " + _id, null);
    }

    public Cursor fetch_expectativas(){
        String[] columns = new String[]{
                Contrato.EXPECTATIVAS._ID, Contrato.EXPECTATIVAS.COLUMNA_INICIO, Contrato.EXPECTATIVAS.COLUMNA_CANTIDAD, Contrato.EXPECTATIVAS.COLUMNA_DIAS, Contrato.EXPECTATIVAS.COLUMNA_ESTADO
        };
        Cursor cursor = sqLiteDatabase.query(Contrato.EXPECTATIVAS.NOMBRE_TABLA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_last_expectativa(){
        String[] columns = new String[]{
                Contrato.EXPECTATIVAS._ID, Contrato.EXPECTATIVAS.COLUMNA_INICIO, Contrato.EXPECTATIVAS.COLUMNA_CANTIDAD, Contrato.EXPECTATIVAS.COLUMNA_DIAS, Contrato.EXPECTATIVAS.COLUMNA_ESTADO
        };
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Contrato.EXPECTATIVAS.NOMBRE_TABLA + " WHERE " + Contrato.EXPECTATIVAS._ID + " = (SELECT MAX(" + Contrato.EXPECTATIVAS._ID +") FROM " + Contrato.EXPECTATIVAS.NOMBRE_TABLA + ")", null);
        return cursor;
    }
}
