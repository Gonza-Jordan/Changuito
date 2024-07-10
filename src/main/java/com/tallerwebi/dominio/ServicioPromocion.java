package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPromocion {

    List<Promocion> buscarPromociones();

    Promocion buscarPromocion(Integer idPromocion);

    List<Promocion> obtenerPromocionesDeProducto(Producto producto);

    }
