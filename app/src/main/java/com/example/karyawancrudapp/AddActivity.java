package com.example.karyawancrudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements KaryawanAdapter.onDataListener {
    private EditText namanya, divisinya, jabatanya, telpnya;
    private DBSourceData dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        namanya = (EditText) findViewById(R.id.et_nama);
        divisinya = (EditText) findViewById(R.id.et_divisi);
        jabatanya = (EditText) findViewById(R.id.et_jabatan);
        telpnya = (EditText) findViewById(R.id.et_telp);

        dataSource = new DBSourceData(this);
        dataSource.open();
    }

    public void aksiSimpan(View v){
        String n = null;
        String d = null;
        String j = null;
        String t = null;
        Karyawan k = null;

        if (namanya.getText() != null && divisinya.getText()!=null && jabatanya.getText()!=null && telpnya.getText()!=null){
            n = namanya.getText().toString();
            d = divisinya.getText().toString();
            j = jabatanya.getText().toString();
            t = telpnya.getText().toString();
            k = dataSource.createKaryawan(n,d,j,t);
            Toast.makeText(this, k.toString()+" Telah disimpan ke DB.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Pastikan tidak ada isian kosong!", Toast.LENGTH_SHORT).show();
        }

    }

    public void aksiBack(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDataClick(Karyawan karyawan, int position) {

    }
}