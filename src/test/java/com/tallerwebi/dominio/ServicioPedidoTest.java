package com.tallerwebi.dominio;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioPedidoTest {
    private ServicioPedido servicioPedido;
    private RepositorioPedido repositorioPedidoMock;
    private SessionFactory sessionFactory;


    @BeforeEach
    public void init() {
        this.repositorioPedidoMock = mock(RepositorioPedido.class);
        this.servicioPedido = new ServicioPedidoImpl(this.repositorioPedidoMock);
    }

    @Test
    public void queSePuedaConsultarUnPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(this.repositorioPedidoMock.buscar(pedido.getId())).thenReturn(pedido);

        //Ejecucion
        Pedido pedidoBuscado = this.servicioPedido.buscar(pedido.getId());

        //Verficacion
        assertThat(pedido.getFechaDeCreacion(), equalTo(pedidoBuscado.getFechaDeCreacion()));
    }

    @Test
    public void queSePuedaGuardarCarrito() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        //Ejecucion
        doNothing().when(this.repositorioPedidoMock).guardar(pedido);
        this.servicioPedido.registrar(pedido);

        //Verficacion
        verify(this.repositorioPedidoMock,times(1)).guardar(pedido);
    }

}
