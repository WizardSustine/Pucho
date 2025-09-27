package com.example.pucho.SQLite;

import static com.example.pucho.SQLite.ContratoSQL.SQL_CREATE_ENTRIES_PUCHOS;
import static com.example.pucho.SQLite.ContratoSQL.SQL_CREATE_ENTRIES_EXP;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BDHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Puchos.db";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_PUCHOS);
        db.execSQL(SQL_CREATE_ENTRIES_EXP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_ENTRIES_PUCHOS);
        db.execSQL(SQL_CREATE_ENTRIES_EXP);
        onCreate(db);
    }
}
