package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
//    private String nombreUsuario;
    private String descripcion;
    private Integer idResenia = 0 ;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false, unique = true)
//    private Date fechaDeCreacion;

    //   public Resenia(Integer idProducto, String nombreUsuario, String descripcion) {
    public Resenia(Integer idProducto,String descripcion) {
       this.idProducto = idProducto;
//        this.nombreUsuario = nombreUsuario;
        this.descripcion = descripcion;
//        this.fechaDeCreacion = new Date();
        this.idResenia ++;
    }

    public Resenia() {

    }


    public Integer getIdProductoReseña() {
        return idProducto;
    }
//    public String getNombreUsuario(){
//           return nombreUsuario;
//    }
    public String getDescripcion(){
           return descripcion;
    }
//    public Date getFechaDeCreacion(){
//           return fechaDeCreacion;
//    }

}
