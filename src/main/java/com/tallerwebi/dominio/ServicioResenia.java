package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioResenia {
    void guardarResenia(Resenia resenia);
    List<Resenia> obtenerResenias(Integer idProducto);
}
