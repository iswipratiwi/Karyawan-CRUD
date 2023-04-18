package com.example.karyawancrudapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder> {

    public ArrayList<Karyawan> dataList;
    private onDataListener ondatalistener;

    public KaryawanAdapter(ArrayList<Karyawan> dataList, onDataListener karyawanListener){
        this.dataList = dataList;
        this.ondatalistener = karyawanListener;
    }

    @NonNull
    @Override
    public KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_karyawan, parent, false);
        return new KaryawanViewHolder(view, ondatalistener);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanViewHolder holder, int position) {
        Karyawan karyawan = dataList.get(holder.getAdapterPosition());

        holder.cvdata.setCardBackgroundColor(Integer.parseInt("#DC5890"));
        holder.tvnama.setText("Nama\t\t\t\t\t\t:\t\t" + karyawan.getNama());
        holder.tvdivisi.setText("Divisi\t\t\t\t:\t\t" + karyawan.getDivisi());
        holder.tvjabatan.setText("Jabatan\t\t\t:\t\t" + karyawan.getJabatan());
        holder.tvtelp.setText("No. Telp\t\t\t\t:\t\t" + karyawan.getTelp());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

     class KaryawanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cvdata;
        private TextView tvnama, tvdivisi, tvjabatan, tvtelp;
        onDataListener karyawanListener;
        public KaryawanViewHolder(@NonNull View itemView, onDataListener karyawanListener) {
            super(itemView);
            cvdata = itemView.findViewById(R.id.cvData);
            tvnama = itemView.findViewById(R.id.tvNama);
            tvdivisi = itemView.findViewById(R.id.tvDivisi);
            tvjabatan = itemView.findViewById(R.id.tvJabatan);
            tvtelp = itemView.findViewById(R.id.tvTelp);
            this.karyawanListener = karyawanListener;
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {
             karyawanListener.onDataClick(dataList.get(getAdapterPosition()), getAdapterPosition());
         }
     }

    public interface onDataListener{
        void onDataClick(Karyawan karyawan, int position);
    }
}
