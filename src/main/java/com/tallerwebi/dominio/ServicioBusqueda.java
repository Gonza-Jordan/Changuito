package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBusqueda {
    List<Producto> consultarProductoPorNombre(String nombre);

    List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria);

    List<Producto> consultarProductoPorCategoria (Categoria categoria);
}
