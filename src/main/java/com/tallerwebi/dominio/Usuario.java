package com.tallerwebi.dominio;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@DynamicUpdate
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @Column(length = 30, nullable = false, unique = true, updatable=false)
    private Integer dni;

    @Column(length = 200, nullable = false)
    private String direccion;

    @Column(length = 30, nullable = false, unique = true, updatable=false)
    private String email;

    @Column(length = 18, nullable = false)
    private String contrasena;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List <Carrito> carritos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List <Pedido> pedidos;

    @Column()
    private Boolean admin;

    @Column()
    private Date stampCarritoActivo;

    public String getEmail() {
        return email;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getDni() {
        return dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public Date getStampCarritoActivo() {
        return stampCarritoActivo;
    }

    public void setStampCarritoActivo(Date stampCarritoActivo) {
        this.stampCarritoActivo = stampCarritoActivo;
    }

    public Long getId() {
        return id;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
