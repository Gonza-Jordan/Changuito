package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ControladorUsuarioTest {

    private ControladorUsuario controladorUsuario;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioUsuario servicioUsuarioMock;
    private ServicioProducto servicioProductoMock;


    private RedirectAttributes redirectMock;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        datosLoginMock = mock(DatosLogin.class);
        usuarioMock = mock(Usuario.class);
//		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        servicioProductoMock = mock(ServicioProductoImpl.class);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock, servicioProductoMock);

        redirectMock = mock(RedirectAttributes.class);
        ;

    }

    //    test login
    @Test
    public void queAlSolicitarLaPantallaDeLoginSeMuestreLaVistaLogin() {

        ModelAndView modelAndView = controladorUsuario.irALogin();

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("login"));
    }


    //    test nuevo usuario
    @Test
    public void queAlSolicitarLaPantallaDeNuevoUsuarioSeMuestreLaVistaNuevoUsuario() {

        ModelAndView modelAndView = controladorUsuario.nuevoUsuario();

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("nuevoUsuario"));
    }


    //    test mi cuenta
    @Test
    public void queAlSolicitarLaPantallaMiCuentaYNoEstaLogueadoRedirijaALogin() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.irMiCuenta(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void queAlSolicitarLaPantallaMiCuentaSeMuestreLaVistaMiCuenta() {

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(servicioUsuarioMock.consultarUsuario(usuarioMock.getEmail())).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorUsuario.irMiCuenta(requestMock);

        String viewName = modelAndView.getViewName();

        assertThat(viewName, equalToIgnoringCase("miCuenta"));
    }


    //    test cerrar sesion
    @Test
    public void queAlCerrarSesionRedirijaAHome() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        doNothing().when(sessionMock).removeAttribute("usuario");

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.cerrarSession(requestMock);
        String viewName = modelAndView.getViewName();

        // validacion
        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }


    //    test validar login
    @Test
    public void queAlNoPoderValidarLoginRedirijaALogin() {
        when(servicioUsuarioMock.validarContrasena(datosLoginMock.getEmail(), datosLoginMock.getContrasena())).thenReturn(null);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addFlashAttribute("error", "Usuario o clave incorrecta");

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.validarLogin(datosLoginMock, requestMock, redirectMock);
        String viewName = modelAndView.getViewName();

        // validacion
        assertThat(viewName, equalToIgnoringCase("redirect:/login"));
        assertThat((String) redirectAttributes.getFlashAttributes().get("error"), equalToIgnoringCase("Usuario o clave incorrecta"));
    }

    @Test
    public void queAlValidarLoginRedirijaAHome() {
        when(requestMock.getSession(true)).thenReturn(sessionMock);
        when(servicioUsuarioMock.validarContrasena(datosLoginMock.getEmail(), datosLoginMock.getContrasena())).thenReturn(usuarioMock);


        // ejecucion
        ModelAndView modelAndView = controladorUsuario.validarLogin(datosLoginMock, requestMock, redirectMock);
        String viewName = modelAndView.getViewName();

        // validacion
        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queAlValidarLoginConAdminRedirijaAdministrador() {
        when(requestMock.getSession(true)).thenReturn(sessionMock);
        when(servicioUsuarioMock.validarContrasena(datosLoginMock.getEmail(), datosLoginMock.getContrasena())).thenReturn(usuarioMock);
        when(usuarioMock.getAdmin()).thenReturn(true);

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.validarLogin(datosLoginMock, requestMock, redirectMock);
        String viewName = modelAndView.getViewName();

        // validacion
        assertThat(viewName, equalToIgnoringCase("redirect:/administrador"));
    }


    //    test registrarme
    @Test
    public void registrameSiUsuarioNoExisteDebeCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addFlashAttribute("register", "Usuario registrado correctamente");

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.registrarme(usuarioMock, redirectMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        assertThat((String) redirectAttributes.getFlashAttributes().get("register"), equalToIgnoringCase("Usuario registrado correctamente"));

    }


    @Test
    public void registrarmeSiUsuarioExisteDebeVolverAFormulario() throws UsuarioExistente {
        // preparacion
        doThrow(UsuarioExistente.class).when(servicioUsuarioMock).registrar(usuarioMock);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addFlashAttribute("error", "El usuario ya existe");

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.registrarme(usuarioMock, redirectMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/nuevo-usuario"));
        assertThat((String) redirectAttributes.getFlashAttributes().get("error"), equalToIgnoringCase("El usuario ya existe"));

    }


    @Test
    public void errorEnRegistrarmeDebeVolverAFormulario() throws Exception {
        // preparacion
        doThrow(RuntimeException.class).when(servicioUsuarioMock).registrar(usuarioMock);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addFlashAttribute("error", "Error al registrar el nuevo usuario");

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.registrarme(usuarioMock, redirectMock);


        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/nuevo-usuario"));
        assertThat((String) redirectAttributes.getFlashAttributes().get("error"), equalToIgnoringCase("Error al registrar el nuevo usuario"));

    }


//    test modificar
    @Test
    public void queAlModificarUsuarioRedirjaAHome() {
        // preparacion
        doNothing().when(this.servicioUsuarioMock).modificar(usuarioMock);
        when(requestMock.getSession(true)).thenReturn(sessionMock);

        // ejecucion
        ModelAndView modelAndView = controladorUsuario.modificar(usuarioMock, requestMock);
        String viewName = modelAndView.getViewName();

        // validacion
        assertThat(viewName, equalToIgnoringCase("redirect:/home"));
    }


}
