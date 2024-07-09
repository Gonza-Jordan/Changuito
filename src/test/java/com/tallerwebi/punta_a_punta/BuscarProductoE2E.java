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

public class BuscarProductoE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaHome vistaHome;
    VistaProductoFiltrado vistaProductoFiltrado;
    Page page;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
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
        vistaProductoFiltrado = new VistaProductoFiltrado(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDecirTUCHANGUITOEnElNavbar() {
        vistaHome.navegar();
        String texto = vistaHome.obtenerTextoDeLaBarraDeNavegacion();
        assertThat("TU CHANGUITO", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaDecirBUSCAREnElBotonDelBuscador() {
        vistaHome.navegar();
        String texto = vistaHome.obtenerTextoDelBotonDelBuscador();
        assertThat("BUSCAR", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaIrALaVistaDeProductoFiltradoCuandoSeCompletaConAlgunaLetraEnElBuscador() {
        vistaHome.navegar();
        vistaHome.escribirPalabraABuscar("A");
        vistaHome.darClickEnBuscar();
        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/productoFiltrado"));
    }

    @Test
    void cuandoSeEscribeLaPalabraARROZEnElBuscadorDebeIrALaPaginaDeProductoFiltradoConTodosLosProductosDeArroz() {
        vistaHome.navegar();
        vistaHome.escribirPalabraABuscar("Arroz");
        vistaHome.darClickEnBuscar();
        List<String> nombres = vistaProductoFiltrado.obtenerNombresDeLosProductos();

        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/productoFiltrado"));
        assertThat(nombres, everyItem(containsStringIgnoringCase("Arroz")));
    }

    @Test
    void deberiaDecirPRODUCTOBUSCADOARROZEnElTitulo() {
        vistaHome.navegar();
        vistaHome.escribirPalabraABuscar("Arroz");
        vistaHome.darClickEnBuscar();

        String texto = vistaProductoFiltrado.obtenerTextoDelTitulo();
        assertThat("Producto buscado: Arroz", equalToIgnoringCase(texto));
    }

    @Test
    void cuandoSeEscribeUnNombreDeUnProductoQueNoExisteEnElBuscadorDebeIrALaPaginaDeProductoFiltradoYMostrarElMensajeProductoNoEncontrado() {
        vistaHome.navegar();
        vistaHome.escribirPalabraABuscar("zktrrdsqkwdncmkdjfk");
        vistaHome.darClickEnBuscar();
        List<String> nombres = vistaProductoFiltrado.obtenerNombresDeLosProductos();
        String texto = vistaProductoFiltrado.obtenerTextoDeError();

        assertThat(nombres, empty());
        assertThat(">> Productos no encontrados", equalToIgnoringCase(texto));
    }

}
