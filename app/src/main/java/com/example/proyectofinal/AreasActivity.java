package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class AreasActivity extends AppCompatActivity {

    ListView lvareas;
    Button btnvolverarea,btnagregararea;
    ArrayList<String> listareas;
    ArrayList<Integer> listaid;
    RequestQueue requestQueue;
    Area area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        lvareas = findViewById(R.id.lvarea);
        btnvolverarea = findViewById(R.id.btnvolverarea);
        btnagregararea = findViewById(R.id.btnagregararea);

        lvareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String elegido = lvareas.getItemAtPosition(position).toString();
                int IdElegido = listaid.get(position);
                area = new Area(IdElegido,elegido);
                Intent i = new Intent(view.getContext(), AreaSeleccionadaActivity.class);
                i.putExtra("area", area);
                startActivity(i);
            }
        });

        btnvolverarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AreasActivity.this, TabActivity.class);
                i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(i, 0);
            }
        });

        btnagregararea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AgregarAreaActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestQueue = Volley.newRequestQueue(this);
        listarareas();
    }

    public void listarareas() {
        listareas = new ArrayList<String>();
        listaid = new ArrayList<Integer>();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.UrlAreas + "getall.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            listareas.add(object.getString("area"));
                            listaid.add(object.getInt("id"));
                        }
                    } catch (Exception e) {
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (AreasActivity.this, android.R.layout.simple_list_item_1, listareas){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            /// Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text size 25 dip for ListView each item
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

                            // Return the view
                            return view;
                        }
                    };

                    lvareas.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AreasActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
    }

}