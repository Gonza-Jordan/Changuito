package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private Integer idSupermercado;
    private String descripcion;
    private Integer idResenia = 0 ;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false, unique = true)
//    private Date fechaDeCreacion;


    //   public Resenia(Integer idProducto, String nombreUsuario, String descripcion) {
    public Resenia(Integer idProducto,String descripcion, Integer idSupermercado) {
       this.idProducto = idProducto;
       this.idSupermercado =idSupermercado;

        this.descripcion = descripcion;
//        this.fechaDeCreacion = new Date();
        this.idResenia ++;
    }

    public Resenia() {

    }


    public Integer getIdProductoResenia() {
        return idProducto;
    }
//    public String getNombreUsuario(){
//           return nombreUsuario;
//    }
    public String getDescripcion(){
           return descripcion;
    }

    public Integer getIdSupermercado() {
        return idSupermercado;
    }


//    public Date getFechaDeCreacion(){
//           return fechaDeCreacion;
//    }

}
