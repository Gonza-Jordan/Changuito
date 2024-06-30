package com.tallerwebi.dominio;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Paquete extends Promocion {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Paquete_supermercado_producto",
            joinColumns = @JoinColumn(name = "paquete_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "producto_id"),
                    @JoinColumn(name = "supermercado_id")
            }
    )
    private List<SupermercadoProducto> productos;
    private Double descuento;
    private String nombre;

    public Paquete(){

    }

    public Paquete(Double precioFinal, LocalDate fechaInicio, LocalDate fechaFin, List<SupermercadoProducto> productos, Double descuento, String nombre) {
        super(precioFinal, fechaInicio, fechaFin);
        this.productos = productos;
        this.descuento = descuento;
        this.nombre = nombre;
    }


//    @Override
//    public Double calcularPrecio() {
//        Double totalPrecio = 0.0;
//        for (SupermercadoProducto producto : productos) {
//            totalPrecio += producto.getPrecio();
//        }
//        Double descuentoAplicado = totalPrecio * (descuento / 100);
//        return totalPrecio - descuentoAplicado;
//    }


    public List<SupermercadoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<SupermercadoProducto> productos) {
        this.productos = productos;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
