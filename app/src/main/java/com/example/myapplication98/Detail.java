package com.example.myapplication98;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Detail extends AppCompatActivity {
public static final String EXTRA_DETAIL ="detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        TextView rvNama = findViewById(R.id.tvNama);
        ImageView ivAgenda = findViewById(R.id.ivAgenda);
        Nama nama = getIntent().getParcelableExtra(EXTRA_DETAIL);

        rvNama.setText(nama.getNama());
        Glide.with(this)
                .load( "http://192.168.43.229/loginregister/public/data_file/" + nama.getGambar())
                .apply(new RequestOptions().centerCrop())
                .into(ivAgenda);



    }
}
