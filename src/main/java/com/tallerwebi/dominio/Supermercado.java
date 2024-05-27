package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Supermercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSupermercado;

    private String nombre;
    private String ubicacion;
    private String localidad;
    private String logo;

    public Supermercado() {

    }

    public Supermercado(String nombre, String ubicacion, String localidad, String logo) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.localidad = localidad;
        this.logo = logo;
    }

    // Getters y Setters

    public Integer getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(Integer idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
