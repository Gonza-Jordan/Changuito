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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioSupermercadoProductoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;

    @BeforeEach
    public void init() { this.repositorioSupermercadoProducto = new RepositorioSupermercadoProductoImpl(this.sessionFactory);
        this.repositorioSupermercadoProducto = mock(RepositorioSupermercadoProducto.class);}

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedanFiltrarTodosLosProductosDeUnaSubcategoriaPorPrecioMenorAMil(){
//        //Preparacion
//        String subcategoriaStr = Subcategoria.Gaseosas.toString();
//        Producto productoGaseosa = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
//        Producto otroProductoGaseosa = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/sprite.jpg");
//
//        this.repositorioProducto.guardarProducto(productoGaseosa);
//        this.repositorioProducto.guardarProducto(otroProductoGaseosa);
//
//        List<String> precios = new ArrayList<>();
//        precios.add("< 1000");
//        Map<String, List<String>> filtros = new HashMap<>();
//        filtros.put("precio", precios);
//
//        //Ejecucion
//        List<Producto> productosEncontrados = repositorioProducto.buscarProductosConFiltros(subcategoriaStr, filtros, productoGaseosa.getIdProducto().toString() + "," + otroProductoGaseosa.getIdProducto().toString());
//
//        //Verficacion
//        assertThat(1, equalTo(productosEncontrados.size()));
//        assertThat(productosEncontrados.get(0), equalTo(otroProductoGaseosa));
//        assertThat(productosEncontrados.get(0).getPrecio(), lessThan(1000.00));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedanFiltrarTodosLosProductosDeUnaSubcategoriaPorSupermercadoCoto() {
//        // Preparación
//        String subcategoriaStr = Subcategoria.Gaseosas.toString();
//        Producto productoGaseosa = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
//        Producto otroProductoGaseosa = new Producto("Sprite", 900.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/sprite.jpg");
//
//        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
//        Supermercado supermercadoDia = new Supermercado("Dia", "Avenida Juan Manuel de Rosas 11000", "González Catán", "");
//        this.repositorioSupermercado.guardarSupermercado(supermercadoCoto);
//        this.repositorioSupermercado.guardarSupermercado(supermercadoDia);
//
//        productoGaseosa.setSupermercado(supermercadoCoto);
//        otroProductoGaseosa.setSupermercado(supermercadoDia);
//        this.repositorioProducto.guardarProducto(productoGaseosa);
//        this.repositorioProducto.guardarProducto(otroProductoGaseosa);
//
//        List<String> supermercados = new ArrayList<>();
//        supermercados.add(supermercadoCoto.getIdSupermercado().toString());
//        Map<String, List<String>> filtros = new HashMap<>();
//        filtros.put("supermercado_id", supermercados);
//
//        // Ejecución
//        List<Producto> productosEncontrados = repositorioProducto.buscarProductosConFiltros(subcategoriaStr, filtros, productoGaseosa.getIdProducto().toString() + "," + otroProductoGaseosa.getIdProducto().toString());
//
//        // Verificación
//        assertThat(1, equalTo(productosEncontrados.size()));
//        assertThat(productosEncontrados.get(0), equalTo(productoGaseosa));
//        assertThat(productosEncontrados.get(0).getSupermercado(), equalTo(productoGaseosa.getSupermercado()));
//    }

}

