package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombre;
    private double precio;
    private String codigoBarras;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Subcategoria subcategoria;

    @Column(nullable = true)
    private String urlImagen;

    @Column(nullable = true)
    private Double descuento;

    public Producto() {

    }

    // Constructor sin descuento
    public Producto(String nombre, double precio, String codigoBarras, Categoria categoria, Subcategoria subcategoria, String urlImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.urlImagen = urlImagen;
    }

    // Constructor con descuento
    public Producto(String nombre, double precio, String codigoBarras, Categoria categoria, Subcategoria subcategoria, String urlImagen, Double descuento) {
        this(nombre, precio, codigoBarras, categoria, subcategoria, urlImagen);
        this.descuento = descuento;
    }

    // Getters y Setters

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setPrecioFormateado(String format) {
    }
    public Double getDescuento() { return descuento; }

    public void setDescuento(Double descuento) { this.descuento = descuento; }
}
