package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.ServicioBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service("servicioBusqueda")
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioBusquedaImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public Producto consultarProducto(String nombre) {
        return repositorioProducto.buscarProductoPorNombre(nombre);
    }
}
