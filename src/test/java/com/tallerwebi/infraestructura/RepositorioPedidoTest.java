package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.Pedido;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioPedidoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioPedidoImpl repositorioPedido;

    @BeforeEach
    public void init() {
        this.repositorioPedido = new RepositorioPedidoImpl(this.sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarPedido() {
        // Preparación
        Pedido pedido = new Pedido();

        // Ejecucion
        this.repositorioPedido.guardar(pedido);

        // Verificación
        Pedido pedidoGuardado = this.repositorioPedido.buscar(pedido.getId());
        assertThat(pedido.getId(), equalTo(pedidoGuardado.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarPedidoPorId() {
        // Preparación
        Pedido pedido = new Pedido();
        this.repositorioPedido.guardar(pedido);

        // Ejecucion
        Pedido pedidoBuscado = this.repositorioPedido.buscar(pedido.getId());

        // Verificación
        assertThat(pedido.getId(), equalTo(pedidoBuscado.getId()));
    }

}