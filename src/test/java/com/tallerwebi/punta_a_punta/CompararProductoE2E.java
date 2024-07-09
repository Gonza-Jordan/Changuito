package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaCategoria;
import com.tallerwebi.punta_a_punta.vistas.VistaHome;
import com.tallerwebi.punta_a_punta.vistas.VistaProductoFiltrado;
import com.tallerwebi.punta_a_punta.vistas.VistaProductoSeleccionado;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class CompararProductoE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaHome vistaHome;
    VistaCategoria vistaCategoria;
    VistaProductoFiltrado vistaProductoFiltrado;
    VistaProductoSeleccionado vistaProductoSeleccionado;
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
        vistaCategoria = new VistaCategoria(page);
        vistaProductoFiltrado = new VistaProductoFiltrado(page);
        vistaProductoSeleccionado = new VistaProductoSeleccionado(page);

    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }


    @Test
    void deberiaDecirALMACENEnElCarruselDelHome() {
        vistaHome.navegar();
        List<String> nombres = vistaHome.obtenerNombresDelCarrusel();

        assertThat(nombres, hasItem(containsStringIgnoringCase("Almacen")));
    }

    @Test
    void deberiaIrALaVistaDeCategoriaAlmacenCuandoSeSeleccionaLaCategoriaAlmacen() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();

        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/categoria"));
        assertThat(url, containsStringIgnoringCase("Almacen"));
    }

    @Test
    void deberiaDecirACEITEEnElCarruselDeCategoriaAlmacen() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        List<String> nombres = vistaCategoria.obtenerNombresDelCarrusel();

        assertThat(nombres, hasItem(containsStringIgnoringCase("Aceite")));
    }

    @Test
    void deberiaIrALaVistaDeProductoFiltradoCuandoSeSeleccionaLaSubcategoriaAceite() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        vistaCategoria.darClickEnCategoriaAceite();

        String url = page.url();
        assertThat(url, containsStringIgnoringCase("/spring/productoFiltrado"));
    }

    @Test
    void cuandoSeSeleccionaLaSubcategoriaAceiteDebeIrALaPaginaDeProductoFiltradoConTodosLosProductosDeAceite() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        vistaCategoria.darClickEnCategoriaAceite();

        List<String> nombres = vistaProductoFiltrado.obtenerNombresDeLosProductos();
        assertThat(nombres, everyItem(containsStringIgnoringCase("Aceite")));
    }

    @Test
    void deberiaIrALaVistaDeDetalleProductoCuandoSeSeleccionaCOMPARAREnUnProducto() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        vistaCategoria.darClickEnCategoriaAceite();
        vistaProductoFiltrado.darClickEnElPrimerProducto();

        assertThat(page.url(), containsStringIgnoringCase("/spring/producto_seleccionado"));
    }

    @Test
    void deberiaMostrarElProductoACompararEnGrandeCuandoSeSeleccionaCOMPARAREnUnProducto() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        vistaCategoria.darClickEnCategoriaAceite();
        String nombreDelProducto = vistaProductoFiltrado.obtenerNombreDelPrimerProducto();
        vistaProductoFiltrado.darClickEnElPrimerProducto();
        String nombreDelProductoSeleccionado = vistaProductoSeleccionado.obtenerNombreDelProducto();

        assertThat(nombreDelProducto, equalToIgnoringCase(nombreDelProductoSeleccionado));

    }

    @Test
    void deberianMostrarseCincoProductosDeLaSubcategoriaAceiteCuandoSeComparaUno() {
        vistaHome.navegar();
        vistaHome.darClickEnCategoriaAlmacen();
        vistaCategoria.darClickEnCategoriaAceite();
        vistaProductoFiltrado.darClickEnElPrimerProducto();

        List<String> nombres = vistaProductoSeleccionado.obtenerNombresDeLosProductos();

        assertThat(nombres, everyItem(containsStringIgnoringCase("Aceite")));

    }


}
