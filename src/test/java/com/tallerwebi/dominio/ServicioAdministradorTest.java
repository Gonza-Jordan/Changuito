package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void queSePuedaCrearUnCombo() throws CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException, CantidadInvalidaException, FechaInvalidaException {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        String fechaInicio = "2023-01-01";
        String fechaFin = "2023-12-31";
        Integer cantidadVendida = 2;
        Integer cantidadCobrada = 1;

        //Ejecucion
        Combo comboCreado = this.servicioAdministrador.crearCombo(supermercadoProductoMock, fechaInicio, fechaFin, cantidadVendida, cantidadCobrada);

        //Verficacion
        assertThat(comboCreado, notNullValue());
        assertThat(comboCreado.getProducto().getProducto(), equalTo(productoMock));
        assertThat(comboCreado.getProducto().getSupermercado(), equalTo(supermercadoMock));
        assertThat(comboCreado.getCantidadVendida(), equalTo(2));
        assertThat(comboCreado.getCantidadCobrada(), equalTo(1));

    }


    @Test
    public void queAlCrearUnComboLanceLaExcepcionCantidadVendidaNoPuedeSerMenorOIgualALaCobradaException() {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        String fechaInicio = "2023-01-01";
        String fechaFin = "2023-12-31";
        Integer cantidadVendida = 2;
        Integer cantidadCobrada = 3;

        //Ejecución y verificación
        assertThrows(CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException.class, () -> {
            servicioAdministrador.crearCombo(supermercadoProductoMock, fechaInicio, fechaFin, cantidadVendida, cantidadCobrada);
        });
    }

    @Test
    public void queAlCrearUnComboLanceLaExcepcionCantidadInvalidaException() {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        String fechaInicio = "2023-01-01";
        String fechaFin = "2023-12-31";
        Integer cantidadVendida = -1;
        Integer cantidadCobrada = 3;

        //Ejecución y verificación
        assertThrows(CantidadInvalidaException.class, () -> {
            servicioAdministrador.crearCombo(supermercadoProductoMock, fechaInicio, fechaFin, cantidadVendida, cantidadCobrada);
        });
    }

    @Test
    public void queAlCrearUnComboLanceLaExcepcionFechaInvalidaException() {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        String fechaInicio = "2023-12-01";
        String fechaFin = "2023-11-30";
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);

        Integer cantidadVendida = 5;
        Integer cantidadCobrada = 3;

        //Ejecución y verificación
        assertThrows(FechaInvalidaException.class, () -> {
            servicioAdministrador.crearCombo(supermercadoProductoMock, fechaInicioDate.toString(), fechaFinDate.toString(), cantidadVendida, cantidadCobrada);
        });
    }

    @Test
    public void queSePuedaCrearUnPaquete() throws FechaInvalidaException, DescuentoInvalidoException {
        //Preparacion
        supermercadoProductoMock.setPrecio(1000.00);
        otroSupermercadoProductoMock.setPrecio(1500.00);
        supermercadoProductoListMock.add(supermercadoProductoMock);
        supermercadoProductoListMock.add(otroSupermercadoProductoMock);
        String fechaInicio = "2023-11-01";
        String fechaFin = "2023-12-01";
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);
        Double descuento = 20.0;
        String nombre = "Paquete";

        //Ejecucion
        Paquete paqueteCreado = this.servicioAdministrador.crearPaquete(supermercadoProductoListMock, fechaInicioDate.toString(), fechaFinDate.toString(), descuento, nombre);

        //Verficacion
        assertThat(paqueteCreado, notNullValue());
        assertThat(paqueteCreado.getProductos().get(0).getProducto(), equalTo(productoMock));
        assertThat(paqueteCreado.getProductos().get(1).getProducto(), equalTo(otroProductoMock));
        assertThat(paqueteCreado.getDescuento(), equalTo(20.0));
        assertThat(paqueteCreado.getNombre(), equalTo("Paquete"));

    }

    @Test
    public void queAlCrearUnPaqueteLanceLaExcepcionFechaInvalidaException() {
        //Preparacion
        String fechaInicio = "2023-12-01";
        String fechaFin = "2023-11-30";
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);
        Double descuento = 20.0;
        String nombre = "Paquete";

        //Ejecución y verificación
        assertThrows(FechaInvalidaException.class, () -> {
            servicioAdministrador.crearPaquete(supermercadoProductoListMock, fechaInicioDate.toString(), fechaFinDate.toString(), descuento, nombre);
        });
    }

    @Test
    public void queAlCrearUnPaqueteLanceLaExcepcionDescuentoInvalidoException() {
        //Preparacion
        String fechaInicio = "2023-11-01";
        String fechaFin = "2023-12-01";
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);
        Double descuento = 133.0;
        String nombre = "Paquete";

        //Ejecución y verificación
        assertThrows(DescuentoInvalidoException.class, () -> {
            servicioAdministrador.crearPaquete(supermercadoProductoListMock, fechaInicioDate.toString(), fechaFinDate.toString(), descuento, nombre);
        });
    }

    @Test
    public void queAlActualizarElPrecioDeUnProductoLanceLaExcepcionSinPrecioNiDescuentoException() {
        //Ejecución y verificación
        assertThrows(SinPrecioNiDescuentoException.class, () -> {
            servicioAdministrador.actualizarPrecioYDescuento(supermercadoProductoMock, null, null);
        });
    }

    @Test
    public void queAlActualizarElPrecioDeUnProductoLanceLaExcepcionDescuentoInvalidoException() {
        //Preparacion
        Double precioNuevo = 1500.0;
        Double descuentoNuevo = 188.0;

        //Ejecución y verificación
        assertThrows(DescuentoInvalidoException.class, () -> {
            servicioAdministrador.actualizarPrecioYDescuento(supermercadoProductoMock, precioNuevo, descuentoNuevo);
        });
    }

    @Test
    public void queAlActualizarElPrecioDeUnProductoLanceLaExcepcionPrecioInvalidoException() {
        //Preparacion
        Double precioNuevo = -2000.0;
        Double descuentoNuevo = 20.0;

        //Ejecución y verificación
        assertThrows(PrecioInvalidoException.class, () -> {
            servicioAdministrador.actualizarPrecioYDescuento(supermercadoProductoMock, precioNuevo, descuentoNuevo);
        });
    }









}
