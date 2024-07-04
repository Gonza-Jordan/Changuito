package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface RepositorioSupermercadoProducto {

    void guardar(SupermercadoProducto supermercadoProducto);

    void eliminar(SupermercadoProducto supermercadoProducto);

    void guardarSupermercadoProducto(Supermercado supermercado, Producto producto);

    void asignarPrecioYDescuentoAUnSupermercadoProducto(SupermercadoProducto supermercadoProducto, Double precio, Double descuento);

    List<SupermercadoProducto> buscarConFiltros(String subcategoria, Map<String, List<String>> filtros, List<Integer> productoIds);

    SupermercadoProducto buscarSupermercadoProducto(Integer idProducto, Integer idSupermercado);

}
