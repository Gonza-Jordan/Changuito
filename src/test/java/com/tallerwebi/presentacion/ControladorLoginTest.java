package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioLoginImpl;
import com.tallerwebi.dominio.Subcategoria;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ControladorLoginTest {

    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioLogin servicioLoginMock;

    private RedirectAttributes redirectMock;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("jlopez@gmail.com", "abcd1234");
        usuarioMock = mock(Usuario.class);
//		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLoginImpl.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);

        redirectMock = mock(RedirectAttributes.class);
        ;

    }


    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock, redirectMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioLoginMock, times(1)).registrar(usuarioMock);
    }

    @Test
    public void registrarmeSiUsuarioExisteDeberiaVolverAFormulario() throws UsuarioExistente {
        // preparacion
        doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock, redirectMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/nuevo-usuario"));
        //assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));

    }

    @Test
    public void errorEnRegistrarmeDeberiaVolverAFormulario() throws Exception {
        // preparacion
        doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock, redirectMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/nuevo-usuario"));
    }


}
