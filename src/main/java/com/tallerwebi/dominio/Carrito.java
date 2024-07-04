package com.tallerwebi.dominio;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SupermercadoProducto> supermercadoProducto;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Promocion> promocion;


    public Carrito() {
        this.supermercadoProducto = new ArrayList<>();
        this.promocion = new ArrayList<>();

        this.fechaDeCreacion = new Date();
    }

    public List<Promocion> getPromocion() {
        return promocion;
    }

    public void setPromocion(List<Promocion> promocion) {
        this.promocion = promocion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
