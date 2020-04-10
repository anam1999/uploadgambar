package com.example.myapplication98;

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

public class HadiahAdapter extends RecyclerView.Adapter<HadiahAdapter.ViewHolder> {
    private ArrayList<Hadiah> hadiahlist = new ArrayList<>();
    public void adapter( ArrayList<Hadiah> hadiahslist) {
        hadiahlist.clear();
        hadiahlist.addAll(hadiahslist);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HadiahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hadiah,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final HadiahAdapter.ViewHolder holder, int position) {
        Hadiah hadiah  = hadiahlist.get(position);
        holder.tvHadiah.setText(hadiah.getNama_hadiah());
        holder.tvdeskripsi.setText(hadiah.getDeskripsi());
        holder.poin.setText(hadiah.getPoin());
//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

        Glide.with(holder.itemView.getContext())
                .load( "http://192.168.43.229/belajarapi/public/hadiah/" + hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivHadiah);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hadiah hadiah= hadiahlist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(),PenukaranHadiah.class);
                intent.putExtra(PenukaranHadiah.EXTRA_DETAILs,hadiah);
                holder.itemView.getContext().startActivity(intent);
}
        });
    }

    @Override
    public int getItemCount() {
        return hadiahlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHadiah,tvdeskripsi,poin;
        ImageView ivHadiah;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
           poin= itemView.findViewById(R.id.TotalPoin);
            tvHadiah = itemView.findViewById(R.id.tvNamaHadiah);
            tvdeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            ivHadiah = itemView.findViewById(R.id.ivHadiah);

        }

    }
}

