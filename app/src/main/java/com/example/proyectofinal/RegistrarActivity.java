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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity {

    EditText etnombre, etmail, etcontrasenia, etfechanacimiento;
    Button btnregistrar, btnvolver;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private final String Url =  Urls.UrlUsuario+"registrarse.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        etnombre = findViewById(R.id.etnombre);
        etmail = findViewById(R.id.etmail);
        etcontrasenia = findViewById(R.id.etcontrasenia);
        etfechanacimiento = findViewById(R.id.etfechanacimiento);
        btnregistrar = findViewById(R.id.btnregistrarse);
        btnvolver = findViewById(R.id.btnvolverlogin);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etfechanacimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month,day);
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
                etfechanacimiento.setText(year+"-"+month+"-"+day);
            }
        };

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrarse(Url);
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Registrarse(String url){
        if (etnombre.getText().toString().isEmpty() || etmail.getText().toString().isEmpty() || etcontrasenia.getText().toString().isEmpty() || etfechanacimiento.getText().toString().isEmpty())  {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Todos los campos son obligatorios");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            if (ValidarEmail(etmail.getText().toString().trim())) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegistrarActivity.this, response, Toast.LENGTH_SHORT).show();
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
                        params.put("nombre", etnombre.getText().toString());
                        params.put("email", etmail.getText().toString().trim());
                        params.put("contrasenia", etcontrasenia.getText().toString());
                        params.put("fechanacimiento", etfechanacimiento.getText().toString());
                        params.put("tipo", "paciente");
                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(stringRequest);
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("El email no es valido");
                builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }
    }

    private boolean ValidarEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}