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
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 4000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Sprite", 3500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        List<String> precios = new ArrayList<>();
        precios.add("> 3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precios", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.repositorioProducto.buscarProductosConFiltros(eq(subcategoriaStr), filtrosCaptor.capture(), eq("1,2"))).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, "1,2");

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
    }

}
