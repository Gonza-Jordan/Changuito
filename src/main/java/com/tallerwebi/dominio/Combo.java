package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Combo extends Promocion {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "producto_id", referencedColumnName = "producto_id"),
            @JoinColumn(name = "supermercado_id", referencedColumnName = "supermercado_id")
    })
    private SupermercadoProducto producto;

    private Integer cantidadVendida;
    private Integer cantidadCobrada;


    public Combo() {}

    public Combo(Double precioFinal, LocalDate fechaInicio, LocalDate fechaFin, SupermercadoProducto producto, int cantidadVendida, int cantidadCobrada) {
        super(precioFinal, fechaInicio, fechaFin);
        this.producto = producto;
        this.cantidadVendida = cantidadVendida;
        this.cantidadCobrada = cantidadCobrada;
    }

    public SupermercadoProducto getProducto() {
        return producto;
    }

    public void setProducto(SupermercadoProducto producto) {
        this.producto = producto;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getCantidadCobrada() {
        return cantidadCobrada;
    }

    public void setCantidadCobrada(Integer cantidadCobrada) {
        this.cantidadCobrada = cantidadCobrada;
    }
}
