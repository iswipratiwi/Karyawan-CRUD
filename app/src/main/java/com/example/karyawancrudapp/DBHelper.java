package com.example.karyawancrudapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String db_name = "karyawan.db";
    private static final int db_version = 1;

    public static  final  String TABLE_NAME = "tbl_karyawan";
    public static  final  String COLUMN_ID = "_id";
    public static  final  String COLUMN_NAME ="nama";
    public static  final  String COLUMN_DIVISI = "divisi";
    public static  final  String COLUMN_JABATAN ="jabatan";
    public static  final  String COLUMN_NOTELP ="telp";
    private static final String db_create = "create table " +
            TABLE_NAME + "(" +
            COLUMN_ID   + " integer primary key autoincrement, "+
            COLUMN_NAME + " varchar(50) not null, "+
            COLUMN_DIVISI + " varchar(50) not null, "+
            COLUMN_JABATAN + " varchar(50) not null, "+
            COLUMN_NOTELP + " varchar(50) not null);";

    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    public void onUpgrade(SQLiteDatabase db, int old, int baru){
        Log.w(DBHelper.class.getName(),"Upgrade db dari versi "+ old +" ke "+baru);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}

