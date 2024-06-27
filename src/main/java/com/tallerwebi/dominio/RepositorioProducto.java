package com.tallerwebi.dominio;


import java.util.List;
import java.util.Map;

public interface RepositorioProducto {
    List<Producto> buscarProductoPorNombre(String nombre);

    List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria);

    void guardarProducto(Producto producto);

    List<Producto> buscarProductoPorCategoria(Categoria categoria);

    List<Producto> buscarProductosPorIds(List<Integer> ids);


}
