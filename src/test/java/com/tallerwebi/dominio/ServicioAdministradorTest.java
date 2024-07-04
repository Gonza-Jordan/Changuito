package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SinIdProductoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioAdministradorTest {

    private ServicioAdministrador servicioAdministrador;
    private RepositorioAdministrador repositorioAdministrador;

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
    private Marca marcaCocaCola;
    private Marca marcaSprite;
    private Marca marcaFanta;


    @BeforeEach
    public void init() {
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.repositorioSupermercadoProducto = mock(RepositorioSupermercadoProducto.class);
        this.repositorioAdministrador = mock(RepositorioAdministrador.class);
        this.servicioAdministrador = new ServicioAdministradorImpl(this.repositorioAdministrador);

        this.marcaCocaCola = new Marca("Coca Cola");
        this.marcaSprite = new Marca("Sprite");
        this.marcaFanta = new Marca("Fanta");

        this.productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaCocaCola);
        this.otroProductoMock = new Producto("Sprite", "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaSprite);
        this.otroProductoMockMas = new Producto("Fanta", "111111111", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaFanta);

        this.supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "");
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
    public void queSePuedanBuscarProductosDeUnSupermercado() {
        //Preparacion
        supermercadoProductoListMock.add(supermercadoProductoMock);

        when(this.repositorioAdministrador.obtenerProductosDeUnSupermercado(supermercadoMock.getIdSupermercado())).thenReturn(supermercadoProductoListMock);

        //Ejecucion
        List<SupermercadoProducto> supermercadoProductos = this.servicioAdministrador.buscarProductosDeUnSupermercado(supermercadoMock.getIdSupermercado());

        //Verficacion
        assertThat(supermercadoProductos.size(), equalTo(1));
        assertThat(supermercadoProductos.get(0).getSupermercado().getIdSupermercado(), equalTo(supermercadoMock.getIdSupermercado()));
    }

    @Test
    public void queSePuedaBuscarUnSupermercadoProducto() throws SinIdProductoException {
        //Preparacion
        productoMock.setIdProducto(1);
        supermercadoMock.setIdSupermercado(1);

        when(this.repositorioAdministrador.buscarSupermercadoProducto(supermercadoMock.getIdSupermercado(), productoMock.getIdProducto())).thenReturn(supermercadoProductoMock);

        //Ejecucion
        SupermercadoProducto supermercadoProducto = this.servicioAdministrador.buscarSupermercadoProducto(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado());

        //Verficacion
        assertThat(supermercadoProducto, notNullValue());
        assertThat(supermercadoProducto.getSupermercado(), equalTo(supermercadoMock));
        assertThat(supermercadoProducto.getProducto(), equalTo(productoMock));
    }

    @Test
    public void queSePuedaBuscarUnSupermercadoProductoLanceUnaExcepcionSiNoSeSeleccionoUnProducto() {
        //Ejecución y verificación
        assertThrows(SinIdProductoException.class, () -> {
            servicioAdministrador.buscarSupermercadoProducto(null, supermercadoMock.getIdSupermercado());
        });
    }



}
