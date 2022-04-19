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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CitaSeleccionadaActivity extends AppCompatActivity {
    Cita cita;
    Usuario usuario;
    TextView tvusuario,tvarea,tvfecha;
    Button btneditar,btneliminar,btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_seleccionada);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        tvusuario = findViewById(R.id.tvusuario);
        tvarea = findViewById(R.id.tvarea);
        tvfecha = findViewById(R.id.tvfecha);
        btneditar = findViewById(R.id.btneditarcita);
        btnvolver = findViewById(R.id.btnvolverlogin);
        btneliminar = findViewById(R.id.btneliminarcita);

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent = new Intent(CitaSeleccionadaActivity.this, EditarCitaActivity.class);
                editintent.putExtra("cita",cita);
                editintent.putExtra("usuario",usuario);
                startActivity(editintent);
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CitaSeleccionadaActivity.this, TabActivity.class);
                i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(i, 0);
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cita = (Cita) getIntent().getSerializableExtra("cita");
        tvusuario.setText("Nombre: "+cita.getUsuario());
        tvarea.setText("Area: "+cita.getArea());
        tvfecha.setText("Fecha: "+cita.getFecha());
    }


    public void DeleteData() {
        String UrlDelete = Urls.UrlCitas+"delete.php";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desea eliminar");
        builder.setMessage("Esta seguro que desea eliminar la cita: "+ tvarea.getText() +"-" + tvfecha.getText());
        builder.setPositiveButton("Eliminar",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, UrlDelete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CitaSeleccionadaActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
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
                        params.put("id",String.valueOf(cita.getId()));
                        return params;
                    }
                };
                Volley.newRequestQueue(CitaSeleccionadaActivity.this).add(stringRequest);
                Intent i = new Intent(CitaSeleccionadaActivity.this, TabActivity.class);
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