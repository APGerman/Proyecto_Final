package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Covid19Activity extends AppCompatActivity {

    VideoView videocovid;
    Button btnvolvercovid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19);

        videocovid = findViewById(R.id.videocovid);
        btnvolvercovid = findViewById(R.id.btnvolvercovid);
        String pth = "android.resource://"+getPackageName()+"/"+R.raw.covid;
        Uri uri = Uri.parse(pth);
        videocovid.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videocovid.setMediaController(mediaController);
        videocovid.start();
        mediaController.setAnchorView(videocovid);

        btnvolvercovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}