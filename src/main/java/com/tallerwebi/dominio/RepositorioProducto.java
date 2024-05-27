package com.tallerwebi.dominio;


import java.util.List;

public interface RepositorioProducto {
    List<Producto> buscarProductoPorNombre(String nombre);

    List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria);

    void guardarProducto(Producto producto);

    List<Producto> buscarProductoPorCategoria(Categoria categoria);
}
