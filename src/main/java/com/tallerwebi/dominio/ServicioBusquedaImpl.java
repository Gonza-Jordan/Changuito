package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioBusqueda")
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioBusquedaImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public Producto consultarProductoPorNombre(String nombre) {
        return repositorioProducto.buscarProductoPorNombre(nombre);
    }

    @Override
    public List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria) {
        return repositorioProducto.buscarProductosPorSubcategoria(subcategoria);
    }


}
