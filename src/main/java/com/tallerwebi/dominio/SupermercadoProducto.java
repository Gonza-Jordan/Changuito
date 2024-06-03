package com.tallerwebi.dominio;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SupermercadoProducto implements Serializable {
    @EmbeddedId
    private SupermercadoProductoId id = new SupermercadoProductoId();

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @MapsId("supermercadoId")
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "descuento")
    private Double descuento;

    public SupermercadoProducto() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        producto.agregarSupermercadoProducto(this); // Agregar este producto a la lista de SupermercadoProducto en Producto
    }

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
        supermercado.agregarSupermercadoProducto(this); // Agregar este producto a la lista de SupermercadoProducto en Supermercado
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public SupermercadoProductoId getId() {
        return id;
    }

    public void setId(SupermercadoProductoId id) {
        this.id = id;
    }

}



