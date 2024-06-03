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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioSupermercadoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioSupermercado repositorioSupermercado;

    @BeforeEach
    public void init() {
        this.repositorioSupermercado = new RepositorioSupermercadoImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnSupermercado() {
        //Preparacion
        Supermercado supermercadoAGuardar = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        //Ejecucion
        this.repositorioSupermercado.guardarSupermercado(supermercadoAGuardar);

        //Verficacion
        Supermercado supermercadoObtenido = (Supermercado) this.sessionFactory.getCurrentSession()
                .createQuery("FROM Supermercado WHERE nombre = 'Carrefour'")
                .getSingleResult();

        assertThat(supermercadoObtenido, equalTo(supermercadoAGuardar));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaAsignarUnSupermercadoAUnProducto() {
//        //Preparacion
//        Supermercado supermercadoAGuardar = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//        Producto arroz = new Producto("Arroz Gallo Oro 1kg", 3750, "11450502019", Categoria.Almacen, Subcategoria.Arroz, "https://jumboargentina.vtexassets.com/arquivos/ids/803457-1200-auto?v=638379921950070000&width=1200&height=auto&aspect=true", 3562.5);
//
//        //Ejecución
//        this.repositorioSupermercado.guardarSupermercado(supermercadoAGuardar);
//        this.repositorioSupermercado.guardarProductoConSupermercado(supermercadoAGuardar, arroz);
//
//        //Verficacion
//        Producto productoObtenido = (Producto) this.sessionFactory.getCurrentSession()
//                .createQuery("FROM Producto WHERE supermercado = :supermercado")
//                .setParameter("supermercado", supermercadoAGuardar)
//                .getSingleResult();
//
//
//        assertThat(productoObtenido, equalTo(arroz));
//        assertThat(productoObtenido.getSupermercado(), equalTo(supermercadoAGuardar));
//
//    }

    }