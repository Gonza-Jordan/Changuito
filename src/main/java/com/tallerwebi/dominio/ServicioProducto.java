package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProducto {
    Producto consultarProductoPorId(Integer idProducto);
    Producto buscarProducto(Integer idProducto);
    List<Producto> obtenerTodosLosProductos();
}