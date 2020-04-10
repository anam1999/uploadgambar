package com.example.myapplication98;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

 public class TambahHadiah extends AppCompatActivity implements View.OnClickListener {
     TextView tvNama,tvdeskripsi,poin;
     Button btnSimpan;
     ImageView ivPhoto;
     Uri UriPhoto;
     Bitmap BitPhoto;
     String StringImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hadiah);

        tvNama = findViewById(R.id.tv_nama_hadiah);
        poin=findViewById(R.id.tv_poin);
        tvdeskripsi=findViewById(R.id.tv_deskripsi);
        btnSimpan = findViewById(R.id.btn_simpan);
        ivPhoto = findViewById(R.id.iv_photo);
        btnSimpan.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
    }
     public void onClick(View v) {
         switch (v.getId()){

             case R.id.btn_simpan:
                 sendData();
                 sendToMain();
                 if (tvNama.getText().toString().length()==0) {
                     tvNama.setError("Harap diisi terlebih dahulu");
                 }
                 else{
                     Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                 }
                 break;

             case R.id.iv_photo:
                 pickImage();
                 break;

         }
     }
     private void pickImage() {
         CropImage.activity()
                 .setGuidelines(CropImageView.Guidelines.ON)
                 .setAspectRatio(4,3)
                 .start(TambahHadiah.this);

     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data){
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
             CropImage.ActivityResult result = CropImage.getActivityResult(data);
             if (resultCode == RESULT_OK){
                 UriPhoto = result.getUri();
                 if (UriPhoto != null){

                     try {
                         InputStream inputStream = getContentResolver().openInputStream(UriPhoto);
                         BitPhoto = BitmapFactory.decodeStream(inputStream);
                         if (BitPhoto!=null){

                             StringImage = imgToString(BitPhoto);
                         }


                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                 }

                 ivPhoto.setImageURI(UriPhoto);

             }
             else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                 Exception error = result.getError();
             }
         }

     }

     private void sendToMain() {
         Intent intent = new Intent(this, LihatHadiah.class);
         startActivity(intent);
         finish();
     }

     private void sendData() {
         StringRequest srSendData = new StringRequest(Request.Method.POST, "http://192.168.43.229/belajarapi/public/api/tambahhadiah", new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 Toast.makeText(TambahHadiah.this, response, Toast.LENGTH_LONG).show();

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
//                 Toast.makeText(TambahHadiah.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
             }
         }){
             @Override
             protected Map<String, String> getParams() {
                 Map<String, String> map = new HashMap<>();
                 map.put("nama_hadiah", tvNama.getText().toString());
                 map.put("deskripsi", tvdeskripsi.getText().toString());
                 map.put("point_id", poin.getText().toString());
                 if(StringImage!=null){
                     map.put("file",StringImage);
                 }
                 return map;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(TambahHadiah.this);
         requestQueue.add(srSendData);
     }

     private String imgToString(Bitmap bitmap){
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

         String encodeImage=null;
         if (bitmap!=null){
             byte[] imageByte = outputStream.toByteArray();
             encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
         }


         return encodeImage;
     }
 }

