package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class CitasAdminActivity extends AppCompatActivity {

    RecyclerView rvcitas;
    ArrayList<Cita> listacitas;
    Cita cita;
    Usuario usuario;
    Button btnvolverac,btncitashoy;
    TextView textView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_admin);
        rvcitas = findViewById(R.id.rvcitas);
        btnvolverac = findViewById(R.id.btnvolverac);
        btncitashoy = findViewById(R.id.btncitashoyac);
        rvcitas.setLayoutManager(new LinearLayoutManager(this));
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        textView = findViewById(R.id.textView);

        btnvolverac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CitasAdminActivity.this, TabActivity.class);
                i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(i, 0);
            }
        });

        btncitashoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarcitas();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestQueue = Volley.newRequestQueue(this);
        btncitashoy.setText("");
        listarcitas();
    }

    public void listarcitas() {
        listacitas = new ArrayList<Cita>();
        if (btncitashoy.getText().toString().toLowerCase().equals("ver citas de hoy")) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.UrlCitas + "gettoday.php", new Response.Listener<String>() {
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
                    Toast.makeText(CitasAdminActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
            btncitashoy.setText("ver todas las citas");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.UrlCitas + "getall.php", new Response.Listener<String>() {
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
                    Toast.makeText(CitasAdminActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
            btncitashoy.setText("ver citas de hoy");
        }
    }
}