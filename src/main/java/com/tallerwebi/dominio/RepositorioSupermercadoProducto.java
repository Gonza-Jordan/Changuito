package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface RepositorioSupermercadoProducto {

    void guardarProductoConSupermercado(Supermercado supermercado, Producto producto);

    List<SupermercadoProducto> buscarConFiltros(String subcategoria, Map<String, List<String>> filtros, List<Integer> productoIds);

}
