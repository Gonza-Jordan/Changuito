package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPromocionTest {

    private ServicioPromocion servicioPromocion;
    private RepositorioPromocion repositorioPromocion;

    @BeforeEach
    public void init() {
        this.repositorioPromocion = mock(RepositorioPromocion.class);
        this.servicioPromocion = new ServicioPromocionImpl(this.repositorioPromocion);
    }

    @Test
    public void queSePuedanbuscarPromociones() {
        //Preparacion
        List<Promocion> promocionesEsperadas = new ArrayList<>();
        promocionesEsperadas.add(mock(Promocion.class));
        when(this.repositorioPromocion.obtenerPromociones()).thenReturn(promocionesEsperadas);

        //Ejecucion
        List<Promocion> promocionesObtenidas = this.servicioPromocion.buscarPromociones();

        //Verficacion
        assertThat(promocionesObtenidas.size(), equalTo(1));
        assertThat(promocionesObtenidas, equalTo(promocionesEsperadas));
    }
}
