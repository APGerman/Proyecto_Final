package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VihActivity extends AppCompatActivity {

    VideoView videovih;
    Button btnvolvervih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vih);
        videovih = findViewById(R.id.videovih);
        btnvolvervih = findViewById(R.id.btnvolvervih);
        String pth = "android.resource://"+getPackageName()+"/"+R.raw.vihv;
        Uri uri = Uri.parse(pth);
        videovih.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videovih.setMediaController(mediaController);
        videovih.start();
        mediaController.setAnchorView(videovih);

        btnvolvervih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}