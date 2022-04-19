package com.example.proyectofinal;

import java.io.Serializable;
import java.util.Date;

public class Cita implements Serializable {

    private int id;
    private String usuario;
    private String fecha;
    private String area;
    private String email;

    public Cita(int id, String usuario, String fecha, String area, String email) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.area = area;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
