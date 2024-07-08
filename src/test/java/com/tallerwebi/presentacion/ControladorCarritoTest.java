package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorCarritoTest {

    private ControladorUsuario controladorUsuario;
    private ControladorCarrito controladorCarrito;

    private Usuario usuarioMock;
    private Carrito carritoMock;
    private Pedido pedidoMock;
    private SupermercadoProducto supermercadoProductoMock;
    private Supermercado supermercadoMock;
    private Producto productoMock;
    private Promocion promocionMock;
    private TipoDePago tipoDePago;


    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioUsuario servicioUsuarioMock;
    private ServicioCarrito servicioCarritoMock;
    private ServicioPedido servicioPedidoMock;
    private ServicioSupermercadoProducto servicioSupermercadoProductoMock;
    private ServicioPromocion servicioPromocionMock;


    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("jlopez@gmail.com", "abcd1234");
        usuarioMock = mock(Usuario.class);
        carritoMock = mock(Carrito.class);
        pedidoMock = mock(Pedido.class);
        supermercadoProductoMock = mock(SupermercadoProducto.class);
        supermercadoMock = mock(Supermercado.class);
        productoMock = mock(Producto.class);
        promocionMock = mock(Promocion.class);
        tipoDePago = mock(TipoDePago.class);


        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        servicioCarritoMock = mock(ServicioCarritoImpl.class);
        servicioPedidoMock = mock(ServicioPedidoImpl.class);
        servicioSupermercadoProductoMock = mock(ServicioSupermercadoProductoImpl.class);
        servicioPromocionMock = mock(ServicioPromocionImpl.class);


        controladorUsuario = new ControladorUsuario(servicioUsuarioMock, mock(ServicioProducto.class)); // Adaptación del constructor
        controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioUsuarioMock, servicioPedidoMock, servicioSupermercadoProductoMock, servicioPromocionMock);
    }

    // Test para la pantalla de carrito
    @Test
    public void queAlSolicitarLaPantallaDeCarritoSeMuestreLaVistaCarritoCompras() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.verCarrito(requestMock);
        String viewName = modelAndView.getViewName();

        Map<String, Object> model = modelAndView.getModel();
        Boolean containsCarrito = model.containsKey("carrito");
        Boolean containscantidadProductos = model.containsKey("cantidadProductos");

        assertThat(viewName, equalToIgnoringCase("carritoCompras"));

        assertThat(String.valueOf(containsCarrito), true);
        assertThat(String.valueOf(containscantidadProductos), true);
    }

    @Test
    public void queAlSolicitarLaPantallaDeCarritoYNoEstaLogueadoSeRedirigeALogin() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.verCarrito(requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
    }


    // Test para elminar producto de carrito
    @Test
    public void queAlEliminarProductoRedirijaACarritoDeCompras() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(servicioSupermercadoProductoMock.consultarSupermercadoProducto(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado())).thenReturn(supermercadoProductoMock);
        when(servicioCarritoMock.consultarCarrito(usuarioMock.getStampCarritoActivo())).thenReturn(carritoMock);
        doNothing().when(this.servicioCarritoMock).modificar(carritoMock);

        ModelAndView modelAndView = controladorCarrito.eliminarDelCarrito(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/carritoCompras"));
    }


    // Test para agregar producto al carrito
    @Test
    public void queAlAgregarProductoRedirijaACarritoDeCompras() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/carritoCompras"));
    }

    @Test
    public void queAlAgregarProductoYNoEstaLogueadoRedirijaALogin() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
    }


    // Test para agregar promocion al carrito
    @Test
    public void queAlAgregarPromocionRedirijaACarritoDeCompras() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarritoPromocion(promocionMock.getIdPromocion(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/carritoCompras"));
    }

    @Test
    public void queAlAgregarPromocionYNoEstaLogueadoRedirijaALogin() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarritoPromocion(promocionMock.getIdPromocion(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
    }


    // Test para limpiar carrito
    @Test
    public void queAlLimpiarCarritoRedirjaAHome() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.limpiarCarrito(requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }


    // Test para guardar carrito
    @Test
    public void queAlGuardarCarritoRedirjaAHome() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.guardarCarrito(requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }


    // Test para reutilizar carrito
    @Test
    public void queAlReutilizarCarritoRedirjaACarritoDeCompras() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        servicioCarritoMock.registrar(carritoMock);
        when(servicioCarritoMock.consultarCarritoPorId(carritoMock.getId())).thenReturn(carritoMock);
        usuarioMock.setStampCarritoActivo(carritoMock.getFechaDeCreacion());

        ModelAndView modelAndView = controladorCarrito.reutilizarCarrito(carritoMock.getId(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/carritoCompras"));
    }


    // Test para eliminar carrito
    @Test
    public void queAlEliminarCarritoRedirjaAHome() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.eliminarCarrito(carritoMock.getId(), requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }


    // Test para generar pedido
    @Test
    public void queAlGenerarPedidoRedirjaAMiCuenta() {
        Date date = new Date();
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getStampCarritoActivo()).thenReturn(date);
        when(servicioCarritoMock.consultarCarrito(date)).thenReturn(carritoMock);
        doNothing().when(this.servicioPedidoMock).registrar(pedidoMock);
        doNothing().when(this.servicioUsuarioMock).modificarPedidoCarrito(usuarioMock);
        doNothing().when(this.servicioUsuarioMock).modificar(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.generarPedido(tipoDePago, requestMock);
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/mi-cuenta"));
    }


    // Test para verificar comportamiento al generar pedido//
    @Test
    public void queAlGenerarPedidoCreeUnNuevoPedido() {
        Date date = new Date();
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getStampCarritoActivo()).thenReturn(date);
        when(servicioCarritoMock.consultarCarrito(date)).thenReturn(carritoMock);

        controladorCarrito.generarPedido(tipoDePago, requestMock);

        verify(servicioPedidoMock, times(1)).registrar(any(Pedido.class));
        verify(servicioUsuarioMock, times(1)).modificar(usuarioMock);
        verify(usuarioMock, times(1)).setStampCarritoActivo(null);
        verify(usuarioMock, times(1)).setGuardoCarrito(false);
    }


    // Test para verificar comportamiento al pagar
    @Test
    public void queAlSolicitarLaPantallaDeIrAPagarMuestreTipoDePago() {
        ModelAndView modelAndView = controladorCarrito.irAPagar();
        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("tipoDePago"));
    }

    @Test
    public void queAlSolicitarLaPantallaDePagarMuestrePago() {
        Date date = new Date();
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getStampCarritoActivo()).thenReturn(date);
        when(servicioCarritoMock.consultarCarrito(date)).thenReturn(carritoMock);

        ModelAndView modelAndView = controladorCarrito.pagar(tipoDePago, requestMock);
        String viewName = modelAndView.getViewName();

        Map<String, Object> model = modelAndView.getModel();
        Boolean containsTipoPago = model.containsKey("tipoPago");
        Boolean containsTotalPedido = model.containsKey("totalPedido");

        assertThat(String.valueOf(containsTipoPago), true);
        assertThat(String.valueOf(containsTotalPedido), true);
        assertThat(viewName, equalToIgnoringCase("pago"));

    }

}

