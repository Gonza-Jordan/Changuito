package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(this.servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, null, "1")).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null, "", request);

        //Validacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestreUnMensajeDeErrorPorqueNoEncontroProductos() {
        //Preparacion
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(null);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null, "", request);

        //Validacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Sin resultados"));
    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioEntreMilYDosMilSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setPrecio(1200.00);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setPrecio(1800.00);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> precios = new ArrayList<>();
        precios.add("BETWEEN 1000 AND 2000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", subcategoriaStr, null, null, null, null, null, precios , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), equalTo(supermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getPrecio(), equalTo(otroSupermercadoProductoMock.getPrecio()));
    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioEntreDosMilYTresMilSeMuestreElMensajeDeQueNoSeEncontraronProductos(){
        //Preparacion
        List<String> precios = new ArrayList<>();
        precios.add("BETWEEN 2000 AND 3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        List<SupermercadoProducto> supermercadoProductoList = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq(null))).thenReturn(supermercadoProductoList);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, precios, null, null, "", request);

        //Validacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Sin resultados"));

    }

    @Test
    public void queAlHacerClickEnElCheckboxDeDescuentoDelCincoPorcientoSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
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
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, descuentos, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getDescuento(), equalTo(supermercadoProductoMock.getDescuento()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getDescuento(), equalTo(otroSupermercadoProductoMock.getDescuento()));

    }

    @Test
    public void queAlHacerClickEnElCheckboxDeSupermercadoCotoSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoCoto.setIdSupermercado(1);

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCoto);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCoto);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> supermercados = new ArrayList<>();
        supermercados.add(supermercadoCoto.getIdSupermercado().toString());
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("supermercado_id", supermercados);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, supermercados, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getSupermercado(), equalTo(supermercadoProductoMock.getSupermercado()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getSupermercado(), equalTo(otroSupermercadoProductoMock.getSupermercado()));

    }

    @Test
    public void queAlHacerClickEnElSelectDeOrdenarPorPrecioDeMenorAMayorSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoCoto.setIdSupermercado(1);

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCoto);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setPrecio(1000.00);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCoto);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setPrecio(2000.00);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        String ordenar = "menor_a_mayor";

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar)).thenReturn(supermercadoProductoListMock);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", ordenar, request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), equalTo(supermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getPrecio(), equalTo(otroSupermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), lessThan(otroSupermercadoProductoMock.getPrecio()));

    }

    @Test
    public void queAlHacerClickEnElSelectDeOrdenarPorPrecioDeMayorAMenorSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoCoto.setIdSupermercado(1);

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCoto);
        supermercadoProductoMock.setProducto(productoMock);
        supermercadoProductoMock.setPrecio(2000.00);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCoto);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);
        otroSupermercadoProductoMock.setPrecio(1000.00);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        String ordenar = "mayor_a_menor";

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar)).thenReturn(supermercadoProductoListMock);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", ordenar, request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), equalTo(supermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(1).getPrecio(), equalTo(otroSupermercadoProductoMock.getPrecio()));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getPrecio(), greaterThan((otroSupermercadoProductoMock.getPrecio())));

    }

    @Test
    public void queElFiltroDescuentosMuestreLosDescuentosDeLosProductosDeLaSubcategoriaGaseosas() {
        //Preparacion
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
        otroSupermercadoProductoMock.setDescuento(0.90);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<Double> descuentosEsperados = new ArrayList<>();
        descuentosEsperados.add(0.95);
        descuentosEsperados.add(0.90);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosEsperados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("descuentos"), equalTo(descuentosEsperados));

    }

    @Test
    public void queElFiltroDescuentosMuestreLosDescuentosDeLosProductosDeLaSubcategoriaGaseosasAunSiSeSeleccionaElDescuentoDelCincoPorciento() {
        //Preparacion
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
        otroSupermercadoProductoMock.setDescuento(0.90);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<Double> descuentosEsperados = new ArrayList<>();
        descuentosEsperados.add(0.95);
        descuentosEsperados.add(0.90);

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("descuento", descuentos);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosEsperados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("descuentos")).thenReturn(descuentosEsperados);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, descuentos, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(1));
        assertThat(mav.getModel().get("productos"), equalTo(otraSupermercadoProductoListMock));
        assertThat(mav.getModel().get("descuentos"), equalTo(descuentosEsperados));

    }

    @Test
    public void queElFiltroSupermercadosMuestreLosSupermercadosDeLosProductosDeLaSubcategoriaGaseosas() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCarrefour = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCoto);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<Supermercado> supermercadosEsperados = new ArrayList<>();
        supermercadosEsperados.add(supermercadoCarrefour);
        supermercadosEsperados.add(supermercadoCoto);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarSupermercados(supermercadoProductoListMock)).thenReturn(supermercadosEsperados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("supermercados"), equalTo(supermercadosEsperados));

    }

    @Test
    public void queElFiltroSupermercadosMuestreLosSupermercadosDeLosProductosDeLaSubcategoriaGaseosasAunSiSeSeleccionaElSupermercadoCoto() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCarrefour = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
        Supermercado supermercadoCoto = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        supermercadoCarrefour.setIdSupermercado(1);
        supermercadoCoto.setIdSupermercado(2);

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCoto);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);


        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<Supermercado> supermercadosEsperados = new ArrayList<>();
        supermercadosEsperados.add(supermercadoCarrefour);
        supermercadosEsperados.add(supermercadoCoto);

        List<String> supermercados = new ArrayList<>();
        supermercados.add(supermercadoCoto.getIdSupermercado().toString());
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("supermercado_id", supermercados);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarSupermercados(supermercadoProductoListMock)).thenReturn(supermercadosEsperados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("supermercados")).thenReturn(supermercadosEsperados);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, supermercados, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(1));
        assertThat(mav.getModel().get("productos"), equalTo(otraSupermercadoProductoListMock));
        assertThat(mav.getModel().get("supermercados"), equalTo(supermercadosEsperados));

    }

    @Test
    public void queElFiltroPreciosMuestreLosPreciosEntreRangosDeLosProductosDeLaSubcategoriaGaseosas() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCarrefour = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> preciosSinFormatear = new ArrayList<>();
        preciosSinFormatear.add("1000.00");
        preciosSinFormatear.add("1333.33");
        preciosSinFormatear.add("1666.66");
        preciosSinFormatear.add("2000.00");

        List<String> preciosFormateados = new ArrayList<>();
        preciosFormateados.add("1000");
        preciosFormateados.add("1333");
        preciosFormateados.add("1667");
        preciosFormateados.add("2000");

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock)).thenReturn(preciosSinFormatear);
        when(this.servicioBusqueda.formatearPrecios(preciosSinFormatear)).thenReturn(preciosFormateados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("precios"), equalTo(preciosSinFormatear));
        assertThat(mav.getModel().get("preciosFormateados"), equalTo(preciosFormateados));

    }

    @Test
    public void queElFiltroPreciosMuestreLosPreciosEntreRangosDeLosProductosDeLaSubcategoriaGaseosasAunSiSeSeleccionaRangoEntreMilYDosMil() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoCarrefour = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoCarrefour);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<String> preciosSinFormatear = new ArrayList<>();
        preciosSinFormatear.add("1000.00");
        preciosSinFormatear.add("1333.33");
        preciosSinFormatear.add("1666.66");
        preciosSinFormatear.add("2000.00");

        List<String> preciosFormateados = new ArrayList<>();
        preciosFormateados.add("1000");
        preciosFormateados.add("1333");
        preciosFormateados.add("1667");
        preciosFormateados.add("2000");

        List<String> precios = new ArrayList<>();
        precios.add("BETWEEN 1000 AND 2000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock)).thenReturn(preciosSinFormatear);
        when(this.servicioBusqueda.formatearPrecios(preciosSinFormatear)).thenReturn(preciosFormateados);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("precios")).thenReturn(preciosSinFormatear);
        when(request.getSession().getAttribute("preciosFormateados")).thenReturn(preciosFormateados);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, precios , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(1));
        assertThat(mav.getModel().get("productos"), equalTo(otraSupermercadoProductoListMock));
        assertThat(mav.getModel().get("precios"), equalTo(preciosSinFormatear));
        assertThat(mav.getModel().get("preciosFormateados"), equalTo(preciosFormateados));

    }

    @Test
    public void siNoHayDescuentosDeLosProductosDeLaSubcategoriaGaseosasSeDebeCargarVacioEnElModeloDescuentosDeLaVista() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<Double> descuentosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosVacios);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("descuentos"), equalTo(descuentosVacios));

    }

    @Test
    public void siNoHaySupermercadosDeLosProductosDeLaSubcategoriaGaseosasSeDebeCargarVacioEnElModeloSupermercadosDeLaVista() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<Double> supermercadosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(supermercadosVacios);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("supermercados"), equalTo(supermercadosVacios));

    }

    @Test
    public void siNoHayPreciosDeLosProductosDeLaSubcategoriaGaseosasSeDebeCargarVacioEnElModeloPreciosDeLaVista() {
        //Preparacion
        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");

        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<Double> preciosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(preciosVacios);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("precios"), equalTo(preciosVacios));

    }


}
