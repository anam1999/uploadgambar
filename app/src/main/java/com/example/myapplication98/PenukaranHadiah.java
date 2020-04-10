package com.example.myapplication98;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PenukaranHadiah extends AppCompatActivity {
    public static final String EXTRA_DETAILs ="penukaranhadiah";
    TextView poin1,poinsaya;
    HitungTukarHadiah hitungTukarHadiah =new HitungTukarHadiah();
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penukaran_hadiah);

        TextView rvHadiah = findViewById(R.id.tvNamaHadiah);
        TextView rvDeskripsi = findViewById(R.id.tvDeskripsi);
        TextView rvpoin = findViewById(R.id.TotalPoin);
        final TextView isi = findViewById(R.id.status);
        TextView tukarkan = findViewById(R.id.tukarkan);
        ImageView ivHadiah = findViewById(R.id.ivHadiah);
        poin1=findViewById(R.id.TotalPoin);
        poinsaya=findViewById(R.id.poinsaya);
        final Hadiah hadiah = getIntent().getParcelableExtra(EXTRA_DETAILs);

        rvHadiah.setText(hadiah.getNama_hadiah());
        rvDeskripsi.setText(hadiah.getDeskripsi());
        rvpoin.setText(hadiah.getPoin());
        poinsaya.setText(String.valueOf(hadiah.getPoin()));


        Glide.with(this)
                .load( "http://192.168.43.229/belajarapi/public/hadiah/" +hadiah.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivHadiah);

        tukarkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ini manggil manggil method nya hadia1, trus yg dikirim itu poin sama hadiah 1
                isi.setText(hitungTukarHadiah.hadiah1(ModelpPoinHadiah.poin, ModelpPoinHadiah.hargaHadiah1));
//                poinsaya.setText(String.valueOf(ModelpPoinHadiah.poin));
                poinsaya.setText(String.valueOf(hadiah.getPoin()));
            }
        });


    }
    public void kurang (View view){//perintah tombol tambah
        if(quantity==1){
            Toast.makeText(this,"pesanan minimal 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity-1 ;
        display(quantity);
    }
    public void nambah (View view){//perintah tombol tambah
        if(quantity==10){
            Toast.makeText(this,"pesanan maksimal 10",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1 ;
        display(quantity);
    }
    private void display(int number) {
        TextView nilai = (TextView) findViewById(R.id.nilai);
        nilai.setText("" + number);
    }

//    private int totalharga(){
//        return quantity*totalharga();
//    }

    }

