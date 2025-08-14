package com.example.pucho.SQLite;

import android.provider.BaseColumns;

public class Contrato {
    public Contrato(){}
    public static final class ENTRADAS implements BaseColumns {
        public static final String NOMBRE_TABLA = "AGENDA";
        public static final String COLUMNA_FECHA = "FECHA";
        public static final String COLUMNA_CANTIDAD = "CANTIDAD";
        public static final String COLUMNA_EXPECTATIVA = "EXPECTATIVA";
        public static final String COLUMNA_TIME_LAST = "TIME_LAST";
        public static final String COLUMNA_ESTADO = "ESTADO";
    }

    public static final class EXPECTATIVAS implements BaseColumns {
        public static final String NOMBRE_TABLA = "EXPECTATIVAS";
        public static final String COLUMNA_INICIO = "FECHAINICIO";
        public static final String COLUMNA_DIAS = "DIAS";
        public static final String COLUMNA_CANTIDAD = "CANTIDAD";
        public static final String COLUMNA_ESTADO = "ESTADO";
    }
    public static final String SQL_CREATE_ENTRIES_PUCHOS =
            "CREATE TABLE IF NOT EXISTS " + ENTRADAS.NOMBRE_TABLA + " (" +
            ENTRADAS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ENTRADAS.COLUMNA_FECHA + " TEXT," +
            ENTRADAS.COLUMNA_CANTIDAD + " INTEGER," +
            ENTRADAS.COLUMNA_EXPECTATIVA + " INTEGER," +
            ENTRADAS.COLUMNA_TIME_LAST + " INTEGER, " +
            ENTRADAS.COLUMNA_ESTADO + " TEXT)";
    public static final String SQL_CREATE_ENTRIES_EXP =
            "CREATE TABLE IF NOT EXISTS " + EXPECTATIVAS.NOMBRE_TABLA + " (" +
            EXPECTATIVAS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EXPECTATIVAS.COLUMNA_INICIO + " TEXT," +
            EXPECTATIVAS.COLUMNA_CANTIDAD + " INTEGER," +
            EXPECTATIVAS.COLUMNA_DIAS + " INTEGER," +
            EXPECTATIVAS.COLUMNA_ESTADO + " TEXT)";
    public static final String SQL_DELETE_ENTRIES_PUCHOS =
            "DROP TABLE IF EXISTS " + ENTRADAS.NOMBRE_TABLA;
    public static final String SQL_DELETE_ENTRIES_EXP =
            "DROP TABLE IF EXISTS " + EXPECTATIVAS.NOMBRE_TABLA;
}
