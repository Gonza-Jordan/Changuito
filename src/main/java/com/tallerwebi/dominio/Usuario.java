package com.tallerwebi.dominio;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Column(length = 30, nullable = false, unique = true, updatable = false)
    private String dni;

    @Column(length = 200)
    private String direccion;

    @Column(length = 30, nullable = false, unique = true, updatable = false)
    private String email;

    @Column(length = 18, nullable = false)
    private String contrasena;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Carrito> carritos;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Pedido> pedidos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Producto> favoritos;

    @Column()
    private Boolean admin;

    @Column()
    private Date stampCarritoActivo;

    @Column()
    private Boolean guardoCarrito;

    @ManyToOne
    @JoinColumn(name = "idSupermercado")
    private Supermercado supermercado;

    public Usuario() {
        this.admin = false;
        this.guardoCarrito = false;
    }

    // Getters y setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Date getStampCarritoActivo() {
        return stampCarritoActivo;
    }

    public void setStampCarritoActivo(Date stampCarritoActivo) {
        this.stampCarritoActivo = stampCarritoActivo;
    }

    public Boolean getGuardoCarrito() {
        return guardoCarrito;
    }

    public void setGuardoCarrito(Boolean guardoCarrito) {
        this.guardoCarrito = guardoCarrito;
    }

    public Supermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Set<Producto> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<Producto> favoritos) {
        this.favoritos = favoritos;
    }
}
