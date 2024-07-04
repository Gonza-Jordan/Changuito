package com.tallerwebi.dominio;

public interface ServicioSupermercadoProducto {

    void guardar(SupermercadoProducto supermercadoProducto);

    void eliminar(SupermercadoProducto supermercadoProducto);

    SupermercadoProducto consultarSupermercadoProducto(Integer idProducto, Integer idSupermercado);

}
