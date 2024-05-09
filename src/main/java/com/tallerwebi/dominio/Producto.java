package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private String nombre;
    private double precio;
    private String codigoBarras;
    private Categoria categoria;
    private Subcategoria subcategoria;



    public Producto(int idProducto ,String nombre, double precio, String codigoBarras, Categoria categoria, Subcategoria subcategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
    }



    //Getters y Setters de producto
    public Integer getIdProducto() {
        return idProducto;
    }

    public void SetIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Categoria getCategoria() {return categoria;   }

    public void setCategoria(Categoria categoria) { this.categoria = categoria;   }

    public Subcategoria getSubcategoria() { return subcategoria; }

    public void setSubcategoria(Subcategoria subcategoria) { this.subcategoria = subcategoria; }
}
