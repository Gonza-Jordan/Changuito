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
    public ServicioPromocionImpl(RepositorioPromocion repositorioPromocion){
       this.repositorioPromocion = repositorioPromocion;
    }

    @Override
    public List<Promocion> buscarPromociones() {
        return repositorioPromocion.obtenerPromociones();
    }

}
