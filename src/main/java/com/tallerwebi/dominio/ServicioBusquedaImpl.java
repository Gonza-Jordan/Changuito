package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service("servicioBusqueda")
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;

    @Autowired
    public ServicioBusquedaImpl(RepositorioProducto repositorioProducto, RepositorioSupermercadoProducto repositorioSupermercadoProducto) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioSupermercadoProducto = repositorioSupermercadoProducto;
    }

    @Override
    public List<Producto> consultarProductoPorNombre(String nombre) {
        return repositorioProducto.buscarProductoPorNombre(nombre);
    }

    @Override
    public List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria) {
        return repositorioProducto.buscarProductosPorSubcategoria(subcategoria);
    }

    @Override
    public List<Producto> consultarProductoPorCategoria(Categoria categoria) {
        return repositorioProducto.buscarProductoPorCategoria(categoria);
    }

    @Override
    public List<SupermercadoProducto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds) {
        List<Integer> ids = null;
        if (productoIds != null && !productoIds.isEmpty()) {
            ids = new ArrayList<>();
            for (String id : productoIds.split(",")) {
                ids.add(Integer.parseInt(id));
            }
        }
        return repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);
    }

    @Override
    public List<Producto> consultarProductosPorIds(List<Integer> ids) {
        return repositorioProducto.buscarProductosPorIds(ids);
    }

    @Override
    public List<SupermercadoProducto> ordenarProductos(List<SupermercadoProducto> productosFiltrados, String ordenar) {
        if ("menor_a_mayor".equals(ordenar)) {
            productosFiltrados.sort(Comparator.comparingDouble(this::getPrecioConDescuento));
        } else if ("mayor_a_menor".equals(ordenar)) {
            productosFiltrados.sort(Comparator.comparingDouble(this::getPrecioConDescuento).reversed());
        }
        return productosFiltrados;
    }

    private double getPrecioConDescuento(SupermercadoProducto producto) {
        if (producto.getDescuento() != null) {
            return producto.getPrecio() * producto.getDescuento();
        }
        return producto.getPrecio();
    }
}