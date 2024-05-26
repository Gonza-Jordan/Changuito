package com.tallerwebi.dominio;


import java.util.List;
import java.util.Map;

public interface RepositorioProducto {
    Producto buscarProductoPorNombre(String nombre);

    List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria);

    void guardarProducto(Producto producto);

    List<Producto> buscarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros);
}
