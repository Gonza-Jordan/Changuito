package com.tallerwebi.dominio;

import java.util.Date;

public interface ServicioCarrito {

    Carrito consultarCarrito(Date stamp);
    Carrito consultarCarritoPorId(Long id);
    void registrar(Carrito carrito) ;
    //Usuario validarContrasena(String contrasena, String email);
    void modificar(Carrito carrito);
}
