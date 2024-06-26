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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioProductoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    public void init() { this.repositorioProducto = new RepositorioProductoImpl(this.sessionFactory);}

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnProducto(){
        //Preparacion
        Producto productoAGuardar = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");

        //Ejecucion
        this.repositorioProducto.guardarProducto(productoAGuardar);

        //Verficacion
        Producto productoObtenido = (Producto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(productoObtenido, equalTo(productoAGuardar));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnProductoPorNombre(){
        //Preparacion
        Producto productoGuardado = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
        this.repositorioProducto.guardarProducto(productoGuardado);

        //Ejecucion
        List<Producto> productoEncontrado = this.repositorioProducto.buscarProductoPorNombre("Coca Cola");

        //Verficacion
        assertThat(productoEncontrado.get(0), equalTo(productoGuardado));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnProductoPorSubcategoria(){
        //Preparacion
        Producto productoGuardado = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
        this.repositorioProducto.guardarProducto(productoGuardado);

        //Ejecucion
        List<Producto> productosEncontrados = repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas);

        //Verficacion
        assertThat(1, equalTo(productosEncontrados.size()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanBuscarTodosLosProductosDeUnaSubcategoria(){
        //Preparacion
        Producto productoGaseosa = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
        Producto otroProductoGaseosa = new Producto("Sprite", "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/sprite.jpg");
        Producto productoArroz = new Producto("Arroz", "987654321", Categoria.Almacen, Subcategoria.Arroz, "img/producto/almacen/arroz.jpg");
        this.repositorioProducto.guardarProducto(productoGaseosa);
        this.repositorioProducto.guardarProducto(otroProductoGaseosa);
        this.repositorioProducto.guardarProducto(productoArroz);

        //Ejecucion
        List<Producto> productosEncontrados = repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas);

        //Verficacion
        assertThat(2, equalTo(productosEncontrados.size()));
    }

}