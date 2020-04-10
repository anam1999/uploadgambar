package com.example.myapplication98;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class lihatvideo extends AppCompatActivity {
VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatvideo);
        video =findViewById(R.id.videoView);

        String url ="http://192.168.43.229/belajarapi/public/video/asik.mp4";
        video.setVideoURI(Uri.parse(url));
        video.start();

    }
}
