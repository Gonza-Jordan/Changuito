package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import com.tallerwebi.presentacion.ControladorCarritoCompras;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorCarritoComprasTest {

    private ControladorCarritoCompras controladorCarritoCompras;

    @BeforeEach
    public void init() {
        this.controladorCarritoCompras = new ControladorCarritoCompras();
    }

    @Test
    public void queAlHacerClickEnManzanasSeMuestreLaVistaCategoriaConElNombreManzanas() {

        String nombreDelProducto = "Manzanas";

        ModelAndView mav = this.controladorCarritoCompras.verCarrito();
        String viewName = mav.getViewName();
        List<Producto> productos = (List<Producto>) mav.getModel().get("carrito");

        assertThat(viewName, equalToIgnoringCase("carritoCompras"));
        assertThat(productos.get(0).getNombre(), equalTo(nombreDelProducto));
    }

    @Test
    public void queAlEliminarUnProductoDelCarritoSeRedireccioneAlCarrito() {

        String codigoBarras = "123456789012";
        String redirection = this.controladorCarritoCompras.eliminarDelCarrito(codigoBarras);
        assertThat(redirection, equalToIgnoringCase("redirect:/carritoCompras"));
    }

    @Test
    public void queAlIngresarAlCarritoSeMuestreLaCantidadDeProductosCorrecta() {

        ModelAndView mav = this.controladorCarritoCompras.verCarrito();
        int cantidadProductos = (int) mav.getModel().get("cantidadProductos");

        assertThat(cantidadProductos, equalTo(3)); // Cantidad correcta
    }
}
