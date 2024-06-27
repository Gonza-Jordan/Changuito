package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioCarritoTest {
    private ServicioCarrito servicioCarrito;
    private RepositorioCarrito repositorioCarritoMock;

    @BeforeEach
    public void init() {
        this.repositorioCarritoMock = mock(RepositorioCarrito.class);
        this.servicioCarrito = new ServicioCarritoImpl(this.repositorioCarritoMock);
    }

    @Test
    public void queSePuedaConsultarUnCarritoPorId() {
        //Preparacion
        Carrito carrito = new Carrito();
        this.repositorioCarritoMock.guardar(carrito);

        //Ejecucion
        Carrito carritoBuscado = this.servicioCarrito.consultarCarritoPorId(carrito.getId());

        //Verficacion
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

    @Test
    public void queSePuedaConsultarUnCarritoPorStamp() {
        //Preparacion
        Carrito carrito = new Carrito();
        this.servicioCarrito.registrar(carrito);

        //Ejecucion
        Carrito carritoBuscado = this.servicioCarrito.consultarCarrito(carrito.getFechaDeCreacion());

        //Verficacion
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

}
