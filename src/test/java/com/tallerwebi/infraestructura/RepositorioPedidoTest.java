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

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaModificarCarrito() {
//        // Preparación
//        Carrito carrito = new Carrito();
//
//        SupermercadoProducto superProdu = new SupermercadoProducto();
//        Supermercado supermercado = new Supermercado();
//        Producto producto = new Producto();
//        superProdu.setSupermercado(supermercado);
//        superProdu.setProducto(producto);
//        superProdu.setPrecio(50.0);
//
//        List<SupermercadoProducto> listaSupermercadoProducto = new ArrayList<>();
//        listaSupermercadoProducto.add(superProdu);
//
//        carrito.setSupermercadoProducto(listaSupermercadoProducto);
//
//        // Guardar el usuario primero
//        this.repositorioCarrito.guardar(carrito);
//
//        // Modificar algún atributo del usuario
//        SupermercadoProducto superProdu2 = new SupermercadoProducto();
//        Supermercado supermercado2 = new Supermercado();
//        Producto producto2 = new Producto();
//        superProdu2.setSupermercado(supermercado2);
//        superProdu2.setProducto(producto2);
//        superProdu2.setPrecio(1000.0);
//
//        List<SupermercadoProducto> listaSupermercadoProducto2 = new ArrayList<>();
//        listaSupermercadoProducto2.add(superProdu2);
//
//        carrito.setSupermercadoProducto(listaSupermercadoProducto2);
//;
//
//        // Ejecución
//        this.repositorioCarrito.modificar(carrito);
//
//        // Verificación
//        Carrito carritoModificado = this.repositorioCarrito.buscar(carrito.getFechaDeCreacion());
//
//        Double precioTotal = 0.0;
//        for (SupermercadoProducto superProdu3 : carritoModificado.getSupermercadoProducto()) {
//            precioTotal += superProdu3.getPrecio();
//        }
//
//        assertThat(1000.0, equalTo(precioTotal));
//    }


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