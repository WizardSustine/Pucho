package com.example.pucho.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.ContratoSQL.ENTRADAS;

import java.util.ArrayList;

public class BDManager {
    private static BDHelper bdHELPER;
    private Context context;
    private static SQLiteDatabase sqLiteDatabase;

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

    public static Cursor fetch_puchos(){
        String[] columns = new String[]{
                ENTRADAS._ID, ENTRADAS.COLUMNA_FECHA, ENTRADAS.COLUMNA_CANTIDAD, ENTRADAS.COLUMNA_EXPECTATIVA, ENTRADAS.COLUMNA_TIME_LAST
        };
        Cursor cursor = sqLiteDatabase.query(ENTRADAS.NOMBRE_TABLA, columns, null, null, null, null, ENTRADAS._ID + " DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public static Cursor fetch_graph_puchos() {
        String selectQuery = "SELECT * FROM " + ENTRADAS.NOMBRE_TABLA +
                " ORDER BY " + ENTRADAS._ID + " DESC " +
                " LIMIT 30";
        return sqLiteDatabase.rawQuery(selectQuery, null);
    }
    public void insert_expectativas(Expectativas expectativas){
        sqLiteDatabase.insert(ContratoSQL.EXPECTATIVAS.NOMBRE_TABLA, null, expectativas.toContentValues());
    }

    public int update_expectativas(long _id, Expectativas expectativas){
        int i = sqLiteDatabase.update(ContratoSQL.EXPECTATIVAS.NOMBRE_TABLA, expectativas.toContentValues(), ContratoSQL.EXPECTATIVAS._ID + " = " + _id, null);
        return i;
    }

    public void delete_expectativas(long _id){
        sqLiteDatabase.delete(ContratoSQL.EXPECTATIVAS.NOMBRE_TABLA, ContratoSQL.EXPECTATIVAS._ID + " = " + _id, null);
    }

    public static Cursor fetch_expectativas(){
        String[] columns = new String[]{
                ContratoSQL.EXPECTATIVAS._ID, ContratoSQL.EXPECTATIVAS.COLUMNA_INICIO, ContratoSQL.EXPECTATIVAS.COLUMNA_ULTIMA, ContratoSQL.EXPECTATIVAS.COLUMNA_CANTIDAD, ContratoSQL.EXPECTATIVAS.COLUMNA_DIAS
        };
        Cursor cursor = sqLiteDatabase.query(ContratoSQL.EXPECTATIVAS.NOMBRE_TABLA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToLast();
        }
        return cursor;
    }

}
