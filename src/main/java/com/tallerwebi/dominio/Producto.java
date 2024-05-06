package com.tallerwebi.dominio;

public class Producto {
    private Integer idProducto;
    private String nombre;
    private double precio;
    private String codigoBarras;
    private Categoria categoria;

    public Producto(Integer idProducto ,String nombre, double precio, String codigoBarras, Categoria categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
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
}
