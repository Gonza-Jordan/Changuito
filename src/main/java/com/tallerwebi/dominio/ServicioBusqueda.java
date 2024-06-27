package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioBusqueda {
    List<Producto> consultarProductoPorNombre(String nombre);

    List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria);

    List<Producto> consultarProductoPorCategoria (Categoria categoria);

    List<SupermercadoProducto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds);

    List<Producto> consultarProductosPorIds(List<Integer> ids);

    List<SupermercadoProducto> ordenarProductos(List<SupermercadoProducto> productosFiltrados, String ordenar);

    List<Supermercado> consultarSupermercados(List<SupermercadoProducto> productosFiltrados);

    List<Double> consultarDescuentos(List<SupermercadoProducto> productosFiltrados);

    List<String> consultarPrecios(List<SupermercadoProducto> productosFiltrados);

    List<String> formatearPrecios(List<String> filtrosPreciosAMostrar);

    List<Marca> consultarMarcas(List<SupermercadoProducto> productosFiltrados);

    List<Producto> buscarProductoACompararId(Integer ids);
}
