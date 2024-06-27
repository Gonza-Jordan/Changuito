package com.tallerwebi.dominio;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = true)
    private Date fechaDeCreacion;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private List<SupermercadoProducto> supermercadoProducto;


    public Carrito() {
        this.supermercadoProducto = new ArrayList<>();

        this.fechaDeCreacion = new Date();
    }

    public Long getId() {
        return id;
    }

    public List<SupermercadoProducto> getSupermercadoProducto() {
        return supermercadoProducto;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public void setSupermercadoProducto(List<SupermercadoProducto> supermercadoProducto) {
        this.supermercadoProducto = supermercadoProducto;
    }
}
