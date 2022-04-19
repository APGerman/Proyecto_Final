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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AgregarAreaActivity extends AppCompatActivity {

    EditText etareanueva;
    Button btnguardaraa, btnvolveraa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_area);

        etareanueva = findViewById(R.id.etareanueva);
        btnguardaraa = findViewById(R.id.btnguardaraa);
        btnvolveraa = findViewById(R.id.btnvolveraa);

        btnguardaraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        btnvolveraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarAreaActivity.this, AreasActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(i, 0);
            }
        });

    }

    public void guardar() {
        if (etareanueva.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Todos los campos son obligatorios");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            String area = etareanueva.getText().toString();
            String UrlSave = Urls.UrlAreas + "save.php";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, UrlSave, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AgregarAreaActivity.this, "Nueva area guardada", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("area", area);
                    return params;
                }
            };
            Volley.newRequestQueue(AgregarAreaActivity.this).add(stringRequest);
            Intent i = new Intent(AgregarAreaActivity.this, AreasActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(i, 0);
        }
    }
}