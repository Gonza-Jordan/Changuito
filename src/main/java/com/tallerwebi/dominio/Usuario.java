package com.tallerwebi.dominio;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List <SupermercadoProducto> supermercadoProducto;

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

    public List<SupermercadoProducto> getSupermercadoProducto() {
        return supermercadoProducto;
    }

    public void setSupermercadoProducto(List<SupermercadoProducto> supermercadoProducto) {
        this.supermercadoProducto = supermercadoProducto;
    }
}
