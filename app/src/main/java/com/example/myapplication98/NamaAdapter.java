package com.example.myapplication98;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class NamaAdapter extends RecyclerView.Adapter<NamaAdapter.ViewHolder> {
    private ArrayList<Nama> namalist = new ArrayList<>();
    public void adapter( ArrayList<Nama> agendalist) {
        namalist.clear();
        namalist.addAll(agendalist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NamaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nama,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final NamaAdapter.ViewHolder holder, int position) {
        Nama nama = namalist.get(position);
        holder.tvNama.setText(nama.getNama());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/loginregister/public/data_file/" + nama.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivAgenda);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama nama = namalist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(),Detail.class);
                intent.putExtra(Detail.EXTRA_DETAIL,nama);
                holder.itemView.getContext().startActivity(intent);




            }
        });
    }

    @Override
    public int getItemCount() {
        return namalist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama;
        ImageView ivAgenda;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            ivAgenda = itemView.findViewById(R.id.ivAgenda);

        }

    }

}
