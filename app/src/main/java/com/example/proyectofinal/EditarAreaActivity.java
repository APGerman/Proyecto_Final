package com.example.proyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditarAreaActivity extends AppCompatActivity {

    EditText etareaeditar;
    Button btneditarea, btnvolverea;
    Area area;
    RequestQueue requestQueue;
    String areatexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_area);

        area = (Area) getIntent().getSerializableExtra("area");
        etareaeditar = findViewById(R.id.etareaeditar);
        btneditarea = findViewById(R.id.btneditarea);
        btnvolverea = findViewById(R.id.btnvolverea);
        etareaeditar.setText(area.getArea());
        areatexto = area.getArea();
        btnvolverea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btneditarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });

    }


    public void editar(){
        requestQueue = Volley.newRequestQueue(this);
        if (etareaeditar.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Todos los campos son obligatorios");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            String areas = etareaeditar.getText().toString();
            String UrlEdit = Urls.UrlAreas+"edit.php";
            String UrlEdit2 = Urls.UrlCitas+"edita.php";


            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, UrlEdit, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(EditarAreaActivity.this, "Area Actualizada", Toast.LENGTH_SHORT).show();
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
                    params.put("area",areas);
                    return params;
                }
            };
            requestQueue.add(stringRequest);

            requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest2 = new StringRequest(
                    Request.Method.POST, UrlEdit2, new Response.Listener<String>() {
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
                    params.put("newarea",areas);
                    return params;
                }
            };
            requestQueue.add(stringRequest2);
            area.setArea(areas);
            Intent i = new Intent(EditarAreaActivity.this, AreaSeleccionadaActivity.class);
            i.putExtra("area",area);
            startActivity(i);
        }
    }

}