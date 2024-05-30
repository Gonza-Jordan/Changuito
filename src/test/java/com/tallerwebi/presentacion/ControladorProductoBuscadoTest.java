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
        // Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(productosMock);

        // Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null);

        // Validacion
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestreUnMensajeDeErrorPorqueNoEncontroProductosDeEsaSubcategoria() {
        // Preparacion
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(null);

        // Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, null, null, null);

        // Validacion
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Productos de esa subcategoría no encontrados"));
    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioMenorAMilSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        // Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 900, "123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Sprite", 500, "456", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Fanta", 800, "789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));

        List<String> precios = new ArrayList<>();
        precios.add("menor_a_1000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precios", precios);

        // Capturador de argumentos
        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2,3"))).thenReturn(productosMock);

        // Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, precios , null, "1,2,3");

        // Verificacion
        assertThat(((List<Producto>) mav.getModel().get("productos")).size(), equalTo(3));
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));

    }

    @Test
    public void queAlHacerClickEnElCheckboxDePrecioMayorATresmilSeMuestreElMensajeDeQueNoSeEncontraronProductos(){
        // Preparacion
        List<String> precios = new ArrayList<>();
        precios.add("mayor_a_3000");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precios", precios);

        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq(null))).thenReturn(null);

        // Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, null, null, null, precios, null, null);

        // Validacion
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Sin resultados"));

    }

    @Test
    public void queAlHacerClickEnElCheckboxDeDescuentoDelCincoPorcientoSeMuestrenLosProductosFiltradosDeLaSubcategoriaGaseosas() {
        // Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 900, "123", Categoria.Bebidas, Subcategoria.Gaseosas, "", 855.0));
        productosMock.add(new Producto("Sprite", 500, "456", Categoria.Bebidas, Subcategoria.Gaseosas, "", 475.0));
        productosMock.add(new Producto("Fanta", 800, "789", Categoria.Bebidas, Subcategoria.Gaseosas, "", 760.0));

        List<String> descuentos = new ArrayList<>();
        descuentos.add("0.95");
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("precios", descuentos);

        // Capturador de argumentos
        ArgumentCaptor<Map<String, List<String>>> filtrosCaptor = ArgumentCaptor.forClass(Map.class);
        when(this.servicioBusqueda.consultarProductosConFiltros(eq("Gaseosas"), filtrosCaptor.capture(), eq("1,2,3"))).thenReturn(productosMock);

        // Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.buscarProductos("Bebidas", "Gaseosas", null, null, descuentos, null, null, null, null, "1,2,3");

        // Verificacion
        assertThat(((List<Producto>) mav.getModel().get("productos")).size(), equalTo(3));
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));

    }
    @Test
    public void queAlIngresarArrozMuestreTodaLaLista(){
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Arroz Ala", 900, "123", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Arroz gallo", 500, "456", Categoria.Bebidas, Subcategoria.Gaseosas, ""));
        productosMock.add(new Producto("Fanta", 800, "789", Categoria.Bebidas, Subcategoria.Gaseosas, ""));

    }

}
