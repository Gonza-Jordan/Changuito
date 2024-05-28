package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioBusqueda {
    List<Producto> consultarProductoPorNombre(String nombre);

    List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria);

    List<Producto> consultarProductoPorCategoria (Categoria categoria);

    List<Producto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds);

    List<Producto> consultarProductosPorIds(List<Integer> ids);
}
