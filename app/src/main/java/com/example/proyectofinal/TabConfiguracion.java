package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.toolbox.Volley;

public class TabConfiguracion extends Fragment {

    Usuario usuario;
   View pestania;
   Button btnadarea, btnadcita,btncerrars;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pestania= inflater.inflate(R.layout.activity_tab_configuracion,container,false);
        btnadarea = pestania.findViewById(R.id.btnadarea);
        btnadcita = pestania.findViewById(R.id.btnadcitas);
        btncerrars = pestania.findViewById(R.id.btncerrars);

        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        if(usuario.getTipo().equals("admin")){
            btnadcita.setVisibility(View.VISIBLE);
            btnadarea.setVisibility(View.VISIBLE);
        }

        btnadarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pestania.getContext(), AreasActivity.class);
                startActivity(i);
            }
        });

        btnadcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabintent = new Intent(pestania.getContext(), CitasAdminActivity.class);
                tabintent.putExtra("usuario",usuario);
                startActivity(tabintent);
            }
        });

        btncerrars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pestania.getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return pestania;
    }
}