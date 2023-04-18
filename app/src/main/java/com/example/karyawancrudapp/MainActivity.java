package com.example.karyawancrudapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements KaryawanAdapter.onDataListener {

    DBSourceData datasource;
    private ExtendedFloatingActionButton buttonadd;
    private RecyclerView recyclerview;
    private ArrayList<Karyawan> listkaryawan;
    private KaryawanAdapter adapterkaryawan;
    private EditText etnama;
    private EditText etdivisi;
    private EditText etjabatan;
    private EditText ettelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("listkaryawan");

        recyclerview = findViewById(R.id.listdata);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        datasource = new DBSourceData(this);
        datasource.open();
        listkaryawan = datasource.getAllKaryawan();


        ArrayList<Karyawan> dataKaryawan = listkaryawan;
        adapterkaryawan = new KaryawanAdapter(dataKaryawan, this);
        recyclerview.setAdapter(adapterkaryawan);

        buttonadd = findViewById(R.id.add_data);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateKaryawan(Karyawan karyawan) {
        DBSourceData datasource = new DBSourceData(this);
        datasource.open();

        datasource.updateKaryawan(karyawan);
        listkaryawan.clear();
        listkaryawan.addAll(datasource.getAllKaryawan());
        adapterkaryawan.notifyDataSetChanged();
        datasource.close();

        Toast.makeText(this, "Karyawan atas nama" + karyawan.getNama() + " berhasil diubah!", Toast.LENGTH_LONG).show();
    }

    private void dialogUpdate(final Karyawan karyawan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ubah Data Karyawan");
        View view = getLayoutInflater().inflate(R.layout.edit, null);

        etnama = view.findViewById(R.id.nama);
        etdivisi = view.findViewById(R.id.divisi);
        etjabatan = view.findViewById(R.id.jabatan);
        ettelp = view.findViewById(R.id.telp);

        etnama.setText(karyawan.getNama());
        etdivisi.setText(karyawan.getDivisi());
        etjabatan.setText(karyawan.getJabatan());
        ettelp.setText(karyawan.getTelp());
        builder.setView(view);

        if (karyawan != null){
            builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String namabaru = etnama.getText().toString();
                    String divisibaru = etdivisi.getText().toString();
                    String jabatanbaru = etjabatan.getText().toString();
                    String telpbaru = ettelp.getText().toString();

                    if (!karyawan.getNama().equals(namabaru) || !karyawan.getDivisi().equals(divisibaru) || !karyawan.getJabatan().equals(jabatanbaru) || !karyawan.getTelp().equals(telpbaru)) {
                        karyawan.setNama(namabaru);
                        karyawan.setDivisi(divisibaru);
                        karyawan.setJabatan(jabatanbaru);
                        karyawan.setTelp(telpbaru);
                        updateKaryawan(karyawan);
                    } else {
                        Toast.makeText(MainActivity.this, "Tidak ada yang diubah!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onDataClick(Karyawan k, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogUpdate(k);
            }
        });

        builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBSourceData datasource = new DBSourceData(MainActivity.this);
                datasource.open();
                datasource.deleteKaryawan(k.get_id());
                listkaryawan.clear();
                listkaryawan.addAll(datasource.getAllKaryawan());
                adapterkaryawan.notifyDataSetChanged();
                datasource.close();
            }
        });

        builder.setNeutralButton("Batal", null);

        Dialog dialog = builder.create();
        dialog.show();
    }
}
