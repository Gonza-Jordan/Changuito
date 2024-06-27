package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioCarritoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioCarritoImpl repositorioCarrito;

    @BeforeEach
    public void init() {
        this.repositorioCarrito = new RepositorioCarritoImpl(this.sessionFactory);
    }


//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaModificarCarrito() {
//        // Preparación
//        Carrito carrito = new Carrito();
//
//        Producto producto = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercado = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//        producto.setIdProducto(1);
//        supermercado.setIdSupermercado(1);
//
//
//        SupermercadoProducto superProdu = new SupermercadoProducto();
//        SupermercadoProductoId superProduId = new SupermercadoProductoId(producto.getIdProducto(), supermercado.getIdSupermercado());
//        superProduId.setProductoId(producto.getIdProducto());
//        superProduId.setSupermercadoId(supermercado.getIdSupermercado());
//        superProdu.setId(superProduId);
//
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
//        Carrito carritoGuardado= this.repositorioCarrito.buscar(carrito.getFechaDeCreacion());
//
//
//        // Modificar algún atributo del usuario
//        Producto producto2 = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercado2 = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//        producto2.setIdProducto(2);
//        supermercado2.setIdSupermercado(2);
//
//        SupermercadoProducto superProdu2 = new SupermercadoProducto();
//        SupermercadoProductoId superProduId2 = new SupermercadoProductoId(producto2.getIdProducto(), supermercado2.getIdSupermercado());
//        superProduId2.setProductoId(producto2.getIdProducto());
//        superProduId2.setSupermercadoId(supermercado2.getIdSupermercado());
//        superProdu2.setId(superProduId2);
//
//        superProdu2.setSupermercado(supermercado2);
//        superProdu2.setProducto(producto2);
//        superProdu2.setPrecio(1000.0);
//
//        List<SupermercadoProducto> listaSupermercadoProducto2 = new ArrayList<>();
//        listaSupermercadoProducto2.add(superProdu2);
//
//        carrito.setSupermercadoProducto(listaSupermercadoProducto2);
//
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
    public void queSePuedaGuardarCarrito() {
        // Preparación
        Carrito carrito = new Carrito();

        // Ejecucion
        this.repositorioCarrito.guardar(carrito);


        // Verificación
        Carrito carritoGuardado = this.repositorioCarrito.buscar(carrito.getFechaDeCreacion());
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoGuardado.getFechaDeCreacion()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarCarritoPorId() {
        // Preparación
        Carrito carrito = new Carrito();
        this.repositorioCarrito.guardar(carrito);

        // Ejecucion
        Carrito carritoBuscado = this.repositorioCarrito.buscarPorId(carrito.getId());


        // Verificación
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarCarritoPorStamp() {
        // Preparación
        Carrito carrito = new Carrito();
        this.repositorioCarrito.guardar(carrito);

        // Ejecucion
        Carrito carritoBuscado = this.repositorioCarrito.buscar(carrito.getFechaDeCreacion());


        // Verificación
        assertThat(carrito.getFechaDeCreacion(), equalTo(carritoBuscado.getFechaDeCreacion()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarCarrito() {
        // Preparación
        Carrito carrito = new Carrito();
        this.repositorioCarrito.guardar(carrito);

        // Ejecucion
        this.repositorioCarrito.eliminar(carrito);


        // Verificación
        Carrito carritoGuardado = this.repositorioCarrito.buscar(carrito.getFechaDeCreacion());
        assertThat(null, equalTo(carritoGuardado));
    }
}