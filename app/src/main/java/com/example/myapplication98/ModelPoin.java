package com.example.myapplication98;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelPoin   extends ViewModel {
    private MutableLiveData<ArrayList<Poin>> poin = new MutableLiveData<>();

    public void simpan(RequestQueue queue, final Context context) {
        final ArrayList<Poin> poinArrayList = new ArrayList<>();
        String url = "http://192.168.43.229/belajarapi/public/api/point";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("upload");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject objek = data.getJSONObject(i);
                        int id = objek.getInt("id");
                        String title = objek.getString("nama");
                        String poins = objek.getString("point");

                        Poin poin = new Poin(id, title, poins);
                        poinArrayList.add(poin);

                    }
                    poin.postValue(poinArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    public LiveData<ArrayList<Poin>> Ambil() {
        return poin;
    }


}
