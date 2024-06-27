package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioUsuario servicioUsuarioMock;
    private ServicioCarrito servicioCarritoMock;
    private ServicioPedido servicioPedidoMock;
    private ServicioSupermercadoProducto servicioSupermercadoProductoMock;


    private RedirectAttributes redirectMock;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("jlopez@gmail.com", "abcd1234");
        usuarioMock = mock(Usuario.class);
        carritoMock = mock(Carrito.class);
        pedidoMock = mock(Pedido.class);
        supermercadoProductoMock = mock(SupermercadoProducto.class);
        supermercadoMock = mock(Supermercado.class);
        productoMock = mock(Producto.class);


        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);


        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        servicioCarritoMock = mock(ServicioCarritoImpl.class);
        servicioPedidoMock = mock(ServicioPedidoImpl.class);
        servicioSupermercadoProductoMock = mock(ServicioSupermercadoProductoImpl.class);


        controladorUsuario = new ControladorUsuario(servicioUsuarioMock);
        controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioUsuarioMock, servicioPedidoMock, servicioSupermercadoProductoMock);

        redirectMock = mock(RedirectAttributes.class);
        ;

    }

    //    CARRITO DE COMPRAS
    @Test
    public void queAlSolicitarLaPantallaDeCarritoSeMuestreLaVistaCarritoCompras() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.verCarrito(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("carritoCompras"));
    }

    @Test
    public void queAlSolicitarLaPantallaDeCarritoYNoEstaLogueadoSeRedirigeALogin() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.verCarrito(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void queAlSolicitarLaPantallaDeCarritoMuestreCarrito() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        //when(servicioCarritoMock.consultarCarrito(usuarioMock.getStampCarritoActivo())).thenReturn(carritoMock);

        ModelAndView modelAndView = controladorCarrito.verCarrito(requestMock);

        Map<String, Object> model = modelAndView.getModel();
        Boolean containsCarrito = model.containsKey("carrito");
        Boolean containscantidadProductos = model.containsKey("cantidadProductos");


        assertThat(String.valueOf(containsCarrito), true);
        assertThat(String.valueOf(containscantidadProductos), true);
    }

    //    AGREGAR AL CARRITO
    @Test
    public void queAlAgregarProductoRedirijaACarritoDeCompras() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        //when(servicioSupermercadoProductoMock.consultarSupermercadoProducto(productoMock.getIdProducto(), supermercadoMock.getIdSupermercado())).thenReturn(supermercadoProductoMock);

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

    //    LIMPIAR CARRITO
    @Test
    public void queAlLimpiarCarritoRedirjaAHome() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.limpiarCarrito(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }

//    GUARDAR CARRITO

    @Test
    public void queAlGuardarCarritoRedirjaAHome() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.guardarCarrito(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }

    //    REUTILIZAR CARRITO

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

    //    ELIMINAR CARRITO

    @Test
    public void queAlEliminarCarritoRedirjaAHome() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

//        when(servicioCarritoMock.consultarCarritoPorId(carritoMock.getId())).thenReturn(carritoMock);
//        servicioUsuarioMock.eliminarCarritoDeUsuario(usuarioMock,carritoMock);
//        when(servicioUsuarioMock.consultarUsuario(usuarioMock.getEmail())).thenReturn(usuarioMock);


        ModelAndView modelAndView = controladorCarrito.eliminarCarrito(carritoMock.getId(), requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }

//    GENERAR PEDIDO

    @Test
    public void queAlGenerarPedidoRedirjaAMiCuenta() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.generarPedido(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/mi-cuenta"));
    }


}
