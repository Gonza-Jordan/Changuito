package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPromocion")
@Transactional
public class ServicioPromocionImpl implements ServicioPromocion {

    private RepositorioPromocion repositorioPromocion;

    @Autowired
    public ServicioPromocionImpl(RepositorioPromocion repositorioPromocion) {
        this.repositorioPromocion = repositorioPromocion;
    }

    @Override
    public List<Promocion> buscarPromociones() {
        return repositorioPromocion.obtenerPromociones();
    }


    @Override
    public Promocion buscarPromocion(Integer idPromocion) {
        return repositorioPromocion.buscarPromocion(idPromocion);
    }

    @Override
    public List<Promocion> obtenerPromocionesDeProducto(Producto producto) {
        return repositorioPromocion.obtenerPromocionesDeProducto(producto);
    }

    @Override
    public List<Promocion> obtenerTodasLasPromociones() {
        return repositorioPromocion.obtenerTodasLasPromociones();
    }



}

