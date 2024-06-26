package com.tallerwebi.dominio;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombre;
    private String codigoBarras;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Subcategoria subcategoria;

    @Column(nullable = true)
    private String urlImagen;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<SupermercadoProducto> supermercados = new HashSet<>();

    public Producto() {
    }

    //Constructor sin descuento
    public Producto(String nombre, String codigoBarras, Categoria categoria, Subcategoria subcategoria, String urlImagen) {
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.urlImagen = urlImagen;
    }

    public Set<SupermercadoProducto> getSupermercados() {
        return this.supermercados;
    }

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

    public void agregarSupermercadoProducto(SupermercadoProducto supermercadoProducto) {
    }
}
