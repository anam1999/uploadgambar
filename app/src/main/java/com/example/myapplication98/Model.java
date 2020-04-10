package com.example.myapplication98;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Model extends ViewModel {
    private MutableLiveData<ArrayList<Nama>> nama = new MutableLiveData<>();
    public void simpan (RequestQueue queue, final Context context){
        final ArrayList<Nama> namaArrayList= new ArrayList<>();
        String url = "http://192.168.43.229/loginregister/public/api/upload";
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i =0; i <data.length(); i++){
                        JSONObject objek =data.getJSONObject(i);
                        int id = objek.getInt("id");
                               String title = objek.getString("nama");
                                String image = objek.getString("file");
                                Nama nama = new Nama(id, title,image);
                                namaArrayList.add(nama);

                    }
                    nama.postValue(namaArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(context, "berhasil" , Toast.LENGTH_LONG).show();
//                        try {
//                            JSONArray productArr = new JSONArray(response);
//
//                            for (int i=0; i < productArr.length(); i++){
//                                JSONObject productObj = productArr.getJSONObject(i);
//                                int id = productObj.getInt("id");
//                                String title = productObj.getString("nama");
//                                String image = productObj.getString("file");
//                                Nama nama = new Nama(id, title,image);
//                                namaArrayList.add(nama);
//
//                            }
//                         nama.postValue(namaArrayList);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(context, error.toString() , Toast.LENGTH_LONG).show();
//
//            }
//        });
        queue.add(request);
    }
    public LiveData<ArrayList<Nama>> Ambil(){return nama;}

}
