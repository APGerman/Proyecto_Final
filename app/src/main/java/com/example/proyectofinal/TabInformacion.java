package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TabInformacion extends Fragment {

    View pestania;
    LinearLayout ll1,ll2,ll3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pestania= inflater.inflate(R.layout.activity_tab_informacion,container,false);
        ll1 = pestania.findViewById(R.id.ll1);
        ll2 = pestania.findViewById(R.id.ll2);
        ll3 = pestania.findViewById(R.id.ll3);

        ll1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(pestania.getContext(), Covid19Activity.class);
                startActivity(i);
                return false;
            }
        });
        ll2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(pestania.getContext(), DengueActivity.class);
                startActivity(i);
                return false;
            }
        });
        ll3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(pestania.getContext(), VihActivity.class);
                startActivity(i);
                return false;
            }
        });

        return pestania;
    }
}