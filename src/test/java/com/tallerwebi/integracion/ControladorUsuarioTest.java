package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.DatosLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorUsuarioTest {

    private Usuario usuarioMock;
    private ServicioUsuarioImpl mockServicioUsuario;
    private Carrito carritoMock;
    private SupermercadoProducto superProduMock;



    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        carritoMock = mock(Carrito.class);
        superProduMock = mock(SupermercadoProducto.class);


        //when(usuarioMock.getEmail()).thenReturn("jlopez@gmail.com");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockServicioUsuario = mock(ServicioUsuarioImpl.class);

    }

    //    /login
    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {

        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("datosLogin"))
                .andExpect(view().name("login"));
    }

    //    /nuevo-usuario
    @Test
    public void debeRetornarLaPaginaNuevoUsuarioCuandoSeNavegaANuevoUsuario() throws Exception {

        this.mockMvc.perform(get("/nuevo-usuario"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usuario"))
                .andExpect(view().name("nuevo-usuario"));

    }

    //    /nuevo-usuario
//    @Test
//    public void debeRetornarLaPaginaMiCuentaCuandoSeNavegaAMiCuentaYEstaLogueado() throws Exception {
//
//        List<Carrito> carritoList=new ArrayList<>();
//        List<SupermercadoProducto> superProduList=new ArrayList<>();
//
//        SupermercadoProducto superProdu=new SupermercadoProducto();
//        Supermercado supermercado=new Supermercado();
//        Producto produ=new Producto();
//        superProdu.setProducto(produ);
//        superProdu.setSupermercado(supermercado);
//        Carrito carrito=new Carrito();
//
//
//        superProduList.add(superProdu);
//        carrito.setSupermercadoProducto(superProduList);
//        carritoList.add(carrito);
//
//
//        Usuario usuario3 = new Usuario();
//        usuario3.setContrasena("abcd1234");
//        usuario3.setEmail("jlopez@gmail.com");
//        usuario3.setCarritos(carritoList);
//        mockServicioUsuario.registrar(usuario3);
//
//
//        this.mockMvc.perform(get("/mi-cuenta")
//                        .sessionAttr("usuario", usuario3))
//                .andExpect(status().isOk())
//                .andExpect(view().name("mi-cuenta"));
//    }

    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaAMiCuentaYNoEstaLogueado() throws Exception {

        this.mockMvc.perform(get("/mi-cuenta"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(0))
                .andExpect(redirectedUrl("/login"));
    }

    //    /sign-out
    @Test
    public void debeRetornarLaPaginaHomeCuandoSeCierraSesionYSeDebeHaberEliminadoLaSesion() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/sign-out")
                        .sessionAttr("usuario", this.usuarioMock))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        assert (usuario == null);
    }

    //    /validar-login
    @Test
    public void debeRetornarLaPaginaLoginYNoSettearLaSesionLuegoDeIniciarSesionInorrectamente() throws Exception {

        this.mockMvc.perform(post("/validar-login")
                        .param("email", "a@a.com")
                        .param("contrasena", "123")
                        .flashAttr("error", "Usuario o clave incorrecta"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("error", "Usuario o clave incorrecta"));
    }

    @Test
    public void debeRetornarLaPaginaLoginYSettearLaSesionLuegoDeIniciarSesionCorrectamente() throws Exception {

        DatosLogin datosLogin = new DatosLogin();
        datosLogin.setEmail("jlopez@gmail.com");
        datosLogin.setContrasena("abcd1234");

        Usuario usuario = new Usuario();
        usuario.setContrasena("abcd1234");
        usuario.setEmail("jlopez@gmail.com");

        when(mockServicioUsuario.validarContrasena("jlopez@gmail.com", "abcd1234")).thenReturn(usuario);

//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        this.mockMvc.perform(post("/validar-login")
                        .param("email", "jlopez@gmail.com")
                        .param("contrasena", "abcd1234")
                        .session(new MockHttpSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));


    }


}
