package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaHome;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaProductoFiltrado;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class IniciarSesionE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaHome vistaHome;
    VistaLogin vistaLogin;
    Page page;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
//       browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        page = context.newPage();
        vistaHome = new VistaHome(page);
        vistaLogin = new VistaLogin(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDecirMICUENTAEnElHeader() {
        vistaHome.navegar();
        String texto = vistaHome.obtenerTextoDelHeader();
        assertThat("Mi Cuenta", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaIrALaVistaDeLoginCuandoSeSeleccionaLaOpcionMiCuentaEnElHeader() {
        vistaHome.navegar();
        vistaHome.darClickEnMiCuenta();
        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/login"));
    }

    @Test
    void cuandoSeEscribeUnMailYUnaContrasenaValidaYSeSeleccionaIniciarSesionDebeRedirigirAlHome() {
        vistaLogin.navegar();
        vistaLogin.escribirEMAIL("jlopez@gmail.com");
        vistaLogin.escribirClave("1234");
        vistaLogin.darClickEnIniciarSesion();

        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/home"));
    }

    @Test
    void cuandoSeIniciaSesionDebeCambiarElTextoDeMICUENTAAElNombreDeUsuario() {
        vistaLogin.navegar();
        vistaLogin.escribirEMAIL("jlopez@gmail.com");
        vistaLogin.escribirClave("1234");
        vistaLogin.darClickEnIniciarSesion();

        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/home"));

        String texto = vistaHome.obtenerTextoDelHeader();
        assertThat("Jose", equalToIgnoringCase(texto));

    }

    @Test
    void deberiaDarUnErrorAlCompletarConUnUsuarioNoExistenteEnElLoginAlSeleccionarIniciarSesion() {
        vistaLogin.navegar();
        vistaLogin.escribirEMAIL("usuarioNoExistente@gmail.com");
        vistaLogin.escribirClave("0000");
        vistaLogin.darClickEnIniciarSesion();

        String texto = vistaLogin.obtenerMensajeDeError();
        assertThat("Error Usuario o clave incorrecta", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaDarUnErrorAlCompletarConUnUsuarioExistentePeroConContrasenaEquivocadaEnElLoginAlSeleccionarIniciarSesion() {
        vistaLogin.navegar();
        vistaLogin.escribirEMAIL("jlopez@gmail.com");
        vistaLogin.escribirClave("contrasenaErronea");
        vistaLogin.darClickEnIniciarSesion();

        String texto = vistaLogin.obtenerMensajeDeError();
        assertThat("Error Usuario o clave incorrecta", equalToIgnoringCase(texto));
    }

    @Test
    void cuandoSeIniciaSesionYSeleccionoMiNombreDeCuentaMeRedirigeAMiCuenta() {
        vistaLogin.navegar();
        vistaLogin.escribirEMAIL("jlopez@gmail.com");
        vistaLogin.escribirClave("1234");
        vistaLogin.darClickEnIniciarSesion();
        assertThat(page.url(), containsStringIgnoringCase("/spring/home"));

        vistaHome.darClickEnMiCuenta();
        assertThat(page.url(), containsStringIgnoringCase("/spring/mi-cuenta"));
    }

}
