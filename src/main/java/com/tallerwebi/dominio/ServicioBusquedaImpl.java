package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service("servicioBusqueda")
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioBusquedaImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
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
    public List<Producto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds) {
        return repositorioProducto.buscarProductosConFiltros(subcategoriaStr, filtros, productoIds);
    }

    public List<Producto> consultarProductosPorIds(List<Integer> ids) {
        return repositorioProducto.buscarProductosPorIds(ids);
    }


}
