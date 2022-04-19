package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etmail, etpass;
    Button btnlogin, btnregistrarse;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etmail = findViewById(R.id.etmail);
        etpass = findViewById(R.id.etpass);
        btnlogin = findViewById(R.id.btnlogin);
        btnregistrarse = findViewById(R.id.btnregistrarse);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validacion(Urls.UrlUsuario+"login.php");
            }
        });

        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etmail.getText().toString().equals("2019-8294") && etpass.getText().toString().equals("ITLA")){
                    Intent regisintent = new Intent(MainActivity.this, RegistrarAdminActivity.class);
                    startActivity(regisintent);
                }
                else {
                    Intent regisintent = new Intent(MainActivity.this, RegistrarActivity.class);
                    startActivity(regisintent);
                }
            }
        });

    }

    private void validacion(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("no esta registrado")){
                    Toast.makeText(MainActivity.this,"email o contraseña incorrecto",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            usuario = new Usuario(object.getInt("id"),object.getString("nombre"),object.getString("email"),object.getString("contrasenia"), object.getString("fechanacimiento"),object.getString("tipo"));
                        }
                        Toast.makeText(MainActivity.this,"Inicio de sesion exitoso",Toast.LENGTH_LONG).show();
                        Intent tabintent = new Intent(MainActivity.this, TabActivity.class);
                        tabintent.putExtra("usuario",usuario);
                        startActivity(tabintent);
                    } catch (Exception e) {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> Parametos= new HashMap<String,String>();
                Parametos.put("email",etmail.getText().toString());
                Parametos.put("contrasenia",etpass.getText().toString());
                return Parametos;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }
}

