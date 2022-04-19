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

public class EditarCitaActivity extends AppCompatActivity {
    Usuario usuario;
    Cita cita;
    Area area;
    ArrayList<String> listareas;
    Spinner spareaec;
    EditText etfechaec;
    Button btneditarec,btnvolverec;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cita);
        cita = (Cita) getIntent().getSerializableExtra("cita");
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        spareaec = findViewById(R.id.spareaec);
        etfechaec = findViewById(R.id.etfechaec);
        btneditarec = findViewById(R.id.btneditarec);
        btnvolverec = findViewById(R.id.btnvolverec);

        getareas();
        etfechaec.setText(cita.getFecha());

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etfechaec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditarCitaActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month,day);
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
                etfechaec.setText(year+"-"+month+"-"+day);
            }
        };

        btnvolverec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btneditarec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditarCitaActivity.this, android.R.layout.simple_spinner_item, listareas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spareaec.setAdapter(adapter);
                for(int i= 0; i < spareaec.getAdapter().getCount(); i++)
                {
                    if(spareaec.getAdapter().getItem(i).toString().contains(cita.getArea()))
                    {
                        spareaec.setSelection(i);
                        break;
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarCitaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        Volley.newRequestQueue(EditarCitaActivity.this).add(stringRequest);
    }

    public void editar(){
        if (etfechaec.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Todos los campos son obligatorios");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            String area = spareaec.getSelectedItem().toString();
            String fecha = etfechaec.getText().toString();
            String UrlEdit = Urls.UrlCitas+"edit.php";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, UrlEdit, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(EditarCitaActivity.this, "Cita Actualizada", Toast.LENGTH_SHORT).show();
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
                    params.put("area",area);
                    params.put("fecha",fecha);
                    return params;
                }
            };
            Volley.newRequestQueue(EditarCitaActivity.this).add(stringRequest);
            cita.setFecha(fecha);
            cita.setArea(area);
            Intent i = new Intent(EditarCitaActivity.this, CitaSeleccionadaActivity.class);
            i.putExtra("cita",cita);
            i.putExtra("usuario",usuario);
            startActivity(i);
        }
    }
}