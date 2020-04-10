package com.example.myapplication98;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> Listnama  = new ArrayList<>();
        final TextView textView1 =findViewById(R.id.nama1);
        final TextView textView2 =findViewById(R.id.nama2);
        final TextView textView3 =findViewById(R.id.nama3);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.43.229/belajarapi/public/api/delok";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for( int i=0; i < response.length();i++){
                        JSONObject data = response.getJSONObject(i);
                        String nama = data.getString("nama");
                        Listnama.add(nama);

                    }
                    textView1.setText(Listnama.get(0));
                    textView2.setText(Listnama.get(1));
                    textView3.setText(Listnama.get(2));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(arrayRequest);

        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.nama4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                String url ="http://192.168.43.229/belajarapi/public/api/nambah";
                StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(),"Berhasil", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("nama", editText.getText().toString());
                        return MyData;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });

    }
    }

