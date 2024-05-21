package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Subcategoria;
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
        Producto productoAGuardar = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas);

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
        Producto productoGuardado = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas);

        //Ejecucion
        this.repositorioProducto.guardarProducto(productoGuardado);

        //Verficacion
        Producto productoEncontrado = repositorioProducto.buscarProductoPorNombre("Coca Cola");

        assertThat(productoEncontrado, equalTo(productoGuardado));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnProductoPorSubcategoria(){
        //Preparacion
        Producto productoGuardado = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas);

        //Ejecucion
        this.repositorioProducto.guardarProducto(productoGuardado);

        //Verficacion
        List<Producto> productosEncontrados = repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas);

        assertThat(1, equalTo(productosEncontrados.size()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanBuscarTodosLosProductosDeUnaSubcategoria(){
        //Preparacion
        Producto productoGaseosa = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas);
        Producto otroProductoGaseosa = new Producto("Sprite", 1500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas);
        Producto productoArroz = new Producto("Arroz", 1000.00, "987654321", Categoria.Almacen, Subcategoria.Arroz);

        //Ejecucion
        this.repositorioProducto.guardarProducto(productoGaseosa);
        this.repositorioProducto.guardarProducto(otroProductoGaseosa);
        this.repositorioProducto.guardarProducto(productoArroz);

        //Verficacion
        List<Producto> productosEncontrados = repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas);

        assertThat(2, equalTo(productosEncontrados.size()));

    }


}