package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class ControladorProductoBuscadoTest {

    private ServicioBusqueda servicioBusqueda;
    private ControladorProductoBuscado controladorProductoBuscado;

    @BeforeEach
    public void init() {
        this.servicioBusqueda = mock(ServicioBusqueda.class);
        this.controladorProductoBuscado = new ControladorProductoBuscado(this.servicioBusqueda);
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestrenLosProductosDelTipoGaseosas() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();
        List<Producto> productosMock = new ArrayList<>();
        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);

        when(this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, null, "1")).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null, "");

        //Validacion
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestreUnMensajeDeErrorPorqueNoEncontroProductos() {
        //Preparacion
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(null);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null, "");

        //Validacion
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Sin resultados"));
    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioMenorAMilSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setPrecio(900.00);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setPrecio(800.00);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> precios = new ArrayList<>();
        precios.add("menor_a_1000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2,3"))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", subcategoriaStr, null, null, null, null, null, precios , null, "1,2,3", "");

        //Verificacion
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), equalTo(supermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getPrecio(), equalTo(otroSupermercadoProductoMock.getPrecio()));
    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioMayorATresmilSeMuestreElMensajeDeQueNoSeEncontraronProductos(){
        //Preparacion
        List<String> precios = new ArrayList<>();
        precios.add("mayor_a_3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        List<SupermercadoProducto> supermercadoProductoList = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq(null))).thenReturn(supermercadoProductoList);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, precios, null, null, "");

        //Validacion
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Sin resultados"));

    }

    @Test
    public void queAlHacerClickEnElCheckboxDeDescuentoDelCincoPorcientoSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
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

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2,3"))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, descuentos, null, null, null , null, "1,2,3", "");

        //Verificacion
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getDescuento(), equalTo(supermercadoProductoMock.getDescuento()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getDescuento(), equalTo(otroSupermercadoProductoMock.getDescuento()));


    }

}
