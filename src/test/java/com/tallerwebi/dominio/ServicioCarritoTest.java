package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioCarritoImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ServicioCarritoTest {
    private ServicioCarrito servicioCarrito;
    private RepositorioCarrito repositorioCarritoMock;
    private SessionFactory sessionFactory;


    @BeforeEach
    public void init() {
        this.repositorioCarritoMock = mock(RepositorioCarrito.class);
        this.servicioCarrito = new ServicioCarritoImpl(this.repositorioCarritoMock);
    }

    @Test
    public void queSePuedaConsultarUnCarritoPorId() {
        //Preparacion
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        when(this.repositorioCarritoMock.buscarPorId(carrito.getId())).thenReturn(carrito);

       //Ejecucion
        Carrito carritoBuscado = this.servicioCarrito.consultarCarritoPorId(carrito.getId());

        //Verficacion
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

    @Test
    public void queSePuedaConsultarUnCarritoPorStamp() {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        when(this.repositorioCarritoMock.buscar(carrito.getFechaDeCreacion())).thenReturn(carrito);

        //Ejecucion
        Carrito carritoBuscado = this.servicioCarrito.consultarCarrito(carrito.getFechaDeCreacion());

        //Verficacion
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

    @Test
    public void queSePuedaGuardarCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1L);

        //Ejecucion
        doNothing().when(this.repositorioCarritoMock).guardar(carrito);
        this.servicioCarrito.registrar(carrito);

        //Verficacion
        verify(this.repositorioCarritoMock,times(1)).guardar(carrito);
    }

    @Test
    public void queSePuedaEliminarCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1L);

        //Ejecucion
        doNothing().when(this.repositorioCarritoMock).guardar(carrito);
        this.servicioCarrito.eliminar(carrito);

        //Verficacion
        verify(this.repositorioCarritoMock,times(1)).eliminar(carrito);

    }

    @Test
    public void queSePuedaMdificarCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1L);

        //Ejecucion
        doNothing().when(this.repositorioCarritoMock).modificar(carrito);
        this.servicioCarrito.modificar(carrito);

        //Verficacion
        verify(this.repositorioCarritoMock,times(1)).modificar(carrito);
    }

}
