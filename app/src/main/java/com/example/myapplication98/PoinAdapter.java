package com.example.myapplication98;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PoinAdapter extends RecyclerView.Adapter<PoinAdapter.ViewHolder> {
private ArrayList<Poin> poinlist = new ArrayList<>();
public void adapter( ArrayList<Poin> poinslist) {
        poinlist.clear();
        poinlist.addAll(poinslist);
        notifyDataSetChanged();
        }

@NonNull
@Override
public PoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hadiah,parent,false);

        return new ViewHolder(view);

        }

@Override
public void onBindViewHolder(@NonNull final PoinAdapter.ViewHolder holder, int position) {
        Poin poin  = poinlist.get(position);
        holder.poin.setText(poin.getTotalpoin());

//        holder.ivNotebook.setImageDrawable(myContext.getResources().getDrawable(product.getImage()));

//        Glide.with(holder.itemView.getContext())
//        .load( "http://192.168.43.229/belajarapi/public/hadiah/" + hadiah.getGambar())
//        .apply(new RequestOptions().centerCrop())
//        .into(holder.ivHadiah);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Poin poin= poinlist.get(holder.getAdapterPosition());
        Intent intent = new Intent(holder.itemView.getContext(),PenukaranHadiah.class);
        intent.putExtra(PenukaranHadiah.EXTRA_DETAILs,poin);
        holder.itemView.getContext().startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return poinlist.size();
        }


public class ViewHolder extends RecyclerView.ViewHolder {

    TextView tvHadiah,tvdeskripsi,poin;
    ImageView ivHadiah;


    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        poin= itemView.findViewById(R.id.poinsaya);

    }

}
}

