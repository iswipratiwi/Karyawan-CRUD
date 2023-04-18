package com.example.karyawancrudapp;

import static com.example.karyawancrudapp.DBHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBSourceData {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_DIVISI, DBHelper.COLUMN_JABATAN, DBHelper.COLUMN_NOTELP};

    public DBSourceData (Context context){
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Karyawan createKaryawan(String nama, String divisi, String jabatan, String telp){
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME,nama);
        values.put(dbHelper.COLUMN_DIVISI,divisi);
        values.put(dbHelper.COLUMN_JABATAN,jabatan);
        values.put(dbHelper.COLUMN_NOTELP,telp);

        long insertId = database.insert(TABLE_NAME, null,values);
        Cursor cursor = database.query(TABLE_NAME, allColumns,
                dbHelper.COLUMN_ID+"="+insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Karyawan newkaryawan = cursorToKaryawan(cursor);
        cursor.close();
        return newkaryawan;
    }

    private Karyawan cursorToKaryawan(Cursor cursor) {
        Karyawan karyawan = new Karyawan();
        karyawan.set_id(cursor.getLong(0));
        karyawan.setNama(cursor.getString(1));
        karyawan.setDivisi(cursor.getString(2));
        karyawan.setJabatan(cursor.getString(3));
        return karyawan;
    }

    public ArrayList getAllKaryawan(){
        ArrayList<Karyawan> daftarKaryawan = new ArrayList<Karyawan>();
        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Karyawan krywn = cursorToKaryawan(cursor);
            daftarKaryawan.add(krywn);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarKaryawan;
    }

    public Karyawan getKaryawan(long id){
        Karyawan k = new Karyawan();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        k = cursorToKaryawan(cursor);
        cursor.close();
        return k;
    }

    public void updateKaryawan(Karyawan x){
        String s = "_id = " +x.get_id();
        ContentValues c = new ContentValues();
        c.put(dbHelper.COLUMN_NAME,x.getNama());
        c.put(dbHelper.COLUMN_DIVISI,x.getDivisi());
        c.put(dbHelper.COLUMN_JABATAN,x.getJabatan());
        c.put(dbHelper.COLUMN_NOTELP,x.getTelp());
        database.update(dbHelper.TABLE_NAME, c, s, null);
    }

    public void deleteKaryawan(long id){
        String s = "_id = " +id;
        database.delete(dbHelper.TABLE_NAME,s, null);
    }
}
