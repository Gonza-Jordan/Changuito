package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorAdministradorTest {

    private ControladorAdministrador controladorAdministrador;
    private ServicioAdministrador servicioAdministrador;
    private HttpServletRequest request;
    private HttpSession session;
    private Usuario usuario;
    private Supermercado supermercado;

    @BeforeEach
    public void init() {
        this.servicioAdministrador = mock(ServicioAdministrador.class);
        this.controladorAdministrador = new ControladorAdministrador(this.servicioAdministrador);
        this.usuario = mock(Usuario.class);
        this.supermercado = mock(Supermercado.class);
        this.supermercado.setIdSupermercado(1);
        this.usuario.setSupermercado(supermercado);

        when(supermercado.getIdSupermercado()).thenReturn(1);
        when(usuario.getSupermercado()).thenReturn(supermercado);

        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        session.setAttribute("usuario", usuario);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("usuario")).thenReturn(usuario);
        when(request.getSession().getAttribute("supermercado")).thenReturn(supermercado);


    }

    @Test
    public void queAlSolicitarLaPantallaDeAdministradorSeMuestreLaVistaAdministrador() {
        //Ejecucion
        ModelAndView modelAndView = controladorAdministrador.irAdministrador(request);
        String viewName = modelAndView.getViewName();

        //Validacion
        assertThat(viewName, equalToIgnoringCase("administrador"));
    }

    @Test
    public void queAlSolicitarLaPantallaDeCrearComboSeMuestreLaVistaDeCombo() {
        //Ejecucion
        ModelAndView modelAndView = this.controladorAdministrador.irACombo(request);
        String viewName = modelAndView.getViewName();

        //Validacion
        assertThat(viewName, equalToIgnoringCase("crearCombo"));
    }

    @Test
    public void queAlSolicitarLaPantallaDeCrearPaqueteSeMuestreLaVistaDePaquete() {
        //Ejecucion
        ModelAndView modelAndView = this.controladorAdministrador.irAPaquete(request);
        String viewName = modelAndView.getViewName();

        //Validacion
        assertThat(viewName, equalToIgnoringCase("crearPaquete"));
    }

    @Test
    public void queAlSolicitarLaPantallaDeActualizarPrecioYDescuentoSeMuestreLaVistaDeAsignarPrecioYDescuento() {
        //Ejecucion
        ModelAndView modelAndView = this.controladorAdministrador.irAAsignarPrecioYDescuento(request);
        String viewName = modelAndView.getViewName();

        //Validacion
        assertThat(viewName, equalToIgnoringCase("asignarPrecioYDescuento"));
    }

    @Test
    public void queAlIngresarALaPantallaDeComboSeMuestrenLosProductosDelSupermercado() {
        //Ejecucion
        ModelAndView mav = this.controladorAdministrador.irACombo(request);
        List<SupermercadoProducto> supermercadoProductos = (List<SupermercadoProducto>) mav.getModel().get("productos");

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("crearCombo"));
        assertThat(mav.getModel().get("productos"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is(nullValue()));
        assertThat(supermercadoProductos, notNullValue());
    }

    @Test
    public void queAlIngresarALaPantallaDePaqueteSeMuestrenLosProductosDelSupermercado() {
        //Ejecucion
        ModelAndView mav = this.controladorAdministrador.irAPaquete(request);
        List<SupermercadoProducto> supermercadoProductos = (List<SupermercadoProducto>) mav.getModel().get("productos");

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("crearPaquete"));
        assertThat(mav.getModel().get("productos"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is(nullValue()));
        assertThat(supermercadoProductos, notNullValue());
    }

    @Test
    public void queAlIngresarALaPantallaDeActualizarPrecioYDescuentoSeMuestrenLosProductosDelSupermercado() {
        //Ejecucion
        ModelAndView mav = this.controladorAdministrador.irAAsignarPrecioYDescuento(request);
        List<SupermercadoProducto> supermercadoProductos = (List<SupermercadoProducto>) mav.getModel().get("productos");

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("asignarPrecioYDescuento"));
        assertThat(mav.getModel().get("productos"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is(nullValue()));
        assertThat(supermercadoProductos, notNullValue());
    }


}
