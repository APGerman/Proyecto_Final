package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabCitas extends Fragment {
    View pestania;
    RecyclerView rvcitas;
    ArrayList<Cita> listacitas;
    Cita cita;
    Usuario usuario;
    Button btnagregarcitas,btncitashoy;
    TextView textView;
    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pestania= inflater.inflate(R.layout.activity_tab_citas,container,false);
        rvcitas = pestania.findViewById(R.id.rvcitas);
        btnagregarcitas = pestania.findViewById(R.id.btnagregarcita);
        btncitashoy = pestania.findViewById(R.id.btncitashoy);
        rvcitas.setLayoutManager(new LinearLayoutManager(pestania.getContext()));
        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");
        textView = pestania.findViewById(R.id.textView);

        btnagregarcitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pestania.getContext(), AgregarCitaActivity.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        btncitashoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarcitas();
            }
        });


        return pestania;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestQueue = Volley.newRequestQueue(pestania.getContext());
        btncitashoy.setText("");
        listarcitas();
    }

    public void listarcitas() {
        listacitas = new ArrayList<Cita>();
        if (btncitashoy.getText().toString().toLowerCase().equals("ver citas de hoy")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.UrlCitas+"gettodayuser.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            cita = new Cita(object.getInt("id"), object.getString("usuario"), object.getString("fecha"), object.getString("area"), object.getString("email"));
                            listacitas.add(cita);
                        }
                    } catch (Exception e) {
                    }
                    Adaptador adap = new Adaptador(listacitas, usuario);
                    rvcitas.setAdapter(adap);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(pestania.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> Parametos = new HashMap<String, String>();
                    Parametos.put("usuario", usuario.getNombre());
                    Parametos.put("email", usuario.getEmail());
                    return Parametos;
                }
            };

            requestQueue.add(stringRequest);
            btncitashoy.setText("ver todas mis citas");
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.UrlCitas+"getalluser.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            cita = new Cita(object.getInt("id"), object.getString("usuario"), object.getString("fecha"), object.getString("area"), object.getString("email"));
                            listacitas.add(cita);
                        }
                    } catch (Exception e) {
                    }
                    Adaptador adap = new Adaptador(listacitas, usuario);
                    rvcitas.setAdapter(adap);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(pestania.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> Parametos = new HashMap<String, String>();
                    Parametos.put("usuario", usuario.getNombre());
                    Parametos.put("email", usuario.getEmail());
                    return Parametos;
                }
            };
            requestQueue.add(stringRequest);
            btncitashoy.setText("ver citas de hoy");
        }


    }
}