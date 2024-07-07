package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }
    @Override
    public Producto consultarProductoPorId(Integer idProducto) {
        return repositorioProducto.buscarPorId(idProducto);
    }

    @Override
    public Producto buscarProducto(Integer idProducto) {
        return repositorioProducto.buscarPorId(idProducto);
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.obtenerTodos();
    }
}