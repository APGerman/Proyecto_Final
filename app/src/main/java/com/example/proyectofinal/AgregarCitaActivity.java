package com.example.proyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgregarCitaActivity extends AppCompatActivity {
    Usuario usuario;
    Area area;
    ArrayList<String> listareas;
    Spinner spareanc;
    EditText etfechanc;
    Button btnguardarnc,btnvolvernc;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cita);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        spareanc = findViewById(R.id.spareanc);
        etfechanc = findViewById(R.id.etfechanc);
        btnguardarnc = findViewById(R.id.btnguardarnc);
        btnvolvernc = findViewById(R.id.btnvolvernc);
        getareas();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etfechanc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarCitaActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month,day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
                return true;
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                etfechanc.setText(year+"-"+month+"-"+day);
            }
        };

        btnvolvernc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarCitaActivity.this, TabActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(i, 0);
            }
        });

        btnguardarnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

    }

    public void getareas(){
        String urlareas = Urls.UrlAreas+"getall.php";
        listareas = new ArrayList<String>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlareas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        area = new Area(object.getInt("id"),object.getString("area"));
                        listareas.add(area.getArea());
                    }

                } catch (Exception e) {

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarCitaActivity.this, android.R.layout.simple_spinner_item, listareas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spareanc.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgregarCitaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        Volley.newRequestQueue(AgregarCitaActivity.this).add(stringRequest);
    }

    public void guardar(){
        if (etfechanc.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Todos los campos son obligatorios");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            String area = spareanc.getSelectedItem().toString();
            String fecha = etfechanc.getText().toString();
            String UrlSave = Urls.UrlCitas+"save.php";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, UrlSave, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AgregarCitaActivity.this, "Nueva cita guardada", Toast.LENGTH_SHORT).show();
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
                    params.put("usuario",usuario.getNombre());
                    params.put("area",area);
                    params.put("fecha",fecha);
                    params.put("email",usuario.getEmail());
                    return params;
                }
            };
            Volley.newRequestQueue(AgregarCitaActivity.this).add(stringRequest);
            Intent i = new Intent(AgregarCitaActivity.this, TabActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(i, 0);
        }
    }
}