package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class ControladorProductoBuscadoTest {

    private ServicioBusqueda servicioBusqueda;
    private ControladorProductoBuscado controladorProductoBuscado;

    private Producto productoMock;
    private Producto otroProductoMock;
    private Producto otroProductoMockMas;
    private Supermercado supermercadoMock;
    private Supermercado otroSupermercadoMock;
    private SupermercadoProducto supermercadoProductoMock;
    private SupermercadoProducto otroSupermercadoProductoMock;
    private SupermercadoProducto otroSupermercadoProductoMockMas;
    private List<SupermercadoProducto> supermercadoProductoListMock;
    private HttpServletRequest request;
    private HttpSession session;
    private List<String> precios;
    private List<String> descuentos;
    private List<String> supermercados;
    private List<String> marcas;
    private Map<String, List<String>> filtros;
    private Marca marcaCocaCola;
    private Marca marcaSprite;
    private Marca marcaFanta;


    @BeforeEach
    public void init() {
        this.servicioBusqueda = mock(ServicioBusqueda.class);
        this.controladorProductoBuscado = new ControladorProductoBuscado(this.servicioBusqueda);

        this.marcaCocaCola = new Marca("Coca Cola");
        this.marcaSprite = new Marca("Sprite");
        this.marcaFanta = new Marca("Fanta");

        this.productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaCocaCola);
        this.otroProductoMock = new Producto("Sprite", "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaSprite);
        this.otroProductoMockMas = new Producto("Fanta", "111111111", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaFanta);

        this.supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "");
        this.otroSupermercadoMock = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        this.supermercadoMock.setIdSupermercado(2);
        this.otroSupermercadoMock.setIdSupermercado(1);

        this.supermercadoProductoMock = new SupermercadoProducto();
        this.otroSupermercadoProductoMock = new SupermercadoProducto();
        this.otroSupermercadoProductoMockMas = new SupermercadoProducto();

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        supermercadoProductoMock.setProducto(productoMock);

        otroSupermercadoProductoMock.setSupermercado(otroSupermercadoMock);
        otroSupermercadoProductoMock.setProducto(otroProductoMock);

        otroSupermercadoProductoMockMas.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMockMas.setProducto(otroProductoMockMas);

        supermercadoProductoListMock = new ArrayList<>();
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        this.precios = new ArrayList<>();
        this.descuentos = new ArrayList<>();
        this.supermercados = new ArrayList<>();
        this.marcas = new ArrayList<>();
        this.filtros = new HashMap<>();

        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestrenLosProductosDelTipoGaseosas() {
        //Preparacion
        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq(null))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null, "", request);

        //Validacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestreUnMensajeDeErrorPorqueNoEncontroProductos() {
        //Preparacion
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(null);

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
        supermercadoProductoMock.setPrecio(1200.00);
        otroSupermercadoProductoMock.setPrecio(1800.00);

        precios.add("BETWEEN 1000 AND 2000");
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", Subcategoria.Gaseosas.toString(), null, null, null, null, null, precios , null, "1,2", "", request);

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
        precios.add("BETWEEN 2000 AND 3000");
        filtros.put("precio", precios);

        List<SupermercadoProducto> supermercadoProductoList = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq(null))).thenReturn(supermercadoProductoList);

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
        supermercadoProductoMock.setDescuento(0.95);
        otroSupermercadoProductoMock.setDescuento(0.95);

        descuentos.add("0.95");
        filtros.put("descuento", descuentos);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

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
        supermercados.add(otroSupermercadoMock.getIdSupermercado().toString());
        filtros.put("supermercado_id", supermercados);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);

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
    public void queAlIngresarArrozMuestreTodaLaLista(){
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Arroz Ala",  "1235248", Categoria.Bebidas, Subcategoria.Gaseosas,"",marcaCocaCola));
        productosMock.add(new Producto("Arroz gallo", "4562548", Categoria.Bebidas, Subcategoria.Gaseosas, "",marcaSprite));
        productosMock.add(new Producto("Fanta", "12566789", Categoria.Bebidas, Subcategoria.Gaseosas, "",marcaSprite));

        when(this.servicioBusqueda.consultarProductoPorNombre("Arroz")).thenReturn(productosMock);

        ModelAndView mav = this.controladorProductoBuscado.irAProductoBuscado("arroz");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/spring/productoFiltrado?productoAbuscar=" + "Arroz"+ "&productoIds=" + "null,null,null" );

        assertThat(mav.getView(), equalTo(redirectView));
    }


    @Test
    public void queAlHacerClickEnElCheckboxDeMarcaCocaColaSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        marcas.add("1");
        filtros.put("marca", marcas);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1"))).thenReturn(otraSupermercadoProductoListMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, marcas, null, null , null, "1", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(1));
        assertThat(mav.getModel().get("productos"), equalTo(otraSupermercadoProductoListMock));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).get(0).getProducto().getMarca(), equalTo(marcaCocaCola));

    }

    @Test
    public void queAlHacerClickEnElSelectDeOrdenarPorPrecioDeMenorAMayorSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        otroSupermercadoProductoMock.setPrecio(2000.00);

        String ordenar = "menor_a_mayor";

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar)).thenReturn(supermercadoProductoListMock);

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
        supermercadoProductoMock.setPrecio(2000.00);
        otroSupermercadoProductoMock.setPrecio(1000.00);

        String ordenar = "mayor_a_menor";

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar)).thenReturn(supermercadoProductoListMock);

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
        supermercadoProductoMock.setDescuento(0.95);
        otroSupermercadoProductoMock.setDescuento(0.90);

        List<Double> descuentosEsperados = new ArrayList<>();
        descuentosEsperados.add(0.95);
        descuentosEsperados.add(0.90);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosEsperados);

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
        supermercadoProductoMock.setDescuento(0.95);
        otroSupermercadoProductoMock.setDescuento(0.90);

        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<Double> descuentosEsperados = new ArrayList<>();
        descuentosEsperados.add(0.95);
        descuentosEsperados.add(0.90);

        descuentos.add("0.95");
        filtros.put("descuento", descuentos);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosEsperados);

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
        List<Supermercado> supermercadosEsperados = new ArrayList<>();
        supermercadosEsperados.add(supermercadoMock);
        supermercadosEsperados.add(otroSupermercadoMock);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarSupermercados(supermercadoProductoListMock)).thenReturn(supermercadosEsperados);

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
        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<Supermercado> supermercadosEsperados = new ArrayList<>();
        supermercadosEsperados.add(supermercadoMock);
        supermercadosEsperados.add(otroSupermercadoMock);

        supermercados.add(otroSupermercadoMock.getIdSupermercado().toString());
        filtros.put("supermercado_id", supermercados);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarSupermercados(supermercadoProductoListMock)).thenReturn(supermercadosEsperados);

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
    public void queElFiltroMarcasMuestreLasdMarcasDeLosProductosDeLaSubcategoriaGaseosas() {
        //Preparacion
        List<Marca> marcasEsperadas = new ArrayList<>();
        marcasEsperadas.add(marcaCocaCola);
        marcasEsperadas.add(marcaSprite);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarMarcas(supermercadoProductoListMock)).thenReturn(marcasEsperadas);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("marcas"), equalTo(marcasEsperadas));

    }

    @Test
    public void queElFiltroMarcasMuestreLasMarcasDeLosProductosDeLaSubcategoriaGaseosasAunSiSeSeleccionaLaMarcaCocaCola() {
        //Preparacion
        List<SupermercadoProducto> otraSupermercadoProductoListMock = new ArrayList<>();
        otraSupermercadoProductoListMock.add(supermercadoProductoMock);

        List<Marca> marcasEsperadas = new ArrayList<>();
        marcasEsperadas.add(marcaCocaCola);
        marcasEsperadas.add(marcaSprite);

        marcas.add("1");
        filtros.put("marca", marcas);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarMarcas(supermercadoProductoListMock)).thenReturn(marcasEsperadas);

        when(request.getSession().getAttribute("marcas")).thenReturn(marcasEsperadas);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, marcas, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(1));
        assertThat(mav.getModel().get("productos"), equalTo(otraSupermercadoProductoListMock));
        assertThat(mav.getModel().get("marcas"), equalTo(marcasEsperadas));

    }

    @Test
    public void queElFiltroPreciosMuestreLosPreciosEntreRangosDeLosProductosDeLaSubcategoriaGaseosas() {
        //Preparacion
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

        precios.add("BETWEEN 1000 AND 2000");
        filtros.put("precio", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(otraSupermercadoProductoListMock);
        when(this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock)).thenReturn(preciosSinFormatear);
        when(this.servicioBusqueda.formatearPrecios(preciosSinFormatear)).thenReturn(preciosFormateados);

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
        List<Double> descuentosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(descuentosVacios);

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
        List<Double> supermercadosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(supermercadosVacios);

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
        List<Double> preciosVacios = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock)).thenReturn(preciosVacios);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("precios"), equalTo(preciosVacios));

    }

    @Test
    public void siNoHayMarcasDeLosProductosDeLaSubcategoriaGaseosasSeDebeCargarVacioEnElModeloMarcasDeLaVista() {
        //Preparacion
        List<Marca> marcasVacias = new ArrayList<>();

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2"))).thenReturn(supermercadoProductoListMock);
        when(this.servicioBusqueda.consultarMarcas(supermercadoProductoListMock)).thenReturn(marcasVacias);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null , null, "1,2", "", request);

        //Verificacion
        assertThat(mav.getViewName(), equalTo("productoBuscado"));
        assertThat(((List<SupermercadoProducto>) mav.getModel().get("productos")).size(), equalTo(2));
        assertThat(mav.getModel().get("productos"), equalTo(supermercadoProductoListMock));
        assertThat(mav.getModel().get("marcas"), equalTo(marcasVacias));

    }


}
