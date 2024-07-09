package com.tallerwebi.dominio;

import java.util.Date;

public interface ServicioCarrito {
    Carrito consultarCarrito(Date stamp);
    Carrito consultarCarritoPorId(Long id);
    void registrar(Carrito carrito);
    void modificar(Carrito carrito);
    void eliminar(Carrito carrito);
    void agregarPromocionAlCarrito(Long carritoId, Promocion promocion);
}

