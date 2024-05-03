package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class Supermercado {
    private String nombre;
    private List<Producto> productos;

    public Supermercado(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProducto(int idProducto) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProducto) {
                return producto;
            }
        }
        return null; // Retorna null si el producto no se encuentra en el supermercado
    }

    public void eliminarProducto(int idProducto) {
        productos.removeIf(producto -> producto.getIdProducto() == idProducto);
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
