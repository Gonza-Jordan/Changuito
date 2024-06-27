package com.tallerwebi.dominio;

import java.util.Date;

public interface RepositorioCarrito {

    void guardar(Carrito carrito);

    Carrito buscar(Date stamp);

    Carrito buscarPorId(Long id);

    void modificar(Carrito carrito);

    void eliminar(Carrito carrito);


}


