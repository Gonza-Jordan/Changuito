package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ServicioSupermercadoProducto")
@Transactional
public class ServicioSupermercadoProductoImpl implements ServicioSupermercadoProducto {

    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;

    @Autowired
    public ServicioSupermercadoProductoImpl(RepositorioSupermercadoProducto repositorioSupermercadoProducto) {
        this.repositorioSupermercadoProducto = repositorioSupermercadoProducto;
    }

    @Override
    public SupermercadoProducto consultarSupermercadoProducto(Integer idProducto, Integer idSupermercado) {
        return repositorioSupermercadoProducto.buscarSupermercadoProducto(idProducto, idSupermercado);
    }



}
