package com.tallerwebi.dominio;


import java.util.List;

public interface RepositorioProducto {
    Producto buscarProductoPorNombre(String nombre);

    List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria);

    void guardarProducto(Producto producto);
}
