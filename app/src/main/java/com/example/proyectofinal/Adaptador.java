package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adaptador  extends RecyclerView.Adapter<Adaptador.CitasViewHolder>{

    ArrayList<Cita> listacitas;
    Area areactual;
    Usuario usuario;
    public Adaptador(ArrayList<Cita> listacitas, Usuario usuario){
        this.listacitas = listacitas;
        this.usuario = usuario;
    }


    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_rv, null, false);
        return new CitasViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {
        holder.tvnombrecita.setText("Nombre: "+listacitas.get(position).getUsuario());
        holder.tvareacita.setText("Area: "+listacitas.get(position).getArea());
        holder.tvfechacita.setText("Fecha: "+listacitas.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return listacitas.size();
    }

    public class CitasViewHolder extends RecyclerView.ViewHolder {
        TextView tvnombrecita,tvareacita, tvfechacita;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnombrecita = itemView.findViewById(R.id.tvnombrecita);
            tvareacita = itemView.findViewById(R.id.tvareacita);
            tvfechacita = itemView.findViewById(R.id.tvfechacita);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Toast.makeText(context, "Cita: "+tvnombrecita.getText().toString() +" "+ tvareacita.getText().toString() +" "+ tvfechacita.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent citaeditintent = new Intent(context, CitaSeleccionadaActivity.class);
                    citaeditintent.putExtra("cita",listacitas.get(getAdapterPosition()));
                    citaeditintent.putExtra("usuario",usuario);
                    context.startActivity(citaeditintent);
                }
            });
        }
    }
}
