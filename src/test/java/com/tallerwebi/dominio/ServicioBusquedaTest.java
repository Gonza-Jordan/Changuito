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
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBusquedaTest {
    private ServicioBusqueda servicioBusqueda;
    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;
    private Producto productoMock;
    private Producto otroProductoMock;
    private Producto otroProductoMockMas;
    private Supermercado supermercadoMock;
    private Supermercado otroSupermercadoMock;
    private SupermercadoProducto supermercadoProductoMock;
    private SupermercadoProducto otroSupermercadoProductoMock;
    private SupermercadoProducto otroSupermercadoProductoMockMas;
    private List<SupermercadoProducto> supermercadoProductoListMock;
    private Marca marca = new Marca("Marca");

    @BeforeEach
    public void init() {
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.repositorioSupermercadoProducto = mock(RepositorioSupermercadoProducto.class);
        this.servicioBusqueda = new ServicioBusquedaImpl(this.repositorioProducto, this.repositorioSupermercadoProducto);

        this.productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg", marca);
        this.otroProductoMock = new Producto("Sprite", "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", marca);
        this.otroProductoMockMas = new Producto("Fanta", "111111111", Categoria.Bebidas, Subcategoria.Gaseosas, "", marca);

        this.supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
        this.otroSupermercadoMock = new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "");
        this.otroSupermercadoMock.setIdSupermercado(2);

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
    }

    @Test
    public void queSePuedaConsultarUnProductoPorNombre() {
        //Preparacion
        List<Producto> listProductoMock = new ArrayList<>();
        listProductoMock.add(productoMock);
        when(this.repositorioProducto.buscarProductoPorNombre(productoMock.getNombre())).thenReturn(listProductoMock);

        //Ejecucion
        List<Producto> productoObtenido = this.servicioBusqueda.consultarProductoPorNombre(productoMock.getNombre());

        //Verficacion
        assertThat(productoObtenido.get(0), equalTo(productoMock));
    }

    @Test
    public void queSePuedanConsultarTodosLosProductosDeUnaSubcategoria() {
        //Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(productoMock);
        productosMock.add(otroProductoMock);
        when(this.repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas);

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroPreciosConLaCondicionEntreMilYDosMil() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        supermercadoProductoMock.setPrecio(1500.00);
        otroSupermercadoProductoMock.setPrecio(1800.00);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        List<String> precios = new ArrayList<>();
        precios.add("BETWEEN 1000 AND 2000");
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
    public void queSePuedanConsultarLosProductosConElFiltroSupermercadoConLaCondicionCoto() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        supermercadoProductoListMock.add(otroSupermercadoProductoMock);
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
        assertThat(supermercadosProductosObtenidos.get(0).getSupermercado(), equalTo(otroSupermercadoMock));
        assertThat(supermercadosProductosObtenidos.get(1).getSupermercado(), equalTo(otroSupermercadoMock));
    }

    @Test
    public void queSePuedanConsultarLosProductosConElFiltroDescuentoConLaCondicionCincoPorciento() {
        //Preparacion
        String subcategoriaStr = Subcategoria.Gaseosas.toString();

        supermercadoProductoMock.setDescuento(0.95);
        otroSupermercadoProductoMock.setDescuento(0.95);

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

    @Test
    public void queSePuedanOrdenarLosProductosConElSelectDeOrdenarPorPrecioDeMenorAMayor() {
        //Preparacion
        supermercadoProductoMock.setPrecio(4000.00);
        otroSupermercadoProductoMock.setPrecio(4500.00);
        otroSupermercadoProductoMockMas.setPrecio(3000.00);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMockMas);

        String ordenar = "menor_a_mayor";

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar);

        //Verficacion
        assertThat(supermercadosProductosObtenidos.size(), equalTo(3));
        assertThat(supermercadosProductosObtenidos.get(0).getProducto(), equalTo(otroSupermercadoProductoMockMas.getProducto()));
        assertThat(supermercadosProductosObtenidos.get(1).getProducto(), equalTo(supermercadoProductoMock.getProducto()));
        assertThat(supermercadosProductosObtenidos.get(2).getProducto(), equalTo(otroSupermercadoProductoMock.getProducto()));

    }

    @Test
    public void queSePuedanOrdenarLosProductosConElSelectDeOrdenarPorPrecioDeMayorAMenor() {
        //Preparacion
        supermercadoProductoMock.setPrecio(5000.00);
        otroSupermercadoProductoMock.setPrecio(6000.00);
        otroSupermercadoProductoMockMas.setPrecio(7000.00);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMockMas);

        String ordenar = "mayor_a_menor";

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar);

        //Verficacion
        assertThat(supermercadosProductosObtenidos.size(), equalTo(3));
        assertThat(supermercadosProductosObtenidos.get(0).getProducto(), equalTo(otroSupermercadoProductoMockMas.getProducto()));
        assertThat(supermercadosProductosObtenidos.get(1).getProducto(), equalTo(otroSupermercadoProductoMock.getProducto()));
        assertThat(supermercadosProductosObtenidos.get(2).getProducto(), equalTo(supermercadoProductoMock.getProducto()));
    }

    @Test
    public void queSePuedanOrdenarLosProductosConElSelectDeOrdenarPorSupermercadoDeAZ() {
        //Preparacion
        supermercadoMock.setNombre("Carrefour");
        otroSupermercadoMock.setNombre("Coto");

        supermercadoProductoMock.setSupermercado(supermercadoMock);
        otroSupermercadoProductoMock.setSupermercado(otroSupermercadoMock);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        String ordenar = "supermercado_ascendente";

        //Ejecucion
        List<SupermercadoProducto> supermercadosProductosObtenidos = this.servicioBusqueda.ordenarProductos(supermercadoProductoListMock, ordenar);

        //Verficacion
        assertThat(supermercadosProductosObtenidos.size(), equalTo(2));
        assertThat(supermercadosProductosObtenidos.get(0).getSupermercado(), equalTo(supermercadoMock));
        assertThat(supermercadosProductosObtenidos.get(1).getSupermercado(), equalTo(otroSupermercadoMock));
    }

    @Test
    public void queSePuedanConsultarLosDescuentosQueTienenLosProductos() {
        // Preparacion
        supermercadoProductoMock.setDescuento(0.95);
        otroSupermercadoProductoMock.setDescuento(0.80);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        // Ejecucion
        List<Double> descuentosObtenidos = this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock);

        // Verficacion
        assertThat(descuentosObtenidos.size(), equalTo(2));
        assertThat(descuentosObtenidos.get(0), equalTo(supermercadoProductoMock.getDescuento()));
        assertThat(descuentosObtenidos.get(1), equalTo(otroSupermercadoProductoMock.getDescuento()));

    }

    @Test
    public void queSePuedanConsultarLosPreciosQueTienenLosProductos() {
        // Preparacion
        supermercadoProductoMock.setPrecio(1000.0);
        otroSupermercadoProductoMock.setPrecio(2000.0);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        // Ejecucion
        List<String> preciosObtenidos = this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock);

        // Verficacion
        assertThat(preciosObtenidos.size(), equalTo(4));
        assertThat(preciosObtenidos.get(0), equalTo(supermercadoProductoMock.getPrecio().toString()));
        assertThat(preciosObtenidos.get(1), equalTo(String.valueOf(1000.0 + ((2000.0 - 1000.0) / 3.0))));
        assertThat(preciosObtenidos.get(2), equalTo(String.valueOf(1000.0 + (2 * (2000.0 - 1000.0) / 3.0))));
        assertThat(preciosObtenidos.get(3), equalTo(otroSupermercadoProductoMock.getPrecio().toString()));

    }

    @Test
    public void queSePuedanFormatearLosPreciosQueTienenLosProductos() {
        // Preparacion
        supermercadoProductoMock.setPrecio(1000.0);
        otroSupermercadoProductoMock.setPrecio(2000.0);

        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);

        // Ejecucion
        List<String> preciosObtenidos = this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock);
        List<String> preciosFormateados = this.servicioBusqueda.formatearPrecios(preciosObtenidos);

        // Verficacion
        assertThat(preciosFormateados.size(), equalTo(4));
        assertThat(preciosFormateados.get(0), equalTo("1000"));
        assertThat(preciosFormateados.get(1), equalTo("1333"));
        assertThat(preciosFormateados.get(2), equalTo("1667"));
        assertThat(preciosFormateados.get(3), equalTo("2000"));
    }

    @Test
    public void queSiSeConsultaElPrecioDeUnSoloProductoDevuelvaComoPreciosElMismoValor() {
        // Preparacion
        supermercadoProductoMock.setPrecio(1000.0);

        supermercadoProductoListMock.add(supermercadoProductoMock);

        // Ejecucion
        List<String> preciosObtenidos = this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock);

        // Verficacion
        assertThat(preciosObtenidos.size(), equalTo(4));
        assertThat(preciosObtenidos.get(0), equalTo("1000.0"));
        assertThat(preciosObtenidos.get(1), equalTo("1000.0"));
        assertThat(preciosObtenidos.get(2), equalTo("1000.0"));
        assertThat(preciosObtenidos.get(3), equalTo("1000.0"));
    }

    @Test
    public void queAlConsultarLosSupermercadosALosQuePertenecenLosProductosDevuelvaVacioSiNoHayProductos() {
        // Preparacion
        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        List<Supermercado> supermercadosVacios = new ArrayList<>();

        // Ejecucion
        List<Supermercado> supermercadosObtenidos = this.servicioBusqueda.consultarSupermercados(supermercadoProductoListMock);

        // Verificacion
        assertThat(supermercadosObtenidos.size(), equalTo(0));
        assertThat(supermercadosObtenidos, equalTo(supermercadosVacios));
    }

    @Test
    public void queAlConsultarLosDescuentosDeLosProductosDevuelvaVacioSiNoHayProductos() {
        // Preparacion
        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        List<Double> descuentosVacios = new ArrayList<>();

        // Ejecucion
        List<Double> descuentosObtenidos = this.servicioBusqueda.consultarDescuentos(supermercadoProductoListMock);

        // Verificacion
        assertThat(descuentosObtenidos.size(), equalTo(0));
        assertThat(descuentosObtenidos, equalTo(descuentosVacios));
    }

    @Test
    public void queAlConsultarLosPreciosDeLosProductosDevuelvaVacioSiNoHayProductos() {
        // Preparacion
        List<SupermercadoProducto> supermercadoProductoListMock = new ArrayList<>();
        List<String> preciosVacios = new ArrayList<>();

        // Ejecucion
        List<String> preciosObtenidos = this.servicioBusqueda.consultarPrecios(supermercadoProductoListMock);

        // Verificacion
        assertThat(preciosObtenidos.size(), equalTo(0));
        assertThat(preciosObtenidos, equalTo(preciosVacios));
    }

}
