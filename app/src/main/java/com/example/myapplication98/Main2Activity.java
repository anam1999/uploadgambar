package com.example.myapplication98;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    Button btnTambah;
    RecyclerView rvNama;
NamaAdapter namaAdapter;
    ArrayList<Nama> namaArrayList;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        namaArrayList = new ArrayList<>();
        btnTambah = findViewById(R.id.btn_tambah);
        rvNama = findViewById(R.id.rv_Nama);
        btnTambah.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        rvNama.setLayoutManager(new LinearLayoutManager(this));
        namaAdapter = new NamaAdapter();
        Model model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(Model.class);
        model.simpan(queue,this);
        model.Ambil().observe(this, new Observer<ArrayList<Nama>>() {
            @Override
            public void onChanged(ArrayList<Nama> namas) {
                namaAdapter.adapter(namas);
            }
        });
        rvNama.setHasFixedSize(true);
        rvNama.setAdapter(namaAdapter);
        namaAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tambah:
                sendToTambah();
        }
    }

    private void sendToTambah() {
        Intent intent = new Intent(this, TambahNama.class);
        startActivity(intent);
        finish();
    }

}

