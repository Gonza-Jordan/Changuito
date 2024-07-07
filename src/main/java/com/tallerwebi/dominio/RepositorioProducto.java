package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProducto {
    void guardarProducto(Producto producto);
    List<Producto> buscarProductoPorNombre(String nombre);
    List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria);
    List<Producto> buscarProductoPorCategoria(Categoria categoria);
    List<Producto> buscarProductosPorIds(List<Integer> ids);
    Producto buscarProductoPorId(Integer id);
    Producto buscarPorId(Integer id);
    List<Producto> obtenerTodos();
}
