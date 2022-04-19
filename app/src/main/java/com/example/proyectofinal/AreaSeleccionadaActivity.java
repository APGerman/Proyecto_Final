package com.example.proyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

public class AreaSeleccionadaActivity extends AppCompatActivity {

    Area area;
    TextView tvnombrearea;
    Button btneditaras,btneliminaras,btnvolveras;
    RequestQueue requestQueue;
    String areatexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_seleccionada);
        tvnombrearea = findViewById(R.id.tvnombrearea);
        btneditaras = findViewById(R.id.btneditaras);
        btneliminaras = findViewById(R.id.btneliminaras);
        btnvolveras = findViewById(R.id.btnvolveras);

        btneditaras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent = new Intent(AreaSeleccionadaActivity.this, EditarAreaActivity.class);
                editintent.putExtra("area",area);
                startActivity(editintent);            }
        });

        btnvolveras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AreaSeleccionadaActivity.this, AreasActivity.class);
                i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(i, 0);
            }
        });

        btneliminaras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestQueue = Volley.newRequestQueue(this);
        area = (Area) getIntent().getSerializableExtra("area");
        tvnombrearea.setText("Nombre: "+area.getArea());
        areatexto = area.getArea();
    }

    public void DeleteData(){
        requestQueue = Volley.newRequestQueue(this);
        String UrlDelete = Urls.UrlAreas+"delete.php";
        String UrlDelete2 = Urls.UrlCitas+"deletea.php";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desea eliminar");
        builder.setMessage("Esta seguro que desea eliminar el area: "+ tvnombrearea.getText() + ". Esto tambien borrara cualquier cita que contenga dicha area;");
        builder.setPositiveButton("Eliminar",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, UrlDelete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AreaSeleccionadaActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id",String.valueOf(area.getId()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

                StringRequest stringRequest2 = new StringRequest(
                        Request.Method.POST, UrlDelete2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("area",areatexto);
                        return params;
                    }
                };
                requestQueue.add(stringRequest2);
                Intent i = new Intent(AreaSeleccionadaActivity.this, AreasActivity.class);
                i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(i, 0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}