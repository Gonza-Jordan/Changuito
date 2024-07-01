package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAdministrador {
    List<SupermercadoProducto> obtenerProductosDeUnSupermercado(Integer idSupermercado);

    void guardarCombo(Combo combo);

    SupermercadoProducto buscarSupermercadoProducto(Integer productoId, Integer idSupermercado);

    List<SupermercadoProducto> obtenerProductosPorIds(List<Integer> productoIds, Integer idSupermercado);

    void guardarPaquete(Paquete paquete);

    void actualizarPrecioYDescuento(SupermercadoProducto supermercadoProducto, Double precio, Double descuento);
}
