package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPromocion {

    List<Promocion> obtenerPromociones();

    void guardarPromocion(Promocion promocion);

    Promocion buscarPromocion(Integer idPromocion);
}
