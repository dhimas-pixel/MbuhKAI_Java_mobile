package com.stephanusdhimas.UAS_MbuhKAI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private List<BookingHelper> hList;
    Context context;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("booking");

    public RecycleAdapter(List<BookingHelper> hList, Activity activity) {
        this.activity = activity;
        this.hList = hList;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingHelper model = hList.get(position);
        holder.tv_asal.setText("Asal : " + model.getAsal() + " - " + "Tujuan : " + model.getTujuan());
        holder.tv_tanggal.setText("Tanggal : " + model.getTanggal() + " - " + "Waktu : " + model.getWaktu());
        holder.tv_dewasa.setText("Jumlah Dewasa : " + model.getDewasa());
        holder.tv_anak.setText("Jumlah Anak : " + model.getAnak());
        holder.tv_total.setText("Total Harga : " + model.getTotal());

//        Delete
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        database.child(model.getNama()).child(model.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).setMessage("Apakah yakin mau dihapus?");
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_asal;
        TextView tv_tanggal;
        TextView tv_dewasa;
        TextView tv_anak;
        TextView tv_total;

        CardView cardView;
        ImageView hapus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_asal = itemView.findViewById(R.id.tv_asal);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_dewasa = itemView.findViewById(R.id.tv_dewasa);
            tv_anak = itemView.findViewById(R.id.tv_anak);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.card_view);
            hapus = itemView.findViewById(R.id.hapus);
        }
    }
}
