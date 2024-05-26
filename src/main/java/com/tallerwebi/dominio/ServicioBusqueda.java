package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioBusqueda {
    Producto consultarProductoPorNombre(String nombre);

    List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria);

    List<Producto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros);
}
