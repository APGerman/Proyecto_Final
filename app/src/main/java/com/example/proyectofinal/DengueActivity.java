package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class DengueActivity extends AppCompatActivity {

    VideoView videodengue;
    Button btnvolverdengue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dengue);

        videodengue = findViewById(R.id.videodengue);
        btnvolverdengue = findViewById(R.id.btnvolverdengue);
        String pth = "android.resource://"+getPackageName()+"/"+R.raw.denguev;
        Uri uri = Uri.parse(pth);
        videodengue.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videodengue.setMediaController(mediaController);
        videodengue.start();
        mediaController.setAnchorView(videodengue);

        btnvolverdengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}