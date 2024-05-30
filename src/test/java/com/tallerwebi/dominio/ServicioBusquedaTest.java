package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioProductoImpl;
import com.tallerwebi.presentacion.ControladorProductoBuscado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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

    @BeforeEach
    public void init(){
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.servicioBusqueda = new ServicioBusquedaImpl(this.repositorioProducto);
    }

    @Test
    public void queSePuedaConsultarUnProductoPorNombre(){
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
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
        productosMock.add(new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Sprite", 1500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
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
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 4000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Sprite", 3500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));

        List<String> precios = new ArrayList<>();
        precios.add("> 3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioProducto.buscarProductosConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq("1,2"))).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroSupermercadoConLaCondicionCoto(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        List<Producto> productosMock = new ArrayList<>();
        Producto productoMock = new Producto("Coca Cola", 4000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "", null);
        Producto segundoProductoMock = new Producto("Sprite", 3500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", null);
        productosMock.add(productoMock);
        productosMock.add(segundoProductoMock);

        Supermercado supermercadoMock = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoMock.setIdSupermercado(2);

        productoMock.setSupermercado(supermercadoMock);
        segundoProductoMock.setSupermercado(supermercadoMock);

        List<String> supermercados = new ArrayList<>();
        supermercados.add("2");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("supermercado_id", supermercados);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioProducto.buscarProductosConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq("1,2"))).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
        assertThat(productosObtenidos.get(0).getSupermercado(), equalTo(supermercadoMock));
        assertThat(productosObtenidos.get(1).getSupermercado(), equalTo(supermercadoMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroDescuentoConLaCondicionCincoPorciento(){
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        List<Producto> productosMock = new ArrayList<>();
        Producto productoMock = new Producto("Coca Cola", 4000.0, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "", 3800.0);
        Producto segundoProductoMock = new Producto("Sprite", 3500.0, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", 3325.0);
        productosMock.add(productoMock);
        productosMock.add(segundoProductoMock);

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("descuento", descuentos);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioProducto.buscarProductosConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq("1,2"))).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
        assertThat(productoMock.getPrecio() * (Double.parseDouble(descuentos.get(0))), equalTo(productosObtenidos.get(0).getDescuento()));
        assertThat(segundoProductoMock.getPrecio() * (Double.parseDouble(descuentos.get(0))), equalTo(productosObtenidos.get(1).getDescuento()));

    }

}
