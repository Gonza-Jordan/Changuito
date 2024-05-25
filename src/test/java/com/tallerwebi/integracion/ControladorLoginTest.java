package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.DatosLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

    private Usuario usuarioMock;
    private ServicioLogin mockServicioLogin;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("jlopez@gmail.com");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockServicioLogin = mock(ServicioLogin.class);
    }


    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
        assertThat(modelAndView.getModel().get("datosLogin").toString(), containsString("com.tallerwebi.presentacion.DatosLogin"));
    }

    @Test
    public void debeRetornarLaPaginaNuevoUsuarioCuandoSeNavegaANuevoUsuario() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/nuevo-usuario"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
        assertThat(modelAndView.getModel().get("usuario").toString(), containsString("com.tallerwebi.dominio.Usuario"));
    }

    @Test
    public void debeRetornarLaPaginaMiCuentaCuandoSeNavegaAMiCuentaYEstaLogueado() throws Exception {

        Usuario usuario = new Usuario();

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuario")).thenReturn(usuario);

        this.mockMvc.perform(get("/mi-cuenta").sessionAttr("usuario", usuario))
                .andExpect(status().isOk())
                .andExpect(model().attribute("usuario", usuario))
                .andExpect(view().name("mi-cuenta"));
    }

    @Test
    public void debeRetornarLaPaginaHomeCuandoSeCierraSesion() throws Exception {

        Usuario usuario = new Usuario();

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuario")).thenReturn(usuario);

        session.removeAttribute("usuario");

        this.mockMvc.perform(get("/sign-out"))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaAMiCuentaYNoEstaLogueado() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/mi-cuenta"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }
}
