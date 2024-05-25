package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBusqueda {
    Producto consultarProductoPorNombre(String nombre);

    List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria);
}
