package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioProductoImpl;
import com.tallerwebi.presentacion.ControladorProductoBuscado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBusquedaTest {
    private ServicioBusqueda servicioBusqueda;
    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;

    @BeforeEach
    public void init(){
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.repositorioSupermercadoProducto = mock(RepositorioSupermercadoProducto.class);
        this.servicioBusqueda = new ServicioBusquedaImpl(this.repositorioProducto, this.repositorioSupermercadoProducto);
    }

    @Test
    public void queSePuedaConsultarUnProductoPorNombre(){
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
        List<Producto> listProductoMock = new ArrayList<>();
        listProductoMock.add(productoMock);
        when(this.repositorioProducto.buscarProductoPorNombre(productoMock.getNombre())).thenReturn(listProductoMock);

        //Ejecucion
        List <Producto> productoObtenido = this.servicioBusqueda.consultarProductoPorNombre(productoMock.getNombre());

        //Verficacion
        assertThat(productoObtenido.get(0), equalTo(productoMock));
    }

    @Test
    public void queSePuedanConsultarTodosLosProductosDeUnaSubcategoria(){
        //Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        when(this.repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas);

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroPreciosConLaCondicionMayorATresmil(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setPrecio(3500.00);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setPrecio(4500.00);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> precios = new ArrayList<>();
        precios.add("> 3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        List<Integer> productosIds = new ArrayList<>();
        productosIds.add(1);
        productosIds.add(2);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioSupermercadoProducto.buscarConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq(productosIds))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(supermercadosProductosObtenidos, equalTo(supermercadoProductoListMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroSupermercadoConLaCondicionCoto(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoMock.setIdSupermercado(2);

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> supermercados = new ArrayList<>();
        supermercados.add("2");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("supermercado_id", supermercados);

        List<Integer> productosIds = new ArrayList<>();
        productosIds.add(1);
        productosIds.add(2);


        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioSupermercadoProducto.buscarConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq(productosIds))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(supermercadosProductosObtenidos, equalTo(supermercadoProductoListMock));
        assertThat(supermercadosProductosObtenidos.get(0).getSupermercado(), equalTo(supermercadoMock));
        assertThat(supermercadosProductosObtenidos.get(1).getSupermercado(), equalTo(supermercadoMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroDescuentoConLaCondicionCincoPorciento(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setDescuento(0.95);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setDescuento(0.95);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("descuento", descuentos);

        List<Integer> productosIds = new ArrayList<>();
        productosIds.add(1);
        productosIds.add(2);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioSupermercadoProducto.buscarConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq(productosIds))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(supermercadosProductosObtenidos, equalTo(supermercadoProductoListMock));
        assertThat(supermercadosProductosObtenidos.get(0).getDescuento(), equalTo(supermercadoProductoMock.getDescuento()));
        assertThat(supermercadosProductosObtenidos.get(1).getDescuento(), equalTo(otroSupermercadoProductoMock.getDescuento()));

    }

}
