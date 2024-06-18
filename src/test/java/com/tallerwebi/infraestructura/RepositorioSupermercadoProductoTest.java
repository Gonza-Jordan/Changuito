package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioSupermercadoProductoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;
    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercado repositorioSupermercado;

    @BeforeEach
    public void init() {
        this.repositorioSupermercadoProducto = new RepositorioSupermercadoProductoImpl(this.sessionFactory);
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.repositorioSupermercado = mock(RepositorioSupermercado.class);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnSupermercadoProducto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);

        //Ejecucion
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);

        //Verficacion
        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(supermercadoProductoObtenido.getProducto().getNombre(), equalTo(productoMock.getNombre()));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaAsignarPrecioAUnSupermercadoProducto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);

        SupermercadoProducto supermercadoProductoBuscado = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        //Ejecucion
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoBuscado, 3000.00, null);

        //Verficacion
        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(supermercadoProductoObtenido.getPrecio(), equalTo(3000.00));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaAsignarDescuentoAUnSupermercadoProducto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);

        SupermercadoProducto supermercadoProductoBuscado = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        //Ejecucion
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoBuscado, null, 0.90);

        //Verficacion
        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(supermercadoProductoObtenido.getDescuento(), equalTo(0.90));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaAsignarPrecioYDescuentoAUnSupermercadoProducto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);

        SupermercadoProducto supermercadoProductoBuscado = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        //Ejecucion
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoBuscado, 3000.00, 0.90);

        //Verficacion
        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(supermercadoProductoObtenido.getPrecio(), equalTo(3000.00));
        assertThat(supermercadoProductoObtenido.getDescuento(), equalTo(0.90));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaSobreescribirUnPrecioYUnDescuentoAUnSupermercadoProducto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);

        SupermercadoProducto supermercadoProductoBuscado = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        //Ejecucion
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoBuscado, 3000.00, 0.90);
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoBuscado, 1500.00, 0.80);

        //Verficacion
        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        assertThat(supermercadoProductoObtenido.getPrecio(), equalTo(1500.00));
        assertThat(supermercadoProductoObtenido.getDescuento(), equalTo(0.80));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanFiltrarTodosLosProductosDeUnaSubcategoriaPorPrecioEntreMilYDosMil(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioProducto.guardarProducto(otroProductoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, otroProductoMock);

        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        SupermercadoProducto otroSupermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Sprite'")
                .getSingleResult();

        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoObtenido, 2200.00, null);
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(otroSupermercadoProductoObtenido, 1200.00, null);

        List<String> precios = new ArrayList<>();
        precios.add("BETWEEN 1000 AND 2000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        List<Integer> ids = new ArrayList<>();
        ids.add(productoMock.getIdProducto());
        ids.add(otroProductoMock.getIdProducto());

        //Ejecucion
        List<SupermercadoProducto> productosEncontrados = repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);

        //Verficacion
        assertThat(1, equalTo(productosEncontrados.size()));
        assertThat(productosEncontrados.get(0).getProducto(), equalTo(otroProductoMock));
        assertThat(productosEncontrados.get(0).getPrecio(), equalTo(1200.00));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanFiltrarTodosLosProductosDeUnaSubcategoriaPorSupermercadoCoto() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        Supermercado supermercadoCarrefour = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioProducto.guardarProducto(otroProductoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoCoto);
        this.repositorioSupermercado.guardarSupermercado(supermercadoCarrefour);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoCoto, productoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoCarrefour, otroProductoMock);

        List<String> supermercados = new ArrayList<>();
        supermercados.add(supermercadoCoto.getIdSupermercado().toString());
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("supermercado_id", supermercados);

        List<Integer> ids = new ArrayList<>();
        ids.add(productoMock.getIdProducto());
        ids.add(otroProductoMock.getIdProducto());

        //Ejecucion
        List<SupermercadoProducto> productosEncontrados = repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);

        //Verficacion
        assertThat(1, equalTo(productosEncontrados.size()));
        assertThat(productosEncontrados.get(0).getProducto(), equalTo(productoMock));
        assertThat(productosEncontrados.get(0).getSupermercado(), equalTo(supermercadoCoto));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanFiltrarTodosLosProductosDeUnaSubcategoriaPorDescuentoIgualAlCincoPorciento(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioProducto.guardarProducto(otroProductoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, otroProductoMock);

        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        SupermercadoProducto otroSupermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Sprite'")
                .getSingleResult();

        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoObtenido, null, 0.95);
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(otroSupermercadoProductoObtenido, null, 0.90);

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("descuento", descuentos);

        List<Integer> ids = new ArrayList<>();
        ids.add(productoMock.getIdProducto());
        ids.add(otroProductoMock.getIdProducto());

        //Ejecucion
        List<SupermercadoProducto> productosEncontrados = repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);

        //Verficacion
        assertThat(1, equalTo(productosEncontrados.size()));
        assertThat(productosEncontrados.get(0).getProducto(), equalTo(productoMock));
        assertThat(productosEncontrados.get(0).getDescuento(), equalTo(0.95));
    }

    @Test
    @Transactional
    @Rollback
    public void queSiNoSeEncuentranProductosDeUnaSubcategoriaPorDescuentoIgualAlCincoPorcientoDevuelvaVacio(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioProducto.guardarProducto(otroProductoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, otroProductoMock);

        SupermercadoProducto supermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Coca Cola'")
                .getSingleResult();

        SupermercadoProducto otroSupermercadoProductoObtenido = (SupermercadoProducto) this.sessionFactory.getCurrentSession()
                .createQuery("FROM SupermercadoProducto WHERE producto.nombre = 'Sprite'")
                .getSingleResult();

        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(supermercadoProductoObtenido, null, 0.90);
        this.repositorioSupermercadoProducto.asignarPrecioYDescuentoAUnSupermercadoProducto(otroSupermercadoProductoObtenido, null, 0.90);

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("descuento", descuentos);

        List<Integer> ids = new ArrayList<>();
        ids.add(productoMock.getIdProducto());
        ids.add(otroProductoMock.getIdProducto());

        //Ejecucion
        List<SupermercadoProducto> productosEncontrados = repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);

        //Verficacion
        assertThat(0, equalTo(productosEncontrados.size()));
        assertThat(productosEncontrados, empty());
    }
}

